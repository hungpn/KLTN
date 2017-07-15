package uit.nguyenhung.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import uit.nguyenhung.main.Main;
import uit.nguyenhung.process.StringProcessing;

public class LoadDatabase {
	private Statement stmt;
	private Connection connecter;
	
	public LoadDatabase() {
		this.stmt = null;
		this.connecter = null;
	}
	
	public void addAllComment(List<String> comments, String name) {
		try {
			  Class.forName("org.sqlite.JDBC");
			  this.connecter = DriverManager.getConnection(Main.pathDatabase);
		      System.out.println("Opened database successfully");
		      //this.connecter.setAutoCommit(false);
		      this.stmt = this.connecter.createStatement();
		      for( int i = 0; i < comments.size(); i++)
		      {
			      String sql = "INSERT INTO Comment (id,product_name,original_content) " +
		                   "VALUES (" + (i + 1) + ", '" + name + "', '" + comments.get(i) +"');"; 
			      this.stmt.executeUpdate(sql);
			      System.out.println("Opened database successfully");
		      }
		      this.stmt.close();
		      this.connecter.close();
		    } catch ( Exception e ) 
		    {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
	}
	
	/*public static void main(String[] args) {
		IO io = new IO();
		List<String> comments = StringProcessing.convertStringToListStringByWord(io.readFile("E:\\Tai Lieu\\khoa luan\\comments vs vnsentiwordnet\\Galaxy S7 edge.txt"), "\n\n");
		LoadDatabase ld = new LoadDatabase();
		ld.addAllComment(comments, "SamSungS7");
	}*/
}
