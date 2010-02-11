/* $Id$
 *****************************************************************************
 * Copyright (c) 2009-2010 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    thn
 *    euluis
 *****************************************************************************
 *
 * Some portions of this file was previously release using the BSD License:
 */

// Copyright (c) 2007-2008 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.profile.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import junit.framework.TestCase;

import org.argouml.FileHelper;
import org.argouml.model.InitializeModel;
import org.argouml.model.UmlException;
import org.argouml.profile.Profile;
import org.argouml.profile.ProfileException;
import org.argouml.profile.ProfileManager;
import org.argouml.profile.ProfileMother;
import org.argouml.uml.cognitive.critics.ProfileGoodPractices;

/**
 * Tests for the ProfileManagerImpl class.
 *
 * @author Luis Sergio Oliveira (euluis)
 */
public class TestProfileManagerImpl extends TestCase {

    private ProfileManager manager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        InitializeModel.initializeDefault();
        manager = new ProfileManagerImpl();
    }

    /**
     * test profile manager 
     */
    public void testProfileManagerImpl() {
        List<Profile> registeredProfiles = manager.getRegisteredProfiles();
        assertTrue(1 <= registeredProfiles.size());
        Set<String> internalProfileNameSet = new HashSet<String>();
        for (Profile profile : registeredProfiles) {
            if (profile.getDisplayName().equals(ProfileUML.NAME_UML14)) {
                internalProfileNameSet.add(profile.getDisplayName());
            }
        }
        assertEquals(1, internalProfileNameSet.size());
    }

    /**
     * test remove profile
     */
    public void testRemoveProfileThatIsntDefault() {
        Profile gpProfile = manager.getProfileForClass(ProfileGoodPractices.class
                .getName());
        assertNotNull(gpProfile);
        assertTrue(manager.getRegisteredProfiles().contains(gpProfile));
        manager.removeProfile(gpProfile);
        assertFalse(manager.getRegisteredProfiles().contains(gpProfile));
    }

    /**
     * Test remove the base UML profile. 
     */
    public void testRemoveDefaultProfile() {
        Profile umlProfile = manager.getProfileForClass(ProfileUML.class
                .getName());
        assertNotNull(umlProfile);
        assertTrue(manager.getRegisteredProfiles().contains(umlProfile));
        assertTrue(manager.getDefaultProfiles().contains(umlProfile));
        manager.removeProfile(umlProfile);
        assertTrue(manager.getRegisteredProfiles().contains(umlProfile));
        assertTrue(manager.getDefaultProfiles().contains(umlProfile));
    }
    
    /**
     * Test register and remove a dummy profile.
     */
    public void testRegisterAndRemoveDummyProfile() {
        Profile testProfile = new Profile() {

            @Override
            public String getDisplayName() {
                return "Test Profile";
            }

            @Override
            public Collection getProfilePackages() throws ProfileException {
                return new Vector();
            }
            
        };
        
        manager.registerProfile(testProfile);        
        assertTrue(manager.getRegisteredProfiles().contains(testProfile));
        
        manager.addToDefaultProfiles(testProfile);                
        assertTrue(manager.getDefaultProfiles().contains(testProfile));
        
        manager.removeProfile(testProfile);
        assertFalse(manager.getRegisteredProfiles().contains(testProfile));
        assertFalse(manager.getDefaultProfiles().contains(testProfile));
    }
    
    /**
     * When loading profiles, check that XMI profile dependency resolution is
     * handled correctly when two user defined profiles are handed to
     * {@link ProfileManagerImpl#loadProfiles(List)} with the first being the
     * dependent profile of the second.
     * 
     * @throws IOException when file IO goes wrong...
     * @throws UmlException when UML manipulation goes wrong...
     */
    public void testXmiProfileDependencyResolutionWithProfilesHandedInReverseOrderOfDependencyInOneCall()
            throws IOException, UmlException {
        List<Profile> registeredProfiles = manager.getRegisteredProfiles();
        int numRegisteredBefore = registeredProfiles.size();
        ProfileMother mother = new ProfileMother();
        List<File> profileFiles =
            mother.createProfileFilePairWithSecondDependingOnFirstThroughXmi();
        Collections.reverse(profileFiles);
        
        ProfileManagerImpl managerImpl = (ProfileManagerImpl) manager;
        managerImpl.loadProfiles(profileFiles);
        
        List<Profile> registeredProfilesAfter =
            manager.getRegisteredProfiles();
        assertEquals("Now we should have two more registered profiles.",
            numRegisteredBefore + 2, registeredProfilesAfter.size());
    }

    /**
     * When loading profiles, check that XMI profile dependency resolution is
     * handled correctly when two user defined profiles are handed to
     * {@link ProfileManagerImpl#loadProfiles(List)} in two calls, with the
     * first call handing the dependent profile of the second and only on the
     * second call being handed the profile from which the first depends.
     * 
     * @throws IOException when file IO goes wrong...
     * @throws UmlException when UML manipulation goes wrong...
     */
    public void testXmiProfileDependencyResolutionWithProfilesHandedInReverseOrderOfDependencyInTwoCalls()
            throws IOException, UmlException {
        List<Profile> registeredProfiles = manager.getRegisteredProfiles();
        int numRegisteredBefore = registeredProfiles.size();
        ProfileMother mother = new ProfileMother();
        List<File> profileFiles =
            mother.createProfileFilePairWithSecondDependingOnFirstThroughXmi();
        
        File baseProfileFile = profileFiles.get(0);
        String baseProfileFileName = baseProfileFile.getName();
        File baseProfileDirectory = FileHelper.createTempDirectory(
            getClass().getCanonicalName());
        String newBaseProfileFileName = "new-base-profile.xmi";
        File newBaseProfileFile = new File(baseProfileDirectory,
            newBaseProfileFileName);
        assertTrue(baseProfileFile.renameTo(newBaseProfileFile));
        baseProfileFile = newBaseProfileFile;
        
        ProfileManagerImpl managerImpl = (ProfileManagerImpl) manager;
        
        File dependentProfileFile = profileFiles.get(1);
        replaceStringInFile(dependentProfileFile, baseProfileFileName,
            newBaseProfileFileName);
        ArrayList<File> dependentProfileList = new ArrayList<File>();
        dependentProfileList.add(dependentProfileFile);
        managerImpl.loadProfiles(dependentProfileList);
        assertEquals("We should have exaclty the same number of registered "
            + "profiles as in the begining.",
            numRegisteredBefore, manager.getRegisteredProfiles().size());
        
        ArrayList<File> baseProfileList = new ArrayList<File>();
        baseProfileList.add(baseProfileFile);
        managerImpl.loadProfiles(baseProfileList);
        
        assertEquals("Now we should have two more registered profiles.",
            numRegisteredBefore + 2, manager.getRegisteredProfiles().size());
    }

    private void replaceStringInFile(File file, String regex,
            String replacement) throws IOException {
        StringBuffer fileContents = new StringBuffer();
        BufferedReader reader = null;
        String fileContents2 = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            while (null != (line = reader.readLine())) {
                fileContents.append(line);
                fileContents.append("\n");
            }
            fileContents2 = fileContents.toString();
            fileContents2 = fileContents2.replaceAll(regex, replacement);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        if (fileContents2 != null && file.delete() && file.createNewFile()) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.append(fileContents2);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
}
