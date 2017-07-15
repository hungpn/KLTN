package uit.nguyenhung.node;

import java.util.ArrayList;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.model.SentiWord;
import uit.nguyenhung.model.Vocabulary;

public class FeatureNode {
	public Vocabulary ft;
	public ArrayList<SentiPhraseGraph> sps;
	public float score;

	public FeatureNode(Vocabulary ft, ArrayList<SentiPhraseGraph> sps) {
		this.ft = ft;
		this.sps = sps;
		this.score = 0;
		this.calculate();
	}

	private void calculate() {
		ArrayList<SentiWord> sentiWordsOfFeature = new ArrayList<SentiWord>();
		for (SentiWord stw : StoringData.sSentiWords) {
			if (stw.mFeature.equals(this.ft.mWord.replaceAll("\\]|\\[", ""))) {
				sentiWordsOfFeature.add(stw);
			}
		}
		for (SentiPhraseGraph stwg : this.sps) {
			this.score += stwg.calculate(sentiWordsOfFeature);
		}
		System.out.println();
	}
}
