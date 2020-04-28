import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import Connections.Connection;

public class Main {
	
	private String fstNum, scndNum, operation, method;
	private static Main singletonRef = null;
	
	private Main(String fstNum, String scndNum, String operation, String method) {
		this.fstNum = fstNum;
		this.scndNum = scndNum;
		this.operation = operation;
		this.method = method;
	}
	
	static Main getReference (String fstNum, String scndNum, String operation, String method) {
		if (singletonRef == null) {
			singletonRef = new Main (fstNum, scndNum, operation, method);
		}
		return singletonRef;
	}
	
	void setValues(String fstNum, String scndNum, String operation, String method) {
		this.fstNum = fstNum;
		this.scndNum = scndNum;
		this.operation = operation;
		this.method = method;
	}
	
	String getResult ( ) {
		String response = null;
		Connection con = Connection.getConnection ();
		
		if ("GET".equals(method.toUpperCase())){
			try {
				response = con.getMethodCall(fstNum, scndNum, operation);
			}
			catch (IOException e) {
				response = e.toString();
			}
			catch (Exception e) {
				response = e.toString();
			}
		}
		else if ("POST".equals(method.toUpperCase())){
			try {
				response = con.postMethodCall(fstNum, scndNum, operation);
			}
			catch (IOException e) {
				response = e.toString();
			}
			catch (Exception e) {
				response = e.toString();
			}
		}
		else {
			response = "Wrong Method Choosen. Supports GET and POST only";
		}
		return response;				
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner (System.in);
		boolean cont= true;	
		Main clinetApp = null;
		
		while (cont) {		
			System.out.print ("Please Enter Your First Number : ");
			String fstNum = scn.next();
			System.out.print ("\nPlease Enter Your Second Number :");
			String scndNum = scn.next();
			System.out.print ("\nPlease Enter Your Operation :");
			String operation = scn.next();
			System.out.print ("\nPlease Enter Your Method :");
			String method = scn.next();
			
			String encodedOperation = Base64.getEncoder().encodeToString(operation.getBytes());
			
			if (singletonRef == null) {
				clinetApp = getReference (fstNum, scndNum, encodedOperation, method);
			}
			else {
				clinetApp.setValues(fstNum, scndNum, encodedOperation, method);
			}
			
		    String ans = clinetApp.getResult ();
		    System.out.println (ans);
		    
		    System.out.print ("\nDo You Wish To Continue [true/false] :");
			cont = scn.nextBoolean();
			System.out.println ("");
		
		}
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	

}