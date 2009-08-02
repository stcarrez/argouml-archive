// $Id: FigPermission.java 17045 2009-04-05 16:52:52Z mvw $
// Copyright (c) 1996-2009 The Regents of the University of California. All
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

package org.argouml.structure2.diagram;

import org.argouml.uml.diagram.DiagramSettings;

/**
 * The Fig for a permission, which is a form of dependency.
 * <p>
 * TODO: In UML 2.x, the import and access Permissions have become
 * PackageImports with public visibility and non-public visibility respectively.
 * (ArgoUML only supports the <<import>> Permission currently). The friend
 * Permission has been dropped. Also the type hierarchy has been reorganized so
 * that PackageImport is not a subtype of Dependency.
 * 
 * @author Markus Klink
 */
public class FigPermission2 extends FigDependency2 {
    
    /**
     * Construct a Fig.
     * 
     * @param owner owning UML element
     * @param settings render settings
     */
    public FigPermission2(Object owner, DiagramSettings settings) {
        super(owner, settings);
    }

}

