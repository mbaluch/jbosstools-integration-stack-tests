package org.jboss.tools.bpmn2.reddeer.properties.jbpm;

import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.tools.bpmn2.reddeer.DefaultCheckBox;
import org.jboss.tools.bpmn2.reddeer.DefaultCombo;
import org.jboss.tools.bpmn2.reddeer.editor.dialog.jbpm.ImplementationDialog;
import org.jboss.tools.bpmn2.reddeer.editor.dialog.jbpm.MessageDialog;
import org.jboss.tools.bpmn2.reddeer.editor.dialog.jbpm.OperationDialog;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.ErrorRef;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.Message;

/**
 *
 */
public class SendTaskTab extends GeneralPropertiesTab {

	/**
	 * 
	 * @param implementationUri
	 */
	public void setImplementation(String implementationUri) {
		DefaultCombo combo = new DefaultCombo("Implementation");
		if (!combo.contains(implementationUri)) {
			new PushButton(0).click();
			new ImplementationDialog().add(implementationUri);
		}
		combo.setSelection(implementationUri);
	}
	
	/**
	 * 
	 * @param operationContractName
	 * @param inMessage
	 * @param outMessage
	 * @param errorRef
	 */
	public void setOperation(String operationContractName, Message inMessage, Message outMessage, ErrorRef errorRef) {
		setOperation(operationContractName.split("/")[0], operationContractName.split("/")[1], inMessage, outMessage, errorRef);
	}
	
	/**
	 * 
	 * @param interfaceName
	 * @param operationName
	 * @param inMessage
	 * @param outMessage
	 * @param errorRef
	 */
	public void setOperation(String interfaceName, String operationName, Message inMessage, Message outMessage, ErrorRef errorRef) {
		DefaultCombo combo = new DefaultCombo("Operation");
		if (!combo.contains(interfaceName + "/" + operationName)) {
			new PushButton(2).click();
			new OperationDialog().addOperation(operationName, inMessage, outMessage, errorRef);
		}
		combo.setSelection(interfaceName + "/" + operationName);
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setMessage(Message message) {
		DefaultCombo combo = new DefaultCombo("Message");
		String comboItem = message.getName() + "(" + message.getDataType()  + ")";
		if (!combo.contains(comboItem)) {
			new PushButton(4).click();
			new MessageDialog().add(message);
		}
		combo.setSelection(comboItem);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setIsForCompensation(boolean value) {
		new DefaultCheckBox("Is For Compensation").setChecked(value);
	}
		
}
