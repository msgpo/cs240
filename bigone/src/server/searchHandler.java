package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;
import java.util.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.model.*;
import shared.communication.*;
import server.*;

public class searchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("big server"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Object[] o = (Object[])xmlStream.fromXML(exchange.getRequestBody());
		userToken t = (userToken)o[0];
		searchProposal sp = (searchProposal)o[1];
		ArrayList<searchBlob> sb;
		
		try {
			sb = facade.search(t, sp);
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(sb, exchange.getResponseBody());
		exchange.getResponseBody().close();
		
	}
}
