package uit.nguyenhung.downloadweb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import uit.nguyenhung.list.model.StoringData;
import uit.nguyenhung.model.Comment;
import uit.nguyenhung.process.StringProcessing;

public class DownloadComment {

	public static String getTextNomal(String link) {
		String text = "";
		try {
			URL url = new URL(link);
			URLConnection urlConnect = url.openConnection();
			// Dùng phương tiện Scanner để đọc
			Scanner input = new Scanner(urlConnect.getInputStream(), "UTF-8");// đọc
																				// theo
																				// bảng
																				// mã
																				// utf-8

			while (input.hasNextLine()) {
				// Đọc 1 dòng
				String line = input.nextLine();
				if (line != "") {
					text += line;
				}
			}
			// đóng luồng
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	public static ArrayList<String> getArticleIds(String productName) {
		String result = getTextNomal("http://timkiem.vnexpress.net/sohoa?q="
				+ productName);
		ArrayList<String> articleIds = new ArrayList<String>();
		if (result
				.contains("<div class=\"block_mid_new\" id=\"result_search\">")
				&& result
						.contains("<div class=\"bottom_pagination width_common\">")) {
			result = result
					.substring(
							result.indexOf("<div class=\"block_mid_new\" id=\"result_search\">"),
							result.indexOf("<div class=\"bottom_pagination width_common\">"));
			articleIds = StringProcessing.getTextWithRegularExpression(result,
					"[0-9]+\\.html");
		}

		return articleIds;
	}

	public static ArrayList<Comment> getCommentsAnId(String articleId,
			String productName) {
		articleId = articleId.replace(".html", "");
		ArrayList<Comment> result = new ArrayList<>();
		String text = getTextNomal("http://usi.saas.vnexpress.net/index/get?offset=0&limit=100&frommobile=0&sort=like&objectid="
				+ articleId + "&objecttype=1&siteid=1002592&categoryid=1002644");
		
		// xử lý để tách ra từng comment
		text = text.replaceAll("(\\\"content\\\"\\:\\\")|(\\\"\\,\\\"full\\_name\\\")",
				"Split");
		ArrayList<String> contents = StringProcessing
				.convertStringToListStringByWord(text, "Split");
		int count = 1;
		for (int i = 1; i < contents.size(); i += 2) {
			contents.set(i, contents.get(i).replaceAll("(\\<br\\/\\>)|(\\<p\\/\\>)", "\n").replaceAll("(@\\w+ )|(\")|(@[\\S\\s]+\\:)", "").trim());
			StringBuffer sb = new StringBuffer(contents.get(i).toString());
			Comment c = new Comment(count, productName, sb.toString(),
					articleId);
			result.add(c);
			count++;
		}
		return result;
	}

	public static ArrayList<Comment> getComments(
			String productName) {
		ArrayList<String> articleIds = getArticleIds(productName);
		ArrayList<Comment> result = new ArrayList<>();
		for (String i : articleIds) {
			result.addAll(getCommentsAnId(i, productName));
		}
		return result;
	}
}
