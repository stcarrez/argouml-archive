// Copyright (c) 1995, 1996 Regents of the University of California.
// All rights reserved.
//
// This software was developed by the Arcadia project
// at the University of California, Irvine.
//
// Redistribution and use in source and binary forms are permitted
// provided that the above copyright notice and this paragraph are
// duplicated in all such forms and that any documentation,
// advertising materials, and other materials related to such
// distribution and use acknowledge that the software was developed
// by the University of California, Irvine.  The name of the
// University may not be used to endorse or promote products derived
// from this software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
// IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
// WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.

// File: ActiveList.java
// Classes: ActiveList
// Original Author: jrobbins@ics.uci.edu
// $Id$

package uci.gef;

import java.util.*;
import java.awt.*;

/** An ActiveComponent that holds an AWT List and
 *  executes one of a set of uci.gef.Action's based on which one
 *  was chosen. Client Frames must still process events and find when
 *  Active component should handle a given event. <p>
 *
 *  Needs-More-Work: I should define an ActiveChoice that is especially
 *  for setting graphical attributes (see DiagramElement).
 *
 *  Needs-More-Work: This code might get deleted in a future release
 *  if I delete all Palettes and ActiveComponents. */

public class ActiveList extends ActiveComponent {

  /** The AWT List object. */
  private java.awt.List _list;

  /** The Action's to be preformed when a one item is chosen. */
  private Hashtable _actions;

  /** Construct a new ActiveList. */
  public ActiveList() {
    _list = new java.awt.List();
    _actions = new Hashtable();
  }

  /** Construct a new ActiveList. */
  public ActiveList(int numElements) {
    _list = new java.awt.List(numElements, false);
    _actions = new Hashtable();
  }

  /** Return the AWT Choice object. */
  public Component component() { return _list; }
  public Action action() { return null; }
  public Action action(String label) { return (Action) _actions.get(label); }

  /** Based on the event that selects an item, find the proper Action
   *  and execute it.
   *
   * @param e The event that selected an item from my Choice object. */
  public void doIt(java.awt.Event e) {
    Action act = (Action) _actions.get(e.arg);
    if (act != null) { act.doIt(e); }
  }

  public void undoIt() {
    /* needs-more-work: I need the event, or the name of the choice... */
  }

  /** This should be called repeatedly to build up a multiple choice
   *   pop-up menu.
   *
   * @param label  A String describing this item.
   * @param act    An Action to be executed when that item is chosen. */
  public void addItem(String label, Action act) {
    _list.addItem(label);
    _actions.put(label, act);
  }

} /* end class ActiveList */

