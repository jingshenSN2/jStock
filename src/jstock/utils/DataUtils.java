package jstock.utils;

import java.text.SimpleDateFormat;

import jstock.core.StockDayData;
import jstock.core.StockDayDataFrame;

public class DataUtils
{
	/**
	 * ��URL�е��ַ�������Ϊ��Ʊ���ݲ�������һ��������
	 * 
	 * @param urlContent
	 *            ��URL�л�ȡ�Ĺ�Ʊ����
	 * @return ������Ʊ���ݵ�StockDayData�������
	 */
	public static StockDayData[] getStockDayData(String urlContent)
	{
		String[] dataStrArray = getStockDataArray(urlContent);
		int length = dataStrArray.length;
		StockDayData[] dataArray = new StockDayData[length - 2];

		for (int i = 0; i < length - 2; i++)
		{
			dataArray[i] = parseStockData(dataStrArray[i]);
		}
		return dataArray;
	}

	/**
	 * @param urlContent
	 *            ��URL�л�ȡ�Ĺ�Ʊ����
	 * @return ���ع�Ʊ��������
	 */
	public static String getStockCNName(String urlContent)
	{
		String[] dataStrArray = getStockDataArray(urlContent);
		int length = dataStrArray.length;
		String temp = dataStrArray[length - 1];
		String unicodeString = temp.split(",")[2].split(":")[1].split("\"")[1];
		return LangUtils.transformZH(unicodeString);
	}

	/**
	 * @param urlContent
	 *            ��URL�л�ȡ�Ĺ�Ʊ����
	 * @return ����URL���ݵõ����ַ�������
	 */
	private static String[] getStockDataArray(String urlContent)
	{
		String initString = "," + urlContent.substring(9);
		String[] dataStrArray = initString.split("]");
		return dataStrArray;
	}

	public static Object[][] getTableModel(StockDayDataFrame frame)
	{
		StockDayData[] datas = frame.getKDatas();
		Object[][] model = new Object[datas.length][7];
		for (int i = 0; i < datas.length; i++)
		{
			StockDayData data = datas[i];
			model[i][0] = new SimpleDateFormat("yyyy-MM-dd").format(data.getDate());
			model[i][1] = data.getOpen();
			model[i][2] = data.getHigh();
			model[i][3] = data.getLow();
			model[i][4] = data.getClose();
			model[i][5] = data.getVolume();
			model[i][6] = data.getChg();
		}
		return model;
	}

	/**
	 * @param dataStr
	 *            ����URL���ݵõ����ַ���
	 * @return ������Ʊ���ݵ�StockDayData�����
	 */
	private static StockDayData parseStockData(String dataStr)
	{
		String date = dataStr.substring(3, 11);
		String otherData = dataStr.substring(13);
		String[] dataPieces = otherData.split(",");
		StockDayData stockData = new StockDayData(date, parseD(dataPieces[0]), parseD(dataPieces[1]),
				parseD(dataPieces[2]), parseD(dataPieces[3]), Long.parseLong(dataPieces[4]), parseD(dataPieces[5]));
		return stockData;
	}

	private static double parseD(String dString)
	{
		double d = Double.parseDouble(dString);
		return d;
	}
}
