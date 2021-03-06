package org.jboss.tools.bpmn2.ui.bot.test.testcase.editor;

import java.util.Arrays;

import org.jboss.tools.bpmn2.reddeer.editor.ElementType;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.Message;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.Process;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.ScriptTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.MessageStartEvent;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.StartEvent;
import org.jboss.tools.bpmn2.ui.bot.test.JBPM6BaseTest;
import org.jboss.tools.bpmn2.ui.bot.test.jbpm.TriggeredNodesListener;
import org.jboss.tools.bpmn2.ui.bot.test.requirements.ProcessDefinitionRequirement.ProcessDefinition;

import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkflowProcessInstance;

@ProcessDefinition(name="BPMN2-MessageStart", project="EditorTestProject")
public class MessageStartTest extends JBPM6BaseTest {

	private String changedVar = "";
	
	@Override
	public void buildProcessModel() {
		Process process = new Process("BPMN2-MessageStart");
		process.addLocalVariable(VARIABLE1, "String");
		process.addMessage("HelloMessage", "String");
		
		new StartEvent("StartProcess").delete();
		process.add("StartProcess", ElementType.MESSAGE_START_EVENT);
		
		MessageStartEvent start = new MessageStartEvent("StartProcess");
		start.setMessageMapping(new Message("HelloMessage", "String"), VARIABLE1);
		start.append("Script", ElementType.SCRIPT_TASK);
		
		ScriptTask script = new ScriptTask("Script");
		script.setScript("Java", "System.out.println(" + VARIABLE1 + ");");
		script.append("EndProcess", ElementType.TERMINATE_END_EVENT);
	}

	@Override
	public void assertRunOfProcessModel(KieSession kSession) {
 		TriggeredNodesListener triggered = new TriggeredNodesListener(
			Arrays.asList("StartProcess", "Script", "EndProcess"), null); 
		kSession.addEventListener(triggered);
		
		kSession.addEventListener(new DefaultProcessEventListener() {
			public void afterProcessStarted(ProcessStartedEvent event) {
			
				changedVar = (String) ((WorkflowProcessInstance) event.getProcessInstance()).getVariable(VARIABLE1);
			}
		});
		
		
		kSession.signalEvent("Message-HelloMessage", "bpmn rules world");
		
		assertEquals("Variable "+VARIABLE1+" schould change because of start message", "bpmn rules world", changedVar);
		assertEquals("There shouldn't be a active process instance", 0, kSession.getProcessInstances().size());

	}
}