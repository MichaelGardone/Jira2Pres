package j2p.J2P.connections;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public abstract class Connection {
	//"tsmith", "Tman1234"
	protected Client client;
	protected HttpServer server;
	protected WebTarget target;
	
	protected HttpAuthenticationFeature auth = null;
	
	protected ConnectionURLs curls = new ConnectionURLs();
	protected String url = "";
	protected String user = "";
	protected String pass = "";
	
	public Connection(String url, String user, char[] pass) {
		this.url = url;
		this.user = user;
		String temp = "";
		for(char c : pass) {
			temp += c;
		}
		this.pass = temp;
	}
	
	public Connection(String url, String user, String pass) {
		this.url = url;
		this.user = user;
		this.pass = pass;
	}
	
	public abstract String connect();
	public abstract String connect(int id);
	public abstract String connect(int id, int sprintId);
	
	public void shutDown() {
		server.shutdown();
		client.close();
	}
	
	public Client getClient() {
		return client;
	}
	
	public WebTarget getTarget() {
		return target;
	}
	
	public String getResult() {
		return target.request(MediaType.APPLICATION_JSON).get(String.class);
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPass() {
		return pass;
	}
	
}
