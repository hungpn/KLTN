package uit.nguyenhung.model;

import java.util.ArrayList;

public class Feature {
	public String mId;
	public String mName;
	public ArrayList<String> mIndicators;
	public ArrayList<String> mHiddenIndicators;

	public Feature() {
	}

	public Feature(String id, String name, ArrayList<String> indicators,
			ArrayList<String> hiddenIndicators) {
		this.mId = id;
		this.mName = name;
		this.mIndicators = indicators;
		this.mHiddenIndicators = hiddenIndicators;
	}

}
