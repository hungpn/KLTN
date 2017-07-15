package uit.nguyenhung.node;

import java.util.ArrayList;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.model.DegreeWord;
import uit.nguyenhung.model.DeniedWord;
import uit.nguyenhung.model.Rule;
import uit.nguyenhung.model.SentiWord;
import uit.nguyenhung.model.Vocabulary;
import uit.nguyenhung.process.StringProcessing;

public class SentiPhraseGraph {
	public Vocabulary dgw;
	public Vocabulary dnw;
	public Vocabulary stw;
	public String rule;

	public SentiPhraseGraph() {
		this.dgw = new Vocabulary();
		this.dnw = new Vocabulary();
		this.stw = new Vocabulary();
		this.rule = "";
	}

	public SentiPhraseGraph(Vocabulary dgw, Vocabulary dnw, Vocabulary stw,
			String rule) {
		this.dgw = dgw;
		this.dnw = dnw;
		this.stw = stw;
		this.rule = rule;
	}

	public float calculate(ArrayList<SentiWord> sentiWords) {
		Rule r = new Rule(this.rule);
		float score = 0;
		ArrayList<Float> sentiScores = getScoreOfSenti(sentiWords);

		switch (r.mGroup) {
		case "g01": {
			for (Float ss : sentiScores) {
				score += ss;
			}
			break;
		}
		case "g02": {
			for (Float ss : sentiScores) {
				score += -1 * ss;
			}
			break;
		}
		case "g03": {
			float degreeScore = getScoreDegree();
			for (Float ss : sentiScores) {
				score += degreeScore * ss;
			}
			break;
		}
		case "g04": {
			float degreeScore = getScoreDegree();
			for (Float ss : sentiScores) {
				score += degreeScore * -1 * ss;
			}
			break;
		}
		case "g05": {
			float denidewScore = getScoreDenied();
			float degreeScore = getScoreDegree();
			for (Float ss : sentiScores) {
				score += degreeScore * denidewScore * ss;
			}
			break;
		}
		default:
			break;
		}
		return score;
	}

	private ArrayList<Float> getScoreOfSenti(ArrayList<SentiWord> sentiWords) {
		ArrayList<Float> scores = new ArrayList<Float>();
		ArrayList<String> words = new ArrayList<String>();

		if (this.stw.mWord.contains("+")) {
			words.addAll(StringProcessing.convertStringToListStringByWord(
					this.stw.mWord.replace("+", "  "), "  "));
		} else {
			words.add(this.stw.mWord.replace("+", "  "));
		}

		for (String w : words) {
			for (SentiWord stw : sentiWords) {
				if (stw.mWord.equals(w.replace("_", " "))) {
					scores.add(stw.mScore);
				}
			}
		}
		return scores;
	}

	private float getScoreDegree() {
		float score = 0;
		for (DegreeWord dgw : StoringData.sDegreeWords) {
			if (dgw.mWord.equals(this.dgw.mWord.replace("_", " "))) {
				return dgw.mScore;
			}
		}
		return score;
	}

	private float getScoreDenied() {
		float score = 0;
		for (DeniedWord dnw : StoringData.sDeniedWords) {
			if (dnw.mWord.equals(this.dnw.mWord.replace("_", " "))) {
				return dnw.mScore;
			}
		}
		return score;
	}
}
