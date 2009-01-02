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

package org.argouml.uml.diagram.static_structure.ui;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import javax.swing.Action;
import org.apache.log4j.Logger;
import org.argouml.i18n.Translator;
import org.argouml.kernel.DelayedVChangeListener;
import org.argouml.model.Model;
import org.argouml.model.RemoveAssociationEvent;
import org.argouml.uml.CommentEdge;
import org.argouml.uml.diagram.ui.FigEdgeModelElement;
import org.argouml.uml.diagram.ui.FigNodeModelElement;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerPerspectiveMutable;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigNode;


/**
 * Class to display a UML note connection to a
 * annotated model element.<p>
 *
 * The owner of this fig is always CommentEdge
 *
 * @author Andreas Rueckert a_rueckert@gmx.net
 * @author jaap.branderhorst@xs4all.nl
 */
public class FigEdgeNote
    extends FigEdgeModelElement
    implements VetoableChangeListener,
	       DelayedVChangeListener,
	       MouseListener,
	       KeyListener,
	       PropertyChangeListener {

    private static final long serialVersionUID = 7210384676965727564L;

    private static final Logger LOG = Logger.getLogger(FigEdgeNote.class);

    private CommentEdge owner;
    
    private Object comment;
    private Object annotatedElement;

    /**
     * Construct a new note connection. Use the same layout as for
     * other edges.
     * @deprecated only for use by PGML parser
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public FigEdgeNote() {
        super();
        LOG.info("Constructing a FigEdgeNote");
        setBetweenNearestPoints(true);
        getFig().setDashed(true);
        allowRemoveFromDiagram(false);
    }

    /**
     * Constructor that hooks the Fig to a CommentEdge.
     *
     * TODO: What do we need to do about this constructor?  It's different
     * from all the rest.
     * 
     * @param commentEdge the CommentEdge
     * @param theLayer the layer (ignored)
     * @deprecated for 0.27.4 by tfmorris.  Need to define replacement...
     */
    @Deprecated
    public FigEdgeNote(Object commentEdge, Layer theLayer) {
        this();

        if (!(theLayer instanceof LayerPerspectiveMutable)) {
            throw new IllegalArgumentException(
                    "The layer must be a mutable perspective. Got "
                    + theLayer);
        }

        if (!(commentEdge instanceof CommentEdge)) {
            throw new IllegalArgumentException(
                    "The owner must be a CommentEdge. Got " + commentEdge);
        }

        Object fromNode = ((CommentEdge) commentEdge).getSource();
        if (!(Model.getFacade().isAModelElement(fromNode))) {
            throw new IllegalArgumentException(
                    "The given comment edge must start at a model element. "
                    + "Got " + fromNode);
        }

        Object toNode = ((CommentEdge) commentEdge).getDestination();
        if (!(Model.getFacade().isAModelElement(toNode))) {
            throw new IllegalArgumentException(
                    "The given comment edge must end at a model element. Got "
                    + toNode);
        }

        Fig destFig = theLayer.presentationFor(toNode);
        if (destFig instanceof FigEdgeModelElement) {
            destFig = ((FigEdgeModelElement) destFig).getEdgePort();
        }
        if (!(destFig instanceof FigNodeModelElement)) {
            throw new IllegalArgumentException(
                    "The given comment edge must end at a model element"
                    + " in the given layer.");
        }

        Fig sourceFig = theLayer.presentationFor(fromNode);
        if (sourceFig instanceof FigEdgeModelElement) {
            sourceFig = ((FigEdgeModelElement) sourceFig).getEdgePort();
        }
        if (!(sourceFig instanceof FigNodeModelElement)) {
            throw new IllegalArgumentException(
                    "The given comment edge must start at a model element "
                    + "in the given layer.");
        }

        setLayer(theLayer);
        setDestFigNode((FigNode) destFig);
        setDestPortFig(destFig);
        setSourceFigNode((FigNode) sourceFig);
        setSourcePortFig(sourceFig);
        computeRoute();

        setOwner(commentEdge);
    }


    /*
     * @see org.tigris.gef.presentation.FigEdge#setFig(org.tigris.gef.presentation.Fig)
     */
    @Override
    public void setFig(Fig f) {
        LOG.info("Setting the internal fig to " + f);
        super.setFig(f);
        getFig().setDashed(true);
        //throw new IllegalArgumentException();
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#canEdit(org.tigris.gef.presentation.Fig)
     */
    @Override
    protected boolean canEdit(Fig f) { return false; }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Translator.localize("misc.comment-edge");
    }

    /*
     * Listen for a RemoveAssociationEvent between the comment
     * and the annotated element. When recieved delete the CommentEdge
     * and this FigEdgeNote.
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#modelChanged(java.beans.PropertyChangeEvent)
     */
    @Override
    protected void modelChanged(PropertyChangeEvent e) {
        if (e instanceof RemoveAssociationEvent
                && e.getOldValue() == annotatedElement) {
            removeFromDiagram();
        }
    }
    
    /*
     * @see org.tigris.gef.presentation.Fig#getTipString(java.awt.event.MouseEvent)
     */
    @Override
    public String getTipString(MouseEvent me) {
        return "Comment Edge"; // TODO: get tip string from comment
    }

    
    /*
     * @see org.tigris.gef.presentation.Fig#setOwner(java.lang.Object)
     */
    @Deprecated
    public void setOwner(Object newOwner) {
        if (newOwner == null) {
            // hack to avoid loading problems since we cannot store
            // the whole model yet in XMI
            newOwner = new CommentEdge(comment, annotatedElement);
        }
        owner = (CommentEdge) newOwner;
    }

    /*
     * @see org.tigris.gef.presentation.Fig#getOwner()
     */
    @Override
    public Object getOwner() {
        if (owner == null) {
            // hack to avoid loading problems since we cannot store
            // the whole model yet in XMI
            owner = new CommentEdge();
        }
        return owner;
    }

    /**
     * Overrides the standard method to return null. A note edge
     * cannot have a stereotype.
     *
     * @return empty array of actions.
     */
    @Override
    protected final Action[] getApplyStereotypeActions() {
        return new Action[0];
    }

    /*
     * @see org.tigris.gef.presentation.Fig#postLoad()
     */
    @Override
    public void postLoad() {
        super.postLoad();
        // TODO: Why is a Fig modifying the underlying model?!?!
//        CommentEdge o = (CommentEdge) getOwner();
//        o.setDestination(getDestFigNode().getOwner());
//        o.setSource(getSourceFigNode().getOwner());
    }
    
    /**
     * generate the notation for the modelelement and stuff it into the text Fig
     */
    @Override
    protected void updateNameText() {
        return;
    }
    
    /**
     * generate the notation for the stereotype and stuff it into the text Fig
     */
    @Override
    protected void updateStereotypeText() {
        return;
    }
    
    /*
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#updateListeners(java.lang.Object)
     */
    @Override
    protected void updateListeners(Object oldOwner, Object newOwner) {
        // no listeners to update
    }
    
    /*
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent pve) {
        modelChanged(pve);
    }

    
    /*
     * @see org.tigris.gef.presentation.Fig#removeFromDiagram()
     */
    @Override
    public void removeFromDiagramImpl() {
        superRemoveFromDiagram();
    }
    

    /**
     * Returns the source of the edge. The source is the owner of the
     * node the edge travels from in a binary relationship. For
     * instance: for a classifierrole, this is the sender.
     * @return MModelElement
     */
    @Override
    protected Object getSource() {
        Object theOwner = getOwner();
        if (theOwner != null) {
            return ((CommentEdge) theOwner).getSource();
        }
        return null;
    }
    /**
     * Returns the destination of the edge. The destination is the
     * owner of the node the edge travels to in a binary
     * relationship. For instance: for a classifierrole, this is the
     * receiver.
     * @return Object
     */
    @Override
    protected Object getDestination() {
        Object theOwner = getOwner();
        if (theOwner != null) {
            return ((CommentEdge) theOwner).getDestination();
        }
        return null;
    }
    
    /*
     * @see org.tigris.gef.presentation.FigEdge#setDestFigNode(org.tigris.gef.presentation.FigNode)
     */
    @Override
    public void setDestFigNode(FigNode fn) {
        if (fn != null && Model.getFacade().isAComment(fn.getOwner())) {
            Object oldComment = comment;
            if (oldComment != null) {
                removeElementListener(oldComment);
            }
            comment = fn.getOwner();
            if (comment != null) {
                addElementListener(comment);
            }
            
            ((CommentEdge) getOwner()).setComment(comment);
        } else if (fn != null 
                && !Model.getFacade().isAComment(fn.getOwner())) {
            annotatedElement = fn.getOwner();
            ((CommentEdge) getOwner()).setAnnotatedElement(annotatedElement);
        }

        super.setDestFigNode(fn);
    }

    /*
     * @see org.tigris.gef.presentation.FigEdge#setSourceFigNode(org.tigris.gef.presentation.FigNode)
     */
    @Override
    public void setSourceFigNode(FigNode fn) {
        if (fn != null && Model.getFacade().isAComment(fn.getOwner())) {
            Object oldComment = comment;
            if (oldComment != null) {
                removeElementListener(oldComment);
            }
            comment = fn.getOwner();
            if (comment != null) {
                addElementListener(comment);
            }
            ((CommentEdge) getOwner()).setComment(comment);
        } else if (fn != null 
                && !Model.getFacade().isAComment(fn.getOwner())) {
            annotatedElement = fn.getOwner();
            ((CommentEdge) getOwner()).setAnnotatedElement(annotatedElement);
        }
        super.setSourceFigNode(fn);
    }
} 
