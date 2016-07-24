package com.etf.volume.simulator.service.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.etf.volume.simulator.domain.ticker.technicals.OperationStatus;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicals;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsResponse;
import com.etf.volume.simulator.service.constants.OperationStatusEnum;
import com.etf.volume.simulator.service.exception.InvalidRequestException;
import com.etf.volume.simulator.service.validator.VolumeSimulatorValidator;

/**
 * 
 * @author Shawn Stewart
 */
@RestController
public class TickerTechnicalsController {
	
	private static final String INVALID_TICKER = "N/A";
	
	private VolumeSimulatorValidator volumeSimulatorValidator;
	
	public void setVolumeSimulatorValidator(VolumeSimulatorValidator volumeSimulatorValidator) {
		this.volumeSimulatorValidator = volumeSimulatorValidator;
	}

	@RequestMapping(value="/getTickerTechnicals", method=RequestMethod.POST)
	public TickerTechnicalsResponse getTickerTechnicals(@RequestBody TickerTechnicalsRequest request) throws Exception {
		TickerTechnicalsResponse response = new TickerTechnicalsResponse();
		try {
			volumeSimulatorValidator.validateTickerTechnicalsRequest(request);
		} catch (InvalidRequestException e) {
			OperationStatus operationStatus = new OperationStatus();
			operationStatus.setOperationStatus(OperationStatusEnum.FAILURE.toString());
			operationStatus.setOperationMessage("Request Had Null Ticker");
			
			response.setOperationStatus(operationStatus);
			throw e;
		}
		String ticker = request.getTicker();
		Stock stock = YahooFinance.get(ticker);
		if (stock != null && !INVALID_TICKER.equals(stock.getName())) {
			populateSuccessfulResponse(response, stock);
			
		} else {
			//TODO LOGGING
			OperationStatus operationStatus = new OperationStatus();
			operationStatus.setOperationStatus(OperationStatusEnum.FAILURE.toString());
			operationStatus.setOperationMessage("Could not retrive technicals for " + request.getTicker());
			response.setOperationStatus(operationStatus);
		}
		return response;
	}
	
	/**
	 * 
	 * @param response
	 * @param stock
	 */
	private void populateSuccessfulResponse(TickerTechnicalsResponse response, Stock stock) {
		TickerTechnicals tickerTechnicals = new TickerTechnicals();
		tickerTechnicals.setName(stock.getName());
		tickerTechnicals.setAverageVolume(stock.getQuote().getAvgVolume());
		tickerTechnicals.setVolume(stock.getQuote().getVolume());
		tickerTechnicals.setPreviousDayClosingPrice(stock.getQuote().getPreviousClose());
		tickerTechnicals.setOpenPrice(stock.getQuote().getOpen());
		tickerTechnicals.setCurrentPrice(stock.getQuote().getPrice());
		response.setTickerTechnicals(tickerTechnicals);
		OperationStatus operationStatus = new OperationStatus();
		operationStatus.setOperationStatus(OperationStatusEnum.SUCCESS.toString());
		operationStatus.setOperationMessage("SUCCESS");
		response.setOperationStatus(operationStatus);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public void handleInvalidRequest() {
		//TODO
	}
	
}
