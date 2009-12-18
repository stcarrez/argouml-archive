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

package org.argouml.core.propertypanels.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.argouml.i18n.Translator;
import org.argouml.model.Model;
import org.tigris.gef.undo.UndoableAction;

/**
 * A compound control containing all the visual controls for specifying
 * multiplicity.
 * @author Bob Tarling
 * @since 0.23 alpha2
 */
class UMLMultiplicityPanel extends JPanel implements ItemListener {

    private MultiplicityComboBox multiplicityComboBox;
    private MultiplicityCheckBox checkBox;
    private MultiplicityComboBoxModel multiplicityComboBoxModel;
    
    private static List multiplicityList = new ArrayList();
    
    static {
        multiplicityList.add("1");
        multiplicityList.add("0..1");
        multiplicityList.add("0..*");
        multiplicityList.add("1..*");
    }

    /**
     * Constructor
     */
    public UMLMultiplicityPanel(
            final String propertyName,
            final Object target) {
        super(new BorderLayout());
        
        multiplicityComboBoxModel =
            new MultiplicityComboBoxModel(propertyName);
        
        checkBox = new MultiplicityCheckBox();
        multiplicityComboBox =
		new MultiplicityComboBox(
		        multiplicityComboBoxModel,
		        multiplicityComboBoxModel.getAction());
        multiplicityComboBox.setEditable(true);
        multiplicityComboBox.addItemListener(this);
        add(checkBox, BorderLayout.WEST);
        add(multiplicityComboBox, BorderLayout.CENTER);
        setTarget(target);
    }
    
    /**
     * Enforce that the preferred height is the minimum height.
     * This works around a bug in Windows LAF of JRE5 where a change
     * in the preferred/min size of a combo has changed and has a knock
     * on effect here.
     * If the layout manager for prop panels finds the preferred
     * height is greater than the minimum height then it will allow
     * this component to resize in error.
     * See issue 4333 - Sun has now fixed this bug in JRE6 and so this
     * method can be removed once JRE5 is no longer supported.
     * @return the preferred dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(
                super.getPreferredSize().width,
                getMinimumSize().height);
    }

    public void itemStateChanged(ItemEvent event) {
        if (event.getSource() == multiplicityComboBox && getTarget() != null) {
            Object item = multiplicityComboBox.getSelectedItem();
            Object target = multiplicityComboBoxModel.getTarget();
            Object multiplicity = Model.getFacade().getMultiplicity(target);
            if (Model.getFacade().isAMultiplicity(item)) {
                if (!multiplicity.equals(item)) {
                    Model.getCoreHelper().setMultiplicity(target, item);
                }
            } else if (item instanceof String) {
                if (!item.equals(Model.getFacade().toString(multiplicity))) {
                    Model.getCoreHelper().setMultiplicity(
                            target,
                            Model.getDataTypesFactory().createMultiplicity(
                                    (String) item));
                }
            } else {
                Model.getCoreHelper().setMultiplicity(target, null);
            }
        }
    }
    
    private Object getTarget() {
	return multiplicityComboBoxModel.getTarget();
    }
    
    /**
     * Sets the target.
     * @param target
     */
    public void setTarget(Object target) {
        if (getTarget() != target) {
            multiplicityComboBoxModel.setTarget(target);
            multiplicityComboBox.setTarget(target);
            checkBox.setTarget(target);
        }
    }

    /**
     * An editable and searchable combobox to edit the multiplicity attribute of
     * some modelelement.
     *
     * @author jaap.branderhorst@xs4all.nl
     * @since Jan 5, 2003
     */
    private class MultiplicityComboBox extends UMLSearchableComboBox {

        /**
         * The class uid
         */
        private static final long serialVersionUID = -5860730478954634611L;

        /**
         * Constructor for UMLMultiplicityComboBox2.
         * @param arg0 the combobox model
         * @param selectAction the action
         */
        public MultiplicityComboBox(UMLComboBoxModel model,
                Action selectAction) {
            super(model, selectAction);
        }

        /**
         * On enter, the text the user has filled in the textfield is first
         * checked to see if it's a valid multiplicity. If so then that is the
         * multiplicity to be set. If not, the combobox searches for a
         * multiplicity starting with the given text. If there is no
         * multiplicity starting with the given text, the old value is reset
         * in the comboboxeditor.
         * 
         * {@inheritDoc}
         */
        protected void doOnEdit(Object item) {
            String text = (String) item;
            Object multi = null;
            try {
                multi = Model.getDataTypesFactory().createMultiplicity(text);
            } catch (IllegalArgumentException e) {
                Object o = search(text);
                if (search(text) != null ) {
                    multi = o;
                }
            }
            if (multi != null) {
                setSelectedItem(multi);
            } else {
                getEditor().setItem(getSelectedItem());
            }
        }

	public void setTarget(Object target) {
	    boolean exists = (target != null 
	        && Model.getFacade().getMultiplicity(target) != null);
	    multiplicityComboBox.setEnabled(exists);
	    multiplicityComboBox.setEditable(exists);
	    checkBox.setSelected(exists);
	}
    }
    
    
    /**
     * A model for multiplicities.
     */
    private class MultiplicityComboBoxModel
        extends UMLComboBoxModel {

        /**
         * Constructor for UMLMultiplicityComboBoxModel.
         *
         * @param propertySetName the name of the property set
         */
        public MultiplicityComboBoxModel(String propertySetName) {
            super(propertySetName, false);
        }
    
        /*
         * @see org.argouml.uml.ui.UMLComboBoxModel#isValidElement(Object)
         */
        protected boolean isValidElement(Object element) {
            return element instanceof String;
        }
    
        /*
         * @see org.argouml.uml.ui.UMLComboBoxModel#buildModelList()
         */
        protected void buildModelList() {
            setElements(multiplicityList);
            Object t = getTarget();
            if (Model.getFacade().isAModelElement(t)) {
                addElement(Model.getFacade().getMultiplicity(t));
            }
        }
    
        /*
         * @see org.argouml.uml.ui.UMLComboBoxModel#addElement(java.lang.Object)
         */
        public void addElement(Object o) {
            if (o == null) {
                return;
            }
            if (Model.getFacade().isAMultiplicity(o)) {
                o = Model.getFacade().toString(o);
                if ("".equals(o)) {
                    o = "1";
                }
            }
            if (!multiplicityList.contains(o) && isValidElement(o)) {
                multiplicityList.add(o);
            }
            super.addElement(o);
        }
    
        /*
         * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
         */
        public void setSelectedItem(Object anItem) {
            addElement(anItem);
            super.setSelectedItem((anItem == null) ? null 
                    : Model.getFacade().toString(anItem));
        }

        protected Object getSelectedModelElement() {
            if (getTarget() != null) {
                return Model.getFacade().toString(
                        Model.getFacade().getMultiplicity(getTarget()));
            }
            return null;
        }

        @Override
        protected Object getTarget() {
            return super.getTarget();
        }
        
        public Action getAction() {
            return new ActionSetClassifierRoleMultiplicity();
        }
        
        /**
        *
        * @author mkl
        */
        class ActionSetClassifierRoleMultiplicity extends ActionSetMultiplicity {

            /**
             * The class uid
             */
            private static final long serialVersionUID = -6091471231385415904L;

            public ActionSetClassifierRoleMultiplicity() {
                super();
            }

            /*
             * @see org.argouml.uml.ui.ActionSetMultiplicity#setSelectedItem(
             *      java.lang.Object, java.lang.Object)
             */
            public void setSelectedItem(Object item, Object target) {
                if (target != null
                        && Model.getFacade().isAClassifierRole(target)) {
                    if (Model.getFacade().isAMultiplicity(item)) {
                        if (!item.equals(Model.getFacade().getMultiplicity(target))) {
                            Model.getCoreHelper().setMultiplicity(target, item);
                        }
                    } else if (item instanceof String) {
                        if (!item.equals(Model.getFacade().toString(
                                Model.getFacade().getMultiplicity(target)))) {
                            Model.getCoreHelper().setMultiplicity(
                                    target,
                                    Model.getDataTypesFactory().createMultiplicity(
                                            (String) item));
                        }
                    } else {
                        Model.getCoreHelper().setMultiplicity(target, null);
                    }
                }
            }
        }
    }
    
    private class MultiplicityCheckBox extends JCheckBox
        implements ItemListener {
	
	public MultiplicityCheckBox() {
	    addItemListener(this);
	}

	public void itemStateChanged(ItemEvent e) {
	    if (e.getStateChange() == ItemEvent.SELECTED) {
		String comboText =
		    (String) multiplicityComboBox.getSelectedItem();
                Object multi =
                    Model.getDataTypesFactory().createMultiplicity(comboText);
		if (multi == null) {
                    Model.getCoreHelper().setMultiplicity(getTarget(), "1");
		} else {
                    Model.getCoreHelper().setMultiplicity(getTarget(), multi);
		}
		multiplicityComboBox.setEnabled(true);
		multiplicityComboBox.setEditable(true);
	    } else {
		multiplicityComboBox.setEnabled(false);
		multiplicityComboBox.setEditable(false);
                Model.getCoreHelper().setMultiplicity(getTarget(), null);
	    }
	}
	
	/**
	 * Sets the target
	 * @param theTarget
	 */
	public void setTarget (Object theTarget) {
	    boolean exists = theTarget != null 
	        && Model.getFacade().getMultiplicity(theTarget) != null;
	    setSelected(exists);
        }
    }
    
}


/**
 * Framework action to set the multiplicity of some modelelement.
 * @author jaap.branderhorst@xs4all.nl
 * @since Jan 6, 2003
 */
abstract class ActionSetMultiplicity extends UndoableAction {

    /**
     * Constructor for ActionSetMultiplicity.
     */
    protected ActionSetMultiplicity() {
        super(Translator.localize("Set"), null);
        // Set the tooltip string:
        putValue(Action.SHORT_DESCRIPTION, 
                Translator.localize("Set"));
    }

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object source = e.getSource();
        if (source instanceof UMLComboBox) {
            Object selected = ((UMLComboBox) source).getSelectedItem();
            Object target = ((UMLComboBox) source).getTarget();
            if (target != null && selected != null)
                setSelectedItem(selected, target);
        }
    }

    /**
     * The user should implement this method to set the multiplicity (the given
     * item) for the target of the comboboxmodel (target
     * @param item The multiplicity that should be set
     * @param target The target of the comboboxmodel (the modelelement that
     * should have its multiplicity set).
     */
    public abstract void setSelectedItem(Object item, Object target);

}
