package org.jboss.tools.teiid.reddeer.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * 
 * @author apodhrad
 *
 */
public class ServerWizard extends NewWizardDialog {

	private String[] type;
	private String name;

	public ServerWizard() {
		super("Server", "Server");
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void execute() {
		open();

		selectType(type);
		
		new LabeledText("Server name:").setText(name);

		next();
		finish();
	}
	
	private void selectType(String[] type){
		String[] array = new String[type.length];
		System.arraycopy(type, 0, array, 0, array.length);
		
		try {
			
			new DefaultTreeItem(0, array).select();//eclipse kepler (0), eclipse juno (1)
			return;
		} catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		try {
			array[array.length-1] = type[array.length-1] + "+";
			new DefaultTreeItem(0, array).select();//eclipse kepler (0), eclipse juno (1)
			return;
		} catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
