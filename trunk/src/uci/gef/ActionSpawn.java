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

// File: ActionSpawn.java
// Classes: ActionSpawn
// Original Author: ics125b spring 1996
// $Id$

package uci.gef;

import java.awt.*;

/** Action to open a new editor on the same document as in the current
 *  editor.  Works by calling Editor.clone() then show().
 *
 * @see Editor */

public class ActionSpawn extends Action {

  public ActionSpawn() {  }

  public String name() { return "Spawn Editor"; }

  public void doIt(Event e) {
    Editor ce = Globals.curEditor();
    Editor ed = (Editor) ce.clone();
    // use clone because ce may be of a subclass of Editor
    ed.show();
  }

  public void undoIt() { System.out.println("Cannot undo ActionSpawn"); }

} /* end class ActionSpawn */
