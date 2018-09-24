package jstock.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlUtils
{
	private static String firstUrl = "http://img1.money.126.net/data/";

	/**
	 * �������в�������һ�������˹�Ʊ�������ݵ�URL��
	 * 
	 * @param period
	 *            ʱ�����ڣ�"day"��ʾ�����ݣ�"week"��ʾ�����ݣ�"month"��ʾ������
	 * @param year
	 *            ��ݣ�����"YYYY"�ĸ�ʽ
	 * @param market
	 *            ��Ʊ�����г���"sh"��ʾ�Ϻ���"sz"��ʾ���ڣ�"hk"��ʾ��ۣ�"us"��ʾ����
	 * @param stockcode
	 *            ��Ʊ����(����Ϊ6λ���۹�Ϊ5λ������ΪӢ�Ĵ�д��ĸ)
	 * @return ��Ʊ��������URL
	 */
	public static String getDayLineUrl(String period, String year, String market, String stockcode)
	{
		String finalUrl = firstUrl;
		if (market.equals("sh") || market.equals("sz"))
		{
			finalUrl += "hs";
		}
		else
		{
			finalUrl += market;
		}
		finalUrl += "/kline/" + period + "/history/" + year + "/";
		if (market.equals("sh"))
		{
			finalUrl += '0';
		}
		else if (market.equals("sz"))
		{
			finalUrl += '1';
		}
		finalUrl += stockcode + ".json";
		return finalUrl;
	}

	/**
	 * ����URL���ӣ���ȡ���е�����
	 * 
	 * @param urlString
	 *            ��getDayLineUrl������ȡ�Ĺ�Ʊ����URL
	 * @return URL�е�ȫ������
	 */
	public static String getUrlContent(String urlString)
	{
		URL url = null;
		BufferedReader input = null;
		StringBuffer stringBuffer = new StringBuffer();
		try
		{
			url = new URL(urlString);
			HttpURLConnection httpURLConnection = null;
			URLConnection urlConnection = url.openConnection();
			if (urlConnection instanceof HttpURLConnection) {
				httpURLConnection = (HttpURLConnection) urlConnection;
			}
			input = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			String str = null;
			while ((str = input.readLine()) != null)
			{
				stringBuffer.append(str);
			}
		}
		catch (Exception e)
		{
		}
		finally
		{
			try
			{
				if (input != null)
				{
					input.close();
				}
			}
			catch (IOException ex)
			{
			}
		}
		String result = stringBuffer.toString();
		return result;
	}
}
