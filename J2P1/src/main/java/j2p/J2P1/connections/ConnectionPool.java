package j2p.J2P1.connections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectionPool {
	private HashMap<String, Connection> cp = new HashMap<String, Connection>();
	
	public void addToPool(String key, Connection connection) {
		cp.put(key, connection);
	}
	
	public Connection getConnection(String key) {
		return cp.get(key);
	}
	
	public List<Connection> findType(String desirable) {
		List<Connection> connections = new ArrayList<Connection>();
		
		for(String key : cp.keySet()) {
			if(key.contains(desirable)) {
				connections.add(cp.get(key));
			}
		}
		
		return connections;
	}
	
	public boolean doesConnectionExist(String key) {
		return cp.containsKey(key);
	}
	
	public boolean clearPool() {
		try {
			cp.clear();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
