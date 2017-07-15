package uit.nguyenhung.model;

public class Relation {
	public String id;
	public String leftObject;
	public String rightObject;
	public String label;
	
	public Relation(String id, String leftObject, String rightObject,
			String label) {
		this.id = id;
		this.leftObject = leftObject;
		this.rightObject = rightObject;
		this.label = label;
	}
	
	
}
