package j2p.J2P.customfields;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings({ "serial", "rawtypes" })
public class CheckListRenderer extends JCheckBox implements ListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		setEnabled(list.isEnabled());
		setSelected(((CheckListItem) value).isSelected());
		setFont(new Font("SansSerif", Font.PLAIN, 14));
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setText(value.toString());
		return this;
	}
}
