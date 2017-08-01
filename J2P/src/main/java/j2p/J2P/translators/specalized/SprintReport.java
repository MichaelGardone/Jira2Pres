package j2p.J2P.translators.specalized;

import java.util.List;

import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.PriorityObject;
import j2p.J2P.objects.SprintObject;
import j2p.J2P.objects.TaskObject;

public class SprintReport extends Report {
	

	public SprintReport(List<IssueObject> issues, List<TaskObject> tasks, List<PriorityObject> priorities) {
		super(issues, tasks, priorities);
	}

	// Only able to hard code the layout
	// Create slides from the individual issues
	public void writeReport(XMLSlideShow ss, SprintObject so) {
		
		XSLFSlideMaster masters = ss.getSlideMasters().get(0);
		XSLFSlideLayout layout = masters.getLayout(SlideLayout.TITLE);
		XSLFSlide slide = ss.createSlide(layout);
		
		XSLFTextShape title = slide.getPlaceholder(0);
		title.setText(so.getName() + " (" + so.getId() + ")");
			
		XSLFTextShape body = slide.getPlaceholder(1);
		body.clearText();
		
		XSLFTextParagraph paragraph = body.addNewTextParagraph();
		
		XSLFTextRun timeFrame = paragraph.addNewTextRun();
		timeFrame.setText("Started - Closed:\n" + so.getStart() + " - " + so.getEnd());
		timeFrame.setFontSize(24d);
		
		for(IssueObject obj : issues) {
			masters = ss.getSlideMasters().get(0);
			layout = masters.getLayout(SlideLayout.TITLE_AND_CONTENT);
			slide = ss.createSlide(layout);
			
			title = slide.getPlaceholder(0);
			title.setText(obj.getSummary());
			
			body = slide.getPlaceholder(1);
			body.clearText();
				
			paragraph = body.addNewTextParagraph();
			
			XSLFTextRun reporterName = paragraph.addNewTextRun();
			reporterName.setText("Reporter Name: " + obj.getReporterName());;
			reporterName.setFontSize(24d);
			
			XSLFTextRun assigneeName = paragraph.addNewTextRun();
			assigneeName.setText("\nAssignee Name: " + obj.getDisplayName() + " (" + obj.getUser() + ")");
			assigneeName.setFontSize(24d);
			
			XSLFTextRun status = paragraph.addNewTextRun();
			status.setText("\nStatus: " + obj.getStatus());
			status.setFontSize(24d);
			
			XSLFTextRun type = paragraph.addNewTextRun();
			type.setText("\nTask Type: " + obj.getType());
			type.setFontSize(24d);
			
			XSLFTextRun points = paragraph.addNewTextRun();
			points.setText("\nPoints: " + obj.getPoints());
			points.setFontSize(24d);
			
			XSLFTextRun priority = paragraph.addNewTextRun();
			priority.setText("\nPriority: " + obj.getPriorityName() + " (" + obj.getPriority() + ")");
			priority.setFontSize(24d);
			
			XSLFTextRun timeSpent = paragraph.addNewTextRun();
			if(obj.getTimeSpent() == null)
				timeSpent.setText("\nTime Spent: None"); 
			else
				timeSpent.setText("\nTime Spent: " + obj.getTimeSpent());
			timeSpent.setFontSize(24d);
			
			XSLFTextRun subtask = paragraph.addNewTextRun();
			if(obj.getSubtask() == false) {
				subtask.setText("\nSubtask? Not a subtask");
			} else {
				subtask.setText("\nSubtask? Is a subtask");
			}
			subtask.setFontSize(24d);
			
			//ID (Big number)
			XSLFTextRun numId = paragraph.addNewTextRun();
			numId.setText("\nID: "+obj.getId());
			numId.setFontSize(16d);
			
			//Key
			XSLFTextRun key = paragraph.addNewTextRun();
			key.setText("\nKey: "+obj.getKey());
			key.setFontSize(16d);
		}
			
	}
}
