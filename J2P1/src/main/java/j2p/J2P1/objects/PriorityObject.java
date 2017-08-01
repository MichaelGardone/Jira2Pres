package j2p.J2P1.objects;

public class PriorityObject {
	
	private String name;
	private int priorityLevel;
	
	public PriorityObject(String name, int priorityLevel) {
		this.name = name;
		this.priorityLevel = priorityLevel;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriorityLevel() {
		return priorityLevel;
	}
	
}
