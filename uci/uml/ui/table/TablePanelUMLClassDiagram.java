// Copyright (c) 1996-98 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation for educational, research and non-profit
// purposes, without fee, and without a written agreement is hereby granted,
// provided that the above copyright notice and this paragraph appear in all
// copies. Permission to incorporate this software into commercial products
// must be negotiated with University of California. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "as is",
// without any accompanying services from The Regents. The Regents do not
// warrant that the operation of the program will be uninterrupted or
// error-free. The end-user understands that the program was developed for
// research purposes and is advised not to rely exclusively on the program for
// any reason. IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY
// PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES,
// INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS
// DOCUMENTATION, EVEN IF THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY
// DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE
// SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
// ENHANCEMENTS, OR MODIFICATIONS.

package uci.uml.ui.table;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.text.Document;
import com.sun.java.swing.plaf.metal.MetalLookAndFeel;

import uci.util.*;
import uci.ui.*;
import uci.gef.*;
import uci.uml.Foundation.Core.*;
import uci.uml.Foundation.Data_Types.*;

public class TablePanelUMLClassDiagram extends TablePanel {

  ////////////////////////////////////////////////////////////////
  // instance variables

  JSortedTable _table2 = new JSortedTable();
  JSortedTable _table3 = new JSortedTable();
  TableModelComposite _tableModelOper = new TableModelOper();
  TableModelComposite _tableModelAttr = new TableModelAttr();
  JPanel _south = new JPanel();
  JScrollPane _sp2;
  JScrollPane _sp3;

  ////////////////////////////////////////////////////////////////
  // constructors

  public TablePanelUMLClassDiagram() {
    super("UMLClassDiagram");

    _south.setLayout(new GridLayout(1, 2, 5, 5));

    _sp2 = new JScrollPane(_table2,
			   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    _sp3 = new JScrollPane(_table3,
			   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    _sp1.setPreferredSize(new Dimension(300, 300));
    _sp1.setSize(new Dimension(300, 300));

    _sp2.setPreferredSize(new Dimension(300, 300));
    _sp2.setSize(new Dimension(300, 300));

    _sp3.setPreferredSize(new Dimension(300, 300));
    _sp3.setSize(new Dimension(300, 300));

    _content.setPreferredSize(new Dimension(300, 600));
    _content.setSize(new Dimension(300, 600));

    JPanel sp2W = new JPanel();
    sp2W.setLayout(new BorderLayout());
    sp2W.add(new JLabel("Operations of selected class:"), BorderLayout.NORTH);
    sp2W.add(_sp2, BorderLayout.CENTER);

    JPanel sp3W = new JPanel();
    sp3W.setLayout(new BorderLayout());
    sp3W.add(new JLabel("Attributes of selected class:"), BorderLayout.NORTH);
    sp3W.add(_sp3, BorderLayout.CENTER);

    _south.add(sp3W);
    _south.add(sp2W);

    _content.add(_south, BorderLayout.SOUTH);

    Font labelFont = MetalLookAndFeel.getSubTextFont();
    _table2.setFont(labelFont);
    _table3.setFont(labelFont);
    setEditors(_table2);
    setEditors(_table3);

    _table2.getSelectionModel().addListSelectionListener(this);
    _table3.getSelectionModel().addListSelectionListener(this);
  }

  /////////////////////////////////////////////////////////////////
  // ListSelectionListener implemention

  public void valueChanged(ListSelectionEvent lse) {
    super.valueChanged(lse);
    if (lse.getValueIsAdjusting()) return;
    Object src = lse.getSource();
    if (src == _table2.getSelectionModel()) {
      int row = lse.getFirstIndex();
      if (_tableModelOper != null) {
	Vector rowObjects = _tableModelOper.getRowObjects();
	if (row >= 0 && row < rowObjects.size()) {
	  Object sel = rowObjects.elementAt(row);
	  objectSelected(sel);
	  return;
	}
      }
    }
    if (src == _table3.getSelectionModel()) {
      int row = lse.getFirstIndex();
      if (_tableModelAttr != null) {
	Vector rowObjects = _tableModelAttr.getRowObjects();
	if (row >= 0 && row < rowObjects.size()) {
	  Object sel = rowObjects.elementAt(row);
	  objectSelected(sel);
	  return;
	}
      }
    }
  }

  public void objectSelected(Object sel) {
    super.objectSelected(sel);
    if (sel instanceof Classifier) {
      _tableModelOper.setTarget((Classifier)sel);
      _tableModelAttr.setTarget((Classifier)sel);
    }
  }



  public void setTablePerspective() {
    super.setTablePerspective();
    if (_tableModel instanceof TableModelClassByProps) {
      _table2.setModel(_tableModelOper);
      _table3.setModel(_tableModelAttr);
      _south.setVisible(true);
    }
    else {
      _south.setVisible(false);
    }
    validate();
  }

  public void initTableModels() {
    _tableModels.addElement(new TableModelClassByProps());
    _tableModels.addElement(new TableModelAssocByProps());
    //_tableModels.addElement(new TableModelClassByClass());
  }

} /* end class TablePanelUMLClassDiagram */
