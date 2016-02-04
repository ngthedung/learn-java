package com.hkt.client.swingexp.model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ManagerConvert {
	private static String[] lop = { "", "nghìn", "triệu", "tỷ", "nghìn tỷ", "triệu tỷ", "tỷ tỷ" };
	private List<String> list = new ArrayList<String>();

	public ManagerConvert() {
	}

	private static ManagerConvert managerConvert;

	public static ManagerConvert getInstance() {
		if (managerConvert == null) {
			managerConvert = new ManagerConvert();
		}
		return managerConvert;
	}

	private String readHundred(String tram, String pre) {
		String[] tmp = { "", "mươi", "trăm" };
		String[] s = { "", "", "" };
		int valueLength = 2;
		int value = Integer.parseInt(tram);
		int i = 0;
		if (value == 0 && tram.length() == 1) {
			s[valueLength] = String.valueOf(value) + " " + tmp[i];
		} else {
			while (value != 0) {
				int n = value % 10;
				s[valueLength] = String.valueOf(n) + " " + tmp[i];
				i++;
				valueLength--;
				value /= 10;
			}
		}
		String str = s[0] + " " + s[1] + " " + s[2];
		if (str.trim().length() != 0) {
			str = str + " " + pre;
		}

		return str;
	}

	public String convertMoney(String strMoney) {
		boolean am= false;
		if (strMoney.indexOf("-") == 0) {
			am = true;
			if (strMoney.indexOf(".") > 0) {
				strMoney = strMoney.substring(1, strMoney.indexOf("."));
			} else {
				strMoney = strMoney.substring(1);
			}
		}
		String str = "";
		for (int i = 0; i < strMoney.length(); i++) {
			String s1 = String.valueOf(strMoney.charAt(i));
			if (!s1.trim().isEmpty()) {
				str = str + s1;
			}
		}
		String moneyText = readNumber(str);
		if(am){
			moneyText = "- " + moneyText;
		}
		return moneyText;
	}

	public String readNumber(String strNum) {
		int classNum = (int) Math.ceil((double) strNum.length() / 3);
		list = new ArrayList<String>();
		String readNum = "";
		int numLength = strNum.length();
		int i, j, k;
		for (i = classNum - 1; i >= 0; i--) {
			list.add(lop[i]);
		}
		List<String> strings = new ArrayList<String>();
		String s = "";
		int count = 0;
		while (numLength > 0) {
			s = s + strNum.charAt(numLength - 1);
			count++;
			if (count > 2 || numLength == 1) {
				s = swap(s);
				strings.add(s);
				count = 0;
				s = "";
			}
			numLength--;
		}
		for (j = 0, k = list.size() - 1; j < list.size() && k >= 0; j++, k--) {
			readNum = readNum.trim() + " " + readHundred(strings.get(k), list.get(j)).trim();
		}
		readNum = validate(readNum);
		String str = readNum.trim();
		String a = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
		a = a.replaceAll("  ", " ");
		a = a.replaceAll("  ", " ");
		return a;
	}

	/** đọc 1 000 thành một khôngkhôngkhông */
	public String readNumberReal(String strNum) {
		return strNum = strNum.replaceAll("0", "không ").replaceAll("1", "một ").replaceAll("2", "hai ")
		    .replaceAll("3", "ba ").replaceAll("4", "bốn ").replaceAll("5", "năm ").replaceAll("6", "sáu ")
		    .replaceAll("7", "bảy ").replaceAll("8", "tám ").replaceAll("9", "chín ");
	}

	private String swap(String s) {
		StringBuilder builder = new StringBuilder();
		for (int i = s.length() - 1; i >= 0; i--) {
			builder.append(s.charAt(i));
		}
		return builder.toString();
	}

	private String validate(String strNum) {
		strNum = strNum.replaceAll("0 mươi", "linh").replaceAll("1 mươi", "mười").replaceAll("mươi 1", "mươi mốt")
		    .replaceAll("mười 5", "mười lăm").replaceAll("mươi 5", "mươi lăm").replaceAll("mươi 0", "mươi")
		    .replaceAll("linh 0", "").replaceAll("mươi 4", "mươi tư").replaceAll("linh 4", "linh tư");

		strNum = strNum.replaceAll("0", "không").replaceAll("1", "một").replaceAll("2", "hai").replaceAll("3", "ba")
		    .replaceAll("4", "bốn").replaceAll("5", "năm").replaceAll("6", "sáu").replaceAll("7", "bảy")
		    .replaceAll("8", "tám").replaceAll("9", "chín").replaceAll("mười không", "mười");
		return strNum;
	}

	public String convertFilter(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{Block=CombiningDiacriticalMarks}+", "")
		    .toLowerCase().replaceAll("\u0111", "d");
		return str;
	}
}
