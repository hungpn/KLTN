package uit.nguyenhung.model;

public class DeniedWord {
	public String mId;
	public String mWord;
	public float mScore;

	public DeniedWord() {
	}

	public DeniedWord(String id, String word, float score) {
		this.mId = id;
		this.mWord = word;
		this.mScore = score;
	}

}
