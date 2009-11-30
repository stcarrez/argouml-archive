// $Id$
// Copyright (c) 2003-2009 The Regents of the University of California. All
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

package org.argouml.core.propertypanels.ui;

import org.apache.log4j.Logger;
import org.argouml.model.Model;

/**
 *
 * @author mkl
 *
 */
class UMLTimeExpressionModel extends UMLExpressionModel {

    private static final Logger LOG =
        Logger.getLogger(UMLTimeExpressionModel.class);

    /**
     * The constructor.
     *
     */
    public UMLTimeExpressionModel(Object target) {
        super(target, "when");
    }

    /*
     * @see org.argouml.uml.ui.UMLExpressionModel2#getExpression()
     */
    public Object getExpression() {
        return Model.getFacade().getWhen(getTarget());
    }

    /*
     * @see org.argouml.uml.ui.UMLExpressionModel2#setExpression(java.lang.Object)
     */
    public void setExpression(Object expression) {
        Object target = getTarget();

        if (target == null) {
            throw new IllegalStateException("There is no target");
        }
        /* If we do not set it to null first, then we get a MDR DebugException: */
        Model.getStateMachinesHelper().setWhen(target, null);
        Model.getStateMachinesHelper().setWhen(target, expression);
    }

    /*
     * @see org.argouml.uml.ui.UMLExpressionModel2#newExpression()
     */
    public Object newExpression(String lang, String body) {
        LOG.debug("new time expression");
        return Model.getDataTypesFactory().createTimeExpression(lang, body);
    }

}
