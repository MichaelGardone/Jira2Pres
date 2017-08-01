package j2p.J2P.objects;

public class SprintObject {
	
	private int id;
	private String state;
	private String name;
	private String startDate;
	private String endDate;
	private String completeDate;
	
	private String startMonth;
	
	private BoardObject bo;
	
	public SprintObject(String name, String state, int id, String startDate, String endDate, String completeDate) {
		this.name 	      = name;
		this.id	  	      = id;
		this.state	      = state;
		this.startDate    = parseDate(startDate);
		this.endDate      = parseDate(endDate);
		this.completeDate = parseDate(completeDate);
	}
	
	// For inactive sprints
	public SprintObject(String name, String state, int id) {
		this.name 	      = name;
		this.id	  	      = id;
		this.state	      = state;
	}

	private String parseDate(String date) {
		char[] c = date.toCharArray();
		String day = "";
		
		for(int i=0;i<c.length;i++) {
			if(c[i]=='T') break;
			day+=c[i];
		}
		
		c = day.toCharArray();
		day = "";
		day = "" + c[5] + c[6] + "-" + c[8] + c[9] + "-" + c[0] + c[1] + c[2] + c[3];
		
		startMonth = "" + c[5] + c[6];
		
		return day;
	}
	
	public String getName() {
		return name;
	}
	
	public String getState() {
		return state;
	}
	
	public String getStart() {
		return startDate;
	}
	
	public String getEnd() {
		return endDate;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCompleteDate() {
		return completeDate;
	}
	
	public String getStartMonth() {
		return startMonth;
	}
	
	public void setBoardObject(BoardObject bo) {
		this.bo = bo;
	}
	
	public BoardObject getBoardObject() {
		return bo;
	}
	
}
