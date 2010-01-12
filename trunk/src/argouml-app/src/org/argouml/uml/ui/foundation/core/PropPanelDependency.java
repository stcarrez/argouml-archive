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

package org.argouml.uml.ui.foundation.core;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.argouml.i18n.Translator;
import org.argouml.uml.ui.ActionNavigateNamespace;
import org.argouml.uml.ui.UMLLinkedList;
import org.argouml.uml.ui.foundation.extension_mechanisms.ActionNewStereotype;

/**
 * The properties panel for a Dependency.
 *
 */
public class PropPanelDependency extends PropPanelRelationship {

    /**
     * The serial version.
     */
    private static final long serialVersionUID = 3665986064546532722L;

    /**
     * The scrollpane with the modelelement that is the supplier of this
     * dependency
     */
    private JScrollPane supplierScroll;

    /**
     * The scrollpane with the modelelement that is the client of this
     * dependency
     */
    private JScrollPane clientScroll;

    /**
     * 'default' constructor used if a modelelement is a child of dependency (or
     * dependency itself) but does not have a proppanel of their own.
     */
    public PropPanelDependency() {
        this("label.dependency", lookupIcon("Dependency"));

        addField(Translator.localize("label.name"),
                getNameTextField());
        addField(Translator.localize("label.namespace"),
                getNamespaceSelector());

        addSeparator();

        addField(Translator.localize("label.suppliers"),
                supplierScroll);
        addField(Translator.localize("label.clients"),
                clientScroll);

        addAction(new ActionNavigateNamespace());
        addAction(new ActionNewStereotype());
        addAction(getDeleteAction());
    }

    /**
     * Constructor that should be used by subclasses to initialize the
     * attributes a dependency has.
     * {@inheritDoc}
     * 
     * @see org.argouml.uml.ui.PropPanel#PropPanel(String, ImageIcon)
     */
    protected PropPanelDependency(String name, ImageIcon icon) {
        super(name, icon);
        JList supplierList = new UMLLinkedList(
                new UMLDependencySupplierListModel(), true);
        supplierScroll = new JScrollPane(supplierList);

        JList clientList = new UMLLinkedList(
                new UMLDependencyClientListModel(), true);
        clientScroll = new JScrollPane(clientList);
    }

    /**
     * @return Returns the supplierScroll.
     */
    protected JScrollPane getSupplierScroll() {
        return supplierScroll;
    }

    /**
     * @return Returns the clientScroll.
     */
    protected JScrollPane getClientScroll() {
        return clientScroll;
    }


}
