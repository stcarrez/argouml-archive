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

// File: FigBranchState.java
// Classes: FigBranchState
// Original Author: jrobbins@ics.uci.edu
// $Id$

package uci.uml.visual;

import java.awt.*;
import java.util.*;
import java.beans.*;
import com.sun.java.swing.*;

import uci.gef.*;
import uci.graph.*;
import uci.uml.ui.*;
import uci.uml.generate.*;
import uci.uml.Foundation.Core.*;
import uci.uml.Behavioral_Elements.State_Machines.*;

/** Class to display graphics for a UML State in a diagram. */

public class FigBranchState extends FigNode
implements VetoableChangeListener, DelayedVetoableChangeListener {

  ////////////////////////////////////////////////////////////////
  // constants

  public static final int MARGIN = 2;
  public static final int X = 0;
  public static final int Y = 0;
  public static final int WIDTH = 32;
  public static final int HEIGHT = 32;

  ////////////////////////////////////////////////////////////////
  // instance variables

  /** The main label on this icon. */
//  FigText _name;

  /** UML does not really use ports, so just define one big one so
   *  that users can drag edges to or from any point in the icon. */

  FigPoly _bigPort;

  // add other Figs here as needed
  FigPoly _head;

  ////////////////////////////////////////////////////////////////
  // constructors
  public FigBranchState() {
    _bigPort = new FigPoly( Color.cyan, Color.cyan);
    _head = new FigPoly(Color.black, Color.white);
    _bigPort.addPoint(X, Y);
    _bigPort.addPoint(X+WIDTH/2, Y+HEIGHT/2);
    _bigPort.addPoint(X, Y+HEIGHT);
    _bigPort.addPoint(X-WIDTH/2, Y+HEIGHT/2);

    _head.addPoint(X, Y);
    _head.addPoint(X+WIDTH/2, Y+HEIGHT/2);
    _head.addPoint(X, Y+HEIGHT);
    _head.addPoint(X-WIDTH/2, Y+HEIGHT/2);
    _head.addPoint(X, Y);

    // add Figs to the FigNode in back-to-front order
    addFig(_bigPort);
    addFig(_head);

    setBlinkPorts(false); //make port invisble unless mouse enters
    Rectangle r = getBounds();
  }

  public FigBranchState(GraphModel gm, Object node) {
    this();
    setOwner(node);
  }

  public Object clone() {
    FigBranchState figClone = (FigBranchState) super.clone();
    Vector v = figClone.getFigs();
    figClone._bigPort = (FigPoly) v.elementAt(0);
    figClone._head = (FigPoly) v.elementAt(1);
    return figClone;
  }

  public void setOwner(Object node) {
    super.setOwner(node);
    bindPort(node, _bigPort);
    // if it is a UML meta-model object, register interest in any change events
    if (node instanceof ElementImpl)
      ((ElementImpl)node).addVetoableChangeListener(this);
  }

  /** Initial states are fixed size. */
  public boolean isResizable() { return false; }


  /** If the UML meta-model object changes state. Update the Fig.  But
   *  we need to do it as a "DelayedVetoableChangeListener", so that
   *  model changes complete before we update the screen. */
  public void vetoableChange(PropertyChangeEvent pce) {
    // throws PropertyVetoException
    //System.out.println("FigBranchState got a change notification!");
    Object src = pce.getSource();
    if (src == getOwner()) {
      DelayedChangeNotify delayedNotify = new DelayedChangeNotify(this, pce);
      SwingUtilities.invokeLater(delayedNotify);
    }
  }

  /** The UML meta-model object changed. Now update the Fig to show
   *  its current  state. */
  public void delayedVetoableChange(PropertyChangeEvent pce) {
    // throws PropertyVetoException
    //System.out.println("FigBranchState got a delayed change notification!");
    Object src = pce.getSource();
    if (src == getOwner()) {
     // updateText();
      // you may have to update more than just the text
    }
  }

  /** Update the text labels */
  protected void updateText() { }

  public void dispose() {
    //System.out.println("disposing FigBranchState");
    Element elmt = (Element) getOwner();
    if (elmt == null) return;
    Project p = ProjectBrowser.TheInstance.getProject();
    p.moveToTrash(elmt);
    StateVertex sv = (StateVertex) getOwner();
    try { sv.setParent(null); }
    catch (PropertyVetoException pve) { }
    super.dispose();
  }

  static final long serialVersionUID = 7975577199958200215L;

} /* end class FigBranchState */
