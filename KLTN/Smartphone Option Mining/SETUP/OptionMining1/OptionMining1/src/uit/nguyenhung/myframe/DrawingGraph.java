package uit.nguyenhung.myframe;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.beans.Transient;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.model.Relation;
import uit.nguyenhung.model.Rule;
import uit.nguyenhung.model.Vocabulary;
import uit.nguyenhung.node.FeatureNode;
import uit.nguyenhung.node.Graph;
import uit.nguyenhung.node.SentiPhraseGraph;

public class DrawingGraph extends JPanel {
	private int wBaseRect;
	private int hBaseRect;
	private int distanceRect;
	private int distanceDashedRect;
	private Graph graph;

	public DrawingGraph(Graph graph) {
		this.graph = graph;
		wBaseRect = 160;
		hBaseRect = 80;
		distanceRect = 50;
		distanceDashedRect = 5;
		this.setBackground(Color.WHITE);
		this.setLayout(null);
	}

	public void drawing() {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * r9_r16(g, 50, 10, new SentiPhraseNode("", "không/dnw", "đẹp/stw",
		 * "r8")); r10_r11_r17_r20(g, 160, 10, new SentiPhraseNode("quá/dgw",
		 * "", "đẹp/stw", "r10")); r12_r18(g, 380, 10, new
		 * SentiPhraseNode("quá/dgw", "không/dnw", "đẹp/stw", "r12"));
		 * r13_r14_r19_r21(g, 550, 10, new SentiPhraseNode("quá/dgw",
		 * "không/dnw", "đẹp/stw", "r8"));
		 */
		if (graph.productNode.mContent != "" && graph.productNode.mContent != null) {
			drawGraph(g, 160, 10);
		}
	}

	private String formatWord(String str, String color) {
		String pre = "<html><body style='text-align:center; padding-bottom: 5px; font-size: 12px; color:"
				+ color + "'>";
		str = pre + str;
		return str;
	}

	private String formatFeatureWord(String str, String color) {
		String pre = "<html><body style='text-align:center; color:" + color
				+ "; font-size: 14px;'>";
		str = pre + str;
		return str;
	}

	private final int ARR_SIZE = 5;

	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
				new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
	}

	public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {

		// creates a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();

		// set the stroke of the copy, not the original
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(x1, y1, x2, y2);

		// gets rid of the copy
		g2d.dispose();
	}

	public void drawDashedRect(Graphics g, int x, int y, int width, int height) {
		drawDashedLine(g, x, y, x + width, y);
		drawDashedLine(g, x, y + height, x + width, y + height);
		drawDashedLine(g, x, y, x, y + height);
		drawDashedLine(g, x + width, y, x + width, y + height);
	}

	private void createTextPane(int x, int y, Vocabulary text, Color c) {
		JTextPane jTextPane = new JTextPane();
		jTextPane.setContentType("text/html");
		jTextPane.setBounds(x, y, wBaseRect, hBaseRect);
		String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(),
				c.getBlue());
		jTextPane.setText(formatWord(text.mWord, hex));
		jTextPane.setBorder(new LineBorder(c, 2));
		jTextPane.setForeground(c);
		jTextPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(c), text.mTag.toUpperCase()));
		this.add(jTextPane);
	}

	private void r8_r15_r23(int x, int y, SentiPhraseGraph spn) {

		createTextPane(x - wBaseRect / 2, y, spn.stw, Color.BLUE);
	}

	private void r9_r16(Graphics g, int x, int y, SentiPhraseGraph spn) {
		// draw stw
		createTextPane(x - wBaseRect / 2, y, spn.stw, Color.BLUE);

		// draw dnw
		createTextPane(x - wBaseRect / 2, y + hBaseRect + distanceRect,
				spn.dnw, Color.MAGENTA);

		// draw line
		drawArrow(g, x, y + hBaseRect + distanceRect, x, y + hBaseRect);
		drawRelation(spn.dnw.mTag, spn.stw.mTag, x, y + hBaseRect
				+ distanceRect, x);
	}

	private void r10_r11_r17_r20_r24(Graphics g, int x, int y,
			SentiPhraseGraph spn) {
		// draw stw
		createTextPane(x - wBaseRect / 2, y, spn.stw, Color.BLUE);

		// draw dgw
		createTextPane(x - wBaseRect / 2, y + hBaseRect + distanceRect,
				spn.dgw, Color.PINK);

		// draw line
		drawArrow(g, x, y + hBaseRect + distanceRect, x, y + hBaseRect);
		drawRelation(spn.dgw.mTag, spn.stw.mTag, x, y + hBaseRect
				+ distanceRect, x);
	}

	private void r12_r18(Graphics g, int x, int y, SentiPhraseGraph spn) {
		// draw stw
		createTextPane(x - wBaseRect / 2, y, spn.stw, Color.BLUE);

		// draw dnw
		createTextPane(x - wBaseRect / 2, y + hBaseRect + distanceRect,
				spn.dnw, Color.MAGENTA);

		// draw line
		drawArrow(g, x, y + hBaseRect + distanceRect, x, y + hBaseRect);
		drawRelation(spn.dnw.mTag, spn.stw.mTag, x, y + hBaseRect
				+ distanceRect, x);

		// draw dot-line rect
		drawDashedRect(g, x - wBaseRect / 2 - distanceDashedRect, y
				- distanceDashedRect, wBaseRect + distanceDashedRect * 2,
				hBaseRect * 2 + distanceDashedRect * 2 + distanceRect);

		// draw dgw
		createTextPane(x - wBaseRect + distanceDashedRect * 4, y + hBaseRect
				* 2 + distanceRect * 2, spn.dgw, Color.PINK);

		// draw line
		drawArrow(g, x - wBaseRect / 2 + distanceDashedRect * 4, y + hBaseRect
				* 2 + distanceRect * 2,
				(x - wBaseRect / 2 - distanceDashedRect)
						+ (wBaseRect + distanceDashedRect * 2) / 2, (hBaseRect
						* 2 + distanceDashedRect * 2 + distanceRect)
						+ (y - distanceDashedRect));
		drawRelation(spn.dgw.mTag, spn.dnw.mTag + " + " + spn.stw.mTag, x
				- wBaseRect / 2 + distanceDashedRect * 4, y + hBaseRect * 2
				+ distanceRect * 2, (x - wBaseRect / 2 - distanceDashedRect)
				+ (wBaseRect + distanceDashedRect * 2) / 2);
	}

	private void r13_r14_r19_r21_r22(Graphics g, int x, int y,
			SentiPhraseGraph spn) {
		// draw stw
		createTextPane(x - wBaseRect / 2, y, spn.stw, Color.BLUE);

		// draw dgw
		createTextPane(x - wBaseRect / 2, y + hBaseRect + distanceRect,
				spn.dgw, Color.PINK);

		// draw line
		drawArrow(g, x, y + hBaseRect + distanceRect, x, y + hBaseRect);
		drawRelation(spn.dgw.mTag, spn.stw.mTag, x, y + hBaseRect
				+ distanceRect, x);

		// draw dot-line rect
		drawDashedRect(g, x - wBaseRect / 2 - distanceDashedRect, y
				- distanceDashedRect, wBaseRect + distanceDashedRect * 2,
				hBaseRect * 2 + distanceDashedRect * 2 + distanceRect);

		// draw dnw
		createTextPane(x - wBaseRect + distanceDashedRect * 4, y + hBaseRect
				* 2 + distanceRect * 2, spn.dnw, Color.MAGENTA);

		// draw line
		drawArrow(g, x - wBaseRect / 2 + distanceDashedRect * 4, y + hBaseRect
				* 2 + distanceRect * 2,
				(x - wBaseRect / 2 - distanceDashedRect)
						+ (wBaseRect + distanceDashedRect * 2) / 2, (hBaseRect
						* 2 + distanceDashedRect * 2 + distanceRect)
						+ (y - distanceDashedRect));
		drawRelation(spn.dnw.mTag, spn.dgw.mTag + " + " + spn.stw.mTag, x
				- wBaseRect / 2 + distanceDashedRect * 4, y + hBaseRect * 2
				+ distanceRect * 2, (x - wBaseRect / 2 - distanceDashedRect)
				+ (wBaseRect + distanceDashedRect * 2) / 2);
	}

	private void drawRelation(String leftObject, String rightObject,
			int xStartArrow, int yStartArrow, int xFinishArrow) {
		for (Relation r : StoringData.sRelations) {
			if (r.leftObject.equals(leftObject)
					&& r.rightObject.equals(rightObject)) {
				JLabel lRelation = new JLabel();
				lRelation.setBounds(xStartArrow - (xStartArrow - xFinishArrow)
						/ 2 + 10, yStartArrow - this.distanceRect / 2 - 10,
						100, 20);
				lRelation.setText(r.label);

				this.add(lRelation);
				break;
			}
		}
	}

	private void drawFeatureNode(Graphics g, int x, int y, FeatureNode ftn) {

		// draw feature node
		// draw stw

		createTextPane(x - wBaseRect / 2, y, ftn.ft, Color.GREEN);

		// draw senti phrase node
		int size = ftn.sps.size();
		int delta = wBaseRect + distanceDashedRect * 4;
		int x1 = x;
		int y1 = y + hBaseRect + distanceRect;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (ftn.sps.get(i).rule.length() == 3) {
					Rule r = new Rule(ftn.sps.get(i).rule);
					drawArrow(g, x1, y1, x, y + hBaseRect);
					drawRelation(ftn.sps.get(i).stw.mTag, ftn.ft.mTag, x1, y1,
							x);
					switch (r.mGroup) {
					case "g01":
						r8_r15_r23(x1, y1, ftn.sps.get(i));
						break;
					case "g02":
						r9_r16(g, x1, y1, ftn.sps.get(i));
						break;
					case "g03":
						r10_r11_r17_r20_r24(g, x1, y1, ftn.sps.get(i));
						break;
					case "g04":
						r12_r18(g, x1, y1, ftn.sps.get(i));
						break;
					case "g05":
						r13_r14_r19_r21_r22(g, x1, y1, ftn.sps.get(i));
						break;
					default:
						break;
					}
					x1 += delta;
				}
			}
		}
	}

	private void drawGraph(Graphics g, int x, int y) {
		// draw product
		createTextPane(x - wBaseRect / 2, y, this.graph.productNode,
				Color.BLACK);

		// draw feature node
		int x1 = x;
		int y1 = y + hBaseRect + distanceRect;
		int size = this.graph.featureNodes.size();
		int delta = distanceDashedRect * 4;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (this.graph.featureNodes.get(i).sps.size() > 0) {
					drawArrow(g, x1, y1, x, y + hBaseRect);
					drawRelation(this.graph.featureNodes.get(i).ft.mTag,
							this.graph.productNode.mTag, x1, y1, x);
					drawFeatureNode(g, x1, y1, this.graph.featureNodes.get(i));
					int sizeStp = this.graph.featureNodes.get(i).sps.size();
					x1 = x1 + wBaseRect * sizeStp + delta * (sizeStp - 1)
							+ delta * 3;
				}
			}
		}
	}
}
