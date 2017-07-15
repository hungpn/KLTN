package uit.nguyenhung.node;

import java.util.ArrayList;

import uit.nguyenhung.model.PartOfSentence;
import uit.nguyenhung.model.Vocabulary;

public class Graph {
	public Vocabulary productNode;
	public ArrayList<FeatureNode> featureNodes;

	public Graph(){
		this.productNode = new Vocabulary();
		this.featureNodes = new ArrayList<FeatureNode>();
	}
	
	public Graph(Vocabulary productNode, ArrayList<FeatureNode> featureNodes) {
		this.productNode = productNode;
		this.featureNodes = featureNodes;
	}

	public Graph(ArrayList<PartOfSentence> pos, String productNode) {
		Vocabulary temp = new Vocabulary(productNode + "/p");
		this.productNode = temp;
		this.featureNodes = new ArrayList<FeatureNode>();

		ArrayList<Vocabulary> features = new ArrayList<Vocabulary>();
		for (PartOfSentence p : pos) {
			if (!checkContain(p.featureVocabulary, features)) {
				features.add(p.featureVocabulary);
			}
		}

		for (Vocabulary f : features) {
			ArrayList<SentiPhraseGraph> seniPhraseNodes = new ArrayList<SentiPhraseGraph>();
			for (PartOfSentence p : pos) {
				if (f.mContent.equals(p.featureVocabulary.mContent)) {
					seniPhraseNodes.add(p.sentiPhraseGraph);
				}
			}
			this.featureNodes.add(new FeatureNode(f, seniPhraseNodes));
		}
	}

	public boolean checkContain(Vocabulary v, ArrayList<Vocabulary> features){
		for (Vocabulary f : features) {
			if(v.mContent.equals(f.mContent))
				return true;
		}
		return false;
	}
}
