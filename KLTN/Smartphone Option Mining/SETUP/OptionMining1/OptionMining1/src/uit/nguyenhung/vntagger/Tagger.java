package uit.nguyenhung.vntagger;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class Tagger {
	private VietnameseMaxentTagger tagger;
	private Tokenizer token;
	private String[] listLabel = { "E", "V", "N", "CC", "C", "A", "Np", "L",
			"R", "X", "M", "P" };

	public Tagger() {
		this.tagger = new VietnameseMaxentTagger();
		this.token = new Tokenizer();
	}

	public String tagText(String text) {
		text = this.tagger.tagText(text);
		text = this.token.tokenText(text).replace(" / ", "/");
		for (String label : this.listLabel) {
			text = text.replace("/" + label + "_", "/" + label + " ");
		}
		text = processException(text);
		return text;
	}

	private String processException(String text) {
		text = text.replaceAll("siêu\\/Z phẩm\\/N", "siêu_phẩm/N");
		return text;
	}
}
