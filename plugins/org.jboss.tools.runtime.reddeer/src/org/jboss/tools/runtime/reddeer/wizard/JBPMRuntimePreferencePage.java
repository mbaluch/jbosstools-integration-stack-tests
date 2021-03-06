package org.jboss.tools.runtime.reddeer.wizard;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;

/**
 * 
 * @author apodhrad
 *
 */
public class JBPMRuntimePreferencePage extends WorkbenchPreferencePage {

	public JBPMRuntimePreferencePage() {
		super("JBoss jBPM", "Runtime Locations");
	}

	public JBPMRuntimeWizard addRuntime() {
		new PushButton("Add...").click();
		return new JBPMRuntimeWizard();
	}

	public List<String> getJBPMRuntimes() {
		List<String> jbpmRuntimes = new ArrayList<String>();
		List<TableItem> items = new DefaultTable().getItems();
		for (TableItem item : items) {
			jbpmRuntimes.add(item.getText(0));
		}
		return jbpmRuntimes;
	}
}
