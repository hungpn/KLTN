package uit.nguyenhung.model;

import uit.nguyenhung.list.model.StoringData;

public class SentiWord {
	public String mId;
	public String mWord;
	public String mFeature;
	public String mTag = "stw";
	public float mScore;

	public SentiWord() {
		// TODO Auto-generated constructor stub
	}

	public SentiWord(String word, String feature) {
		this.mId = "";
		this.mWord = "";
		this.mFeature = "";
		this.mScore = 0;
		for (SentiWord stw : StoringData.sSentiWords) {
			if (stw.mFeature.equals(feature) && stw.mWord.endsWith(word)) {
				this.mId = stw.mId;
				this.mWord = stw.mWord;
				this.mFeature = stw.mFeature;
				this.mScore = stw.mScore;
				return;
			}
		}
	}
	
	public SentiWord(String id, String word, String feature, float score) {
		this.mId = id;
		this.mWord = word;
		this.mFeature = feature;
		this.mScore = score;
	}

}
