package j2p.J2P1.objects;

public class TaskObject {
	
	private String name;
	private boolean subtask = false; // False means not a subtask
	
	public TaskObject(String name, boolean subtask) {
		this.name = name;
		this.subtask = subtask;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getSubtask() {
		return subtask;
	}
	
}
