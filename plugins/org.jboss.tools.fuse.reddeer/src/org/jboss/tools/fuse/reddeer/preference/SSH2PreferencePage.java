package org.jboss.tools.fuse.reddeer.preference;

import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;

/**
 * Represents the SSH2 (Network Connections) preference page
 * 
 * @author tsedmik
 */
public class SSH2PreferencePage extends WorkbenchPreferencePage {
	
	private static final String SSH2HOME = "SSH2 home:";

	public SSH2PreferencePage() {
		super("General", "Network Connections", "SSH2");
	}

	public void setSSH2Home(String path) {
		new LabeledText(SSH2HOME).setText(path);
	}
}
