/* $Id: $
 *****************************************************************************
 * Copyright (c) 2009 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bob Tarling
 *****************************************************************************
 */

package org.argouml.core.propertypanels.ui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Action;

import org.apache.log4j.Logger;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.profile.Profile;
import org.argouml.profile.ProfileException;
import org.argouml.ui.UndoableAction;

class UMLComponentInstanceClassifierComboBoxModel
    extends  UMLComboBoxModel {
    
    private static final Logger LOG =
	Logger.getLogger("UMLComponentInstanceClassifierComboBoxModel");
    
    public UMLComponentInstanceClassifierComboBoxModel(
            final String propertyName,
            final Object target) {
        super(propertyName, true);
        setTarget(target);
    }
    
    /*
     * @see org.argouml.uml.ui.UMLModelElementListModel#buildModelList()
     */
    protected void buildModelList() {
        List list = new ArrayList();
        
        // Get all classifiers in our model
        // TODO: We need the property panels to have some reference to the
        // project they belong to instead of using deprecated functionality
        Project p = ProjectManager.getManager().getCurrentProject();
        Object model = p.getRoot();
        list.addAll(Model.getModelManagementHelper()
                .getAllModelElementsOfKindWithModel(model, Model.getMetaTypes().getComponent()));
        
        // Get all classifiers in all top level packages of all profiles
        for (Profile profile : p.getProfileConfiguration().getProfiles()) {
    	try {
		    for (Object topPackage : profile.getProfilePackages()) {
		            list.addAll(Model.getModelManagementHelper()
		                    .getAllModelElementsOfKindWithModel(topPackage,
		                	    Model.getMetaTypes().getComponent()));
		    }
		} catch (ProfileException e) {
		    // TODO: We need to rethrow this as some other exception
		    // type but that is too much change for the moment.
		    LOG.error("Exception", e);
		}
        }
        setElements(list);
    }
    
    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel#isValidElement(Object)
     */
    protected boolean isValidElement(Object element) {
        return Model.getFacade().isAClassifier(element)
            && Model.getFacade().getRepresentedClassifier(getTarget()) 
                == element;
    }
    
    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel#getSelectedModelElement()
     */
    protected Object getSelectedModelElement() {
        Collection list = Model.getFacade().getClassifiers(getTarget());
        if (list.size() == 0) {
            return null;
        }
        return list.iterator().next();
    }
    
    public Action getAction() {
        return new ActionSet();
    }
    
    private class ActionSet extends UndoableAction {

        ActionSet() {
            super(Translator.localize("action.set"),
                    ResourceLoaderWrapper.lookupIcon("action.set"));
        }

        public void actionPerformed(ActionEvent e) {
            super.actionPerformed(e);
            UMLComboBox source = (UMLComboBox) e.getSource();
            Object target = source.getTarget();
            Object newValue = source.getSelectedItem();
            List classifiers = new ArrayList(1);
            if (Model.getFacade().isAClassifier(newValue)) {
                classifiers.add(newValue);
            }
   	    Model.getCommonBehaviorHelper().setClassifiers(getTarget(), classifiers);
        }

    }
}

