// $Id$
// Copyright (c) 2007 The Regents of the University of California. All
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

import org.argouml.uml.diagram.DiagramSettings;
import org.argouml.uml.diagram.ui.ArgoFigGroup;
import org.tigris.gef.presentation.FigRect;

/**
 * TODO: Document!
 * 
 * @author penyaskito
 */
class FigActivation extends ArgoFigGroup {
    
    static final int DEFAULT_HEIGHT = 40;
    static final int DEFAULT_WIDTH = 20;
    
    private FigRect rectFig;
    private FigDestroy destroyFig;

    
    /**
     * TODO: Document
     * 
     * @param x
     * @param y
     * @param destroy 
     * @deprecated for 0.28 by tfmorris.  
     */
    public FigActivation(int x, int y, boolean destroy) {
        super();
        initialize(new Rectangle(x, y, DEFAULT_HEIGHT, DEFAULT_WIDTH), destroy);
    }

    private void initialize(Rectangle bounds, boolean destroy) {
        if (bounds.width == 0) {
            bounds.width = DEFAULT_WIDTH;
        }
        if (bounds.height == 0) {
            bounds.height = DEFAULT_HEIGHT;
        }
        rectFig = new FigRect(bounds.x - bounds.width / 2, bounds.y,
                bounds.width, bounds.height, LINE_COLOR, FILL_COLOR);
        rectFig.setLineWidth(LINE_WIDTH);
        addFig(rectFig);
        setDestroy(destroy);
    }

    /**
     * Create a new default activation fig (ie one without a destroy fig at the
     * end).
     * 
     * @param owner owning UML element or null
     * @param bounds position (top center) and size. If the width or height is
     *            0, the default will be used.
     * @param settings rendering settings
     */
    public FigActivation(Object owner, Rectangle bounds,
            DiagramSettings settings) {
        this(owner, bounds, settings, false);
    }

    /**
     * Create a new activation fig which optionally ends with a destroy fig.
     * 
     * @param owner owning UML element or null
     * @param bounds position (top center) and size.  If the width or height is
     *            0, the default will be used.
     * @param settings rendering settings
     * @param destroy true if activation should end with a destroy fig.
     */
    public FigActivation(Object owner, Rectangle bounds,
            DiagramSettings settings, boolean destroy) {
        super(owner, settings);
        initialize(bounds, destroy);
    }
    
    /**     
     * @param isDestroy 
     */
    public void setDestroy (boolean isDestroy) {
        if (isDestroy) {
            if (destroyFig == null) {
                destroyFig = 
                    new FigDestroy(getX() + getWidth() / 2,
                            getY() + getHeight());
                addFig(destroyFig);                
            }
        }
        else {
            if (destroyFig != null) {
                removeFig(destroyFig);
            }
            destroyFig = null;
        }
    }
    
    /**
     * Checks if ends with a destroy message.
     * @return true if ends with a destroy message, false otherwise
     */
    public boolean isDestroy () {
        return destroyFig != null;
    }

}
