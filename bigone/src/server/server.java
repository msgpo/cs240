package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.*;

public class server {

	private int port;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("big gay al's indexing server"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	
	private HttpServer hserver;
	
	/**
	 * create a new server object
	 * @param listenPort the port number to listen on
	 */
	private server(int listenPort) {
		port = listenPort;
	}
	
	private void run() {
		
		logger.info("Initializing Model");
		
		try {
			facade.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			hserver = HttpServer.create(new InetSocketAddress(port),
					MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		hserver.setExecutor(null); // use the default executor
		
		hserver.createContext("/validateUser", validateUserHandler);
		hserver.createContext("/getProjects", getProjectsHandler);
		hserver.createContext("/getSampleImage", getSampleImageHandler);
		hserver.createContext("/downloadBatch", downloadBatchHandler);
		hserver.createContext("/submitBatch", submitBatchHandler);
		hserver.createContext("/getFields", getFieldsHandler);
		hserver.createContext("/search", searchHandler);
		hserver.createContext("/", fileHandler);
		
		logger.info("Starting HTTP Server");

		hserver.start();
	}

	private HttpHandler validateUserHandler = new validateUserHandler();
	private HttpHandler getProjectsHandler = new getProjectsHandler();
	private HttpHandler getSampleImageHandler = new getSampleImageHandler();
	private HttpHandler downloadBatchHandler = new downloadBatchHandler();
	private HttpHandler submitBatchHandler = new submitBatchHandler();
	private HttpHandler getFieldsHandler = new getFieldsHandler();
	private HttpHandler searchHandler = new searchHandler();
	private HttpHandler fileHandler = new fileHandler();
	
	public static void main(String[] args) {
		new server(Integer.parseInt(args[0])).run();
	}

}
