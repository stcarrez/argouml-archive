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



// File: FigPackage.java
// Classes: FigPackage
// Original Author: agauthie@ics.uci.edu
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
import uci.uml.Model_Management.*;

/** Class to display graphics for a UML State in a diagram. */

public class FigPackage extends FigNodeModelElement {

  ////////////////////////////////////////////////////////////////
  // constants

  public final int MARGIN = 2;
  public int x = 0;
  public int y = 0;
  public int width = 140;
  public int height = 100;
  public int indentX = 50;
  public int indentY = 20;
  public int textH = 20;
  protected int _radius = 20;

  ////////////////////////////////////////////////////////////////
  // instance variables

  FigText _body;

  /** UML does not really use ports, so just define one big one so
   *  that users can drag edges to or from any point in the icon. */

  FigRect _bigPort;


  ////////////////////////////////////////////////////////////////
  // constructors

  public FigPackage() {
    Color handleColor = Globals.getPrefs().getHandleColor();
    _body = new FigText(x, y + textH, width, height - textH);
    _bigPort = new FigRect(x, y + textH, width, height - textH,
			   handleColor, Color.lightGray);
    _name.setBounds(x, y, width - indentX, textH + 2);
    _name.setJustification(FigText.JUSTIFY_LEFT);

    // add Figs to the FigNode in back-to-front order
    addFig(_bigPort);
    addFig(_name);
    addFig(_body);

    setBlinkPorts(false); //make port invisble unless mouse enters
    Rectangle r = getBounds();
    setBounds(r.x, r.y, r.width, r.height);
    updateEdges();
  }

  public FigPackage(GraphModel gm, Object node) {
    this();
    setOwner(node);
  }

  public String placeString() { return "new Package"; }

  public Object clone() {
    FigPackage figClone = (FigPackage) super.clone();
    Vector v = figClone.getFigs();
    figClone._bigPort = (FigRect) v.elementAt(0);
    figClone._name = (FigText) v.elementAt(1);
    figClone._body = (FigText) v.elementAt(2);
    return figClone;
  }

  ////////////////////////////////////////////////////////////////
  // user interaction methods

  public void setEnclosingFig(Fig encloser) {
    super.setEnclosingFig(encloser);
    if (!(getOwner() instanceof ModelElement)) return;
    ModelElement me = (ModelElement) getOwner();
    Model m = null;
    ProjectBrowser pb = ProjectBrowser.TheInstance;
    if (encloser != null && (encloser.getOwner() instanceof Model)) {
      m = (Model) encloser.getOwner();
    }
    else {
      if (pb.getTarget() instanceof UMLDiagram) {
	m = (Model) ((UMLDiagram)pb.getTarget()).getModel();
      }
    }
    try {
      me.setNamespace(m);
    }
    catch (Exception e) {
      System.out.println("could not set package");
    }
  }

  ////////////////////////////////////////////////////////////////
  // accessor methods

  public void setOwner(Object node) {
    super.setOwner(node);
    bindPort(node, _bigPort);
  }

  public boolean getUseTrapRect() { return true; }

  public Dimension getMinimumSize() {
    Dimension nameMinimum = _name.getMinimumSize();

    int widthP = nameMinimum.width + indentX;
    int heightP = nameMinimum.height + height - textH;
    return new Dimension(widthP, heightP);
  }

  public void setBounds(int x, int y, int w, int h) {
    if (_name == null) return;
    Rectangle oldBounds = getBounds();
    Dimension nameMinimum = _name.getMinimumSize();

    _name.setBounds(x, y, w - indentX, nameMinimum.height + 2);
    _bigPort.setBounds(x+1, y+1 + nameMinimum.height,
		       w-2, h - 2 - nameMinimum.height);
    _body.setBounds(x, y+1 + nameMinimum.height,
		    w, h - 1 - nameMinimum.height);

    calcBounds(); //_x = x; _y = y; _w = w; _h = h;
    firePropChange("bounds", oldBounds, getBounds());
  }


} /* end class FigPackage */
