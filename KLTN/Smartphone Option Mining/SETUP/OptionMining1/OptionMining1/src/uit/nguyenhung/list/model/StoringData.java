package uit.nguyenhung.list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uit.nguyenhung.main.Main;
import uit.nguyenhung.model.Comment;
import uit.nguyenhung.model.ComparisionWorld;
import uit.nguyenhung.model.DegreeWord;
import uit.nguyenhung.model.DeniedWord;
import uit.nguyenhung.model.Feature;
import uit.nguyenhung.model.Product;
import uit.nguyenhung.model.ReferWord;
import uit.nguyenhung.model.Relation;
import uit.nguyenhung.model.Rule;
import uit.nguyenhung.model.SentiWord;
import uit.nguyenhung.process.StringProcessing;

public class StoringData {
	public static ArrayList<ComparisionWorld> sComWords;
	public static ArrayList<DegreeWord> sDegreeWords;
	public static ArrayList<DeniedWord> sDeniedWords;
	public static ArrayList<ReferWord> sReferWords;
	public static ArrayList<Feature> sFeatures;
	public static ArrayList<Product> sProducts;
	public static ArrayList<SentiWord> sSentiWords;
	public static ArrayList<String> sWordOfSentiWords;
	public static ArrayList<Rule> sRules;
	public static ArrayList<Comment> sComments;
	public static ArrayList<Relation> sRelations;
	public static String[] sExceptions = { "hệ_điều_hành/N", "ram/N" };
	public static String sBrandRegrex = "(htc)|(iphone)|(zenfone)|(sony)|(asus)|(lg)|(bphone)|(sam)|(apple)";
	public static String sEmoticonRegrex = "(:d)|(:D)|(:-D)|(:-\\)+)|(:\\)+)|(:3)|(:\\]+)|(=\\)+)|(=\\]+)|(:p)|(:P)|(:-p)|(:-P)|(xD)|(:s)|(:S)|(:-s)|(:-S)|(:o)|(:O)|(:-o)|(:-O)|(<3)";
	private Connection connecter;
	private Statement stmt;

	public StoringData() {
		this.connecter = null;
		this.stmt = null;
		this.sComWords = new ArrayList<ComparisionWorld>();
		this.sDegreeWords = new ArrayList<DegreeWord>();
		this.sDeniedWords = new ArrayList<DeniedWord>();
		this.sFeatures = new ArrayList<Feature>();
		this.sProducts = new ArrayList<Product>();
		this.sSentiWords = new ArrayList<SentiWord>();
		this.sRules = new ArrayList<Rule>();
		this.sComments = new ArrayList<>();
		this.sReferWords = new ArrayList<>();
		this.sRelations = new ArrayList<Relation>();
		this.sWordOfSentiWords = new ArrayList<String>();
		init();
	}

	private void init() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connecter = DriverManager.getConnection(Main.pathDatabase);
			this.connecter.setAutoCommit(false);
			this.stmt = this.connecter.createStatement();
			System.out.println("connected");

			getAllProducts();
			getAllComWords();
			getAllDegreeWords();
			getAllDeniedWords();
			getAllFeatures();
			getAllSentiWords();
			getAllRules();
			getAllComments();
			getAllRefers();
			getAllRelations();
			this.stmt.close();
			this.connecter.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAllProducts() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Product;");
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String listName = rs.getString("list_name");
				this.sProducts.add(new Product(id, name, StringProcessing
						.convertStringToListStringByWord(listName, ", ")));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllComWords() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM ComparisionWord;");
			while (rs.next()) {
				String id = rs.getString("id");
				String word = rs.getString("word");
				String symbol = rs.getString("symbol");
				this.sComWords.add(new ComparisionWorld(id, word, symbol));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllDegreeWords() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM DegreeWord;");
			while (rs.next()) {
				String id = rs.getString("id");
				String word = rs.getString("word");
				float score = rs.getFloat("score");
				this.sDegreeWords.add(new DegreeWord(id, word, score));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllDeniedWords() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM DeniedWord;");
			while (rs.next()) {
				String id = rs.getString("id");
				String word = rs.getString("word");
				float score = rs.getFloat("score");
				this.sDeniedWords.add(new DeniedWord(id, word, score));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllFeatures() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Feature;");
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String list_indicator = rs.getString("list_indicator");
				String list_hiddenIndicator = rs.getString("list_hidden_indicator");
				this.sFeatures.add(new Feature(id, name, StringProcessing
						.convertStringToListStringByWord(list_indicator, ", "),
						StringProcessing.convertStringToListStringByWord(
								list_hiddenIndicator, ", ")));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllSentiWords() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM SentiWord;");
			while (rs.next()) {
				String id = rs.getString("id");
				String word = rs.getString("word");
				String feature = rs.getString("feature");
				float score = rs.getFloat("score");
				this.sSentiWords.add(new SentiWord(id, word, feature, score));
				this.sWordOfSentiWords.add(word);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllRules() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Rule;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String leftContent = rs.getString("left_content");
				String rightContent = rs.getString("right_content");
				String group = rs.getString("group");
				this.sRules.add(new Rule(id, leftContent, rightContent, group));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getAllComments() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String productName = rs.getString("product_name");
				String originalContent = rs.getString("original_content");
				String articleId = rs.getString("article_id");
				this.sComments
						.add(new Comment(id, productName, originalContent, articleId));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAllRefers() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM ReferWord;");
			while (rs.next()) {
				String id = rs.getString("id");
				String word = rs.getString("word");
				this.sReferWords.add(new ReferWord(id, word));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getAllRelations() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM Relation;");
			while (rs.next()) {
				String id = rs.getString("id");
				String leftObject = rs.getString("left_object");
				String rightObject = rs.getString("right_object");
				String label = rs.getString("label");
				this.sRelations.add(new Relation(id, leftObject, rightObject, label));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getProductName(String productName){
		for (Product p : StoringData.sProducts) {
			if(p.mListName.contains(productName) || p.mName.equals(productName))
			{
				return p.mName;
			}
		}
		return "";
	}
	
	public static ArrayList<Comment> getAListCommentWithProductName(String productName) {
		ArrayList<Comment> result = new ArrayList<Comment>();
		for (Comment comment : sComments) {
			if(comment.productName.equals(productName) || comment.productName.contains(productName)){
				result.add(comment);
			}
		}
		return result;
	}
}
