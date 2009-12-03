// $Id$
// Copyright (c) 2008-2009 The Regents of the University of California. All
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

package org.argouml.uml.diagram.static_structure.ui;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;

import org.argouml.model.AddAssociationEvent;
import org.argouml.model.AssociationChangeEvent;
import org.argouml.model.AttributeChangeEvent;
import org.argouml.model.Model;
import org.argouml.model.RemoveAssociationEvent;
import org.argouml.model.UmlChangeEvent;
import org.argouml.ui.ArgoJMenu;
import org.argouml.uml.diagram.AttributesCompartmentContainer;
import org.argouml.uml.diagram.DiagramSettings;
import org.argouml.uml.diagram.ui.FigAttributesCompartment;
import org.argouml.uml.diagram.ui.FigCompartment;
import org.argouml.uml.ui.foundation.core.ActionAddAttribute;

/**
 * A Fig for a ClassifierBox that adds an attributes compartment.
 *
 * @author Michiel
 */
public abstract class FigClassifierBoxWithAttributes extends FigClassifierBox 
    implements AttributesCompartmentContainer {
    
    /**
     * Construct a Fig with owner, bounds, and settings.
     * 
     * @param owner the model element that owns this fig
     * @param bounds the rectangle defining the bounds
     * @param settings the rendering settings
     */
    public FigClassifierBoxWithAttributes(Object owner, Rectangle bounds,
            DiagramSettings settings) {
        super(owner, bounds, settings);
    }

    /**
     * USED BY PGML.tee.
     * @return the class name and bounds together with compartment
     * visibility.
     */
    @Override
    public String classNameAndBounds() {
        return super.classNameAndBounds()
            + "attributesVisible=" + isAttributesVisible() + ";";
    }

    @Override
    protected void updateListeners(Object oldOwner, Object newOwner) {
        Set<Object[]> listeners = new HashSet<Object[]>();

        // Collect the set of model elements that we want to listen to
        if (newOwner != null) {
            // TODO: Because we get called on each and every change event, when
            // the model is in a state of flux, we'll often get an
            // InvalidElementException before we finish this collection. The
            // only saving grace is that we're called SO many times that on the
            // last time, things should be stable again and we'll get a good set
            // of elements for the final update.  We need a better mechanism.
            
            // add the listeners to the newOwner
            listeners.add(new Object[] {newOwner, null});
            
            // and its stereotypes
            // TODO: Aren't stereotypes handled elsewhere?
            for (Object stereotype 
                    : Model.getFacade().getStereotypes(newOwner)) {
                listeners.add(new Object[] {stereotype, null});
            }

            // and its features
            for (Object feat : Model.getFacade().getFeatures(newOwner)) {
                listeners.add(new Object[] {feat, null});
                // and the stereotypes of its features
                for (Object stereotype 
                        : Model.getFacade().getStereotypes(feat)) {
                    listeners.add(new Object[] {stereotype, null});
                }
                // and the parameter of its operations
                if (Model.getFacade().isAOperation(feat)) {
                    for (Object param : Model.getFacade().getParameters(feat)) {
                        listeners.add(new Object[] {param, null});
                    }
                }
            }
        }
        
        // Update the listeners to match the desired set using the minimal
        // update facility
        updateElementListeners(listeners);
    }
    
    @Override
    public void renderingChanged() {
        super.renderingChanged();
        if (getOwner() != null) {
            // TODO: We shouldn't actually have to do all this work
            updateAttributes();
        }
    }
    
    /*
     * TODO: Based on my comments below, with that work done,
     * this method can be removed - Bob.
     */
    @Override
    protected void updateLayout(UmlChangeEvent event) {
        super.updateLayout(event);

        if (event instanceof AttributeChangeEvent) {
            Object source = event.getSource();
            if (Model.getFacade().isAAttribute(source)) {
                // TODO: We just need to get someone to rerender a single line
                // of text which represents the element here, but I'm not sure
                // how to do that. - tfm
                // TODO: Bob replies - we shouldn't be interested in this event
                // here. The FigFeature (or its notation) should be listen for
                // change and the FigFeature should be update from that.
                updateAttributes();
            }
        } else if (event instanceof AssociationChangeEvent 
                && getOwner().equals(event.getSource())) {
            Object o = null;
            if (event instanceof AddAssociationEvent) {
                o = event.getNewValue();
            } else if (event instanceof RemoveAssociationEvent) {
                o = event.getOldValue();
            }
            if (Model.getFacade().isAAttribute(o)) {
                // TODO: Bob says - we should not be listening here for
                // addition and removal of attributes. This should be done in
                // FigAttributesCompartment.
                updateAttributes();
            }
        }
    }
    
    protected void updateAttributes() {
        FigCompartment fc = getCompartment(Model.getMetaTypes().getAttribute());
        if (!fc.isVisible()) {
            return;
        }
        fc.populate();

        // TODO: make setBounds, calcBounds and updateBounds consistent
        setBounds(getBounds());
    }
}
