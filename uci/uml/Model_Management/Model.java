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



// Source file: f:/jr/projects/uml/Model_Management/Model.java
package uci.uml.Model_Management;

import java.util.*;
import java.beans.PropertyVetoException;

import uci.uml.Foundation.Core.*;
import uci.uml.Foundation.Data_Types.Name;

public class Model extends GeneralizableElementImpl implements Package {
    
  public Model() { }
  public Model(Name name) { super(name); }
  public Model(String nameStr) { super(new Name(nameStr)); }

  ////////////////////////////////////////////////////////////////
  // Package implementation
  
  //% public ModelElement _referencedElement[];
  public Vector _referencedElement;

  public Vector getReferencedElement() {
    return _referencedElement;
  }
  public void setReferencedElement(Vector x) throws PropertyVetoException {
    fireVetoableChange("referencedElemement", _referencedElement, x);
    _referencedElement = x;
  }
  public void addReferencedElement(ModelElement x) {
    if (_referencedElement == null) _referencedElement = new Vector();
    _referencedElement.addElement(x);
  }
  public void removeReferencedElement(ModelElement x) {
    _referencedElement.removeElement(x);
  }
  
}
