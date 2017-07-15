package uit.nguyenhung.main;

import java.awt.EventQueue;
import java.io.File;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.model.Comment;
import uit.nguyenhung.model.Product;
import uit.nguyenhung.model.Sentence;
import uit.nguyenhung.myframe.CommentFrame;
import uit.nguyenhung.myframe.SentenceFrame;
import uit.nguyenhung.vntagger.Tagger;

public class Main {
	public static String pathDatabase = "jdbc:sqlite:";

	public static void main(String[] args) {
		File directory = new File("");
		pathDatabase += directory.getAbsolutePath() + "\\OptionMiningDB";
		StoringData sd = new StoringData();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CommentFrame frame = new CommentFrame();
					frame.setVisible(true);
					/*
					 * Comment comment = new Comment("bphone",
					 * "máy đẹp, cấu hình tốt, màn hình sáng" + "");
					 * SentenceFrame sf = new SentenceFrame(comment);
					 * sf.setVisible(true);
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * Tagger tag = new Tagger(); System.out.println(tag.tagText(""));
		 */
		/*
		 * System.out.println("Done");
		 * 
		 * Sentence sentence = new Sentence();
		 * sentence.setOriginal(("iphone pin yếu hơn bphone") .toString());
		 * sentence.setTagged(sentence.getOriginal()); sentence.setProduct(new
		 * Product("iphone")); sentence.process(); // System.out.println("type "
		 * + sentence.getType());
		 */
	}
}
