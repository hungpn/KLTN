package uit.nguyenhung.myframe;

import java.awt.Color;

public class SentenceItem {
	public String text;
	public Color foreground;
	public Color background;

	public SentenceItem(String text, int type) {
		this.text = text;
		this.foreground = Color.black;
		switch (type) {
		case 0:
			this.background = Color.WHITE;
			this.foreground = Color.BLACK;
			break;
		case 1:
			this.background = Color.GRAY;
			this.foreground = Color.WHITE;
			break;
		default:
			break;
		}
	}
}
