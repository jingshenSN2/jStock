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
	 * 根据下列参数生成一个储存了股票日线数据的URL。
	 * 
	 * @param period
	 *            时间周期，"day"表示日数据，"week"表示周数据，"month"表示月数据
	 * @param year
	 *            年份，采用"YYYY"的格式
	 * @param market
	 *            股票所在市场，"sh"表示上海，"sz"表示深圳，"hk"表示香港，"us"表示美国
	 * @param stockcode
	 *            股票代码(沪深为6位，港股为5位，美股为英文大写字母)
	 * @return 股票日线数据URL
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
	 * 访问URL链接，获取其中的内容
	 * 
	 * @param urlString
	 *            由getDayLineUrl方法获取的股票数据URL
	 * @return URL中的全部内容
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
