package com.stock.technicals.service.validator;

import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.stock.technicals.service.exception.InvalidRequestException;

/**
 * 
 * @author Shawn Stewart
 */
public class TickerTechnicalsValidator {

	public void validateTickerTechnicalsRequest(TickerTechnicalsRequest request) throws Exception{
		if (request.getTicker()==null) {
			throw new InvalidRequestException("Ticker Name Was Null In Request");
		}
	}
}
