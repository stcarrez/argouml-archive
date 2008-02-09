// $Id$
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

package org.argouml.uml.diagram.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.AttributeChangeEvent;
import org.argouml.model.Model;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigText;

/**
 * The Fig for a Generalization.
 * 
 * @author abonner@ics.uci.edu, jaap.branderhorst@xs4all.nl
 */
public class FigGeneralization extends FigEdgeModelElement {

    /*
     * The serialVersionUID (generated by Eclipse)
     */
    private static final long serialVersionUID = 3983170503390943894L;

    /**
     * Text box for discriminator.
     */
    private FigText discriminator = new FigText(10, 30, 90, 20);

    private ArrowHeadTriangle endArrow;

    /**
     * The constructor.
     */
    public FigGeneralization() {
        // UML spec for Generalizations doesn't call for name or stereotype

	discriminator.setFilled(false);
	discriminator.setLineWidth(0);
	discriminator.setReturnAction(FigText.END_EDITING);
	discriminator.setTabAction(FigText.END_EDITING);
	addPathItem(discriminator, new PathConvPercent(this, 40, -10));

        endArrow = new ArrowHeadTriangle();
	endArrow.setFillColor(Color.white);
	setDestArrowHead(endArrow);
	setBetweenNearestPoints(true);

	if (getLayer() == null) {
	    setLayer(ProjectManager.getManager()
		     .getCurrentProject().getActiveDiagram().getLayer());
	}
    }

    /**
     * The constructor that hooks the Fig into the UML element.
     *
     * @param edge the UML element
     * @param lay the layer
     */
    public FigGeneralization(Object edge, Layer lay) {
	this();
	setLayer(lay);
	setOwner(edge);

    }

    /*
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#canEdit(org.tigris.gef.presentation.Fig)
     */
    protected boolean canEdit(Fig f) { return false; }

    /*
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#modelChanged(java.beans.PropertyChangeEvent)
     */
    @Override
    protected void modelChanged(PropertyChangeEvent e) {
        // Name & stereotypes get updated by superclass
        super.modelChanged(e);
        // Update the discriminator if it changed
        if (e instanceof AttributeChangeEvent
                && "discriminator".equals(e.getPropertyName())) {
            updateDiscriminatorText();
        }
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#updateListeners(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void updateListeners(Object oldOwner, Object newOwner) {
        if (oldOwner != null) {
            removeElementListener(oldOwner);
        }
        if (newOwner != null) {
            addElementListener(newOwner, 
                    new String[] {"remove", "discriminator"});
        }
    }

    /**
     * Updates the discriminator text. Called if the model is changed
     * and on construction time.
     */
    public void updateDiscriminatorText() {
  	Object generalization = getOwner();
  	if (generalization == null) {
	    return;
  	}
  	String disc = 
            (String) Model.getFacade().getDiscriminator(generalization);
  	if (disc == null) {
	    disc = "";
  	}
        Project p = getProject();
        if (p != null) {
            Font f = getProject().getProjectSettings().getFont(Font.PLAIN);
            discriminator.setFont(f);
        }
  	discriminator.setText(disc);
    }

    /*
     * @see org.tigris.gef.presentation.Fig#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        endArrow.setLineColor(getLineColor());
        super.paint(g);
    }

    /*
     * @see org.tigris.gef.presentation.Fig#setOwner(Object)
     */
    @Override
    public void setOwner(Object own) {
        super.setOwner(own);
        if (Model.getFacade().isAGeneralization(own)) {
            Object gen = own;	// MGeneralization
            Object subType =
        	Model.getFacade().getSpecific(gen); // GeneralizableElement
            Object superType =
        	Model.getFacade().getGeneral(gen); // GeneralizableElement
            // Due to errors in earlier releases of argouml it can
            // happen that there is a generalization without a child
            // or parent.
            // TODO: Move into XSL. We should not remove from the graph model
            // while we're writing to it or we have a possible cause of
            // concurrent modification exception.
            if (subType == null || superType == null) {
                // TODO: We should warn the user we have removed something - tfm
                removeFromDiagram();
        	return;
            }
            updateDiscriminatorText(); // show it
        } else if (own != null) {
            throw new IllegalStateException(
                    "FigGeneralization has an illegal owner of "
                    + own.getClass().getName());
        }
    }
}
