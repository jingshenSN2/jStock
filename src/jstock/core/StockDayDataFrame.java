package jstock.core;

import java.util.Date;

import jstock.utils.DataUtils;
import jstock.utils.DateUtils;
import jstock.utils.UrlUtils;

public class StockDayDataFrame
{
	private String stockCode;
	private String market;
	private String stockName;
	private StockDayData[] kDatas;

	/**
	 * @param stockCode
	 *            ��Ʊ����(����Ϊ6λ���۹�Ϊ5λ������ΪӢ�Ĵ�д��ĸ)
	 * @param market
	 *            ��Ʊ�����г���"sh"��ʾ�Ϻ���"sz"��ʾ���ڣ�"hk"��ʾ��ۣ�"us"��ʾ����
	 * @param year
	 *            ��ݣ�����"YYYY"�ĸ�ʽ
	 * @param period
	 *            ʱ�����ڣ�"day"��ʾ�����ݣ�"week"��ʾ�����ݣ�"month"��ʾ������
	 */
	public StockDayDataFrame(String stockCode, String market, String year, String period)
	{
		this.stockCode = stockCode;
		this.market = market;
		String urlString = UrlUtils.getDayLineUrl(period, year, market, stockCode);
		String urlContent = UrlUtils.getUrlContent(urlString);
		this.kDatas = DataUtils.getStockDayData(urlContent);
		this.stockName = DataUtils.getStockCNName(urlContent);
	}

	private StockDayDataFrame()
	{
	}

	/**
	 * �ָ��һ����ʱ��Ĺ�Ʊ����
	 * 
	 * @param startString
	 *            ��ʼ���ڣ���ʽΪ"YYYYMMDD"
	 * @param endString
	 *            �������ڣ���ʽΪ"YYYYMMDD"
	 * @return ָ�������ڼ��StockDayDataFrame
	 */
	public StockDayDataFrame subDataFrame(String startString, String endString)
	{
		Date startDate = DateUtils.getDate(startString);
		Date endDate = DateUtils.getDate(endString);
		return subDataFrame(startDate, endDate);
	}

	private StockDayDataFrame subDataFrame(Date startDate, Date endDate)
	{
		if (startDate.before(getFirstDate()))
		{
			startDate = getFirstDate();
		}
		if (endDate.after(getLastDate()))
		{
			endDate = getLastDate();
		}
		int minIndex = 0, maxIndex = kDatas.length;
		for (int i = 0; i < kDatas.length; i++)
		{
			if (startDate.after(kDatas[i].getDate()))
			{
				minIndex = i + 1;
			}
		}
		for (int i = 0; i < kDatas.length; i++)
		{
			if (endDate.before(kDatas[i].getDate()))
			{
				maxIndex = i;
				break;
			}
		}
		StockDayData[] newDatas = new StockDayData[maxIndex - minIndex];
		for (int i = minIndex; i < maxIndex; i++)
		{
			newDatas[i - minIndex] = kDatas[i];
		}
		StockDayDataFrame newFrame = new StockDayDataFrame();
		newFrame.stockCode = stockCode;
		newFrame.market = market;
		newFrame.stockName = stockName;
		newFrame.kDatas = newDatas;
		return newFrame;
	}

	public Date getFirstDate()
	{
		return kDatas[0].getDate();
	}

	public Date getLastDate()
	{
		return kDatas[kDatas.length - 1].getDate();
	}

	public String getStockCode()
	{
		return stockCode;
	}

	public String getMarket()
	{
		return market;
	}

	public String getStockName()
	{
		return stockName;
	}

	public StockDayData[] getKDatas()
	{
		return kDatas;
	}

	public double getMaxHigh()
	{
		double maxHigh = 0;
		for (StockDayData data : kDatas)
		{
			double temp = data.getHigh();
			if (temp > maxHigh)
			{
				maxHigh = temp;
			}
		}
		return maxHigh;
	}

	public double getMinLow()
	{
		double minLow = Double.MAX_VALUE;
		for (StockDayData data : kDatas)
		{
			double temp = data.getLow();
			if (temp < minLow)
			{
				minLow = temp;
			}
		}
		return minLow;
	}

	public long getMaxVolume()
	{
		long maxVolume = 0;
		for (StockDayData data : kDatas)
		{
			long temp = data.getVolume();
			if (temp > maxVolume)
			{
				maxVolume = temp;
			}
		}
		return maxVolume;
	}

	public long getMinVolume()
	{
		long minVolume = Long.MAX_VALUE;
		for (StockDayData data : kDatas)
		{
			long temp = data.getVolume();
			if (temp < minVolume)
			{
				minVolume = temp;
			}
		}
		return minVolume;
	}

	public double getMaxChg()
	{
		double maxChg = -Double.MAX_VALUE;
		for (StockDayData data : kDatas)
		{
			double temp = data.getChg();
			if (temp > maxChg)
			{
				maxChg = temp;
			}
		}
		return maxChg;
	}

	public double getMinChg()
	{
		double minChg = Double.MAX_VALUE;
		for (StockDayData data : kDatas)
		{
			double temp = data.getChg();
			if (temp < minChg)
			{
				minChg = temp;
			}
		}
		return minChg;
	}

	@Override
	public String toString()
	{
		String output = "stock name: " + stockName + "\tstock code: " + stockCode + "\tmarket: " + market + "\n";
		output += "DATE\t\t\tOPEN\t\tCLOSE\t\tHIGH\t\tLOW\t\tVOLUME\t\t %CHG\n";
		for (StockDayData data : kDatas)
		{
			output += data + "\n";
		}
		return output;
	}
}
