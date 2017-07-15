package uit.nguyenhung.vntagger;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class Tokenizer {
	private VietTokenizer tokenizer;

	public Tokenizer() {
		this.tokenizer = new VietTokenizer();
	}

	public String tokenText(String text) {
		return text = this.tokenizer.segment(text);
	}
}