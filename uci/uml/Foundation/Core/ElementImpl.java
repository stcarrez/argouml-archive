// Source file: Foundation/Core/Element.java

package uci.uml.Foundation.Core;

import java.util.*;
import java.beans.*;
import uci.uml.Foundation.Extension_Mechanisms.TaggedValue;
import uci.uml.Foundation.Extension_Mechanisms.Stereotype;
import uci.uml.Foundation.Data_Types.Name;


public class ElementImpl implements Element {
  public Name _name = Name.UNSPEC;
  //% public TaggedValue _characteristic[];
  public Vector _characteristic;
  public Stereotype _classification;
  //% public TaggedValue _taggedValue[];
  public Vector _taggedValue;
  public transient VetoableChangeSupport vetoSupport;
  
  public ElementImpl() { }
  public ElementImpl(Name name) {
    try { setName(name); }
    catch (PropertyVetoException ex) { }
  }
  public ElementImpl(String nameStr) {
    try { setName(new Name(nameStr)); }
    catch (PropertyVetoException ex) { }
  }

  public Vector getCharacteristic() { return _characteristic; }
  public void setCharacteristic(Vector x)
  throws PropertyVetoException {
    fireVetoableChange("characteristic", _characteristic, x);
    _characteristic = x;
  }
  public void addCharacteristic(TaggedValue x)
  throws PropertyVetoException {
    fireVetoableChange("characteristic", _characteristic, x);
    if (_characteristic == null) _characteristic = new Vector();
    _characteristic.addElement(x);
  }
  public void removeCharacteristic(TaggedValue x)
  throws PropertyVetoException {
    fireVetoableChange("characteristic", _characteristic, x);
    _characteristic.removeElement(x);
  }

  public Stereotype getClassification() { return _classification; }
  public void setClassification(Stereotype x)
  throws PropertyVetoException {
    fireVetoableChange("classification", _classification, x);
    _classification = x;
  }

  public Name getName() { return _name; }
  public void setName(Name x)
  throws PropertyVetoException {
    fireVetoableChange("name", _name, x);
    _name = x;
  }

  public Vector getTaggedValue() { return _taggedValue; }
  public void setTaggedValue(Vector x)
  throws PropertyVetoException {
    fireVetoableChange("taggedValue", _taggedValue, x);
    _taggedValue = x;
  }
  public void addTaggedValue(TaggedValue x)
  throws PropertyVetoException {
    fireVetoableChange("taggedValue", _taggedValue, x);
    if (_taggedValue == null) _taggedValue = new Vector();
    _taggedValue.addElement(x);
  }
  public void removeTaggedValue(TaggedValue x)
  throws PropertyVetoException {
    fireVetoableChange("taggedValue", _taggedValue, x);
    _taggedValue.removeElement(x);
  }

  ////////////////////////////////////////////////////////////////
  // VetoableChangeSupport

  public synchronized void
  addVetoableChangeListener(VetoableChangeListener listener) {
    if (vetoSupport == null) vetoSupport = new VetoableChangeSupport(this);
    vetoSupport.addVetoableChangeListener(listener);
  }

  public synchronized void
  removeVetoableChangeListener(VetoableChangeListener listener) {
    if (vetoSupport == null) return;
    vetoSupport.removeVetoableChangeListener(listener);
  }

  public void fireVetoableChange(String propertyName, 
				 Object oldValue, Object newValue)
       throws PropertyVetoException {
	 if (vetoSupport == null) return;
	 vetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
  }
  
  ////////////////////////////////////////////////////////////////
  // OCL support
  
  public String getOCLTypeStr() {
    String javaClassName = getClass().getName();
    int dotIndex = javaClassName.lastIndexOf(".");
    String OCLTypeString = javaClassName.substring(dotIndex+1);
    return OCLTypeString;
  }

}
