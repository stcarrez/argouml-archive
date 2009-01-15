// $Id$
// Copyright (c) 2007-2009 The Regents of the University of California. All
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

package org.argouml.sequence2.diagram;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.argouml.model.Model;
import org.argouml.uml.diagram.ui.ArgoFigGroup;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigRect;


/**
 * This fig is the LifeLine of a ClassifierRole.
 * @author penyaskito
 */
class FigLifeLine extends ArgoFigGroup {

    private static final long serialVersionUID = 466925040550356L;

    private final FigLine lineFig;
    private final FigRect rectFig;
    
    private List<FigActivation> activations;
    private List<FigActivation> stackedActivations;
    
    static final int WIDTH = 150;
    static final int HEIGHT = 500;

    /**
     * Creates a FigLifeLine that starts in (x,y)
     * @param x The x coordinate of the FigLifeLine
     * @param y The y coordinate of the FigLifeLine
     */
    FigLifeLine(int x, int y) {
        super();

        activations = new LinkedList<FigActivation>();
        stackedActivations = new LinkedList<FigActivation>();
        
        rectFig = new FigRect(x, y, WIDTH, HEIGHT); 
        rectFig.setFilled(false);
        rectFig.setLineWidth(0);
        lineFig = new FigLine(x + WIDTH / 2, y, 
                x + WIDTH / 2, y + HEIGHT, LINE_COLOR);
        lineFig.setDashed(true);
        lineFig.setLineWidth(LINE_WIDTH);
        
        addFig(rectFig);
        addFig(lineFig);
    }
    
    // TODO: Does this still need to be synchronized? If so then explain why.
    synchronized void createActivations(final List<FigMessage> messages) {
        clearActivations();
        Collections.sort(messages, new FigMessageComparator());
        
        activations = createStandardActivations(messages);
        stackedActivations = createStackedActivations(messages);
        
        for (FigActivation figAct : activations) {
            addFig(figAct);
        }
        for (FigActivation figAct : stackedActivations) {
            addFig(figAct);
        }       
        calcBounds();
    }

    private List<FigActivation> createStandardActivations(
    		final List<FigMessage> figMessages) {        
	
        final List<FigActivation> newActivations =
            new LinkedList<FigActivation>();
	
        final FigClassifierRole cr =
            (FigClassifierRole) getGroup();                
        FigActivation currentAct = null;
        
        // Check here if there are no incoming call actions
        // if not then create an activation at the top of the lifeline
        if (!hasIncomingCallActions(figMessages) 
                && !hasOutgoingDestroyActions(figMessages)) {
            currentAct = 
                new FigActivation(lineFig.getX(), lineFig.getY(), false);
        }
        
        for (FigMessage figMessage : figMessages) {
            int ySender = 0;
            final Object action = figMessage.getAction();
            if (currentAct == null
                    && cr.equals(figMessage.getDestFigNode())
                    && !cr.equals(figMessage.getSourceFigNode())
                    && Model.getFacade().isACallAction(action)) {
                // if we are the dest and is a call action, create the 
                // activation, but don't add it until the height is set.
        	ySender = figMessage.getFinalY();        	
                currentAct = 
                    new FigActivation(lineFig.getX(), ySender, false); 
            } else if (currentAct == null
                    && cr.equals(figMessage.getDestFigNode())
                    && !cr.equals(figMessage.getSourceFigNode())
                    && Model.getFacade().isACreateAction(action)) {
                // if we are the dest of a create action, create the
                // entire activation, because we should need the destroy X
                currentAct = 
                    new FigActivation(lineFig.getX(), lineFig.getY(), false);
            } else if (currentAct != null
                    && cr.equals(figMessage.getSourceFigNode()) 
                    && !cr.equals(figMessage.getDestFigNode())
                    && Model.getFacade().isAReturnAction(action)) {
                // if we are the source of a return action
                // the figlifeline ends here.
        	ySender = figMessage.getStartY();
                currentAct.setHeight(ySender - currentAct.getY());
                newActivations.add(currentAct);
                currentAct = null;
            } else if (currentAct != null
                    && cr.equals(figMessage.getSourceFigNode())
                    && !cr.equals(figMessage.getDestFigNode())
                    && Model.getFacade().isADestroyAction(action)) {
                // if we are the source of a destroy actionm
                // the figlifeline ends here and we add the activation
        	ySender = figMessage.getFinalY();
                currentAct.setHeight(ySender - currentAct.getY());
                currentAct.setDestroy(true);
                this.setHeight(ySender - getY());
                newActivations.add(currentAct);
                currentAct = null;
            }
        }
        
        // If we have a currentAct object that means have reached the end
        // of the lifeline with a call or a create not returned.
        // Add the activation to the list after setting its height to end
        // at the end of the lifeline.
        if (currentAct != null) {
            currentAct.setHeight(getHeight() - (currentAct.getY() - getY()));
            newActivations.add(currentAct);
        }
        
        return newActivations;
    }
    
    private List<FigActivation> createStackedActivations(
	    final List<FigMessage> figMessages) {
	
	final List<FigActivation> newActivations =
	    new LinkedList<FigActivation>();
	
	FigActivation currentAct = null;
	
        for (FigMessage figMessage : figMessages) {
            int ySender = 0;
            // if we are the dest and is a call action, create the 
            // activation, but don't add it until the height is set.
            if (figMessage.isSelfMessage()) {
                if (figMessage.isCallAction()) {
                    ySender = figMessage.getFinalY();
                    currentAct = new FigActivation(
                            lineFig.getX() + FigActivation.DEFAULT_WIDTH / 2,
                            ySender,
                            false);
                } else if (currentAct != null
                        && figMessage.isReturnAction()) {
                    ySender = figMessage.getStartY();
                    currentAct.setHeight(ySender - currentAct.getY());
                    newActivations.add(currentAct);
                    currentAct = null;
                }
            }
        }
        return newActivations;
    }


    private boolean hasIncomingCallActions(
    		final List<FigMessage> figMessages) {
        final FigClassifierRole cr =
            (FigClassifierRole) getGroup();                
        for (FigMessage figMessage : figMessages) {
            if (cr.equals(figMessage.getDestFigNode())
                    && !cr.equals(figMessage.getSourceFigNode())
                    && (figMessage.isCallAction())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOutgoingDestroyActions(List<FigMessage> messages) {
        boolean found = false;
        final FigClassifierRole cr =
            (FigClassifierRole) getGroup();                
        for (FigMessage message : messages) {
            Object action = message.getAction();
            if (cr.equals(message.getSourceFigNode())                    
                    && !cr.equals(message.getDestFigNode())
                    && Model.getFacade().isADestroyAction(action)) {
                found = true;
                break;
            }
        }
        return found;
    }
    
    private void clearActivations() {
        for (FigActivation oldActivation : activations) {
            removeFig(oldActivation);    
        }
        for (FigActivation oldActivation : stackedActivations) {
            removeFig(oldActivation);    
        }
        activations.clear();
        stackedActivations.clear();
    }
    
    @Override
    public void setFilled(boolean filled) {
        // we do nothing. No call to the parent
    }
    
    @Override
    protected void setBoundsImpl(int x, int y, int w, int h) {
        final Rectangle oldBounds = getBounds();
        
        rectFig.setBounds(x, y, w, h);
        lineFig.setBounds(x + w / 2, y, w, h);
        
        final int yDiff = oldBounds.y - y;
    
        // we don't recalculate activations, just move them
        for (FigActivation act : activations) {
            // TODO: why do we need to remove then add the Fig?
            removeFig(act);
            act.setLocation(
                    lineFig.getX() - FigActivation.DEFAULT_WIDTH / 2,
                    act.getY() - yDiff);
            if (activations.size() == 1 
                    && act.getHeight() == oldBounds.height) {
                act.setHeight(getHeight());
            }
            addFig(act);
        }
        damage();        
        calcBounds();        
        firePropChange("bounds", oldBounds, getBounds());
    }
    
    public int getLineWidth() {
        return lineFig.getLineWidth();
    }
    
    public void setLineWidth(int w) {
        lineFig.setLineWidth(w);
    }
}
