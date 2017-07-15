package uit.nguyenhung.myframe;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

public class SentenceRenderer extends DefaultListCellRenderer implements
		ListCellRenderer<Object> {
	String pre = "<html><body style='width: 190px; padding-top:5px; padding-bottom:10px; padding-left:10px; font-size:10px'>";

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		SentenceItem si = (SentenceItem) value;
		setText(pre + si.text);
		setBackground(si.background);
		setForeground(si.foreground);
		setEnabled(true);
		setFont(list.getFont());
		setBorder(new LineBorder(Color.black));
		return this;
	}
}
