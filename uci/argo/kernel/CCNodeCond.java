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

// File: CCNodeCond.java
// Classes: CCNodeCond
// Original Author: jrobbins@ics.uci.edu
// $Id$

package uci.argo.kernel;

import java.util.*;
import uci.util.*;

/** */

public abstract class CCNodeCond implements CCNode {

  ////////////////////////////////////////////////////////////////
  // instance variables
  public Vector _predecessors = new Vector();
  public Vector _successors = new Vector();


  ////////////////////////////////////////////////////////////////
  // constructor

  public CCNodeCond() { }
  
  ////////////////////////////////////////////////////////////////
  // accessors

  public Vector getSuccessors() { return _successors; }
  public void addSuccessor(CCNode n) { _successors.addElement(n); }
  public void removeSuccessor(CCNode n) { _successors.removeElement(n); }

  public Vector getPredecessors() { return _predecessors; }
  public void addPredecessor(CCNode n) { _predecessors.addElement(n); }
  public void removePredecessor(CCNode n) { _predecessors.removeElement(n); }

  ////////////////////////////////////////////////////////////////
  // evaluation

  public abstract boolean predicate(CCContext ccc);

  public boolean shouldStay(CCContext ccc) { return false; }


} /* end class CCNodeCond */

