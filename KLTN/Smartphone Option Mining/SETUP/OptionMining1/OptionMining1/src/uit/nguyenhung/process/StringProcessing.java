package uit.nguyenhung.process;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessing {

	public List<String> convertStringToListStringByEndLine(String text) {
		List<String> finalList = new ArrayList<String>();

		String[] arrayTemp = text.split("\t\n");

		for (String string : arrayTemp) {
			if (string != "") {
				finalList.add(string);
			}
		}

		return finalList;
	}

	public static ArrayList<String> convertStringToListStringByWord(
			String text, String word) {
		ArrayList<String> finalList = new ArrayList<String>();
		String[] arrayTemp = text.split(word);

		for (String string : arrayTemp) {
			if (string != "" && string != null) {
				finalList.add(string.trim());
			}
		}

		return finalList;
	}

	public static ArrayList<String> getTextWithRegularExpression(String text,
			String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		ArrayList<String> result = new ArrayList<String>();
		while (matcher.find()) {
			if (!matcher.group().toString().equals(" ")
					&& !matcher.group().toString().equals("")
					&& !matcher.group().toString().equals(null) && !result.contains((matcher.group()).trim())) {
				result.add((matcher.group()).trim());
			}
		}
		return result;
	}

	public static String replaceWithRegularExpression(String text,
			String replacement, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			text = text.replace(matcher.group(), replacement);
		}

		return text;
	}

	public static String replaceWithRegularExpressionAndException(String text,
			String replacement, String regex, String[] listException) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			if (!checkContainListStringWithConditionString(text,
					matcher.group(), listException))
				text = text.replace(matcher.group(), replacement);
		}
		return text;
	}

	public static boolean checkContainListStringWithConditionString(String text,
			String conditionString, String[] listCheck) {
		for (String c : listCheck) {
			if (text.contains(c + " " + conditionString)) {
				return true;
			}
		}
		return false;
	}

	public static String convertStringToUnicode(String text) {
		// viet thuong

		text = text.replace("dd", "đ");

		text = text.replace("aas", "ấ");
		text = text.replace("aaf", "ầ");
		text = text.replace("aaj", "ậ");
		text = text.replace("aax", "ẫ");
		text = text.replace("aar", "ẩ");
		text = text.replace("aa", "â");

		text = text.replace("as", "á");
		text = text.replace("af", "à");
		text = text.replace("aj", "ạ");
		text = text.replace("ax", "ã");
		text = text.replace("ar", "ả");

		text = text.replace("aws", "ắ");
		text = text.replace("awf", "ằ");
		text = text.replace("awj", "ặ");
		text = text.replace("awx", "ẵ");
		text = text.replace("awr", "ẳ");
		text = text.replace("aw", "ă");

		text = text.replace("ees", "ế");
		text = text.replace("eef", "ề");
		text = text.replace("eej", "ệ");
		text = text.replace("eex", "ễ");
		text = text.replace("eer", "ể");
		text = text.replace("ee", "ê");

		text = text.replace("es", "é");
		text = text.replace("ef", "è");
		text = text.replace("ej", "ẹ");
		text = text.replace("ex", "ẽ");
		text = text.replace("er", "ẻ");

		text = text.replace("oos", "ố");
		text = text.replace("oof", "ồ");
		text = text.replace("ooj", "ộ");
		text = text.replace("oox", "ỗ");
		text = text.replace("oor", "ổ");
		text = text.replace("oo", "ô");

		text = text.replace("os", "ó");
		text = text.replace("of", "ò");
		text = text.replace("oj", "ọ");
		text = text.replace("ox", "õ");
		text = text.replace("or", "ỏ");

		text = text.replace("ows", "ớ");
		text = text.replace("owf", "ờ");
		text = text.replace("owj", "ợ");
		text = text.replace("owx", "ỡ");
		text = text.replace("owr", "ở");
		text = text.replace("ow", "ơ");

		text = text.replace("us", "ú");
		text = text.replace("uf", "ù");
		text = text.replace("uj", "ụ");
		text = text.replace("ux", "ũ");
		text = text.replace("ur", "ủ");

		text = text.replace("uws", "ứ");
		text = text.replace("uwf", "ừ");
		text = text.replace("uwj", "ự");
		text = text.replace("uwx", "ữ");
		text = text.replace("uwr", "ử");
		text = text.replace("uw", "ư");

		text = text.replace("is", "í");
		text = text.replace("if", "ì");
		text = text.replace("ij", "ị");
		text = text.replace("ix", "ĩ");
		text = text.replace("ir", "ỉ");

		text = text.replace("ys", "ý");
		text = text.replace("yf", "ỳ");
		text = text.replace("yj", "ỵ");
		text = text.replace("yx", "ỹ");
		text = text.replace("yr", "ỷ");

		// chu hoa
		text = text.replace("DD", "Đ");

		text = text.replace("AAS", "Ấ");
		text = text.replace("AAF", "Ầ");
		text = text.replace("AAJ", "Ậ");
		text = text.replace("AAX", "Ẫ");
		text = text.replace("AAR", "Ẩ");
		text = text.replace("AA", "Â");

		text = text.replace("AS", "Á");
		text = text.replace("AF", "À");
		text = text.replace("AJ", "Ạ");
		text = text.replace("AX", "Ã");
		text = text.replace("AR", "Ả");

		text = text.replace("AWS", "Ắ");
		text = text.replace("AWF", "Ằ");
		text = text.replace("AWJ", "Ặ");
		text = text.replace("AWX", "Ẵ");
		text = text.replace("AWR", "Ẳ");
		text = text.replace("AW", "Ă");

		text = text.replace("EE", "Ê");
		text = text.replace("EES", "Ế");
		text = text.replace("EEF", "Ề");
		text = text.replace("EEJ", "Ệ");
		text = text.replace("EEX", "Ễ");
		text = text.replace("EER", "Ể");

		text = text.replace("ES", "É");
		text = text.replace("EF", "È");
		text = text.replace("EJ", "Ẹ");
		text = text.replace("EX", "Ẽ");
		text = text.replace("ER", "Ẻ");

		text = text.replace("OOS", "Ố");
		text = text.replace("OOF", "Ồ");
		text = text.replace("OOJ", "Ộ");
		text = text.replace("OOX", "Ỗ");
		text = text.replace("OOR", "Ổ");
		text = text.replace("OO", "Ô");

		text = text.replace("OS", "Ó");
		text = text.replace("OF", "Ò");
		text = text.replace("OJ", "Ọ");
		text = text.replace("OX", "Õ");
		text = text.replace("OR", "Ỏ");

		text = text.replace("OWS", "Ớ");
		text = text.replace("OWX", "Ờ");
		text = text.replace("OWJ", "Ợ");
		text = text.replace("OWX", "Ỡ");
		text = text.replace("OWR", "Ở");
		text = text.replace("OW", "Ơ");

		text = text.replace("US", "Ú");
		text = text.replace("UF", "Ù");
		text = text.replace("UJ", "Ụ");
		text = text.replace("UX", "Ũ");
		text = text.replace("UR", "Ủ");

		text = text.replace("UWS", "Ứ");
		text = text.replace("UWF", "Ừ");
		text = text.replace("UWJ", "Ự");
		text = text.replace("UWX", "Ữ");
		text = text.replace("UWR", "Ử");
		text = text.replace("UW", "Ư");

		text = text.replace("IS", "Í");
		text = text.replace("IF", "Ì");
		text = text.replace("IJ", "Ị");
		text = text.replace("IX", "Ĩ");
		text = text.replace("IR", "Ỉ");

		text = text.replace("YS", "Ỷ");
		text = text.replace("YF", "Ỳ");
		text = text.replace("YJ", "Ỵ");
		text = text.replace("YX", "Ỹ");
		text = text.replace("YR", "Ỷ");
		return text;
	}
}
