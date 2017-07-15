package uit.nguyenhung.model;

import uit.nguyenhung.list.model.StoringData;

public class Rule {
	public int mId;
	public String mLeftContent;
	public String mRightContent;
	public String mGroup;
	
	public Rule(String rightContent) {
		this.mId = 0;
		this.mLeftContent = "";
		this.mRightContent = "";
		this.mGroup = "";
		for (Rule r : StoringData.sRules) {
			if (r.mRightContent.equals(rightContent)) {
				this.mId = r.mId;
				this.mLeftContent = r.mLeftContent;
				this.mRightContent = r.mRightContent;
				this.mGroup = r.mGroup;
				return;
			}
		}
	}
	public Rule(int id, String leftContent, String rightContent, String group) {
		this.mId = id;
		this.mLeftContent = leftContent;
		this.mRightContent = rightContent;
		this.mGroup = group;
	}
	

}
