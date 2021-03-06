package org.jboss.tools.teiid.reddeer.view;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.jboss.reddeer.swt.api.TreeItem;

public class SQLResult {

	public static final String STATUS_SUCCEEDED = "Succeeded";

	private TreeItem resultRow;

	public SQLResult(TreeItem resultRow) {
		this.resultRow = resultRow;
	}

	public String getStatus() {
		return resultRow.getCell(0);
	}

	public int getCount() {
		new SWTWorkbenchBot().cTabItem("Result1").activate();
		return new SWTWorkbenchBot().table().rowCount();
	}

}
