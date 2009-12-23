// $Id$
// Copyright (c) 2008 The Regents of the University of California. All
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

package org.argouml.core.propertypanels.meta;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This handles the XML events by SAX Api for building 
 * the property panels.
 * @author penyaskito
 */
class XmlSinglePanelHandler extends DefaultHandler {

    /**
     * The panel that will host the controls. 
     */
    private final Map<String, PanelMeta> data;
    
    /**
     * the panel that we are traversing
     */
    private PanelMeta currentPanel = null;
    
    private PropertyMeta current = null;
    
    /**
     * Default constructor.
     * @param theData The XMLPropertyPanelsData that will 
     * host the info read.
     */
    public XmlSinglePanelHandler(
            Map<String, PanelMeta> theData) {
        this.data = theData;
    }

    public void startElement(String namespaceURI, String localName, 
            String qName, Attributes attr) throws SAXException {
        // ignore the parent node, is only the container
        if ("panels".equals(localName)) {
            return;
        }
        if ("panel".equals(localName)) { 
            if (this.currentPanel == null) {                
                currentPanel = new PanelMeta(attr.getValue("name"));
            }
        } else {
            if (isChild(localName)) {
                CheckBoxMeta record = 
                    new CheckBoxMeta(localName, attr.getValue("name"));            
                current.addCheckbox(record);
            } else {
                PropertyMeta record = 
                    new PropertyMeta(localName, 
                            attr.getValue("name"), attr.getValue("type"));
                if (hasChildren(localName)) {
                    current = record;
                }
                currentPanel.addProperty(record);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, 
            String qName) throws SAXException {
        if ("panel".equals(localName)) { 
            data.put(currentPanel.getName(), currentPanel);
            currentPanel = null;
        }
    }

    private boolean isChild(String elementName) {
        // for now, the only child are checkboxes.
        return "checkbox".equals(elementName);
    }

    private boolean hasChildren(String elementName) {
        // for now, the only element that can have 
        // children are checkgroups.
        return "checkgroup".equals(elementName);
    }
}
