package j2p.J2P.customfields;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import j2p.J2P.objects.BoardObject;
import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.SprintObject;

public class JListGenerator {
	public void check(JList list, List<Integer> ids, JButton button) {
		List<CheckListItem> obj = list.getSelectedValuesList();
		for(CheckListItem o : obj) {
			if(!ids.contains(o.getId())) {
				ids.add(o.getId());
			}
			if(o.isSelected() == false && ids.contains(o.getId())) {
				ids.remove(ids.indexOf(o.getId()));
			}
		}
		
		if(ids.size() > 0) {
			button.setEnabled(true);
		} else {
			button.setEnabled(false);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JList generateIssueList(List<IssueObject> issue, final List<Integer> ids, final JButton button) {
		List<CheckListItem> cli = new ArrayList<CheckListItem>();
		
		for(IssueObject io : issue) {
			cli.add(new CheckListItem(io.getSummary() + " (Id: " + io.getId() + ", Type: " + io.getType()
					+ ", Status: " + io.getStatus() + ")", Integer.parseInt(io.getId()), io.getSummary()));
		}
		
		JList list = new JList(cli.toArray());
		list.setCellRenderer(new CheckListRenderer());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();
		        int index = list.locationToIndex(event.getPoint());// Get index of item clicked
		        CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
		        item.setSelected(!item.isSelected()); // Toggle selected state
		        list.repaint(list.getCellBounds(index, index));// Repaint cell
		        check(list, ids, button);
			}
		});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JList generateBoardList(List<BoardObject> boards, final List<Integer> ids, final JButton button) {
		List<CheckListItem> cli = new ArrayList<CheckListItem>();
		
		for(BoardObject bo : boards) {
			cli.add(new CheckListItem(bo.getName() + " (Id: " + bo.getId() + ", Type: " + bo.getType() + ")", bo.getId(), bo.getName()));
		}
		
		JList list = new JList(cli.toArray());
		list.setCellRenderer(new CheckListRenderer());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();
		        int index = list.locationToIndex(event.getPoint());// Get index of item clicked
		        CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
		        item.setSelected(!item.isSelected()); // Toggle selected state
		        list.repaint(list.getCellBounds(index, index));// Repaint cell
		        check(list, ids, button);
			}
		});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JList generateSprintList(List<SprintObject> sprint, final List<Integer> ids, final JButton button) {
		List<CheckListItem> cli = new ArrayList<CheckListItem>();
		
		for(SprintObject so : sprint) {
			if(so.getState() == "active") {
				cli.add(new CheckListItem(so.getName() + " (Id: " + so.getId() + ", Status: " + so.getState() + "); Started: "
						+ so.getStart(), so.getId(), so.getName(), so.getStart()));
			} else if(so.getState() == "closed") {
				cli.add(new CheckListItem(so.getName() + " (Id: " + so.getId() + ", Status: " + so.getState() + ") Started: " 
						+ so.getStart() + "; Ended: " + so.getEnd(),  so.getId(), so.getName(), so.getStart()));
			} else if(so.getState() == "future" ) {
				
			} else {
				cli.add(new CheckListItem(so.getName() + " (Id: " + so.getId() + ", Status: " + so.getState() + ")", 
						so.getId(), so.getName()));
			}
		}
		
		JList list = new JList(cli.toArray());
		list.setCellRenderer(new CheckListRenderer());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();
		        int index = list.locationToIndex(event.getPoint());// Get index of item clicked
		        CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
		        item.setSelected(!item.isSelected()); // Toggle selected state
		        list.repaint(list.getCellBounds(index, index));// Repaint cell
		        check(list, ids, button);
			}
		});
		return list;
	}
	
}
