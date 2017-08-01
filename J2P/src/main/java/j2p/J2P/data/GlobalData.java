package j2p.J2P.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.PriorityObject;
import j2p.J2P.objects.TaskObject;

public class GlobalData {
	
	// Store priorities loaded from a file
	public HashMap<String, Integer> priorityDict = new HashMap<String, Integer>();
	public HashMap<String, Integer> tasksDict = new HashMap<String, Integer>();
	
	// Statistics
	public int totalPoints  = 0;
	public int pointsCompl  = 0;
	public int pointIncmpl  = 0;
	public int totalIssues  = 0;
	public int closedIssue  = 0;
	public int openIssues   = 0;
	public int[] totalTasks = null;
	public int[] priorities = null;
	
	public List<String> priorityNames = new ArrayList<String>();
	
	public void loadPriorities(List<PriorityObject> priorities) {
		for(PriorityObject po : priorities) {
			priorityDict.put(po.getName(), po.getPriorityLevel()-1);
			priorityNames.add(po.getName());
		}
	}
	
	public void loadTaskTypes(List<TaskObject> tasks) {
		for(int i=0; i<tasks.size(); i++) {
			tasksDict.put(tasks.get(i).getName(), i);
		}
	}
	
	public void parseIssues(List<IssueObject> issues) {
		priorities = new int[priorityDict.size()];
		totalTasks = new int[tasksDict.size()];
		
		for(IssueObject obj : issues) {
			totalIssues++;
			totalPoints += obj.getPoints();
			if(obj.getStatus().toLowerCase().equals("done")) {
				pointsCompl+=obj.getPoints();
				closedIssue++;
			} else {
				pointIncmpl+=obj.getPoints();
				openIssues++;
			}
			totalTasks[tasksDict.get(obj.getType())]++;
			priorities[priorityDict.get(obj.getPriorityName())]++;
		}
	}
	
}
