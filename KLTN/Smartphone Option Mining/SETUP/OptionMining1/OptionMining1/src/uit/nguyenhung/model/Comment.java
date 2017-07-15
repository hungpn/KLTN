package uit.nguyenhung.model;

import java.util.ArrayList;
import java.util.HashMap;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.node.FeatureNode;
import uit.nguyenhung.process.StringProcessing;

public class Comment {
	public int id;
	public int type;
	public float score;
	public String productName;
	public String originalContent;
	public String articleId;
	
	public ArrayList<Sentence> sentences;
	public HashMap<String, Integer> typeForFeatures;

	public Comment(int id, String productName, String originalContent,
			String articleId) {
		this.id = id;
		this.productName = productName;
		this.originalContent = originalContent;
		this.type = 0;
		this.sentences = new ArrayList<Sentence>();
		this.articleId = articleId;
		this.typeForFeatures = new HashMap<>();
		String temp = this.originalContent.replaceAll(
				"\\!+|\n|\\?+|(\\. )|((\\.\\.\\.\\.))|(\\:)", " split ");
		ArrayList<String> strs = StringProcessing
				.convertStringToListStringByWord(temp, "split");
		for (String s : strs) {
			if (!s.equals("")) {
				Sentence sen = new Sentence(s, new Product(this.productName));
				sen.process();
				this.sentences.add(sen);
			}
		}
		this.score = 0;
		this.specifyOptionType(this.calculate());
		this.setTypeForFeature();
	}

	public Comment(String productName, String originalContent) {
		this.id = 0;
		this.productName = productName;
		this.originalContent = originalContent;
		this.type = 0;
		this.sentences = new ArrayList<Sentence>();
		String temp = this.originalContent.replaceAll(
				"\\!+|\n|\\?+|(\\. )|((\\.\\.\\.\\.))|(\\:)", " split ");
		ArrayList<String> strs = StringProcessing
				.convertStringToListStringByWord(temp, "split");
		for (String s : strs) {
			if (!s.equals("")) {
				Sentence sen = new Sentence(s, new Product(this.productName));
				sen.process();
				this.sentences.add(sen);
			}
		}
		this.score = 0;
		this.specifyOptionType(this.calculate());
		this.setTypeForFeature();
	}

	public float calculate() {
		float score = 0;
		for (Sentence sen : this.sentences) {
			score += sen.getScore();
		}
		return score;
	}

	public void specifyOptionType(float score) {
		this.score = score;
		if (score > 0.5) {
			this.type = 1; // tích cực
		} else if (score <= 0.5 && score >= -0.5) {
			this.type = 0; // trung lập
		} else if (score < -0.5) {
			this.type = -1; // tiêu cực
		}
	}

	public int specifyOptionTypeForFeature(float score) {
		int type = 0;
		if (score > 0.5) {
			type = 1; // tích cực
		} else if (score <= 0.5 && score >= -0.5) {
			type = 0; // trung lập
		} else if (score < -0.5) {
			type = -1; // tiêu cực
		}
		return type;
	}

	public void setTypeForFeature() {
		for (Feature f : StoringData.sFeatures) {
			float score = 0;
			int check = 0;
			for (Sentence s : this.sentences) {
				for (FeatureNode ftn : s.getGraph().featureNodes) {
					if (ftn.ft.mWord.contains(f.mName)) {
						score += ftn.score;
						check = 1;
						break;
					}
				}
			}
			if (check == 1) {
				this.typeForFeatures.put(f.mName,
						this.specifyOptionTypeForFeature(score));
			} else {
				this.typeForFeatures.put(f.mName, 3);
			}
		}
	}
}
