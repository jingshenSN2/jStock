package jstock.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangUtils
{
	/**
	 * @param dataString
	 *            包含unicode格式的字符串
	 * @return 将unicode识别并转化为中文的字符串
	 */
	public static String transformZH(String dataString)
	{
		String regx1 = "(\\\\\\w{5})+?";// 正则表达式，匹配\\加5个单词字符
		Pattern pattern = Pattern.compile(regx1);
		Matcher matcher = pattern.matcher(dataString);
		while (matcher.find())
		{
			String unicode = matcher.group();
			StringBuilder stringBuilder = new StringBuilder();
			String[] uPieces = unicode.split("\\\\u");
			for (int i = 1; i < uPieces.length; i++)
			{
				int d = Integer.parseInt(uPieces[i], 16);
				stringBuilder.append((char) d);
			}
			dataString = dataString.replace(unicode, stringBuilder.toString());
		}
		return dataString;
	}
}
