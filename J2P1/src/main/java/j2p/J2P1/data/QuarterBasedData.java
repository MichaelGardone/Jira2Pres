package j2p.J2P1.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import j2p.J2P1.objects.IssueObject;
import j2p.J2P1.objects.SprintObject;
import j2p.J2P1.translators.time.SprintTime;

public class QuarterBasedData {
	
	private List<IssueObject> issues;
	
	// All points in each month
	private double[][] totalPoints 		= null;
	private double[][] totalClosedPoints = null;
	
	private double[][] ticketsPerDay;
	
	private int[][] totalTickets;
	private int numOfSprints = 0;
	
	private int[][] closedTickets;
	private long[][] numOfDays;
	
	private HashMap<Integer, List<IssueObject>> issuesPerSprint = new HashMap<Integer, List<IssueObject>>();

	private int[][] openTickets;
	
	private int bugTypes;
	
	public QuarterBasedData(List<IssueObject> issues, int bugTypes) {
		this.issues = issues;
		this.bugTypes = bugTypes;
	}
	
	public void preCheck() {
		List<String> sprints = new ArrayList<String>();
		for(IssueObject io : issues) {
			if(!sprints.contains(io.getSprintObject().getName().replace(' ', '-').toLowerCase())) {
				sprints.add(io.getSprintObject().getName().replace(' ', '-').toLowerCase());
			}
		}
		
		for(String s : sprints) {
			List<IssueObject> temp = new ArrayList<IssueObject>();
			for(IssueObject o : issues) {
				if(s.equals(o.getSprintObject().getName().replace(' ', '-').toLowerCase())) {
					temp.add(o);
				}
			}
			issuesPerSprint.put(numOfSprints, temp);
			numOfSprints++;
		}
		closedTickets = new int[issuesPerSprint.size()][bugTypes];
		openTickets   = new int[issuesPerSprint.size()][bugTypes];
		ticketsPerDay = new double[issuesPerSprint.size()][12];
		totalTickets = new int[issuesPerSprint.size()][12];
		numOfDays = new long[issuesPerSprint.size()][12];
		totalPoints 	  = new double[issuesPerSprint.size()][12];
		totalClosedPoints = new double[issuesPerSprint.size()][12];
	}
	
	public void parseData() {
		for(int i = 0; i<issuesPerSprint.size(); i++) {
			List<IssueObject> temp = issuesPerSprint.get(i);
			for(IssueObject io : temp) {
				totalPoints[i][Integer.parseInt(io.getSprintObject().getStartMonth())-1]+=io.getPoints();
				if(io.getStatus().toLowerCase().equals("done")) {
					totalClosedPoints[i][Integer.parseInt(io.getSprintObject().getStartMonth())-1]+=io.getPoints();
					closedTickets[i][io.getPriority()-1]++;
				} else {
					openTickets[i][io.getPriority()-1]++;
				}
				totalTickets[i][Integer.parseInt(io.getMonth())-1]++;
			}
		}
	}
	
	public void calculateSprintLengths(List<SprintObject> so, boolean incldWknd,int numOfWeekendDays) {
		for(int i=0; i<so.size();i++) {
			SprintTime st = new SprintTime(so.get(i));
			numOfDays[i][Integer.parseInt(so.get(i).getStartMonth())-1]+=st.determineLength(incldWknd, numOfWeekendDays);
		}
	}
	
	public int[][] totalTickets() {
		return totalTickets;
	}
	
	public double[][] getTotalPoints() {
		return totalPoints;
	}
	
	public double[][] getClosedPoints() {
		return totalClosedPoints;
	}
	
	public double[][] getTicketsPerDay() {
		return ticketsPerDay;
	}
	
	public int[][] getClosedTickets() {
		return closedTickets;
	}
	
	public int[][] getOpenTickets() {
		return openTickets;
	}
	
	public long[][] getNumOfDays() {
		return numOfDays;
	}
	
	public int getNumOfSprints() {
		return numOfSprints;
	}
	
	public HashMap<Integer, List<IssueObject>> getIssuesPerSprint() {
		return issuesPerSprint;
	}
	
}
