/* $Id$
 *****************************************************************************
 * Copyright (c) 2009 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfmorris
 *****************************************************************************
 *
 * Some portions of this file was previously release using the BSD License:
 */

// Copyright (c) 1996-2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
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

package org.argouml.uml.ui.foundation.core;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.argouml.i18n.Translator;
import org.argouml.uml.ui.UMLCheckBox2;
import org.argouml.uml.ui.UMLComboBox2;
import org.argouml.uml.ui.UMLMultiplicityPanel;
import org.argouml.uml.ui.UMLRadioButtonPanel;

/**
 * @since Nov 6, 2002
 * @author jaap.branderhorst@xs4all.nl
 */
public class PropPanelStructuralFeature extends PropPanelFeature {

    private JPanel multiplicityComboBox;
    private UMLComboBox2 typeComboBox;
    private UMLRadioButtonPanel changeabilityRadioButtonPanel;
    private UMLCheckBox2 targetScopeCheckBox;

    private static UMLStructuralFeatureTypeComboBoxModel typeComboBoxModel;

    /**
     * Constructor for PropPanelStructuralFeature.
     * @param name the name of the panel, to be shown at the top
     * @param icon the icon for the property panel
     */
    protected PropPanelStructuralFeature(String name, ImageIcon icon) {
        super(name, icon);
    }

    /**
     * Returns the multiplicityComboBox.
     * @return UMLMultiplicityComboBox2
     */
    public JPanel getMultiplicityComboBox() {
	if (multiplicityComboBox == null) {
	    multiplicityComboBox =
		new UMLMultiplicityPanel();
	}
	return multiplicityComboBox;
    }

    /**
     * Returns the typeComboBox.
     * @return UMLComboBox2
     */
    public UMLComboBox2 getTypeComboBox() {
        if (typeComboBox == null) {
	    if (typeComboBoxModel == null) {
		typeComboBoxModel =
		    new UMLStructuralFeatureTypeComboBoxModel();
	    }
            typeComboBox =
		new UMLComboBox2(
				 typeComboBoxModel,
				 ActionSetStructuralFeatureType.getInstance());
	}
	return typeComboBox;
    }

    /**
     * Returns the changeabilityRadioButtonPanel.
     * @return UMLRadioButtonPanel
     */
    public UMLRadioButtonPanel getChangeabilityRadioButtonPanel() {
        if (changeabilityRadioButtonPanel == null) {
            changeabilityRadioButtonPanel =
                new UMLStructuralFeatureChangeabilityRadioButtonPanel(
                        Translator.localize("label.changeability"),
                        true);
        }
	return changeabilityRadioButtonPanel;
    }

    /**
     * Returns the targetScopeCheckBox.
     * 
     * @return UMLCheckBox2
     * @deprecated for 0.27.2 by tfmorris. StructuralFeatures no longer have a
     *             targetScope in UML 2.x. No replacement. This appears unused,
     *             so it can be scheduled for a speedy removal.
     */
    @Deprecated
    public UMLCheckBox2 getTargetScopeCheckBox() {
        if (targetScopeCheckBox == null) {
	    targetScopeCheckBox = new UMLStructuralFeatureTargetScopeCheckBox();
        }
        return targetScopeCheckBox;
    }

}
