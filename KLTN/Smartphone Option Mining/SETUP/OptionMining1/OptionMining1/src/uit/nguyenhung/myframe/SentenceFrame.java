package uit.nguyenhung.myframe;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import uit.nguyenhung.model.Comment;
import uit.nguyenhung.node.FeatureNode;
import uit.nguyenhung.node.Graph;
import uit.nguyenhung.node.SentiPhraseGraph;

import javax.swing.JList;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;

public class SentenceFrame extends JFrame {
	private JPanel contentPane;
	private JTextPane tpComment;
	private JList lSentence;
	private JScrollPane spSentence;
	private JScrollPane spComment;
	private DefaultListModel dm = new DefaultListModel();
	private Comment comment;
	private JScrollPane spResult;
	private JTabbedPane tbResult;
	private JScrollPane spExplain;
	private JScrollPane spGraph;
	private JTextPane tpExplain;
	private JButton btnNewButton_1;

	public SentenceFrame(Comment comment) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1300, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.comment = comment;

		setTpComment();
		setLSentence();
		setTabResult();
	}

	private String formatSentence(String str) {
		String pre = "<html><body style='padding: 5px 5px 5px 5px; color:blue; font-size: 15px;'>";
		str = pre + str;
		return str;
	}

	private void setTpComment() {
		tpComment = new JTextPane();
		tpComment.setForeground(Color.BLUE);

		tpComment.setFont(new Font("Times New Roman", Font.BOLD, 16));
		tpComment.setEditable(false);
		tpComment.setBounds(10, 10, 1040, 100);
		tpComment.setContentType("text/html");
		tpComment
				.setText(formatSentence(addQuoteSymbol(this.comment.originalContent ) + "\nScore = " + this.comment.score));

		this.spComment = new JScrollPane();
		this.spComment
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.spComment.setBounds(10, 10, 1274, 100);
		this.spComment.setViewportView(tpComment);
		this.contentPane.add(spComment);
	}

	private void setTabResult() {
		tbResult = new JTabbedPane(JTabbedPane.TOP);
		tbResult.setBounds(270, 121, 1014, 578);

		// tab test

		/*
		 * ArrayList<SentiPhraseNode> list = new ArrayList<>(); list.add(new
		 * SentiPhraseNode("1", "2", "3", "r13")); list.add(new
		 * SentiPhraseNode("4", "5", "6", "r09")); list.add(new
		 * SentiPhraseNode("7", "8", "9", "r10")); ArrayList<SentiPhraseNode>
		 * list1 = new ArrayList<>(); list1.add(new SentiPhraseNode("10", "11",
		 * "12", "r13")); list1.add(new SentiPhraseNode("13", "14", "15",
		 * "r09")); list1.add(new SentiPhraseNode("16", "17", "18", "r10"));
		 * 
		 * ArrayList<FeatureNode> ft = new ArrayList<>(); ft.add(new
		 * FeatureNode("1", list)); ft.add(new FeatureNode("2", list1));
		 * ft.add(new FeatureNode("2", list1)); Graph graph = new Graph("3",
		 * ft);
		 */

		// tab Explain
		spExplain = new JScrollPane();
		spExplain
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.spExplain
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tpExplain = new JTextPane();
		tpExplain.setText("Hello");
		tpExplain.setContentType("text/html");
		spExplain.setViewportView(tpExplain);
		tbResult.addTab("Explain", null, spExplain, null);

		// tab Graph
		spGraph = new JScrollPane();
		spGraph.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spGraph.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tbResult.addTab("Graph", null, spGraph, null);

		contentPane.add(tbResult);
	}

	private void setLSentence() {
		lSentence = new JList();
		lSentence.setBackground(Color.PINK);
		lSentence.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lSentence.setBounds(10, 140, 230, 480);

		this.spSentence = new JScrollPane();
		this.spSentence
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.spSentence.setBounds(10, 121, 250, 578);
		this.spSentence.setViewportView(lSentence);
		loadSentence();
		spSentence.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.contentPane.add(spSentence);
		this.lSentence.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * JOptionPane.showMessageDialog(null,
				 * lSentence.getSelectedIndex());
				 */
				Graph graph = comment.sentences.get(
						lSentence.getSelectedIndex()).getGraph();

				DrawingGraph dg = new DrawingGraph(graph);
				dg.invalidate();
				// dg.drawing();

				dg.setPreferredSize(new Dimension(600 * graph.featureNodes
						.size(), 700));
				dg.setLayout(new GridLayout(0, 1, 100, 100));

				spGraph.setViewportView(dg);
				tpExplain.setText(buildExplain(comment.sentences.get(
						lSentence.getSelectedIndex()).getResult(), comment.sentences.get(
								lSentence.getSelectedIndex()).getScore()));
			}
		});
	}

	private String buildPTag(String str) {
		return "<p style=\"font-size:120%;\">" + str + "</p>";
	}

	private String buildExplain(ArrayList<String> arr, float score) {
		String result = "";
		if (arr != null) {
			result += "<body><h1 style=\"color:#FF00FF\">Tiền xử lý:</h1>";
			result += "<h2 style=\"color:#0489B1\">- Phát hiện từ chỉ sản phẩm</h2>";
			result += buildPTag(arr.get(0));
			result += "<h2 style=\"color:#0489B1\">- Tìm từ so sánh</h2>";
			result += buildPTag(arr.get(1));
			result += "<h2 style=\"color:#0489B1\">- Xóa emtion, phát hiện dấu hiệu để tách vế câu, gán nhãn từ loại, tìm tên sản phẩm khác được nhắc tới</h2> ";
			result += buildPTag(arr.get(2));
			result += "<h1 style=\"color:#FF00FF\">Phân tích câu:</h1>";
			result += "<h2 style=\"color:#0489B1\">- Kiểm tra câu có đang nói về sản phẩm cần xét hay không ?</h2>";
			result += buildPTag("=>" + arr.get(3));
			result += "<h2 style=\"color:#0489B1\">- Kiểm tra xem câu đang nhận xét về tính năng (feature) nào</h2>";
			result += buildPTag(arr.get(4));
			result += "<h2 style=\"color:#0489B1\">- Kiểm tra từ thể hiện quan điểm của câu</h2>";
			result += buildPTag(arr.get(5));
			result += "<h2 style=\"color:#0489B1\">- Tách vế câu và phân tích</h2>";
			result += buildPTag(arr.get(6));
			result += "<h2 style=\"color:#0489B1\">Score = " + score + "</h2>";
			result += "</body>";
		}
			return result;
		
	}

	private void loadSentence() {
		dm.clear();
		for (int i = 0; i < this.comment.sentences.size(); i++) {
			if (i % 2 == 0) {
				dm.addElement(new SentenceItem("("
						+ (i + 1)
						+ ") <br/><br/>"
						+ addQuoteSymbol(this.comment.sentences.get(i)
								.getOriginal()), 0));
			} else {
				dm.addElement(new SentenceItem("("
						+ (i + 1)
						+ ") <br/><br/>"
						+ addQuoteSymbol(this.comment.sentences.get(i)
								.getOriginal()), 1));
			}
		}
		this.lSentence.setCellRenderer(new SentenceRenderer());
		this.lSentence.setModel(dm);
	}

	private String addQuoteSymbol(String str) {
		str = "\"" + str + "\"";
		return str;
	}
}
