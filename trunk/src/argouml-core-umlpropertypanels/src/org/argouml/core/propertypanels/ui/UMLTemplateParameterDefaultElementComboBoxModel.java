// $Id: UMLStructuralFeatureTypeComboBoxModel.java 16339 2008-12-11 23:31:22Z tfmorris $
// Copyright (c) 1996-2008 The Regents of the University of California. All
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

package org.argouml.core.propertypanels.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Action;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.model.UmlChangeEvent;
import org.argouml.uml.ui.UMLComboBoxModel2;
import org.argouml.uml.util.PathComparator;

/**
 * The combobox model for the default element belonging to some TemplateParameter.
 */
public class UMLTemplateParameterDefaultElementComboBoxModel extends UMLComboBoxModel {

    /**
     * The class uid
     */
    private static final long serialVersionUID = -4042236776070635624L;

    /**
     * Constructor for UMLStructuralFeatureTypeComboBoxModel.
     */
    public UMLTemplateParameterDefaultElementComboBoxModel(
            final String propertyName,
            final Object target) {
        super(propertyName, true); // Allow null
        setTarget(target);
    }

    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel2#isValidElement(Object)
     */
    protected boolean isValidElement(Object element) {
        return Model.getFacade().isAModelElement(element);
    }

    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel2#buildModelList()
     */
    @SuppressWarnings("unchecked")
    protected void buildModelList() {
        Set<Object> elements = new TreeSet<Object>(new PathComparator());

        Project p = ProjectManager.getManager().getCurrentProject();
        if (p == null) {
            return;
        }
        
        for (Object model : p.getUserDefinedModelList()) {
            elements.addAll(Model.getModelManagementHelper()
                    .getAllModelElementsOfKind(
                            model, Model.getMetaTypes().getModelElement()));
        }

        elements.addAll(p.getProfileConfiguration().findByMetaType(
                        Model.getMetaTypes().getClassifier()));

        setElements(elements);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void buildMinimalModelList() {
        Collection list = new ArrayList(1);
        Object element = getSelectedModelElement();
        if (element != null) {
            list.add(element);
        }
        setElements(list);
    }
    
    @Override
    protected boolean isLazy() {
        return true;
    }
    
    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel2#getSelectedModelElement()
     */
    protected Object getSelectedModelElement() {
        Object o = null;
        if (getTarget() != null) {
            o = Model.getFacade().getDefaultElement(getTarget());
        }
        return o;
    }
    
    public Action getAction() {
        return null;
    }
}
