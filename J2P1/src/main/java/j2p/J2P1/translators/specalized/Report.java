package j2p.J2P1.translators.specalized;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import j2p.J2P1.data.GlobalData;
import j2p.J2P1.objects.IssueObject;
import j2p.J2P1.objects.PriorityObject;
import j2p.J2P1.objects.SprintObject;
import j2p.J2P1.objects.TaskObject;

public abstract class Report {
	protected List<IssueObject> issues = new ArrayList<IssueObject>();
	protected GlobalData gd = null;
	
	public Report(List<IssueObject> issues, List<TaskObject> tasks, List<PriorityObject> priorities) {
		this.issues = issues;
		gd = new GlobalData();
		gd.loadPriorities(priorities);
		gd.loadTaskTypes(tasks);
		gd.parseIssues(issues);
	}
	
	public abstract void writeReport(XMLSlideShow ss, SprintObject so);
	
}
