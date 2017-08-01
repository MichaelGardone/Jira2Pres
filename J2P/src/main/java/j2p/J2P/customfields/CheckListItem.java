package j2p.J2P.customfields;

import j2p.J2P.objects.IssueObject;

public class CheckListItem {
	private String label;
	private boolean isSelected = false;
	
	private int id;
	private String key;
	private String startDate;
	private String closeDate;
	private IssueObject issue;
	
	public CheckListItem(String label, int id) {
		this.label = label;
		this.id = id;
	}
	
	public CheckListItem(String label, int id, String key) {
		this.label = label;
		this.id = id;
		this.key = key;
	}
	
	public CheckListItem(String label, int id, String key, IssueObject issue) {
		this.label = label;
		this.id = id;
		this.key = key;
		this.issue = issue;
	}
	
	public CheckListItem(String label, int id, String key, String startDate) {
		this.label = label;
		this.id = id;
		this.key = key;
		this.startDate = startDate;
	}
	
	public CheckListItem(String label, int id, String key, String startDate, String closeDate) {
		this.label = label;
		this.id = id;
		this.key = key;
		this.startDate = startDate;
		this.closeDate = closeDate;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public int getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getCloseDate() {
		return closeDate;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	public IssueObject getIssue() {
		return issue;
	}
}
