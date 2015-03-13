package server;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.*;
import java.util.*;

import com.sun.net.httpserver.*;

import shared.model.*;
import shared.communication.*;
import server.*;

public class fileHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("big server"); 
	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String loc = new String();

		try {
			loc = exchange.getRequestURI().toString();
			loc =  "data" + loc;
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(loc));
			BufferedOutputStream os = 
					new BufferedOutputStream(exchange.getResponseBody());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			int b_y_t_e;
			while((b_y_t_e = is.read()) != -1){
				os.write(b_y_t_e);
			}
			os.flush();
			os.close();
			is.close();
			exchange.getResponseBody().close();
	
		}
		catch (FileNotFoundException e){
			System.out.println("Bad file request:");
			System.out.println(loc.substring(4));
			exchange.sendResponseHeaders(404, -1);
			return;
		}
		catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}		
	}
}
