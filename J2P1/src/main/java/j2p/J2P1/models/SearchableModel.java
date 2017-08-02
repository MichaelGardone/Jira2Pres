package j2p.J2P1.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import j2p.J2P1.objects.BoardObject;

public class SearchableModel extends DefaultListModel {
	
	private List<BoardObject> bo = new ArrayList<BoardObject>();
	
	public SearchableModel() {}
	
	public SearchableModel(List<BoardObject> bo) {
		this.bo = bo;
	}
	
	public void addElement(BoardObject bo) {
		this.bo.add(bo);
	}
	
}
