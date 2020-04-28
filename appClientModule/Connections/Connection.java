package Connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Connection {
	
	private String SERVICE_URL = "http://localhost:8081/SampleWS/view";	
	private static Connection singletonCon = null;
	
	private Connection ( ) {		
	}
	
	public static Connection getConnection () {
		if (singletonCon == null) {
			singletonCon = new Connection ();
		}		
		return singletonCon;
	}	
	
	
	public String getMethodCall (String fstNum, String scndNum, String operation) throws IOException {
		
		String PARA_URL = SERVICE_URL+"?FirstNumber="+fstNum+"&SecondNumber="+scndNum+"&Operation="+operation;
				
		URL obj = new URL(PARA_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append("\n"+inputLine);
		}
		in.close();

		return response.toString();		
	}
	
	public String postMethodCall (String fstNum, String scndNum, String operation) throws IOException {
		
		var values = new HashMap<String, String>() {{
            put("FirstNumber", fstNum);
            put("SecondNumber", scndNum);
            put("Operation", operation);
        }};

        var objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(values);
		URL obj = new URL(SERVICE_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);
		
		OutputStream os = con.getOutputStream();
		byte[] requestBody = request.getBytes("utf-8");
		os.write(requestBody, 0, requestBody.length);           
		int responseCode = con.getResponseCode();	
		
		String responseLine = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		StringBuilder response = new StringBuilder();
		
		while ((responseLine = br.readLine()) != null) {
			response.append("\n" + responseLine.trim());
		}		
		return response.toString();
	}

}
