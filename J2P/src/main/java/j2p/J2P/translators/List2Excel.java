package j2p.J2P.translators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import j2p.J2P.data.GlobalData;
import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.PriorityObject;
import j2p.J2P.objects.TaskObject;

public class List2Excel {
	
	File excelDirectory = null;
	
	// Apache POI
	XSSFWorkbook  wb = new XSSFWorkbook();
	XSSFSheet sh = wb.createSheet();
	private GlobalData gd = new GlobalData();
	
	private List<IssueObject> issues = null;
	private List<PriorityObject> priorities = null;
	private List<TaskObject> tasks = null;
	
	public List2Excel(List<IssueObject> issues, List<PriorityObject> priorities, List<TaskObject> tasks) {
		this.issues = issues;
		this.priorities = priorities;
		this.tasks = tasks;
	}
	
	// Check to see if the file location exists, if not, create it
	public void loadFileLocation() {
		String OS = System.getProperty("os.name").toLowerCase();
		String home = System.getProperty("user.home");
		if(OS.indexOf("win") >= 0) { // Windows
        	excelDirectory = new File(home+"/Documents/J2P/Excel");
        } else if(OS.indexOf("mac") >= 0) { // Mac
        	excelDirectory = new File(home+"/Documents/J2P/Excel");
        } else if(OS.indexOf("sunos") >= 0) { // Solaris
        	//TODO: Support
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) { // Unix
        	//TODO: Support
        } else {
        	System.err.println("J2P is not supported on your OS currently.");
        	System.exit(1);
        }
	}
	
	// Get the issues and turn them into statistics
	public void getStatistics() {
		gd.loadPriorities(priorities);
		gd.loadTaskTypes(tasks);
		gd.parseIssues(issues);
	}
	
	// Put all the stats in a pretty little sheet
	public void populateSheet(String filePrefix) {
		for(int i=0; i<6+gd.priorities.length+gd.totalTasks.length; i++) {
			i = insertIntoDocument(i);
		}
		
		// Actually output now
		FileOutputStream out;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		try {
			//out = new FileOutputStream(excelDirectory.toString()+"/" + so.getName() + ".xlsx");
			//out = new FileOutputStream(filePrefix + "_" + dtf.format(LocalDateTime.now()) + ".xlsx");
			out = new FileOutputStream(excelDirectory + "\\"+ filePrefix + "_" + dtf.format(LocalDateTime.now()) + ".xlsx");
			wb.write(out);
	        out.close();
		    wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Helper function - Organize all the rows and data
	private int insertIntoDocument(int loopNum) {
		Row row = sh.createRow(loopNum);
		int place = 0;
		if(loopNum == 0) { // Header
			Cell cell = row.createCell(1);
			cell.setCellValue("Total");
			cell = row.createCell(2);
			cell.setCellValue("Percentage");
		} else if(loopNum == 1) { // Total points
			for(int x=0; x<3; x++) {
				Cell cell = row.createCell(x);
				if(x == 0) {
					cell.setCellValue("Total Points");
				} else if(x == 1) {
					cell.setCellValue(gd.totalPoints);
				} else {
					double total = (double)(gd.totalPoints)/(double)(gd.totalPoints)*100;
					cell.setCellValue(total + "%");
				}
			}
		} else if(loopNum == 2) { // Total points completed
			for(int x=0; x<3; x++) {
				Cell cell = row.createCell(x);
				if(x==0) {
					cell.setCellValue("Completed");
				} else if(x == 1) {
					cell.setCellValue(gd.pointsCompl);
				} else {
					double total = (double)(gd.pointsCompl)/(double)(gd.totalPoints)*100;
					cell.setCellValue(total + "%");
				}
			}
		} else if(loopNum == 3) { // Total points incomplete
			for(int x=0; x<3; x++) {
				Cell cell = row.createCell(x);
				if(x==0) {
					cell.setCellValue("Incomplete");
				} else if(x == 1) {
					cell.setCellValue(gd.pointIncmpl);
				} else {
					double total = (double)(gd.pointIncmpl)/(double)(gd.totalPoints)*100;
					cell.setCellValue(total + "%");
				}
			}
		} else if(loopNum == 4) { // Total issues
			for(int x=0; x<3; x++) {
				Cell cell = row.createCell(x);
				if(x==0) {
					cell.setCellValue("Total Issues");
				} else if(x == 1) {
					cell.setCellValue(gd.totalIssues);
				} else {
					double total = (double)(gd.totalIssues)/(double)(gd.totalIssues)*100;
					cell.setCellValue(total + "%");
				}
			}
		} else if(loopNum == 5) { // Total open
			for(int x=0; x<3; x++) {
				Cell cell = row.createCell(x);
				if(x==0) {
					cell.setCellValue("Open Issues");
				} else if(x == 1) {
					cell.setCellValue(gd.openIssues);
				} else {
					double total = (double)(gd.openIssues)/(double)(gd.totalIssues)*100;
					cell.setCellValue(total + "%");
				}
			}
		} else if(loopNum == 6) { // Total closed
			for(int x=0; x<3; x++) {
				Cell cell = row.createCell(x);
				if(x==0) {
					cell.setCellValue("Closed Issues");
				} else if(x == 1) {
					cell.setCellValue(gd.closedIssue);
				} else {
					double total = (double)(gd.closedIssue)/(double)(gd.totalIssues)*100;
					cell.setCellValue(total + "%");
				}
			}
		} else if(loopNum >= 7 && loopNum <= 7+gd.totalTasks.length) { // Tasks
			for(TaskObject key : tasks)  {
				row = sh.createRow(loopNum++);
				for(int t=0;t<3;t++) {
					Cell cell = row.createCell(t);
					if(t==0) {
						cell.setCellValue(key.getName());
					} else if(t == 1) {
						cell.setCellValue(gd.totalTasks[place]);
					} else {
						double total = (double)(gd.totalTasks[place])/(double)(gd.totalIssues)*100;
						cell.setCellValue(total + "%");
					}
				}
				place++;
				if(place > 4) {
					place = 0;
				}
			}
		}  else if(loopNum >= 7+gd.totalTasks.length) {
			for(PriorityObject key : priorities)  {
				row = sh.createRow(loopNum++);
				for(int t=0;t<3;t++) {
					Cell cell = row.createCell(t);
					if(t==0) {
						cell.setCellValue(key.getName());
					} else if(t == 1) {
						cell.setCellValue(gd.priorities[place]);
					} else {
						double total = (double)(gd.priorities[place])/(double)(gd.totalIssues)*100;
						cell.setCellValue(total + "%");
					}
				}
				place++;
			}
		}
		return loopNum;
	}
	
}
