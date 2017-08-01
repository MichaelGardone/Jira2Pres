package j2p.J2P.objects;

public class CustomFieldObject {
	
	private String name;
	private String key;
	private int id;
	
	public CustomFieldObject(String name, String key, int id) {
		this.name = name;
		this.key  = key;
		this.id   = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getId() {
		return id;
	}
	
	public String getIdAsString() {
		return id+"";
	}
	
}
