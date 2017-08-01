package j2p.J2P.connections;

import java.io.IOException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class PrioritiesConnection extends Connection {

	public PrioritiesConnection(String url, String user, char[] pass) {
		super(url, user, pass);
	}

	@Override
	public String connect() {
		String result = "Success";
		//auth = HttpAuthenticationFeature.basic(user,pass);
		server = new HttpServer();
		try {
			server.start();
		} catch (IOException e) {
			return e.toString();
		}
		
		client = ClientBuilder.newClient();
		//client.register(auth);
		target = client.target(curls.getPriorities(url));
		
		try {
			target.request().get(String.class);
		} catch(NotAuthorizedException e) {
			return "401 Error - Unauthorized user. Please check your username and password to make sure they are correct.";
		} catch(ProcessingException e) {
			return "Issue trying to connect to get priorities.";
		}  catch(InternalServerErrorException e) {
			return "500 Error - You are having a connection issue. Please contact your SysAdmins, engineers, etc. who are in"
					+ " charge of your JIRA databases and ensure nothing fatal has happened.";
		} catch(ServiceUnavailableException e) {
			return "503 Error - Connection took too long. Please retry.";
		} catch (Exception e) {
			return e.toString();
		}
		
		return result; // succeed
	}

	@Override
	public String connect(int id) {
		return "Don't use me!";
	}

	@Override
	public String connect(int id, int sprintId) {
		return "Don't use me!";
	}
	
	
	
}
