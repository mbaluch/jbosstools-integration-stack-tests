package org.jboss.tools.modeshape.ui.bot.test.suite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.tools.modeshape.reddeer.view.ServerPreferencePage;
import org.jboss.tools.modeshape.reddeer.wizard.ServerWizard;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * ModeShape Test Suite
 * 
 * @author apodhrad
 *
 */
public class ModeshapeSuite extends RedDeerSuite {

	public static final String PROPERTIES_FILE = "swtbot.test.properties.file";

	private static String serverName;
	private static String serverPath;
	
	private static final String TEIID_DRIVER_PATH_sinceDV6 ="/dataVirtualization/jdbc/";

	public ModeshapeSuite(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		super(clazz, foo(builder));
	}

	private static RunnerBuilder foo(RunnerBuilder builder) {
		Properties props = loadSWTBotProperties();
		addServer(props.getProperty("SERVER"));
		closeWelcomePage();
		return builder;
	}

	protected static void closeWelcomePage() {
		for (IViewReference viewReference : WorkbenchLookup.findAllViews()) {
			if (viewReference.getPartName().equals("Welcome")) {
				final IViewReference iViewReference = viewReference;
				Display.syncExec(new Runnable() {
					@Override
					public void run() {
						iViewReference.getPage().hideView(iViewReference);
					}
				});
				break;
			}
		}
	}

	public static String getServerName() {
		return serverName;
	}
	public static String getServerPath() {
		return serverPath;
	}
	
	public static String getModeshapeRepository() {
		Properties props = loadSWTBotProperties();
		return props.getProperty("MODESHAPE");
	}

	private static Properties loadSWTBotProperties() {
		Properties props = new Properties();
		String propsFile = System.getProperty(PROPERTIES_FILE);
		if (propsFile != null) {
			try {
				props.load(new FileReader(propsFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Couldn't find properties file!");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("I/O excpetion during reading properties file!");
			}
		}
		return props;
	}

	private static void addServer(String serverConfig) {
		if (serverConfig == null) {
			return;
		}
		String[] param = serverConfig.split(",");
		if (param.length < 4) {
			throw new RuntimeException("Bad format of SERVER config");
		}
		String type = param[0];
		String version = param[1];
		String path = new File(param[3]).getAbsolutePath();

		serverName = type + "-" + version;
		serverPath = path;

		ServerPreferencePage serverPP = new ServerPreferencePage();
		serverPP.open();
		serverPP.addServerRuntime(serverName, path, getServerRuntime(type, version));
		serverPP.ok();

		ServerWizard serverWizard = new ServerWizard();
		serverWizard.setType(getServerType(type, version));
		serverWizard.setName(serverName);
		serverWizard.execute();
	}

	private static String[] getServerType(String type, String version) {
		String[] serverType = new String[2];
		if (type.equals("EAP")) {
            serverType[0] = "Red Hat JBoss Middleware";
            if (version.startsWith("6.0")) {
                    serverType[1] = "JBoss Enterprise Application Platform 6.0";
            }
            if (version.startsWith("6.1")) {
                    serverType[1] = "JBoss Enterprise Application Platform 6.1+";
            }
		} else if (type.equals("SOA")) {
			serverType[0] = "Red Hat JBoss Middleware";
			if (version.startsWith("5")) {
				serverType[1] = "JBoss Enterprise Application Platform 5.x";
			} if (version.startsWith("6")) {
                serverType[1] = "JBoss Enterprise Application Platform 6.1+";
			}
		} else if (type.equals("DV")) {
			serverType[0] = "Red Hat JBoss Middleware";
			if (version.startsWith("6.1.0")) {
				serverType[1] = "JBoss Enterprise Application Platform 6.1+";
			}
		} else if (type.equals("AS")) {
			serverType[0] = "JBoss Community";
			serverType[1] = "JBoss AS " + version;
		} else {
			throw new RuntimeException("You have to specify if it is AS, EAP, SOA or DV");
		}
		return serverType;
	}

	private static String[] getServerRuntime(String type, String version) {
		String[] serverRuntime = new String[2];
		if (type.equals("EAP")) {
            serverRuntime[0] = "Red Hat JBoss Middleware";
            if (version.startsWith("6.0")) {
                    serverRuntime[1] = "JBoss Enterprise Application Platform 6.0 Runtime";
            }
            if (version.startsWith("6.1")) {
                    serverRuntime[1] = "JBoss Enterprise Application Platform 6.1+ Runtime";
            }
		} else if (type.equals("SOA")) {
			serverRuntime[0] = "Red Hat JBoss Middleware";
			if (version.startsWith("5")) {
				serverRuntime[1] = "JBoss Enterprise Application Platform 5.x Runtime";
			} else if (version.startsWith("6")) {
                serverRuntime[1] = "JBoss Enterprise Application Platform 6.1+ Runtime";
			}
		} else if (type.equals("DV")) {
			serverRuntime[0] = "Red Hat JBoss Middleware";
			if (version.startsWith("6.1.0")) {
                serverRuntime[1] = "JBoss Enterprise Application Platform 6.1+ Runtime";
			}
		} else if (type.equals("AS")) {
			serverRuntime[0] = "JBoss Community";
			serverRuntime[1] = "JBoss " + version + " Runtime";
		} else {
			throw new RuntimeException("You have to specify if it is AS, EAP, SOA or DV");
		}
		return serverRuntime;
	}
	
	public static String getDriverPath(String serverPath){
		String driverName = "";
		File dir = new File(serverPath + TEIID_DRIVER_PATH_sinceDV6);
		for(File file : dir.listFiles()) {
			if(file.getName().startsWith("teiid-") && file.getName().endsWith(".jar")){
		    	driverName = file.getName();
		    	break;
		    }
		}
		return TEIID_DRIVER_PATH_sinceDV6 + driverName;
	}
	
	
}
