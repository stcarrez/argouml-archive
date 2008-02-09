// $Id$
// Copyright (c) 2003-2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

/*
 * SystemInfoDialog.java
 */

package org.argouml.ui;

import java.awt.Frame;
import java.awt.Insets;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.argouml.application.helpers.ApplicationVersion;
import org.argouml.i18n.Translator;
import org.argouml.util.ArgoDialog;

/**
 * Display System Information (JDK Version, JDK Vendor, etc).
 * A Copy to System Clipboard button is provided to help generate bug reports.
 *
 * @author Eugenio Alvarez
 */
public class SystemInfoDialog extends ArgoDialog {

    /**
     * Version generated by Eclipse for rev. 1.12
     */
    private static final long serialVersionUID = 1595302214402366939L;

    /** Insets in pixels  */
    private static final int INSET_PX = 3;

    private JTextArea   info = new JTextArea();
    private JButton     runGCButton = new JButton();
    private JButton     copyButton = new JButton();

    /**
     * The constructor.
     *
     * @param owner - ignored - for backward compatibility only
     * @param modal true if the dialog is modal
     * @deprecated for 0.25.1 by tfmorris - 
     * use {@link #SystemInfoDialog(boolean)}
     */
    public SystemInfoDialog(Frame owner, boolean modal) {
        this(modal);
    }
    
    /**
     * The constructor.
     * 
     * @param modal true if the dialog is modal
     */
    public SystemInfoDialog(boolean modal) {
	super(Translator.localize("dialog.title.system-information"),
		ArgoDialog.CLOSE_OPTION, modal);

	info.setEditable(false);
	info.setMargin(new Insets(INSET_PX, INSET_PX, INSET_PX, INSET_PX));

	runGCButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		runGCActionPerformed(e);
	    }
	});
	copyButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		copyActionPerformed(e);
	    }
	});

	nameButton(copyButton, "button.copy-to-clipboard");
	nameButton(runGCButton, "button.run-gc");
	addButton(copyButton, 0);
	addButton(runGCButton, 0);
	setContent(new JScrollPane(info));
	updateInfo();
	addWindowListener(new WindowAdapter() {
	    public void windowActivated(WindowEvent e) {
		updateInfo();
	    }
	});
        pack();
    }

    /**
     * Run garbage collector.
     * 
     * @param e the action
     */
    private void runGCActionPerformed(ActionEvent e) {
        assert e.getSource() == runGCButton;
	Runtime.getRuntime().gc();
	updateInfo();
    } // end runGC_actionPerformed()

    /**
     * Copy system info to clipboard.
     * 
     * @param e the action
     */
    private void copyActionPerformed(ActionEvent e) {
        assert e.getSource() == copyButton;
	String infoText = info.getText();
	StringSelection contents = new StringSelection(infoText);
	Clipboard clipboard = getToolkit().getSystemClipboard();
	clipboard.setContents(contents, defaultClipboardOwner);
    } // end copy_actionPerformed()

    void updateInfo() {
	info.setText(getInfo());
    } //end updateInfo()
    
    /**
     * Collect system information.
     * @return string containing a variety of system info
     */
    public static String getInfo() {
        StringBuffer s = new StringBuffer();
        s.append("ArgoUML version       : ");
        s.append(ApplicationVersion.getVersion() + "\n");
        s.append("Java Version          : ");
        s.append(System.getProperty("java.version", "") + "\n");
        s.append("Java Vendor           : ");
        s.append(System.getProperty("java.vendor", "") + "\n");
        s.append("Java Vendor URL       : ");
        s.append(System.getProperty("java.vendor.url", "") + "\n");
        s.append("Java Home Directory   : ");
        s.append(System.getProperty("java.home", "") + "\n");
        s.append("Java Classpath                : ");
        s.append(System.getProperty("java.class.path", "") + "\n");
        s.append("Operation System      : ");
        s.append(System.getProperty("os.name", ""));
        s.append(", Version ");
        s.append(System.getProperty("os.version", "") + "\n");
        s.append("Architecture          : ");
        s.append(System.getProperty("os.arch", "") + "\n");
        s.append("User Name             : ");
        s.append(System.getProperty("user.name", "") + "\n");
        s.append("User Home Directory   : ");
        s.append(System.getProperty("user.home", "") + "\n");
        s.append("Current Directory     : ");
        s.append(System.getProperty("user.dir", "") + "\n");
        s.append("JVM Total Memory      : ");
        s.append(String.valueOf(Runtime.getRuntime().totalMemory()) + "\n");
        s.append("JVM Free Memory       : ");
        s.append(String.valueOf(Runtime.getRuntime().freeMemory()) + "\n");
        return s.toString();
    }

    private static ClipboardOwner defaultClipboardOwner =
	new ClipboardObserver();

    static class ClipboardObserver implements ClipboardOwner {
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
    }

} /* end class SystemInfoDialog */
