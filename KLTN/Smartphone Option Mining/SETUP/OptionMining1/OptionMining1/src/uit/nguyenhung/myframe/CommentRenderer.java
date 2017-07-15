package uit.nguyenhung.myframe;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

public class CommentRenderer extends DefaultListCellRenderer implements
		ListCellRenderer<Object> {
	String pre = "<html><body style='width: 700px; padding-top:10px; padding-bottom:10px; padding-left:10px'>";

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		CommentItem ci = (CommentItem) value;
		setText(pre + ci.text);
		setBackground(ci.background);
		setEnabled(true);
		setFont(list.getFont());
		setBorder(new LineBorder(Color.black));

		return this;
	}
}
