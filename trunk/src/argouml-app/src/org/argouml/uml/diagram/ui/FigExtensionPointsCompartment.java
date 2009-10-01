// $Id: eclipse-argo-codetemplates.xml 11347 2006-10-26 22:37:44Z linus $
// Copyright (c) 2009 The Regents of the University of California. All
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

package org.argouml.uml.diagram.ui;

import java.awt.Rectangle;
import java.util.Collection;

import org.argouml.model.Model;
import org.argouml.notation.NotationProviderFactory2;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.DiagramSettings;
import org.argouml.uml.diagram.use_case.ui.FigExtensionPoint;

/**
 * The compartment that contains extension points.
 *
 * @author michiel
 */
public class FigExtensionPointsCompartment extends FigEditableCompartment {

    /**
     * The constructor.
     * 
     * @param owner owning UML element
     * @param bounds position and size
     * @param settings render settings
     */
    public FigExtensionPointsCompartment(Object owner, Rectangle bounds,
            DiagramSettings settings) {
        super(owner, bounds, settings);
        super.populate();
    }

    @Override
    FigSingleLineTextWithNotation createFigText(Object owner, Rectangle bounds,
            DiagramSettings settings) {
        return new FigExtensionPoint(owner, bounds, settings);
    }

    @Override
    protected int getNotationType() {
        return NotationProviderFactory2.TYPE_EXTENSION_POINT;
    }

    @Override
    protected Collection getUmlCollection() {
        Object usecase = getOwner(); //TODO: check!
        return Model.getFacade().getExtensionPoints(usecase);
    }

    @Override
    protected void createModelElement() {
        Object usecase = getGroup().getOwner(); //TODO: check!
        Object ep = Model.getUseCasesFactory().buildExtensionPoint(usecase);
        TargetManager.getInstance().setTarget(ep);
    }

    @Override
    public String getName() {
         return "extension points";
    }

}
