/* $Id: $
 *****************************************************************************
 * Copyright (c) 2010-2011 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bob Tarling
 *****************************************************************************
 */
package org.argouml.activity2.diagram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.argouml.uml.diagram.DiagramSettings;
import org.tigris.gef.presentation.FigRect;

class RectDisplayState extends BaseDisplayState
        implements StereotypeDisplayer, NameDisplayer {

    private static final int PADDING = 8;
    
    public RectDisplayState(
            final Rectangle rect, Color lineColor,
            Color fillColor, Object modelElement, DiagramSettings settings) {
        super(rect, lineColor,
                fillColor, modelElement, settings);
        createBigPort(rect, lineColor, fillColor);
    }

    @Override
    public Dimension getMinimumSize() {
        final Dimension stereoDim = getStereotypeDisplay().getMinimumSize();
        final Dimension nameDim = getNameDisplay().getMinimumSize();

        int w = Math.max(stereoDim.width, nameDim.width) + PADDING * 2;
        /* The stereoDim has height=2, even if it is empty, 
         * hence the -2 below: */
        final int h = stereoDim.height - 2 + nameDim.height + PADDING;
        w = Math.max(w, h + 44); // the width needs to be > the height
        return new Dimension(w, h);
    }
    
    
    
    DiagramElement createBigPort(
            final Rectangle rect,
            final Color lineColor,
            final Color fillColor) {
        return new Rect(
                rect.x, rect.y, rect.width, rect.height, lineColor, fillColor);
    }
    
    
    private class Rect extends FigRect implements DiagramElement {
        Rect(
                final int x, 
                final int y, 
                final int w, 
                final int h, 
                final Color lineColor, 
                final Color fillColor) {
            super(x, y, w, h, lineColor, fillColor);
        }
    }
}
