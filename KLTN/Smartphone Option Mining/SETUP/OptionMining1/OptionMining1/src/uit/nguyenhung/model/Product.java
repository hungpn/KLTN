package uit.nguyenhung.model;

import java.util.ArrayList;

import uit.nguyenhung.list.model.StoringData;

public class Product {
	public String mId;
	public String mName;
	public ArrayList<String> mListName;

	public Product() {
		this.mId = "";
		this.mName = "";
		this.mListName = new ArrayList<String>();
	}

	public Product(String name) {
		for (Product p : StoringData.sProducts) {
			if (p.mListName.contains(name) || p.mName.equals(name)) {
				this.mName = p.mName;
				this.mListName = p.mListName;
				this.mId = p.mId;
				return;
			}
		}
		this.mId = "" + StoringData.sProducts.size();
		this.mName = name;
		this.mListName = new ArrayList<String>();
		this.mListName.add(name);
		System.out.println("Không tìm được sản phẩm");
	}

	public Product(String id, String name, ArrayList<String> listName) {
		this.mId = id;
		this.mName = name;
		this.mListName = listName;
	}
}
