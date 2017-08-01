package j2p.J2P1.objects;

public class BoardObject {
	private int id;
	private String boardName;
	private String type;
	
	public BoardObject(int id, String name, String type) {
		this.id = id;
		this.boardName = name;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return boardName;
	}
	
	public String getType() {
		return type;
	}
	
}
