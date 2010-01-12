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

// Copyright (c) 1996-2006 The Regents of the University of California. All
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

package org.argouml.uml.cognitive.critics;

import java.util.Collection;
import java.util.Iterator;

import org.argouml.cognitive.Designer;
import org.argouml.cognitive.ListSet;
import org.argouml.cognitive.ToDoItem;
import org.argouml.model.Model;
import org.argouml.uml.cognitive.UMLDecision;
import org.argouml.uml.cognitive.UMLToDoItem;
import org.argouml.uml.diagram.deployment.ui.FigComponent;
import org.argouml.uml.diagram.deployment.ui.FigMNode;
import org.argouml.uml.diagram.deployment.ui.UMLDeploymentDiagram;

/**
 * A critic to detect when there are components that
 * are not inside a node.
 *
 * @author 5eichler
 */
public class CrComponentWithoutNode extends CrUML {

    /**
     * The constructor.
     */
    public CrComponentWithoutNode() {
        setupHeadAndDesc();
	addSupportedDecision(UMLDecision.PATTERNS);
    }

    /*
     * @see org.argouml.uml.cognitive.critics.CrUML#predicate2(
     *      java.lang.Object, org.argouml.cognitive.Designer)
     */
    public boolean predicate2(Object dm, Designer dsgr) {
	if (!(dm instanceof UMLDeploymentDiagram)) return NO_PROBLEM;
	UMLDeploymentDiagram dd = (UMLDeploymentDiagram) dm;
	ListSet offs = computeOffenders(dd);
	if (offs == null) return NO_PROBLEM;
	return PROBLEM_FOUND;
    }

    /*
     * @see org.argouml.cognitive.critics.Critic#toDoItem( java.lang.Object,
     *      org.argouml.cognitive.Designer)
     */
    public ToDoItem toDoItem(Object dm, Designer dsgr) {
	UMLDeploymentDiagram dd = (UMLDeploymentDiagram) dm;
	ListSet offs = computeOffenders(dd);
	return new UMLToDoItem(this, offs, dsgr);
    }

    /*
     * @see org.argouml.cognitive.Poster#stillValid(
     *      org.argouml.cognitive.ToDoItem, org.argouml.cognitive.Designer)
     */
    public boolean stillValid(ToDoItem i, Designer dsgr) {
	if (!isActive()) return false;
	ListSet offs = i.getOffenders();
	UMLDeploymentDiagram dd = (UMLDeploymentDiagram) offs.get(0);
	//if (!predicate(dm, dsgr)) return false;
	ListSet newOffs = computeOffenders(dd);
	boolean res = offs.equals(newOffs);
	return res;
    }


    /**
     * If there are components that have no enclosing FigMNode
     * the returned ListSet is not null. Then in the ListSet
     * are the UMLDeploymentDiagram and all FigComponents with no
     * enclosing FigMNode
     *
     * @param dd the diagram to check
     * @return the set of offenders
     */
    public ListSet computeOffenders(UMLDeploymentDiagram dd) {

	Collection figs = dd.getLayer().getContents();
	ListSet offs = null;
	Iterator figIter = figs.iterator();
	boolean isNode = false;
	while (figIter.hasNext()) {
	    Object obj = figIter.next();
	    if (obj instanceof FigMNode) isNode = true;
	}
	figIter = figs.iterator();
	while (figIter.hasNext()) {
	    Object obj = figIter.next();
	    if (!(obj instanceof FigComponent)) continue;
	    FigComponent fc = (FigComponent) obj;
	    if ((fc.getEnclosingFig() == null) && isNode) {
		if (offs == null) {
		    offs = new ListSet();
		    offs.add(dd);
		}
		offs.add(fc);
	    } else if (fc.getEnclosingFig() != null
		     && (((Model.getFacade()
		             .getDeploymentLocations(fc.getOwner()) == null)
			 || (((Model.getFacade()
                                .getDeploymentLocations(fc.getOwner()).size())
			     == 0))))) {
		if (offs == null) {
		    offs = new ListSet();
		    offs.add(dd);
		}
		offs.add(fc);
	    }

	}

	return offs;
    }

}

