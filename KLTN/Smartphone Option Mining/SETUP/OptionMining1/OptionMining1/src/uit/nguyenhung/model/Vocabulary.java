package uit.nguyenhung.model;

import java.util.ArrayList;

import uit.nguyenhung.process.StringProcessing;

public class Vocabulary {
	public String mContent;
	public String mWord;
	public String mTag;

	public Vocabulary() {
		this.mContent = this.mWord = this.mTag = "";
	}

	public Vocabulary(String content) {
		this.mContent = content;
		ArrayList<String> list = StringProcessing
				.convertStringToListStringByWord(content, "/");
		this.mWord = list.get(0);
		this.mTag = list.get(1);
	}
}
