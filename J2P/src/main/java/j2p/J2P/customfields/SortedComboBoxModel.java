package j2p.J2P.customfields;

import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;

import j2p.J2P.helper.SCBMContent;

public class SortedComboBoxModel extends DefaultComboBoxModel {
	
	private static final long serialVersionUID = 6509579658202257246L;

	public SortedComboBoxModel() {
		super();
	}
	

	public SortedComboBoxModel(Object[] objs) {
		Arrays.sort(objs);
		int size = objs.length;
		for(int i=0; i < size; i++ ){
			super.addElement(objs[i]);
		}
		setSelectedItem(0);
	}
	
    @Override
    public void addElement(Object element) {
        insertElementAt(element, 0);
    }

    @Override
    public void insertElementAt(Object element, int index) {
        int size = getSize();
        //  Determine where to insert element to keep model in sorted order            
        for (index = 0; index < size; index++) {
            Comparable c = (Comparable) getElementAt(index);
            if (c.compareTo(element) > 0) {
                break;
            }
        }
        super.insertElementAt(element, index);
    }
	
}
