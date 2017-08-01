package j2p.J2P.connections;

public class ConnectionURLs {
	
	//
	public String getBoards(String base) {
		return base + "rest/agile/1.0/board";
	}
	
	// Conenct to a specific board and get the sprints
	public String getSprints(String base, int boardId) {
		return base + "rest/agile/1.0/board/"+ boardId +"/sprint";
	}
	
	// Connect to a board's sprint to get issues
	public String getIssues(String base, int boardId, int sprintId) {
		return base + "rest/agile/1.0/board/" + boardId + "/sprint/"+ sprintId +"/issue";
	}
	
	// Get priorities
	public String getPriorities(String base) {
		return base + "rest/api/2/priority";
	}
	
	// Get task types
	public String getTaskTypes(String base) {
		return base + "rest/api/2/issuetype";
	}
	
	// Get time lengths
	public String getTaskLengthDeterminer(String base) {
		return base + "rest/api/2/field";
	}
	
}
