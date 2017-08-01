package j2p.J2P1.translators.specalized;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import org.apache.poi.sl.usermodel.TableCell.BorderEdge;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

import j2p.J2P1.data.QuarterBasedData;
import j2p.J2P1.objects.IssueObject;
import j2p.J2P1.objects.PriorityObject;
import j2p.J2P1.objects.SprintObject;
import j2p.J2P1.objects.TaskObject;

public class QuarterlyReport extends Report {
	
	private QuarterBasedData qbd;
	
	public QuarterlyReport(List<SprintObject> sprints, List<IssueObject> issues, List<TaskObject> tasks, List<PriorityObject> priorities,
			boolean incldWknd, int numDays) {
		super(issues, tasks, priorities);
		qbd = new QuarterBasedData(issues, gd.priorities.length);
		qbd.calculateSprintLengths(sprints, incldWknd, numDays);
	}

	@Override
	public void writeReport(XMLSlideShow ss, SprintObject so) {
		qbd.parseData();
		
		for(int x=0;x<qbd.getNumOfSprints(); x++) {
			XSLFSlide slide = ss.createSlide();
				
			XSLFTable completedPoints = slide.createTable();
		    completedPoints.setAnchor(new Rectangle(50, 50, 450, 300));
		    
		    int numColumns = 4;
		    int numRows = 12;
		    XSLFTableRow headerRow = completedPoints.addRow();
		    headerRow.setHeight(50);
		    // header
		    for(int i = 0; i < numColumns; i++) {
		    	XSLFTableCell th = headerRow.addCell();
		        XSLFTextParagraph p = th.addNewTextParagraph();
		        p.setTextAlign(TextAlign.CENTER);
		        XSLFTextRun r = p.addNewTextRun();
		        if(i==0) {
		         	r.setText(so.getBoardObject().getName());
		        } else if(i==1) {
		           	r.setText("Committed");
		        } else if(i==2) {
		          	r.setText("Completed");
		        } else if(i==3) {
		           	r.setText("Target");
		        }
		        r.setBold(true);
		        r.setFontColor(Color.white);
		        th.setFillColor(new Color(79, 129, 189));
		        th.setBorderWidth(BorderEdge.bottom, 2.0);
		        th.setBorderColor(BorderEdge.bottom, Color.white);
		        completedPoints.setColumnWidth(i, 150);  // all columns are equally sized
		    }
		    
		    // rows
		    for(int rownum = 0; rownum < numRows; rownum ++){
		    	XSLFTableRow tr = completedPoints.addRow();
		        tr.setHeight(25);
		        // header
		        for(int i = 0; i < numColumns; i++) {
		        	XSLFTableCell cell = tr.addCell();
		        	XSLFTextParagraph p = cell.addNewTextParagraph();
		        	XSLFTextRun r = p.addNewTextRun();
		        	
		        	if(i==0) {
		        		if(rownum==0) {
		        			r.setText("January");
		        		}else if(rownum==1) {
		        			r.setText("February");
		        		}else if(rownum==2) {
		        			r.setText("March");
		        		}else if(rownum==3) {
		        			r.setText("April");
		        		}else if(rownum==4) {
		        			r.setText("May");
		        		}else if(rownum==5) {
		        			r.setText("June");
		        		}else if(rownum==6) {
		        			r.setText("July");
		        		}else if(rownum==7) {
		        			r.setText("August");
		        		}else if(rownum==8) {
		        			r.setText("September");
		        		}else if(rownum==9) {
		        			r.setText("October");
		        		}else if(rownum==10) {
		        			r.setText("November");
		        		}else if(rownum==11) {
		        			r.setText("December");
		        		}
		        	} else if(i==1) {
		        		r.setText(qbd.getTotalPoints()[x][rownum]+"");
		        	} else if(i==2) {
		        		r.setText(qbd.getClosedPoints()[x][rownum]+"");
		        	} else {
		        		r.setText((double)(qbd.getClosedPoints()[x][rownum])/(double)(qbd.getTotalPoints()[x][rownum])*100+"%");
		        	}
		        	
		        	if(rownum % 2 == 0)
		        		cell.setFillColor(new Color(208, 216, 232));
		        	else
		        		cell.setFillColor(new Color(233, 247, 244));
		        }
		    }
		    
		    slide = ss.createSlide();
		    XSLFTable ticketsPerDay = slide.createTable();
		    ticketsPerDay.setAnchor(new Rectangle(50, 50, 450, 300));
		    numColumns = 3;
		    numRows = 12;
		    headerRow = ticketsPerDay.addRow();
		    headerRow.setHeight(50);
		    for(int i = 0; i < numColumns; i++) {
		    	XSLFTableCell th = headerRow.addCell();
		        XSLFTextParagraph p = th.addNewTextParagraph();
		        p.setTextAlign(TextAlign.CENTER);
		        XSLFTextRun r = p.addNewTextRun();
		        if(i==0) {
		         	r.setText(so.getBoardObject().getName());
		        } else if(i==1) {
		           	r.setText("Total Tickets");
		        } else if(i==2) {
		          	r.setText("Daily Average");
		        }
		        r.setBold(true);
		        r.setFontColor(Color.white);
		        th.setFillColor(new Color(79, 129, 189));
		        th.setBorderWidth(BorderEdge.bottom, 2.0);
		        th.setBorderColor(BorderEdge.bottom, Color.white);
		        ticketsPerDay.setColumnWidth(i, 150);  // all columns are equally sized
		    }
		    for(int rownum = 0; rownum < numRows; rownum ++){
		    	XSLFTableRow tr = ticketsPerDay.addRow();
		        tr.setHeight(25);
		        // header
		        for(int i = 0; i < numColumns; i++) {
		        	XSLFTableCell cell = tr.addCell();
		        	XSLFTextParagraph p = cell.addNewTextParagraph();
		        	XSLFTextRun r = p.addNewTextRun();
		        	
		        	if(i==0) {
		        		if(rownum==0) {
		        			r.setText("January");
		        		}else if(rownum==1) {
		        			r.setText("February");
		        		}else if(rownum==2) {
		        			r.setText("March");
		        		}else if(rownum==3) {
		        			r.setText("April");
		        		}else if(rownum==4) {
		        			r.setText("May");
		        		}else if(rownum==5) {
		        			r.setText("June");
		        		}else if(rownum==6) {
		        			r.setText("July");
		        		}else if(rownum==7) {
		        			r.setText("August");
		        		}else if(rownum==8) {
		        			r.setText("September");
		        		}else if(rownum==9) {
		        			r.setText("October");
		        		}else if(rownum==10) {
		        			r.setText("November");
		        		}else if(rownum==11) {
		        			r.setText("December");
		        		}
		        	} else if(i==1) {
		        		r.setText(qbd.totalTickets()[rownum]+"");
		        	} else {
		        		r.setText((double)(qbd.totalTickets()[rownum])/(double)(qbd.getNumOfDays()[rownum])+"");
		        	}
		        	
		        	if(rownum % 2 == 0)
		        		cell.setFillColor(new Color(208, 216, 232));
		        	else
		        		cell.setFillColor(new Color(233, 247, 244));
		        }
		    }
		    
		    slide = ss.createSlide();
		    XSLFTable bugs = slide.createTable();
		    bugs.setAnchor(new Rectangle(50, 50, 900, 500));
		    numColumns = 6;
		    numRows = 2;
		    headerRow = bugs.addRow();
		    headerRow.setHeight(50);
		    for(int i = 0; i < numColumns; i++) {
		    	XSLFTableCell th = headerRow.addCell();
		        XSLFTextParagraph p = th.addNewTextParagraph();
		        p.setTextAlign(TextAlign.CENTER);
		        XSLFTextRun r = p.addNewTextRun();
		        if(i==0) {
		         	r.setText("Status");
		        } else {
		           	r.setText(gd.priorityNames.get(i-1)+"");
		        }
		        r.setBold(true);
		        r.setFontColor(Color.white);
		        th.setFillColor(new Color(79, 129, 189));
		        th.setBorderWidth(BorderEdge.bottom, 2.0);
		        th.setBorderColor(BorderEdge.bottom, Color.white);
		        bugs.setColumnWidth(i, 80);  // all columns are equally sized
		    }
		    
		    for(int rownum = 0; rownum < numRows; rownum ++){
		    	XSLFTableRow tr = bugs.addRow();
		        tr.setHeight(25);
		        // header
		        for(int i = 0; i < numColumns; i++) {
		        	XSLFTableCell cell = tr.addCell();
		        	XSLFTextParagraph p = cell.addNewTextParagraph();
		        	XSLFTextRun r = p.addNewTextRun();
		        	
		        	if(i==0) {
		        		if(rownum==0) {
		        			r.setText("Open");
		        		}else if(rownum==1) {
		        			r.setText("Closed");
		        		}
		        	} else {
		        		if(rownum==0) {
		        			r.setText(qbd.getOpenTickets()[i-1]+"");
		        		} else {
		        			r.setText(qbd.getClosedTickets()[i-1]+"");
		        		}
		        	}
		        	
		        	if(rownum % 2 == 0)
		        		cell.setFillColor(new Color(208, 216, 232));
		        	else
		        		cell.setFillColor(new Color(233, 247, 244));
		        }
		    }
		    
		}
	}
}

