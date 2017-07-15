package uit.nguyenhung.myframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JComboBox;
import javax.swing.JList;

import uit.nguyenhung.downloadweb.DownloadComment;
import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.model.Comment;
import uit.nguyenhung.model.Product;
import uit.nguyenhung.model.Sentence;

import javax.swing.border.LineBorder;

import edu.stanford.nlp.maxent.Feature;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CommentFrame extends JFrame {

	private JPanel contentPane;
	private static String[] productNames = { "Bphone", "SamSungS7"};
	private JTabbedPane tbResult;
	private JComboBox cbbProductName;
	private JTextField tfProductName;
	private JPanel pStatement;
	private JButton btnStart;
	private JList lCmts;
	private JScrollPane spCmts;
	private DefaultListModel dm = new DefaultListModel();
	private ArrayList<Comment> comments = new ArrayList<Comment>();

	public CommentFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.spCmts = new JScrollPane();
		this.spCmts
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.spCmts.setBounds(10, 150, 970, 400);
		this.spCmts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.contentPane.add(spCmts);

		this.tbResult = new JTabbedPane(JTabbedPane.TOP);
		this.tbResult.setBounds(20, 130, 950, 430);
		this.tbResult.addTab("Comment", null, spCmts, null);

		this.pStatement = new JPanel();
		this.pStatement.setBounds(20, 150, 950, 400);
		this.pStatement.setLayout(null);
		this.pStatement.setBackground(Color.WHITE);
		this.tbResult.addTab("Statement", null, pStatement, null);
		this.contentPane.add(tbResult);
		setBtnStart();
		setTfProductName();
		setLCmts();
		setCbbProductName();

	}

	private void setBtnStart() {
		this.btnStart = new JButton();
		this.btnStart.setText("Bắt đầu");
		this.btnStart.setBounds(20, 90, 100, 20);
		this.add(btnStart);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loadComments(tfProductName.getText());
				loadpStatement();
			}
		});
	}

	private void setLCmts() {
		this.lCmts = new JList();
		lCmts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.spCmts.setViewportView(lCmts);
		lCmts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = lCmts.getSelectedIndex();
				if (index >= 0) {
					SentenceFrame frame = new SentenceFrame(comments.get(index));
					frame.setVisible(true);
				}
			}
		});
	}

	private void setTfProductName() {
		this.tfProductName = new JTextField();
		this.tfProductName.setText("");
		this.tfProductName.setBounds(20, 50, 200, 20);
		this.add(tfProductName);
	}

	private void setCbbProductName() {
		this.cbbProductName = new JComboBox(productNames);
		this.cbbProductName.setBounds(20, 10, 200, 20);

		this.cbbProductName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadComments(cbbProductName.getSelectedItem().toString());
				// tổng hợp theo feature
				loadpStatement();
			}
		});
		loadComments(cbbProductName.getSelectedItem().toString());
		loadpStatement();
		this.contentPane.add(cbbProductName);
	}

	private void loadComments(String productName) {
		dm.clear();
		if (!productName.equals("") && !productName.equals(null)) {
			String name = StoringData.getProductName(productName);
			if (name != "") {
				this.comments = StoringData
						.getAListCommentWithProductName(productName);
			} else {
				this.comments = DownloadComment.getComments(productName);
			}
			for (Comment cm : this.comments) {
				dm.addElement(new CommentItem("\""+cm.originalContent + "\"\nScore = " + cm.score, cm.type));
			}
		}
		this.lCmts.setCellRenderer(new CommentRenderer());
		this.lCmts.setModel(dm);
		System.out.println();
	}

	private Comment process(Comment c) {
		ArrayList<Sentence> temp = new ArrayList<Sentence>();
		for (int i = 0; i < c.sentences.size(); i++) {
			Sentence s = new Sentence(c.sentences.get(i).getOriginal(),
					new Product(c.productName));
			s.process();
			temp.add(s);
			System.out.println();
		}
		c.sentences = temp;
		return c;
	}

	private void loadpStatement() {
		this.pStatement.removeAll();
		int y = 20;
		for (uit.nguyenhung.model.Feature f : StoringData.sFeatures) {
			loadAFeature(f.mName, y);
			y += 40;
		}
	}

	private void loadAFeature(String featrueName, int y) {
		int commentsCount = 0;
		int positiveCount = 0;
		int neutralCount = 0;
		for (Comment comment : this.comments) {
			int type = comment.typeForFeatures.get(featrueName);
			if (type != 3) {
				commentsCount++;
				if (type == 1) {
					positiveCount++;
				} else if (type == 0) {
					neutralCount++;
				}
			}
		}
		int star = 0;
		if (commentsCount != 0) {
			star = ((positiveCount * 100 + (neutralCount * 100 / 2)) / commentsCount);
		}
		JLabel lblFeatureName = new JLabel();
		lblFeatureName.setBounds(20, y, 60, 30);
		lblFeatureName.setText(featrueName);
		JProgressBar pbStar = new JProgressBar();
		pbStar.setBounds(100, y, 200, 30);
		pbStar.setValue(star);
		pbStar.setStringPainted(true);

		JLabel lblStatementSentence = new JLabel();
		lblStatementSentence.setBounds(320, y, 300, 30);
		lblStatementSentence.setText("Positive: " + positiveCount + "/"
				+ commentsCount + ",\t Neutral: " + neutralCount + "/"
				+ commentsCount + ",\t Negative: " + (commentsCount - positiveCount - neutralCount) + "/" + commentsCount);
		this.pStatement.add(lblFeatureName);
		this.pStatement.add(lblStatementSentence);
		this.pStatement.add(pbStar);

	}
}
