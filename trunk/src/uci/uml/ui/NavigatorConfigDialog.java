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





package uci.uml.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.tree.*;
// import com.sun.java.swing.text.*;
// import com.sun.java.swing.border.*;

import uci.util.*;
import uci.ui.*;
import uci.gef.*;
import uci.argo.kernel.*;
import uci.uml.Foundation.Core.*;
import uci.uml.Model_Management.*;
import uci.uml.ui.nav.*;


public class NavigatorConfigDialog extends JDialog
implements ActionListener, ChangeListener, ListSelectionListener, MouseListener {

  ////////////////////////////////////////////////////////////////
  // constants
  public final int WIDTH = 400;
  public final int HEIGHT = 600;

  ////////////////////////////////////////////////////////////////
  // instance variables
  protected JTabbedPane  _tabs = new JTabbedPane();

  protected JPanel  _mainButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
  protected JButton _okButton = new JButton("OK");

  protected JPanel  _persPanel = new JPanel();
  protected JList   _persList = new JList();
  protected JList   _rulesList = new JList();
  protected JList   _ruleLibList = new JList();
  protected JPanel  _persButtons = new JPanel();
  protected JButton _newPersButton = new JButton("New");
  protected JButton _removePersButton = new JButton("Remove");
  protected JButton _dupPersButton = new JButton("Duplicate");
  protected JPanel  _xferButtons = new JPanel();
  protected JButton _addRuleButton = new JButton(">>");
  protected JButton _removeRuleButton = new JButton("<<");

  protected JPanel  _panesPanel = new JPanel();
  protected JPanel  _paneOnePanel = new JPanel();
  protected JRadioButton  _paneOneNotShown = new JRadioButton("Not Shown");
  protected JRadioButton  _paneOneTree = new JRadioButton("Rooted at Project");
  protected ButtonGroup   _paneOneGroup = new ButtonGroup();
  
  protected JPanel  _paneTwoPanel = new JPanel();
  protected JRadioButton  _paneTwoNotShown = new JRadioButton("Not Shown");
  protected JRadioButton  _paneTwoMRU = new JRadioButton("Most Recently Used");
  protected JRadioButton  _paneTwoTree =
  new JRadioButton("Rooted at Pane One Selection");
  protected ButtonGroup   _paneTwoGroup = new ButtonGroup();

  protected JPanel  _paneThreePanel = new JPanel();
  protected JRadioButton  _paneThreeNotShown = new JRadioButton("Not Shown");
  protected JRadioButton  _paneThreeMRU = new JRadioButton("Most Recently Used");
  protected JRadioButton  _paneThreeTree =
  new JRadioButton("Rooted at Pane Two Selection");
  protected ButtonGroup   _paneThreeGroup = new ButtonGroup();


  ////////////////////////////////////////////////////////////////
  // constructors

  public NavigatorConfigDialog(Frame parent) {
    super(parent, "Configure Perspectives", false);
    int x = parent.getLocation().x + (parent.getSize().width - WIDTH) / 2;
    int y = parent.getLocation().y + (parent.getSize().height - HEIGHT) / 2;
    setLocation(x, y);
    setSize(WIDTH, HEIGHT);

    Container content = getContentPane();
    content.setLayout(new BorderLayout());
    
    initPersPanel();
    initPanesPanel();

    JPanel persTab = new JPanel(new BorderLayout(3, 3));
    persTab.add(_persPanel, BorderLayout.CENTER);
    JPanel panesTab = new JPanel(new BorderLayout(3, 3));
    panesTab.add(_panesPanel, BorderLayout.CENTER);
    
    _tabs.addTab("Perspectives", persTab);
    _tabs.addTab("Panes", panesTab);

    _mainButtons.add(_okButton);
    
    content.add(_tabs, BorderLayout.CENTER);
    content.add(_mainButtons, BorderLayout.SOUTH);
    
    getRootPane().setDefaultButton(_okButton);
  }


  public void initPersPanel() {
    _persList.setListData(NavPerspective.getRegisteredPerspectives());
    _ruleLibList.setListData(NavPerspective.getRegisteredRules());
    _rulesList.setListData(new Vector());

    
    GridBagLayout gb = new GridBagLayout();
    _persPanel.setLayout(gb);

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    c.weighty = 0.0;
    c.ipadx = 3; c.ipady = 3;

    JLabel persLabel = new JLabel("Perspectives");
    c.gridx = 0; c.gridy = 0;
    c.gridwidth = 3;
    gb.setConstraints(persLabel, c);
    _persPanel.add(persLabel);

    _persList.setPreferredSize(new Dimension(400, 120));

    c.gridx = 0; c.gridy = 1;
    c.gridwidth = 3;
    c.weighty = 0.5;
    JScrollPane persScroll =
      new JScrollPane(_persList,
		      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    gb.setConstraints(persScroll, c);
    _persPanel.add(persScroll);

    c.weighty = 0.0;
	
    _persButtons.setLayout(new GridLayout(3, 1));
    _newPersButton.setMargin(new Insets(0, 0, 0, 0));
    _removePersButton.setMargin(new Insets(0, 0, 0, 0));
    _dupPersButton.setMargin(new Insets(0, 0, 0, 0));
    _persButtons.add(_newPersButton);
    _persButtons.add(_removePersButton);
    _persButtons.add(_dupPersButton);
    JPanel persButtonWrapper = new JPanel();
    persButtonWrapper.add(_persButtons);
    
    c.gridx = 3; c.gridy = 1;
    c.gridwidth = 1;
    c.weightx = 0.0;
    gb.setConstraints(persButtonWrapper, c);
    _persPanel.add(persButtonWrapper);

    c.gridwidth = 1;
    c.weightx = 1.0;

    JLabel ruleLibLabel = new JLabel("Rules Library");
    c.gridx = 0; c.gridy = 3;
    gb.setConstraints(ruleLibLabel, c);
    _persPanel.add(ruleLibLabel);

    _addRuleButton.setMargin(new Insets(0, 0, 0, 0));
    _removeRuleButton.setMargin(new Insets(0, 0, 0, 0));
    _xferButtons.setLayout(new BoxLayout(_xferButtons, BoxLayout.Y_AXIS));
    _xferButtons.add(_addRuleButton);
    _xferButtons.add(new SpacerPanel());
    _xferButtons.add(_removeRuleButton);
    
    c.gridx = 1; c.gridy = 4;
    c.weightx = 0.0;
    gb.setConstraints(_xferButtons, c);
    _persPanel.add(_xferButtons);

    c.weightx = 1.0;

    JLabel rulesLabel = new JLabel("Selected Rules");
    c.gridx = 2; c.gridy = 3;
    c.gridwidth = 1;
    gb.setConstraints(rulesLabel, c);
    _persPanel.add(rulesLabel);

    c.weighty = 1.0;
    c.gridheight = 2;
    
    _ruleLibList.setMinimumSize(new Dimension(250, 350));
    _rulesList.setMinimumSize(new Dimension(250, 350));
    
    c.gridx = 0; c.gridy = 4;
    c.gridwidth = 1;
    JScrollPane ruleLibScroll =
      new JScrollPane(_ruleLibList,
		      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    gb.setConstraints(ruleLibScroll, c);
    _persPanel.add(ruleLibScroll);

    c.gridx = 2; c.gridy = 4;
    c.gridwidth = 2;
    JScrollPane rulesScroll =
      new JScrollPane(_rulesList,
		      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    gb.setConstraints(rulesScroll, c);
    _persPanel.add(rulesScroll);
    
    // this is the default anyway
//     _persList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//     _ruleLibList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//     _rulesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    _okButton.addActionListener(this);
    _newPersButton.addActionListener(this);
    _removePersButton.addActionListener(this);
    _dupPersButton.addActionListener(this);
    _addRuleButton.addActionListener(this);
    _removeRuleButton.addActionListener(this);
    _persList.addListSelectionListener(this);
    _rulesList.addListSelectionListener(this);
    _rulesList.addMouseListener(this);
    _ruleLibList.addListSelectionListener(this);
    _ruleLibList.addMouseListener(this);

    _removePersButton.setEnabled(false);
    _dupPersButton.setEnabled(false);

    _addRuleButton.setEnabled(false);
    _removeRuleButton.setEnabled(false);

    // needs-more-work: should set up CellEditor for _persList to
    // allow renaming.  Will this force me to use a JTable instead?
  }

  public void initPanesPanel() {
    _paneOnePanel.setBorder(BorderFactory.createTitledBorder("Panel One"));
    _paneOnePanel.setLayout(new BoxLayout(_paneOnePanel, BoxLayout.Y_AXIS));
    _paneOnePanel.add(_paneOneNotShown);
    _paneOnePanel.add(_paneOneTree);
    _paneOneGroup.add(_paneOneNotShown);
    _paneOneGroup.add(_paneOneTree);
    _paneOneTree.setSelected(true);
    _paneOneNotShown.setEnabled(false);
    
    _paneTwoPanel.setBorder(BorderFactory.createTitledBorder("Panel Two"));
    _paneTwoPanel.setLayout(new BoxLayout(_paneTwoPanel, BoxLayout.Y_AXIS));
    _paneTwoPanel.add(_paneTwoNotShown);
    _paneTwoPanel.add(_paneTwoMRU);
    _paneTwoPanel.add(_paneTwoTree);
    _paneTwoGroup.add(_paneTwoNotShown);
    _paneTwoGroup.add(_paneTwoMRU);
    _paneTwoGroup.add(_paneTwoTree);
    _paneTwoNotShown.setEnabled(false);
    _paneTwoNotShown.setSelected(true);
    _paneTwoMRU.setEnabled(false);
    _paneTwoTree.setEnabled(false);
 
    _paneThreePanel.setBorder(BorderFactory.createTitledBorder("Panel Three"));
    _paneThreePanel.setLayout(new BoxLayout(_paneThreePanel, BoxLayout.Y_AXIS));
    _paneThreePanel.add(_paneThreeNotShown);
    _paneThreePanel.add(_paneThreeMRU);
    _paneThreePanel.add(_paneThreeTree);
    _paneThreeGroup.add(_paneThreeNotShown);
    _paneThreeGroup.add(_paneThreeMRU);
    _paneThreeGroup.add(_paneThreeTree);
    _paneThreeNotShown.setEnabled(false);
    _paneThreeNotShown.setSelected(true);
    _paneThreeMRU.setEnabled(false);
    _paneThreeTree.setEnabled(false);

    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new GridLayout(4, 2, 0, 3));
    //innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
    innerPanel.add(_paneOnePanel);
    innerPanel.add(new SpacerPanel());
    innerPanel.add(_paneTwoPanel);
    innerPanel.add(new SpacerPanel());
    innerPanel.add(_paneThreePanel);
    
    _panesPanel.setLayout(new BorderLayout());
    _panesPanel.add(innerPanel, BorderLayout.CENTER);
  }

  ////////////////////////////////////////////////////////////////
  // actions

  public void doNewPers() {
    NavPerspective newPers = new NavPerspective("New Perspective");
    NavPerspective.registerPerspective(newPers);
    _persList.setListData(NavPerspective.getRegisteredPerspectives());
    _persList.setSelectedValue(newPers, true);
    _persList.repaint();
  }

  public void doRemovePers() {
    Object sel = _persList.getSelectedValue();
    if (!(sel instanceof NavPerspective)) {
      System.out.println("doRemovePers: unexepected non-NavPerspective");
      return;
    }
    //needs-more-work: are you sure?
    NavPerspective np = (NavPerspective) sel;
    NavPerspective.unregisterPerspective(np);
  }

  public void doDupPers() {
    Object sel = _persList.getSelectedValue();
    if (!(sel instanceof NavPerspective)) {
      System.out.println("doDupPers: unexepected non-NavPerspective");
      return;
    }
    NavPerspective np = (NavPerspective) sel;
    try {
      NavPerspective newNP = (NavPerspective) np.clone();
      NavPerspective.unregisterPerspective(newNP);
    }
    catch (CloneNotSupportedException cnse) {
      System.out.println("exception while cloning NavPerspective");
    }
  }

  public void doAddRule() {
    Object sel = _persList.getSelectedValue();
    if (!(sel instanceof NavPerspective)) {
      System.out.println("doAddRule: unexepected non-NavPerspective");
      return;
    }
    NavPerspective np = (NavPerspective) sel;
    Object selRule = _ruleLibList.getSelectedValue();
    if (!(selRule instanceof TreeModelPrereqs)) {
      System.out.println("doAddRule: unexepected non-TreeModelPrereqs");
      return;
    }
    TreeModelPrereqs tm = (TreeModelPrereqs) selRule;
    np.addSubTreeModel(tm);
    _rulesList.clearSelection();
    _rulesList.setListData(np.getSubTreeModels());
    //_ruleLibList.repaint();
    //_rulesList.repaint();
    repaint();
  }

  public void doRemoveRule() {
    Object sel = _persList.getSelectedValue();
    if (!(sel instanceof NavPerspective)) {
      System.out.println("doRemoveRule: unexepected non-NavPerspective");
      return;
    }
    NavPerspective np = (NavPerspective) sel;
    Object selRule = _rulesList.getSelectedValue();
    if (!(selRule instanceof TreeModelPrereqs)) {
      System.out.println("doRemoveRule: unexepected non-TreeModelPrereqs");
      return;
    }
    TreeModelPrereqs tm = (TreeModelPrereqs) selRule;
    np.removeSubTreeModel(tm);
    _rulesList.clearSelection();
    _rulesList.setListData(np.getSubTreeModels());
    //_ruleLibList.repaint();
    //_rulesList.repaint();
    repaint();
  }

  public void doSelectPers() {
    Object selPers = _persList.getSelectedValue();
    Object selRule = _ruleLibList.getSelectedValue();
    _removePersButton.setEnabled(selPers != null);
    _dupPersButton.setEnabled(selPers != null);
    if (selPers == null) return;
    NavPerspective np = (NavPerspective) selPers;
    _rulesList.setListData(np.getSubTreeModels());
    getContentPane().layout();
    getContentPane().validate();
    getContentPane().repaint();
    _addRuleButton.setEnabled(selPers != null && selRule != null);
  }

  public void doSelectLibRule() {
    Object selPers = _persList.getSelectedValue();
    Object selRule = _ruleLibList.getSelectedValue();
    _addRuleButton.setEnabled(selPers != null && selRule != null);
  }
  
  public void doSelectRule() {
    Object selPers = _persList.getSelectedValue();
    Object selRule = _rulesList.getSelectedValue();
    _removeRuleButton.setEnabled(selPers != null && selRule != null);
  }

  public void doOk() {
    ProjectBrowser pb = ProjectBrowser.TheInstance;
    NavigatorPane np = pb.getNavPane();
    np.setPerspectives(NavPerspective.getRegisteredPerspectives());
    np.updateTree();
    setVisible(false);
    dispose();
  }

  ////////////////////////////////////////////////////////////////
  // event handlers
  
  public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();
    if (src == _okButton) doOk();
    else if (src == _newPersButton) doNewPers();
    else if (src == _removePersButton) doRemovePers();
    else if (src == _dupPersButton) doDupPers();
    else if (src == _addRuleButton) doAddRule();
    else if (src == _removeRuleButton) doRemoveRule();
    else if (src == _ruleLibList) doAddRule();
    else if (src == _rulesList) doRemoveRule();
  }


  public void stateChanged(ChangeEvent ce) {
    Object src = ce.getSource();
    System.out.println("stateChanged " + src);
  }


  /** Called when the user changes selections in a list. */
  public void valueChanged(ListSelectionEvent lse) {
    if (lse.getValueIsAdjusting()) return;
    Object src = lse.getSource();
    if (src == _persList) doSelectPers();
    else if (src == _ruleLibList) doSelectLibRule();
    else if (src == _rulesList) doSelectRule();
  }

  public void mousePressed(MouseEvent me) { }
  public void mouseReleased(MouseEvent me) { }
  public void mouseEntered(MouseEvent me) { }
  public void mouseExited(MouseEvent me) { }
  public void mouseClicked(MouseEvent me) {
    Object src = me.getSource();
    if (me.getClickCount() != 2) return;
    if (src == _ruleLibList && _addRuleButton.isEnabled()) doAddRule();
    if (src == _rulesList && _removeRuleButton.isEnabled()) doRemoveRule();
  }
  
} /* end class NavigatorConfigDialog */

