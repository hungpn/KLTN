package uit.nguyenhung.model;

import java.util.ArrayList;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.node.SentiPhraseGraph;
import uit.nguyenhung.process.StringProcessing;

public class PartOfSentence {
	public String tagged;
	public ArrayList<Vocabulary> vocabularys;
	public String stringByRule;
	public Vocabulary featureVocabulary;
	public SentiPhraseGraph sentiPhraseGraph;

	public PartOfSentence(String tagged) {
		this.tagged = tagged;
		this.stringByRule = "";
		this.sentiPhraseGraph = new SentiPhraseGraph();
	}
	
	public PartOfSentence(String tagged, ArrayList<Vocabulary> vocabularys,
			String stringByRule, Vocabulary featureVocabulary,
			SentiPhraseGraph sentiPhraseGraph) {
		this.tagged = tagged;
		this.vocabularys = vocabularys;
		this.stringByRule = stringByRule;
		this.featureVocabulary = featureVocabulary;
		this.sentiPhraseGraph = sentiPhraseGraph;
	}



	public void process() {
		this.setForVocabualary();
		this.featureVocabulary = new Vocabulary();
		for (Vocabulary v : this.vocabularys) {
			if (v.mTag.equals("ft")) {
				this.featureVocabulary = v;
				break;
			}
		}
	}

	private boolean checkTp() {
		if (this.tagged.contains("tproduct")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkOP() {
		if (this.tagged.contains("oproduct")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkType() {
		this.tagged = StringProcessing
				.replaceWithRegularExpressionAndException(
						this.tagged,
						"oproduct",
						"((([^/ ]*)/Np )+(([^/ ]*)/Np))|((([^/ ]*)/Np )+(([^/ M]*)/M))" +
						"|((([A-Z])\\w+ ?){2,3})|([A-Z]+[0-9])|([0-9][A-Z]+)|([a-z]+[0-9])|([0-9][a-z]+)",
						StoringData.sExceptions);
		if (checkTp()) {
			if (checkOP()) {
				String regex1 = "(tproduct|=|>|<|oproduct)";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.tagged, regex1);

				if (!result.isEmpty()) {
					if (result.get(0).contains("oproduct")) {
						this.tagged = this.tagged.replace("oproduct", "temp");
						this.tagged = this.tagged.replace("tproduct", "oproduct");
						this.tagged = this.tagged.replace("temp", "oproduct");
						result.set(0, "tproduct");
						result.set(2, "oproduct");
						if (result.get(1).equals(">")) {
							result.set(1, "<");
						} else if (result.get(1).equals("<")) {
							result.set(1, ">");
						}
					}
					if (result.get(0).contains("tproduct")) {
						if (result.get(1).contains("<")) {
							this.tagged = this.tagged.replace("tproduct", "tproduct không");
						}
					}
					return true;
				}
			} else if (!checkOP()) {
				return true;
			}

		} else if (!checkTp()) {
			if (checkOP()) {
				String regex1 = "(=|>|<|oproduct)";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.tagged, regex1);
				if (!result.isEmpty() && result.size() >= 2 && (result.contains("=")||result.contains("<")||result.contains(">"))) {
					if (!result.get(0).contains("oproduct")) {
						if (result.get(0).contains("<")) {
							this.tagged = "không/r " + this.tagged;
						}
					} else if (result.get(0).contains("oproduct")
							&& result.size() >= 2) {
						if (result.get(1).equals(">")) {
							result.set(1, "<");

						} else if (result.get(1).equals("<")) {
							result.set(1, ">");
						}

						if (result.get(1).equals("<")) {
							this.tagged = this.tagged.replace("/ft ",
									"/ft không/dnw ");
						}
					}
					return true;
				} else {
					return false;
				}
			} else if (!checkOP()) {
				return true;
			}
		}
		return false;
	}

	public void setForVocabualary() {
		this.vocabularys = new ArrayList<Vocabulary>();
		ArrayList<String> words = StringProcessing
				.convertStringToListStringByWord(tagged, " ");
		for (int i = 0; i < words.size(); i++) {
			if (!StringProcessing.getTextWithRegularExpression(words.get(i),
					"(\\/stw)|(\\/ft)|(\\/dnw)|(\\/dgw)|(\\/rw)").isEmpty()) {
				this.vocabularys.add(new Vocabulary(words.get(i)));
			}
		}
	}

	public String getDnw() {
		String result = "";
		for (Vocabulary vocabulary : this.vocabularys) {
			if (vocabulary.mTag.equals("dnw")) {
				result = vocabulary.mContent;
			}
		}
		return result;
	}

	public void createStringByRule() {
		for (int i = 0; i < this.vocabularys.size(); i++) {
			if (i == 0) {
				this.stringByRule = this.vocabularys.get(i).mTag;
			} else {
				this.stringByRule += " + " + this.vocabularys.get(i).mTag;
			}
		}

		this.stringByRule = this.stringByRule.replace(" + , + ", " | ");
	}

	public void findRule() {
		for (Rule r : StoringData.sRules) {
			if (this.stringByRule.contains(r.mLeftContent)) {
				this.stringByRule = this.stringByRule.replace(r.mLeftContent,
						r.mRightContent);
			}
		}
		this.stringByRule = this.stringByRule
				.replaceAll("ft|r0[1-7]|dnw|dgw|rw|stw", "").replace("+", "")
				.replace("  ", " ").trim();
	}

	public void creatSentiPhraseNode() {
		for (Vocabulary v : this.vocabularys) {
			switch (v.mTag) {
			case "stw":
				this.sentiPhraseGraph.stw = v;
				break;
			case "dgw":
				this.sentiPhraseGraph.dgw = v;
				break;
			case "dnw":
				this.sentiPhraseGraph.dnw = v;
				break;
			default:
				break;
			}
		}
		this.sentiPhraseGraph.rule = this.stringByRule;
	}

}
