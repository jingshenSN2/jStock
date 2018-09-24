package jstock.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangUtils
{
	/**
	 * @param dataString
	 *            ����unicode��ʽ���ַ���
	 * @return ��unicodeʶ��ת��Ϊ���ĵ��ַ���
	 */
	public static String transformZH(String dataString)
	{
		String regx1 = "(\\\\\\w{5})+?";// ������ʽ��ƥ��\\��5�������ַ�
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
