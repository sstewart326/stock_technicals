package com.stock.technicals.service.validator;

import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.stock.technicals.service.exception.InvalidRequestException;

import yahoofinance.Stock;

/**
 * 
 * @author Shawn Stewart
 */
public class TickerTechnicalsValidator {
	
	private static final String INVALID_TICKER = "N/A";

	public void validateTickerTechnicalsRequest(TickerTechnicalsRequest request) throws Exception{
		if (request.getTicker()==null) {
			throw new InvalidRequestException("Ticker Name Was Null In Request");
		}
	}
	
	public boolean isValidStock(Stock stock) {
		return stock!=null && !INVALID_TICKER.equals(stock.getName());
	}
	
}
