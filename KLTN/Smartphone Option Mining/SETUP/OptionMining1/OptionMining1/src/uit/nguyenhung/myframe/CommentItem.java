package uit.nguyenhung.myframe;

import java.awt.Color;

public class CommentItem {
	public String text;
	public Color foreground;
	public Color background;

	public CommentItem(String text, int type) {
		this.text = text;
		this.foreground = Color.BLACK;
		switch (type) {
		case 0:
			this.background = Color.YELLOW;
			break;
		case -1:
			this.background = Color.RED;
			break;
		case 1:
			this.background = Color.GREEN;
			break;
		default:
			break;
		}
	}
}
