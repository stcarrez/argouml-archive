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

// File: NodeCPU.java
// Classes: NodeCPU
// Original Author: jrobbins@ics.uci.edu
// $Id$

package uci.gef.demo;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import uci.gef.*;
import uci.util.*;

/** An example subclass of NetNode for use in the Example application.
 *
 * @see Example */

public class NodeCPU extends SampleNode {

  ////////////////////////////////////////////////////////////////
  // constants

  public static final String pMEGS_OF_RAM = "Megs Of RAM";
  public static final String pBUS_SPEED_MHZ = "Bus Speed Mhz";
  public static final String pCPU_SPEED_MHZ = "CPU Speed Mhz";
  public static final String pHAS_CD_ROM = "Has CD ROM";
  public static final String pOWNER_INFO = "Owner";

  ////////////////////////////////////////////////////////////////
  // instance variables

  protected int _megsOfRAM = 16;
  protected int _busSpeedMHz = 25;
  protected int _cpuSpeedMHz = 100;
  protected boolean _hasCDROM = true;
  protected String _ownerInfo = "Your Name Here\n"+
  "Your Company\n" + "Your Address Line1\n" + "Your Address Line 2";


  ////////////////////////////////////////////////////////////////
  // constructors and initialization code

  /** Initialize a new NodeCPU from the given default node and
   *  application specific model. <p>
   *
   *  Needs-More-Work: for now we construct the FigNode
   *  programatically, but eventually we will store it in a class
   *  variable and just refer to it, or copy it(?). That way the user
   *  can edit the FigNode(s) stored in the class variable and
   *  have those changes shown for all existing nodes, or for all
   *  future nodes. Maybe I should think about doing virtual copies?
   *  <p> */

  public void initialize(NetNode deft, Object model) {
    portList = new NetPort[2];
    portList[0] = new PortPower(this, PortPower.RECEPTICAL);
    portList[1] = new PortData(this);
   }

  ////////////////////////////////////////////////////////////////
  // accessors

  public int getMegsOfRAM() { return _megsOfRAM; }
  public int getBusSpeedMHz() { return _busSpeedMHz; }
  public int getCPUSpeedMhz() { return _cpuSpeedMHz; }
  public boolean getHasCDROM() { return _hasCDROM; }
  public String getOwnerInfo() { return _ownerInfo; }

  public void setMegsOfRAM(int m) {
    if (m % 8 == 0) _megsOfRAM = m;
  }
  public void setBusSpeedMHz(int b) {
    if (b % 25 == 0 || b % 33 == 0)
      _busSpeedMHz = b;
  }
  public void setCPUSpeedMhz(int c) {
    if (c == 25 || c == 33 || c == 50 || c == 66 || c == 75 ||
	c == 90 || c == 100 || c == 120 || c == 133 || c == 150 ||
	c == 166 || c == 200 || c == 233)
      _cpuSpeedMHz = c;
  }

  public void setHasCDROM(boolean r) { _hasCDROM = r; }

  public void setOwnerInfo(String oi) { _ownerInfo = oi; }

  public Object get(String key) {
    if (key.equals(pMEGS_OF_RAM))
      return new Integer(getMegsOfRAM());
    else if (key.equals(pBUS_SPEED_MHZ))
      return new Integer(getBusSpeedMHz());
    else if (key.equals(pCPU_SPEED_MHZ))
      return new Integer(getCPUSpeedMhz());
    else if (key.equals(pHAS_CD_ROM))
      return getHasCDROM() ? Boolean.TRUE : Boolean.FALSE;
    else if (key.equals(pOWNER_INFO))
      return getOwnerInfo();
    else
      return super.get(key);
  }

  public boolean put(String key, Object value) {
    int i = 0;
    if (value instanceof Integer) i = ((Integer)value).intValue();
    //
    boolean b = false;
    if (value instanceof Boolean) b = ((Boolean)value).booleanValue();
    //
    if (key.equals(pMEGS_OF_RAM)) setMegsOfRAM(i);
    else if (key.equals(pBUS_SPEED_MHZ)) setBusSpeedMHz(i);
    else if (key.equals(pCPU_SPEED_MHZ)) setCPUSpeedMhz(i);
    else if (key.equals(pHAS_CD_ROM)) setHasCDROM(b);
    else if (key.equals(pOWNER_INFO)) setOwnerInfo((String)value);
    else return super.put(key, value);
    setChanged();
    notifyObservers();
    return true;
  }

  public static Vector _ModelKeys = null;

  public Enumeration keysIn(String category) {
    if (_ModelKeys == null) {
      _ModelKeys = new Vector();
      _ModelKeys.addElement(pMEGS_OF_RAM);
      // _ModelKeys.addElement(pBUS_SPEED_MHZ);
      // note that bus speed is not shown in property sheet
      _ModelKeys.addElement(pCPU_SPEED_MHZ);
      _ModelKeys.addElement(pHAS_CD_ROM);
      _ModelKeys.addElement(pOWNER_INFO);
    }
    if (category.equals("Model"))
      return _ModelKeys.elements();
    else
      return EnumerationEmpty.theInstance();
  }

  public boolean canPut(String key) {
    return key.equals(pMEGS_OF_RAM) || key.equals(pBUS_SPEED_MHZ) ||
      key.equals(pCPU_SPEED_MHZ) || key.equals(pHAS_CD_ROM);
    // note that pOWNER_INFO is not editable
  }

  static {
    Vector possibleCPUSpeeds = new Vector();
    possibleCPUSpeeds.addElement(new Integer(25));
    possibleCPUSpeeds.addElement(new Integer(33));
    possibleCPUSpeeds.addElement(new Integer(66));
    possibleCPUSpeeds.addElement(new Integer(75));
    possibleCPUSpeeds.addElement(new Integer(90));
    possibleCPUSpeeds.addElement(new Integer(100));
    possibleCPUSpeeds.addElement(new Integer(120));
    possibleCPUSpeeds.addElement(new Integer(133));
    possibleCPUSpeeds.addElement(new Integer(150));
    possibleCPUSpeeds.addElement(new Integer(166));
    possibleCPUSpeeds.addElement(new Integer(200));
    possibleCPUSpeeds.addElement(new Integer(233));
    // //uci.ui.PropSheetCategory.addEnumProp(pCPU_SPEED_MHZ, possibleCPUSpeeds);
    // //uci.ui.PropSheetCategory.addBigTextProp(pOWNER_INFO, 4);
  }

  ////////////////////////////////////////////////////////////////
  // presentations

  public FigNode makePresentation(Layer lay) {
    URL imageURL = null;
    try {
      imageURL = new URL("http://www.ics.uci.edu/~jrobbins/images/cpu.gif");
    }
    catch (java.net.MalformedURLException e) { }
    Fig obj1 = new FigImage(0, 0, imageURL, new Hashtable());
    int imageWidth = obj1.getWidth();
    Fig obj2 = new FigRect(2, -7, 14, 14, Color.black, Color.white);
    FigRRect obj3 = new FigRRect(imageWidth - 4, 10, 8, 15, Color.black,
			    Color.black);
    obj3.setCornerRadius(3);
    Vector temp_list = new Vector();
    temp_list.addElement(obj1);
    temp_list.addElement(obj2);
    temp_list.addElement(obj3);
    FigNode fn = new FigNode(this, temp_list);
    fn.addPort(portList[0], obj2);
    fn.addPort(portList[1], obj3);
    return fn;
  }

} /* end class NodeCPU */
