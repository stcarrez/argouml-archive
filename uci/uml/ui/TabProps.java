// Copyright (c) 1996-98 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation for educational, research and non-profit
// purposes, without fee, and without a written agreement is hereby granted,
// provided that the above copyright notice and this paragraph appear in all
// copies. Permission to incorporate this software into commercial products may
// be obtained by contacting the University of California. David F. Redmiles
// Department of Information and Computer Science (ICS) University of
// California Irvine, California 92697-3425 Phone: 714-824-3823. This software
// program and documentation are copyrighted by The Regents of the University
// of California. The software program and documentation are supplied "as is",
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


package uci.uml.ui;

//import jargo.kernel.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import uci.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.tree.*;
//import com.sun.java.swing.border.*;

import uci.uml.Foundation.Core.*;
import uci.uml.Behavioral_Elements.State_Machines.*;
import uci.uml.Behavioral_Elements.Use_Cases.*;
import uci.uml.Model_Management.*;

public class TabProps extends TabSpawnable
implements TabModelTarget {
  ////////////////////////////////////////////////////////////////
  // instance variables
  Object _target;
  boolean _shouldBeEnabled = false;
  JPanel blankPane = new JPanel();
  PropPanelModel modelPane = new PropPanelModel();
  PropPanelClass classPane = new PropPanelClass();
  PropPanelInterface interfacePane = new PropPanelInterface();
  PropPanelAttr attrPane = new PropPanelAttr();
  PropPanelOper operPane = new PropPanelOper();
  PropPanelAssoc assocPane = new PropPanelAssoc();
  PropPanelState statePane = new PropPanelState();
  PropPanelPseudostate pseudostatePane = new PropPanelPseudostate();
  PropPanelUseCase useCasePane = new PropPanelUseCase();
  PropPanelActor actorPane = new PropPanelActor();
  // more: packages, ...
  JPanel _lastPanel = null;
  
  ////////////////////////////////////////////////////////////////
  // constructor
  public TabProps() {
    super("Properties");
    setLayout(new BorderLayout());
    //setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  ////////////////////////////////////////////////////////////////
  // accessors
  public void setTarget(Object t) {
    _target = t;
    if (_lastPanel != null) remove(_lastPanel);
    if (_target instanceof Model) {
      _shouldBeEnabled = true;
      modelPane.setTarget(_target);
      add(modelPane, BorderLayout.NORTH);
      _lastPanel = modelPane;
    }
    else if (_target instanceof MMClass) {
      _shouldBeEnabled = true;
      classPane.setTarget(_target);
      add(classPane, BorderLayout.NORTH);
      _lastPanel = classPane;
    }
    else if (_target instanceof Interface) {
      _shouldBeEnabled = true;
      interfacePane.setTarget(_target);
      add(interfacePane, BorderLayout.NORTH);
      _lastPanel = interfacePane;
    }
    else if (_target instanceof Attribute) {
      _shouldBeEnabled = true;
      attrPane.setTarget(_target);
      add(attrPane, BorderLayout.NORTH);
      _lastPanel = attrPane;
    }
    else if (_target instanceof Operation) {
      _shouldBeEnabled = true;
      operPane.setTarget(_target);
      add(operPane, BorderLayout.NORTH);
      _lastPanel = operPane;
    }
    else if (_target instanceof Association) {
      _shouldBeEnabled = true;
      assocPane.setTarget(_target);
      add(assocPane, BorderLayout.NORTH);
      _lastPanel = assocPane;
    }
    else if (_target instanceof State) {
      _shouldBeEnabled = true;
      statePane.setTarget(_target);
      add(statePane, BorderLayout.NORTH);
      _lastPanel = statePane;
    }
    else if (_target instanceof Pseudostate) {
      _shouldBeEnabled = true;
      pseudostatePane.setTarget(_target);
      add(pseudostatePane, BorderLayout.NORTH);
      _lastPanel = pseudostatePane;
    }
    else if (_target instanceof UseCase) {
      _shouldBeEnabled = true;
      useCasePane.setTarget(_target);
      add(useCasePane, BorderLayout.NORTH);
      _lastPanel = useCasePane;
    }
    else if (_target instanceof Actor) {
      _shouldBeEnabled = true;
      actorPane.setTarget(_target);
      add(actorPane, BorderLayout.NORTH);
      _lastPanel = actorPane;
    }
    //else if ...
    else {
      _shouldBeEnabled = false;
      add(blankPane, BorderLayout.NORTH);
      _lastPanel = blankPane;
    }
    validate();
  }
  public Object getTarget() { return _target; }

  public boolean shouldBeEnabled() { return _shouldBeEnabled; }


} /* end class TabProps */
