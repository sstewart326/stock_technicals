package com.stock.technicals.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Class to calculate RSI for a particular ticker. Default Intervals is 14
 * 
 * RSI = 100 - ( 100 / (1+RelativeStrength) )
 * RelativeStrength = Average Daily Gain / Average Daily Loss
 * 
 * @author Shawn Stewart
 */
public class RSICalculator {
	
	private static final Integer DEFAULT_INTERVAL = 14;
	private static final String AVERAGE_UPWARD_MOVEMENT = "AVERAGE_UPWARD_MOVEMENT";
	private static final String AVERAGE_DOWNWARD_MOVEMENT = "AVERAGE_DOWNWARD_MOVEMENT";
	
	/**
	 * Calculates the RSI for a particular ticker. DEFAULT_INTERVAL is 14
	 * 
	 * @param ticker
	 * @return
	 */
	public BigDecimal calculateRSI(String ticker) {
		return calculateRSI(DEFAULT_INTERVAL,ticker);
	}
	
	/**
	 * Calculates the RSI for a particular ticker and provided interval. 
	 * 
	 * @param interval
	 * @param ticker
	 * @return
	 */
	public BigDecimal calculateRSI(Integer interval, String ticker) {
		Stock stock = YahooFinance.get(ticker);
		List<HistoricalQuote> oneYearHistory = stock.getHistory(Interval.DAILY);
		List<HistoricalQuote> snippetList = generateHistoricalQuoteList(interval, oneYearHistory);
		Map<String, BigDecimal> averages = calculateAverageGainsAndLosses(snippetList,interval);
		BigDecimal averageUpwardMovement = averages.get(AVERAGE_UPWARD_MOVEMENT);
		BigDecimal averageDownwardMovement = averages.get(AVERAGE_DOWNWARD_MOVEMENT);
		BigDecimal relativeStrength = averageUpwardMovement.divide(averageDownwardMovement, 2, RoundingMode.HALF_UP);
		
		return new BigDecimal(100).subtract(
				(new BigDecimal(100).divide(
						(new BigDecimal(1).add(relativeStrength))
						,2,RoundingMode.HALF_UP)));
	}

	/**
	 * Generates a Map that returns the AVERAGE_UPWARD_MOVEMENT and AVERAGE_DOWNWARD_MOVEMENT for the
	 * specified interval. These variables are calculated from the daily close value.
	 * 
	 * @param snippetList
	 * @param interval 
	 * @return
	 */
	private Map<String, BigDecimal> calculateAverageGainsAndLosses(List<HistoricalQuote> snippetList, Integer interval) {
		List<BigDecimal> gains = new ArrayList<BigDecimal>();
		List<BigDecimal> losses = new ArrayList<BigDecimal>();
		
		if (snippetList.size()>1) {
			List<HistoricalQuote> reversedSnippetList = Lists.reverse(snippetList);
			for (int i=0; i<reversedSnippetList.size()-1; i++) {
				HistoricalQuote previous = reversedSnippetList.get(i);
				HistoricalQuote next = reversedSnippetList.get(i+1);
				if (next.getClose().compareTo(previous.getClose()) == 1) {
					gains.add(next.getClose().subtract(previous.getClose()));
				} else if (next.getClose().compareTo(previous.getClose()) == -1) {
					losses.add(previous.getClose().subtract(next.getClose()));
				}
			}
		}
		BigDecimal averageDailyGains = calculateAverage(gains, interval);
		BigDecimal averageDailyLosses = calculateAverage(losses, interval);
		
		Map<String,BigDecimal> averages = new HashMap<String,BigDecimal>();
		averages.put(AVERAGE_UPWARD_MOVEMENT, averageDailyGains);
		averages.put(AVERAGE_DOWNWARD_MOVEMENT, averageDailyLosses);
		return averages;
	}
	
	/**
	 * Calculates the average of a BigDecimal list. Interval is used to calculate the average
	 * BigDecimal of the list instead of list size.
	 * 
	 * @param list
	 * @param interval 
	 * @return
	 */
	private BigDecimal calculateAverage(List<BigDecimal> list, Integer interval) {
		BigDecimal sum = new BigDecimal(0);
		for (BigDecimal value : list) {
			sum = sum.add(value);
		}
		return sum.divide(new BigDecimal(interval),2,RoundingMode.HALF_UP);
	}

	/**
	 * Modifies historical quote list to only include values needed for RSI calculation
	 * 
	 * Need to increment interval by 1 because there are x intervals between x+1 days
	 * @param interval
	 * @param oneYearList
	 * @return
	 */
	private List<HistoricalQuote> generateHistoricalQuoteList(Integer interval,List<HistoricalQuote> oneYearList) {
		interval++;
		List<HistoricalQuote> snippetList = new ArrayList<HistoricalQuote>();
		for (int i=0; i<interval; i++) {
			snippetList.add(oneYearList.get(i));
		}
		return snippetList;
	}

}
