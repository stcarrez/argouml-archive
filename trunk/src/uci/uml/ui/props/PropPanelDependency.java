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




package uci.uml.ui.props;

//import jargo.kernel.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.tree.*;
import com.sun.java.swing.text.*;
//import com.sun.java.swing.border.*;

import uci.util.*;
import uci.uml.Foundation.Core.*;
import uci.uml.Foundation.Data_Types.*;
import uci.uml.Foundation.Extension_Mechanisms.*;
import uci.uml.Model_Management.*;
import uci.uml.ui.*;


public class PropPanelDependency extends PropPanelTwoEnds {

  ////////////////////////////////////////////////////////////////
  // constructors

  public PropPanelDependency() {
    super("Dependency");

    remove(_nameField);
    remove(_nameLabel);
  }


  public String getSourceLabel() {
    if (!(_target instanceof Dependency)) return "non dep";
    return "Supplier:";
  }
  public String getSourceValue() {
    if (!(_target instanceof Dependency)) return "non dep";
    Dependency d = (Dependency) _target;
    Vector suppliers = d.getSupplier();
    if (suppliers == null) return "null suppliers";
    if (suppliers.size() == 0) return "no suppliers";
    ModelElement sup = (ModelElement) suppliers.elementAt(0);
    if (sup == null) return "null";
    return sup.getName().getBody();
  }
  public String getDestLabel() {
    if (!(_target instanceof Dependency)) return "non dep";
    return "Client:";
  }
  public String getDestValue() {
    if (!(_target instanceof Dependency)) return "non dep";
    Dependency d = (Dependency) _target;
    Vector clients = d.getClient();
    if (clients == null) return "null clients";
    if (clients.size() == 0) return "no clients";
    ModelElement tar = (ModelElement) clients.elementAt(0);
    if (tar == null) return "null";
    return tar.getName().getBody();
  }
  

} /* end class PropPanelDependency */
