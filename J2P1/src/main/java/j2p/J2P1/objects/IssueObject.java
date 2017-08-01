package j2p.J2P1.objects;

public class IssueObject {
	
	private String id; // Id for the task in JIRA
	private String key; // Key for the task ie CTF-4567
	private String type; // Type i.e. story, epic, technical task, etc.
	private boolean subtask; // Subtask
	private String timeSpent; // Time spent on an issue
	private String user; // Asignee's username
	private String displayName; // Asignee's display name
	private double points; // How much the story is worth
	private String summary; // Name of task
	private String status; // Closed, open, future
	private int priority; // Higher number means more important
	private String priorityName; // Priority name
	private String reporterName; // Reporter's name
	private SprintObject so;
	
	private String updated;
	private String month;
	
	public IssueObject(String id, String key, String type, boolean subtask, String timeSpent, String user,
			String displayName, String reporterName, double points, String summary, String status, int priority,
			String priorityName, String updated) {
		this.id = id;
		this.key = key;
		this.type = type;
		this.subtask = subtask;
		this.timeSpent = timeSpent;
		this.user = user;
		this.displayName = displayName;
		this.points = points;
		this.summary = summary;
		this.status = status;
		this.priority = priority;
		this.priorityName = priorityName;
		this.reporterName = reporterName;
		this.updated = parseDate(updated);
	}
	
	public IssueObject(String id, String key, String type, boolean subtask, String timeSpent, String user,
			String displayName, String reporterName, String summary, String status, int priority,
			String priorityName, String updated) {
		this.id = id;
		this.key = key;
		this.type = type;
		this.subtask = subtask;
		this.timeSpent = timeSpent;
		this.user = user;
		this.displayName = displayName;
		this.points = 0;
		this.summary = summary;
		this.status = status;
		this.priority = priority;
		this.priorityName = priorityName;
		this.reporterName = reporterName;
		this.updated = parseDate(updated);
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
		day = "" + c[5] + c[6] + "/" + c[8] + c[9] + "/" + c[0] + c[1] + c[2] + c[3];
		month = "" + c[5] + c[6];
		
		return day;
	}

	public String getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean getSubtask() {
		return subtask;
	}
	
	public String getTimeSpent() {
		return timeSpent;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public double getPoints() {
		return points;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public String getPriorityName() {
		return priorityName;
	}
	
	public String getReporterName() {
		return reporterName;
	}
	
	public SprintObject getSprintObject() {
		return so;
	}
	
	public void setSprintObject(SprintObject so) {
		this.so = so;
	}
	
	public String getUpdated() {
		return updated;
	}
	
	public String getMonth() {
		return month;
	}
	
}
