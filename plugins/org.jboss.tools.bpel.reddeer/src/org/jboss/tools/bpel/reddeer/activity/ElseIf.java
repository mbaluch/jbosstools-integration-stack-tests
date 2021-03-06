package org.jboss.tools.bpel.reddeer.activity;

import org.jboss.tools.bpel.reddeer.view.BPELPropertiesView;

/**
 * 
 * @author apodhrad
 * 
 */
public class ElseIf extends ContainerActivity {

	public ElseIf(If parent) {
		this(parent, 0);
	}

	public ElseIf(If parent, int index) {
		super(null, "ElseIf", parent, index);
	}

	public ElseIf setCondition(String condition) {
		BPELPropertiesView properties = new BPELPropertiesView();
		properties.setCondition(condition);
		return this;
	}

}
