package jstock.core;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jstock.utils.DateUtils;

public class StockDayData
{
	private Date date;
	private int[] prices = new int[4];
	private long volume;
	private double chg;

	public StockDayData(String dateStr, double open, double close, double high, double low, long volume, double chg)
	{
		this.date = DateUtils.getDate(dateStr);
		this.prices[0] = toInteger(open);
		this.prices[1] = toInteger(close);
		this.prices[2] = toInteger(high);
		this.prices[3] = toInteger(low);
		this.volume = volume;
		this.chg = chg;
	}

	private int toInteger(double price)
	{
		return (int) (price * 100);
	}

	private String toDoubleStr(int price)
	{
		return String.format("%.2f", price / 100.0);
	}

	public Date getDate()
	{
		return date;
	}

	public int[] getPrices()
	{
		return prices;
	}

	public double getOpen()
	{
		return prices[0] / 100.0;
	}

	public double getClose()
	{
		return prices[1] / 100.0;
	}

	public double getHigh()
	{
		return prices[2] / 100.0;
	}

	public double getLow()
	{
		return prices[3] / 100.0;
	}

	public long getVolume()
	{
		return volume;
	}

	public double getChg()
	{
		return chg;
	}

	@Override
	public String toString()
	{
		String tab = "\t\t";
		String output = new SimpleDateFormat("yyyy-MM-dd").format(date) + "  ";
		output += DateUtils.getWeekD(date) + "\t\t";
		for (int i = 0; i < 4; i++)
		{
			output += toDoubleStr(prices[i]) + tab;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.##E0");
		String vString = decimalFormat.format(volume);
		output += vString + tab;
		output += (chg >= 0) ? " " : "";
		output += String.format("%.2f", chg) + " %\t";

		return output;
	}
}
