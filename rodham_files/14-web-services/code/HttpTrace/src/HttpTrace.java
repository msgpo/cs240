
import java.io.*;
import java.net.*;

public class HttpTrace {

	private String urlSpec;
	
	public HttpTrace(String urlSpec) {
		this.urlSpec = urlSpec;
	}
	
	public void run() {
		try {
			URL url = new URL(urlSpec);
			
			if (url.getProtocol().equalsIgnoreCase("http")) {
				
				String host = url.getHost();
				int port = (url.getPort() >= 0 ? url.getPort() : url.getDefaultPort());
				String path = url.getPath();
				
				StringBuilder request = new StringBuilder();
				request.append(String.format("GET %s HTTP/1.0\r\n", path));
				request.append(String.format("Host: %s:%d\r\n", host, port));
				request.append("\r\n");
				
				Socket socket = null;
				try {
					socket = new Socket(host, port);
					
					PrintWriter output = new PrintWriter(socket.getOutputStream());
					InputStream input = socket.getInputStream();
					
					output.print(request.toString());
					output.flush();
					
					String line;
					do {
						line = IOUtils.readLine(input);
						System.out.println(line);
					}
					while (line.length() > 0);
					
					byte[] buffer = new byte[1024];
					int bytes;
					while ((bytes = input.read(buffer)) >= 0) {
						String text = new String(buffer, 0, bytes);
						System.out.print(text);
					}
					
					System.out.println();
				}
				finally {
					IOUtils.safeClose(socket);
				}
			}
			else {
				System.out.println("Error: Only HTTP URLs are supported");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		String urlSpec = "";
		
		if (args.length == 1) {
			urlSpec = args[0];
		}
		
		if (urlSpec.length() > 0) {
			HttpTrace trace = new HttpTrace(urlSpec);
			trace.run();
		}
		else {
			System.out.println("USAGE: java HttpTrace <url>");
		}
	}

}
