package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.model.*;
import shared.communication.*;
import server.*;

public class validateUserHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("big server"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Object[] o = (Object[])xmlStream.fromXML(exchange.getRequestBody());
		userToken t = (userToken)o[0];
		authToken a;
		
		
		try {
			a = facade.validateUser(t);
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
	    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(a, exchange.getResponseBody());
		exchange.getResponseBody().close();
		
	}
}
