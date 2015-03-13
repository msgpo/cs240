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

public class getFieldsHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("big server"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Object[] o = (Object[])xmlStream.fromXML(exchange.getRequestBody());
		userToken t = (userToken)o[0];
		Integer id = -1;
		if(o.length > 1){
			id = (Integer)o[1];
		}
		fieldList fl;
		
		
		try {
			if(id > -1){
				// get all fields from project with id
				fl = facade.getFields(t, id);
			}
			else {
				// get all fields in DB
				fl = facade.getFields(t);
			}
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(fl, exchange.getResponseBody());
		exchange.getResponseBody().close();
		
	}
}
