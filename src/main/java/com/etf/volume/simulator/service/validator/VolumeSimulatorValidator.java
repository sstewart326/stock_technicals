package com.etf.volume.simulator.service.validator;

import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.etf.volume.simulator.service.exception.InvalidRequestException;

/**
 * 
 * @author Shawn Stewart
 */
public class VolumeSimulatorValidator {

	public void validateTickerTechnicalsRequest(TickerTechnicalsRequest request) throws Exception{
		if (request.getTicker()==null) {
			throw new InvalidRequestException("Ticker Name Was Null In Request");
		}
	}
}
