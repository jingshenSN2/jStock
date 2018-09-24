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
	 *            股票代码(沪深为6位，港股为5位，美股为英文大写字母)
	 * @param market
	 *            股票所在市场，"sh"表示上海，"sz"表示深圳，"hk"表示香港，"us"表示美国
	 * @param year
	 *            年份，采用"YYYY"的格式
	 * @param period
	 *            时间周期，"day"表示日数据，"week"表示周数据，"month"表示月数据
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
	 * 分割出一部分时间的股票数据
	 * 
	 * @param startString
	 *            开始日期，格式为"YYYYMMDD"
	 * @param endString
	 *            结束日期，格式为"YYYYMMDD"
	 * @return 指定日期期间的StockDayDataFrame
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
