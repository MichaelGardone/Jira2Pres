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
	
	private double[] ticketsPerDay = new double[12];
	
	private int[] totalTickets = new int[12];
	private int numOfSprints = 0;
	
	private int[] closedTickets;
	private long[] numOfDays = new long[12];
	
	private HashMap<Integer, List<IssueObject>> issueDict = new HashMap<Integer, List<IssueObject>>();

	private int[] openTickets;
	
	public QuarterBasedData(List<IssueObject> issues, int bugTypes) {
		this.issues = issues;
		closedTickets = new int[bugTypes];
		openTickets   = new int[bugTypes];
	}
	
	private void preCheck() {
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
			issueDict.put(numOfSprints, temp);
			numOfSprints++;
		}
		totalPoints 	  = new double[issueDict.size()][12];
		totalClosedPoints = new double[issueDict.size()][12];
	}
	
	public void parseData() {
		preCheck();
		
		for(int i = 0; i<issueDict.size(); i++) {
			List<IssueObject> temp = issueDict.get(i);
			for(IssueObject io : temp) {
				totalPoints[i][Integer.parseInt(io.getSprintObject().getStartMonth())-1]+=io.getPoints();
				if(io.getStatus().toLowerCase().equals("done")) {
					totalClosedPoints[i][Integer.parseInt(io.getSprintObject().getStartMonth())-1]+=io.getPoints();
					closedTickets[io.getPriority()-1]++;
				} else {
					openTickets[io.getPriority()-1]++;
				}
			}
		}
		
		for(IssueObject io : issues) {
			totalTickets[Integer.parseInt(io.getMonth())-1]++;
		}
	}
	
	public void calculateSprintLengths(List<SprintObject> so, boolean incldWknd,int numOfWeekendDays) {
		for(SprintObject s : so) {
			SprintTime st = new SprintTime(s);
			numOfDays[Integer.parseInt(s.getStartMonth())-1]+=st.determineLength(incldWknd, numOfWeekendDays);
		}
	}
	
	public int[] totalTickets() {
		return totalTickets;
	}
	
	public double[][] getTotalPoints() {
		return totalPoints;
	}
	
	public double[][] getClosedPoints() {
		return totalClosedPoints;
	}
	
	public double[] getTicketsPerDay() {
		return ticketsPerDay;
	}
	
	public int[] getClosedTickets() {
		return closedTickets;
	}
	
	public int[] getOpenTickets() {
		return openTickets;
	}
	
	public long[] getNumOfDays() {
		return numOfDays;
	}
	
	public int getNumOfSprints() {
		return numOfSprints;
	}
	
}
