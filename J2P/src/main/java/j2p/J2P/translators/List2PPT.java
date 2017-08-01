package j2p.J2P.translators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.PriorityObject;
import j2p.J2P.objects.SprintObject;
import j2p.J2P.objects.TaskObject;
import j2p.J2P.translators.specalized.QuarterlyReport;
import j2p.J2P.translators.specalized.Report;
import j2p.J2P.translators.specalized.SprintReport;

public class List2PPT {
	
	File pptDirectory = null;
	XMLSlideShow ss = new XMLSlideShow();
	
	private List<SprintObject> so = null;
	private List<IssueObject> issues = null;
	private List<TaskObject> tasks = null;
	private List<PriorityObject> priorities = null;
	private Report report = null;
	private boolean incldeWknds;
	private int numOfDays;
	
	public List2PPT(List<SprintObject> so, List<IssueObject> issues, List<TaskObject> tasks, List<PriorityObject> priorities,
			boolean incldeWknds, int numOfDays) {
		this.so = so;
		this.issues = issues;
		this.tasks = tasks;
		this.priorities = priorities;
		this.incldeWknds = incldeWknds;
		this.numOfDays = numOfDays;
	}
	
	public void loadFileLocation() {
		String OS = System.getProperty("os.name").toLowerCase();
    	String home = System.getProperty("user.home");
    	if(OS.indexOf("win") >= 0) { // Windows
    		if(home.toCharArray()[0] == 'Z') {
    			pptDirectory   = new File("H:/J2P/PowerPoint");
    		} else {
    			pptDirectory   = new File(home+"/Documents/J2P/PowerPoint");
    		}
        } else if(OS.indexOf("mac") >= 0) { // Mac
        	pptDirectory   = new File(home+"/Documents/J2P/PowerPoint");
        } else if(OS.indexOf("sunos") >= 0) { // Solaris
        	//TODO: Support
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) { // Unix
        	//TODO: Support
        } else {
        	System.err.println("jira2presentation is not supported on your OS currently.");
        	System.exit(1);
        }
    	
    	if(!pptDirectory.exists()) {
        	pptDirectory.mkdirs();
        }
	}
	
	public void writeReport(int reportStyle) {
		for(SprintObject sprint : so) {
			if(reportStyle == 0) {
				report = new SprintReport(issues, tasks, priorities);
				report.writeReport(ss, sprint);
				outputSprintReport();
			} else if (reportStyle == 1) {
				report = new QuarterlyReport(so, issues, tasks, priorities, incldeWknds, numOfDays);
				report.writeReport(ss, sprint);
				outputQuarterReport();
			}
		}
	}
	
	public void outputQuarterReport() {
		FileOutputStream out;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		try {
			out = new FileOutputStream(pptDirectory + "\\" + "QuarterlyReport_" + dtf.format(LocalDateTime.now()) + ".ppt");
			ss.write(out);
			out.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void outputSprintReport() {
		FileOutputStream out;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		try {
			out = new FileOutputStream(pptDirectory + "\\" + "SprintReport_" + dtf.format(LocalDateTime.now()) + ".ppt");
			ss.write(out);
			out.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDirectory() {
		return pptDirectory.toString();
	}
	
}
