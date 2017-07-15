package uit.nguyenhung.model;

import java.util.ArrayList;
import java.util.Iterator;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.node.FeatureNode;
import uit.nguyenhung.node.Graph;
import uit.nguyenhung.process.StringProcessing;
import uit.nguyenhung.vntagger.Tagger;

public class Sentence {
	private String mOriginal;
	private String mTagged;
	private float mScore;
	private Product mProduct;
	private int mType;
	private Graph mGraph;
	private ArrayList<String> mResult;
	private ArrayList<PartOfSentence> mSentiParts;
	private ArrayList<String> mFeatureNames;

	public Sentence() {
		this.mOriginal = "";
		this.mTagged = "";
		this.mScore = 0;
		this.mProduct = new Product();
		this.mType = 0;
		this.mSentiParts = new ArrayList<PartOfSentence>();
		this.mFeatureNames = new ArrayList<String>();
		this.mResult = new ArrayList<String>();
		this.mGraph = new Graph();
	}

	public Sentence(String original, Product product) {
		this.mOriginal = original;
		this.mTagged = this.mOriginal;
		this.mScore = 0;
		this.mType = 0;
		this.mProduct = product;
		this.mSentiParts = new ArrayList<PartOfSentence>();
		this.mFeatureNames = new ArrayList<String>();
		this.mResult = new ArrayList<String>();
		this.mGraph = new Graph();
	}

	public String getOriginal() {
		return mOriginal;
	}

	public void setOriginal(String original) {
		this.mOriginal = original;
	}

	public String getTagged() {
		return mTagged;
	}

	public void setTagged(String tagged) {
		this.mTagged = tagged;
	}

	public float getScore() {
		return mScore;
	}

	public void setScore(float score) {
		this.mScore = score;
	}

	public Product getProduct() {
		return mProduct;
	}

	public void setProduct(Product product) {
		this.mProduct = product;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		this.mType = type;
	}

	public ArrayList<PartOfSentence> getSentiParts() {
		return mSentiParts;
	}

	public void setSentiParts(ArrayList<PartOfSentence> sentiParts) {
		this.mSentiParts = sentiParts;
	}

	public ArrayList<String> getResult() {
		return mResult;
	}

	public void setmResult(ArrayList<String> mResult) {
		this.mResult = mResult;
	}

	public Graph getGraph() {
		return mGraph;
	}

	public void setGraph(Graph graph) {
		this.mGraph = graph;
	}

	private void replaceProductName() {
		for (String name : this.mProduct.mListName) {
			if (this.mTagged.contains(name)) {
				this.mTagged = this.mTagged.replace(name, this.mProduct.mName);
			}
		}
		this.mResult.add(this.mTagged);
	}

	private void replaceCpW() {
		for (ComparisionWorld c : StoringData.sComWords) {
			if (this.mTagged.contains(c.mWord)) {
				this.mTagged = this.mTagged.replace(c.mWord, c.mSymbol);
			}
		}
		this.mResult.add(this.mTagged);
	}

	private void tagText() {

		this.mTagged = this.mTagged.replace(this.mProduct.mName, "tproduct");
		this.mTagged = this.mTagged.replaceAll(StoringData.sEmoticonRegrex, "");
		this.mTagged = this.mTagged
				.replaceAll(
						"( bởi vì )|( vì )|( do )|( nên )"
								+ "|( rồi )|( hay )|( mặc dù )|( tuy )|( nhưng )"
								+ "|( không những )|( không chỉ )|( nhờ )|( mà )|( mà còn )"
								+ "|( và )| ; ", " , ").replace(", ,", ",");
		this.mTagged = this.mTagged.replaceAll("@\\w+ ", "").replace("  ", " ");
		Tagger tagger = new Tagger();
		this.mTagged = tagger.tagText(this.mTagged);
		System.out.println(this.mTagged);

		this.mTagged = StringProcessing.replaceWithRegularExpression(
				this.mTagged, "=/cw", "=\\/\\w+");
		this.mTagged = StringProcessing.replaceWithRegularExpression(
				this.mTagged, ">/cw", ">\\/\\w+");
		this.mTagged = StringProcessing.replaceWithRegularExpression(
				this.mTagged, "</cw", "<\\/\\w+");
		this.mTagged = StringProcessing
				.replaceWithRegularExpressionAndException(
						this.mTagged,
						"oproduct",
						"((([^/ ]*)/Np )+(([^/ ]*)/Np))|((([^/ ]*)/Np )+(([^/ M]*)/M))"
								+ "|((([A-Z])\\w+ ?){2,3})|([A-Z]+[0-9])|([0-9][A-Z]+)|([a-z]+[0-9])|([0-9][a-z]+)",
						StoringData.sExceptions);

		this.mTagged = this.mTagged.toLowerCase();
		this.mTagged = this.mTagged.replaceAll(StoringData.sBrandRegrex,
				"oproduct");
		this.mResult.add(this.mTagged);
	}

	private boolean checkTp() {
		if (this.mTagged.contains("tproduct")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkOP() {
		if (this.mTagged.contains("oproduct")) {
			return true;
		} else {
			return false;
		}
	}

	private void checkType() {
		if (checkTp()) {
			if (checkOP()) {
				String regex1 = "(tproduct|=|>|<|oproduct)";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.mTagged, regex1);

				if (!result.isEmpty() && result.size() >= 3) {
					if (result.get(0).contains("oproduct")) {
						this.mTagged = this.mTagged.replace("oproduct", "temp");
						this.mTagged = this.mTagged.replace("tproduct",
								"oproduct");
						this.mTagged = this.mTagged.replace("temp", "tproduct");
						result.set(0, "tproduct");
						result.set(2, "oproduct");
						if (result.get(1).equals(">")) {
							result.set(1, "<");
						} else if (result.get(1).equals("<")) {
							result.set(1, ">");
						}
					}
					if (result.get(0).contains("tp")) {
						if (result.get(1).contains("<")) {
							this.mTagged = this.mTagged.replace("tproduct",
									"tproduct không");
						}
					}
					this.mType = 1;
				}
			} else if (!checkOP()) {
				this.mType = 2;
			}
		} else if (!checkTp()) {
			if (checkOP()) {
				String regex1 = "(\\=|\\>|\\<|oproduct)";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.mTagged, regex1);
				if (!result.isEmpty()
						&& result.size() >= 2
						&& (result.contains("=") || result.contains("<") || result
								.contains(">"))) {
					if (!result.get(0).contains("oproduct")) {
						if (result.get(0).contains("<")) {
							this.mTagged = "không/r " + this.mTagged;
						}
					} else if (result.get(0).contains("oproduct")
							&& result.size() >= 2) {
						if (result.get(1).equals(">")) {
							result.set(1, "<");

						} else if (result.get(1).equals("<")) {
							result.set(1, ">");
						}

						if (result.get(1).equals("<")) {
							this.mTagged = this.mTagged.replace("oproduct",
									"không");
						}
					}
					this.mType = 4;
				} else {
					if (this.mTagged.contains(",/,")) {
						this.mType = 5;
					} else {
						this.mType = 0;
					}

				}
			} else if (!checkOP()) {
				this.mType = 3;
			}
		}
	}

	private void replaceFeature() {
		if (this.mType != 0) {
			this.mResult.add(" true");
			for (Feature f : StoringData.sFeatures) {
				for (String s : f.mIndicators) {
					if (this.mTagged.contains(s.replace(" ", "_") + "/")) {
						this.mTagged = StringProcessing
								.replaceWithRegularExpression(this.mTagged, "["
										+ f.mName.replace(" ", "_") + "]/ft",
										s.replace(" ", "_") + "\\/\\w+");
						if (!this.mFeatureNames.contains(f.mName))
							this.mFeatureNames.add(f.mName);
					}
				}
				if (this.mTagged.contains(",")
						|| !this.mTagged.contains(f.mName)) {
					for (String s : f.mHiddenIndicators) {
						if (!s.equals("")
								&& this.mTagged.contains(s.replace(" ", "_")
										+ "/")) {
							ArrayList<String> result = StringProcessing
									.getTextWithRegularExpression(
											mTagged,
											"(\\S+\\/dnw \\S+\\/v "
													+ s.replace(" ", "_")
													+ "\\/\\w+)|(\\S+\\/dgw \\S+\\/v "
													+ s.replace(" ", "_")
													+ "\\/\\w+)|(\\S+\\/dnw \\S+\\/rw "
													+ s.replace(" ", "_")
													+ "\\/\\w+)|(\\S+\\/dgw \\S+\\/rw "
													+ s.replace(" ", "_")
													+ "\\/\\w+)|(\\S+\\/dgw "
													+ s.replace(" ", "_")
													+ "\\/\\w+)|(\\S+\\/dnw \\S+\\/dgw "
													+ s.replace(" ", "_")
													+ "\\/\\w+)|(\\S+\\/dnw "
													+ s.replace(" ", "_")
													+ "\\/\\w+)");

							if (!result.isEmpty()) {
								for (String r : result) {
									this.mTagged = this.mTagged.replace(r, "["
											+ f.mName.replace(" ", "_")
											+ "]/ft " + r);
								}
							} else {
								this.mTagged = this.mTagged
										.replace(s.replace(" ", "_"), "["
												+ f.mName.replace(" ", "_")
												+ "]/ft " + s.replace(" ", "_"));

							}
							if (!this.mFeatureNames.contains(f.mName))
								this.mFeatureNames.add(f.mName);
							break;
						}
					}
				}
			}

			if (this.mTagged.contains("đẹp") || this.mTagged.contains("xấu")) {
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(
								this.mTagged,
								"(\\[price\\]\\/ft.+\\[design\\]\\/ft)|(\\[screen\\]\\/ft.+\\[design\\]\\/ft)|(\\[camera\\]\\/ft.+\\[design\\]\\/ft)");
				if (!result.isEmpty()) {
					this.mTagged = this.mTagged.replace("[design]/ft ", "");
				}
			}
			this.mTagged = this.mTagged.replace("[design]/ft [design]/ft",
					"[design]/ft").replace("[price]/ft [price]/ft",
					"[price]/ft");
			if (!this.mFeatureNames.isEmpty())
				this.mType = 6;

		} else {
			this.mResult.add(" Câu này không phải là câu quan điểm !");
		}

	}

	private void replaceRW() {
		if (this.mType != 0) {
			for (ReferWord rw : StoringData.sReferWords) {
				String s = rw.mWord.replace(" ", "_") + "/";
				if (this.mTagged.contains(s)) {
					this.mTagged = StringProcessing
							.replaceWithRegularExpression(this.mTagged, s
									+ "rw", s + "\\w+");
				}
			}
		}
	}

	private void replaceDgW() {
		if (this.mType != 0) {
			for (DegreeWord dgw : StoringData.sDegreeWords) {
				String s = " " + dgw.mWord.replace(" ", ".+") + "/";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.mTagged, s);
				if (!result.isEmpty()
						&& !StoringData.sWordOfSentiWords.contains(dgw.mWord)) {
					this.mTagged = StringProcessing
							.replaceWithRegularExpression(this.mTagged, " "
									+ dgw.mWord.replace(" ", "_") + "/dgw", s
									+ "\\w+");
				}
			}
		}
	}

	private void replaceDnW() {
		if (this.mType != 0) {
			this.mTagged = " " + this.mTagged;
			for (DeniedWord dnw : StoringData.sDeniedWords) {
				String s = " " + dnw.mWord.replace(" ", ".+") + "/";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.mTagged, s);
				if (!result.isEmpty()
						&& !StoringData.sWordOfSentiWords.contains(dnw.mWord)) {
					this.mTagged = StringProcessing
							.replaceWithRegularExpression(this.mTagged, " "
									+ dnw.mWord.replace(" ", "_") + "/dnw", s
									+ "\\w+");
				}
			}
		}
	}

	private void replaceStw() {
		if (this.mType == 6) {
			this.mResult.add(this.mTagged);
			for (SentiWord stw : StoringData.sSentiWords) {
				String s = " " + stw.mWord.replace(" ", ".+") + "/";
				ArrayList<String> result = StringProcessing
						.getTextWithRegularExpression(this.mTagged, s);
				if (!result.isEmpty()
						&& this.mFeatureNames.contains(stw.mFeature)) {
					this.mTagged = StringProcessing
							.replaceWithRegularExpression(this.mTagged, " "
									+ stw.mWord.replace(" ", "_") + "/stw", s
									+ "\\w+");

				}
			}

			// thay chữ /stw và/cc /stw, /ft và/cc /ft
			ArrayList<String> results = StringProcessing
					.getTextWithRegularExpression(mTagged,
							"(\\/stw ,/, \\S+\\/stw)|(\\/ft ,/, \\S+\\/ft)");
			while (!results.isEmpty()) {
				for (String r : results) {
					this.mTagged = this.mTagged.replace(r,
							r.replace(" ,/, ", "+").replace("/ft+", "+")
									.replace("+[", "+").replace("/stw+", "+"));
					this.mTagged = this.mTagged.replace("]+", "+");
				}
				results = StringProcessing.getTextWithRegularExpression(
						mTagged,
						"(\\/stw ,/, \\S+\\/stw)|(\\/ft ,/, \\S+\\/ft)");
			}

			for (Feature f : StoringData.sFeatures) {
				this.mTagged = this.mTagged.replaceAll(f.mName + "(\\+"
						+ f.mName + ")+", f.mName);
			}
			this.mType = 7;

		} else {
			this.mResult.add("Câu này không phải là câu quan điểm !");
		}
	}

	private void createListPart() {
		if (this.mType == 7) {
			this.mResult.add(this.mTagged.trim());
			/*
			 * this.mTagged = this.mTagged.replaceAll("(>/cw)|(</cw)|(=/cw)",
			 * ",/,");
			 */
			ArrayList<String> result = StringProcessing
					.convertStringToListStringByWord(this.mTagged, ",/,");
			for (String r : result) {
				if (r.contains("stw")) {
					this.mSentiParts.add(new PartOfSentence(r));
				}
			}

			ArrayList<PartOfSentence> tempList = new ArrayList<>();

			for (int i = 0; i < this.mSentiParts.size(); i++) {
				boolean check = this.mSentiParts.get(i).checkType();
				if (!check) {
					// this.mSentiParts.remove(i);
				} else {
					this.mSentiParts.get(i).process();
					tempList.add(this.mSentiParts.get(i));
				}
			}

			this.mSentiParts = tempList;

			if (this.mSentiParts.size() >= 2) {
				while (!this.mSentiParts.get(0).tagged.contains("ft")) {
					if (!this.mSentiParts.get(1).tagged.contains("ft")) {
						this.mSentiParts.remove(0);
					} else {
						String temp = this.mSentiParts.get(1).featureVocabulary.mContent
								+ " " + this.mSentiParts.get(0).tagged.trim();
						this.mSentiParts.get(0).tagged = temp;
						this.mSentiParts.get(0).setForVocabualary();
						this.mSentiParts.get(0).featureVocabulary = this.mSentiParts
								.get(1).featureVocabulary;
					}
				}
			}
			if (this.mSentiParts.size() > 0) {
				String temp = "";
				for (int i = 1; i < this.mSentiParts.size(); i++) {
					if (!this.mSentiParts.get(i).tagged.contains("ft")) {
						temp = this.mSentiParts.get(i - 1).featureVocabulary.mContent
								+ " " + this.mSentiParts.get(i).tagged.trim();
						this.mSentiParts.get(i).tagged = temp;
						this.mSentiParts.get(i).setForVocabualary();
						this.mSentiParts.get(i).featureVocabulary = this.mSentiParts
								.get(i - 1).featureVocabulary;
					}
				}
				for (PartOfSentence p : this.mSentiParts) {
					p.createStringByRule();
					p.findRule();
					p.creatSentiPhraseNode();
				}

				for (int i = 0; i < this.mSentiParts.size(); i++) {
					if (!StringProcessing.getTextWithRegularExpression(
							this.mSentiParts.get(i).stringByRule, "r0[1-7]")
							.isEmpty()
							|| this.mSentiParts.get(i).stringByRule.length() > 4
							|| this.mSentiParts.get(i).stringByRule.length() < 3) {
						this.mSentiParts.remove(i);
					}
				}

				tempList = new ArrayList<>();
				for (PartOfSentence partOfSentence : this.mSentiParts) {
					if (partOfSentence.featureVocabulary.mWord.contains("+")) {
						System.out.println();
						ArrayList<String> featureWords = StringProcessing
								.convertStringToListStringByWord(
										partOfSentence.featureVocabulary.mWord.replaceAll("(\\[)|(\\])", "").replace("+", "split"),
										"split");
						for (String fw : featureWords) {
							Vocabulary f = new Vocabulary("[" + fw + "]/ft");
							tempList.add(new PartOfSentence(partOfSentence.tagged, partOfSentence.vocabularys, partOfSentence.stringByRule, f, partOfSentence.sentiPhraseGraph));
						}
					} else {
						tempList.add(partOfSentence);
					}
				}
				
				this.mSentiParts = tempList;
			}
			this.mType = 8;
			printSentiParts();
		} else {
			this.mResult.add("Câu này không phải là câu quan điểm !");
			this.mResult.add("");
		}
	}

	private void createGraph() {
		if (this.mType == 8 && this.mSentiParts.size() > 0) {
			this.mGraph = new Graph(this.mSentiParts, this.mProduct.mName);
			this.calculate();
		}

	}

	private boolean verify() {
		if (mProduct.mListName == null) {
			return false;
		}
		return true;
	}

	private void printSentiParts() {
		if (!this.mSentiParts.isEmpty()) {
			String print = "";
			for (PartOfSentence p : this.mSentiParts) {
				System.out.println("- Vế câu "
						+ (this.mSentiParts.indexOf(p) + 1) + ": " + p.tagged);
				print += "+ Vế câu " + (this.mSentiParts.indexOf(p) + 1) + ": "
						+ p.tagged + "<br/>";
				String temp = p.vocabularys.get(0).mContent;
				for (int i = 1; i < p.vocabularys.size(); i++) {
					temp += " + " + p.vocabularys.get(i).mContent;
				}
				System.out.println("Sau khi rút gọn: " + temp);
				print += "	Sau khi rút gọn: " + temp + "<br/>";
				System.out.println("\tString by rule: " + p.stringByRule);
				print += "	=> <strong>" + p.stringByRule
						+ "</strong><br/>";
			}

			this.mResult.add(print);
		} else {
			this.mResult.add("");
		}
	}

	public void calculate() {
		for (FeatureNode ftn : this.mGraph.featureNodes) {
			this.mScore += ftn.score;
		}
	}

	public void process() {
		if (verify()) {
			replaceProductName();
			replaceCpW();
			System.out.println(this.mTagged);
			tagText();
			checkType();
			System.out.println(this.mTagged);
			replaceRW();
			replaceDnW();
			replaceDgW();
			System.out.println(this.mTagged);
			replaceFeature();
			replaceStw();
			System.out.println(this.mTagged.trim());
			createListPart();
			createGraph();
		} else {
			System.out.println("Câu này không phải là câu quan điểm !");
		}
	}
}
