package j2p.J2P1.translators.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import j2p.J2P1.objects.SprintObject;

public class SprintTime {
	
	private SprintObject so;
	
	public SprintTime(SprintObject so) {
		this.so = so;
	}
	
	public long determineLength(boolean incldWknd,int numOfWeekendDays) {
		
		String start = so.getStart();
		String end 	= so.getEnd();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		Date sDate = null;
		Date eDate = null;
		long diff = 0;
		long diffDays = 0;
		try {
			sDate = sdf.parse(start);
			eDate = sdf.parse(end);
			diff = eDate.getTime() - sDate.getTime();
			if(incldWknd) {
				diffDays = diff / (24*60*60*1000);
			} else {
				diffDays = diff / (24*60*60*1000) - numOfWeekendDays;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return diffDays;
	}
	
}
