package jstock.utils;

import java.awt.Color;
import java.awt.Paint;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import jstock.core.StockDayData;
import jstock.core.StockDayDataFrame;

public class ChartUtils
{
	public static void paintKLineData(StockDayDataFrame frame)
	{
		OHLCSeries kSeries = new OHLCSeries("");
		StockDayData[] datas = frame.getKDatas();
		for (StockDayData data : datas)
		{
			Date date = data.getDate();
			double open = data.getOpen();
			double high = data.getHigh();
			double low = data.getLow();
			double close = data.getClose();
			kSeries.add(new Day(date), open, high, low, close);
		}
		final OHLCSeriesCollection kSeriesCollection = new OHLCSeriesCollection();
		kSeriesCollection.addSeries(kSeries);

		TimeSeries vSeries = new TimeSeries("");
		for (StockDayData data : datas)
		{
			Date date = data.getDate();
			long volume = data.getVolume();
			vSeries.add(new Day(date), volume);
		}
		final TimeSeriesCollection vSeriesCollection = new TimeSeriesCollection();
		vSeriesCollection.addSeries(vSeries);

		TimeSeries cSeries = new TimeSeries("");
		for (StockDayData data : datas)
		{
			Date date = data.getDate();
			double chg = data.getChg();
			cSeries.add(new Day(date), chg);
		}
		final TimeSeriesCollection cSeriesCollection = new TimeSeriesCollection();
		cSeriesCollection.addSeries(cSeries);

		double maxHigh = frame.getMaxHigh();
		double minLow = frame.getMinLow();
		long maxVolume = frame.getMaxVolume();
		long minVolume = frame.getMinVolume();

		final CandlestickRenderer candlestickRenderer = new CandlestickRenderer();
		candlestickRenderer.setUseOutlinePaint(true);
		candlestickRenderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);
		candlestickRenderer.setAutoWidthGap(0.001);
		candlestickRenderer.setUpPaint(Color.RED);
		candlestickRenderer.setDownPaint(Color.GREEN);
		DateAxis dateAxis = new DateAxis();
		dateAxis.setAutoRange(false);
		dateAxis.setRange(frame.getFirstDate(), frame.getLastDate());
		dateAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
		dateAxis.setAutoTickUnitSelection(true);
		dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));

		NumberAxis kAxis = new NumberAxis();
		kAxis.setAutoRange(false);
		kAxis.setRange(minLow * 0.98, maxHigh * 1.02);
		kAxis.setTickUnit(new NumberTickUnit((maxHigh * 1.02 - minLow * 0.98) / 10));

		XYPlot kPlot = new XYPlot(kSeriesCollection, dateAxis, kAxis, candlestickRenderer);
		XYBarRenderer barRenderer = new XYBarRenderer()
		{
			private static final long serialVersionUID = 1L;

			public Paint getItemPaint(int i, int j)
			{
				if (kSeriesCollection.getCloseValue(i, j) > kSeriesCollection.getOpenValue(i, j))
				{
					return candlestickRenderer.getUpPaint();
				}
				else
				{
					return candlestickRenderer.getDownPaint();
				}
			}
		};
		barRenderer.setMargin(0.1);
		barRenderer.setShadowVisible(false);

		NumberAxis vAxis = new NumberAxis();
		vAxis.setAutoRange(false);
		vAxis.setRange(minVolume * 0.8, maxVolume * 1.2);
		vAxis.setTickUnit(new NumberTickUnit((maxVolume * 1.2 - minVolume * 0.8) / 4));
		XYPlot vPlot = new XYPlot(vSeriesCollection, null, vAxis, barRenderer);

		CombinedDomainXYPlot combinedDomainXYPlot = new CombinedDomainXYPlot(dateAxis);
		combinedDomainXYPlot.add(kPlot, 3);
		combinedDomainXYPlot.add(vPlot, 1);
		combinedDomainXYPlot.setGap(10);

		JFreeChart chart = new JFreeChart(frame.getStockName(), JFreeChart.DEFAULT_TITLE_FONT, combinedDomainXYPlot,
				false);
		ChartFrame chartFrame = new ChartFrame(frame.getStockName(), chart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}

}
