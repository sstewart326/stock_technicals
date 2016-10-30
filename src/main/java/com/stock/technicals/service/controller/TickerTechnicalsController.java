package com.stock.technicals.service.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.etf.volume.simulator.domain.ticker.technicals.OperationStatus;
import com.etf.volume.simulator.domain.ticker.technicals.RSIStatusRequest;
import com.etf.volume.simulator.domain.ticker.technicals.RSIStatusResponse;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicals;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsResponse;
import com.stock.technicals.service.constants.OperationStatusEnum;
import com.stock.technicals.service.util.RSICalculator;
import com.stock.technicals.service.util.TickerUtilities;
import com.stock.technicals.service.validator.TickerTechnicalsValidator;

/**
 * 
 * @author Shawn Stewart
 */
@RestController
@RequestMapping(value="/1.0/ticker-technicals")
public class TickerTechnicalsController {
	
	@Autowired
	private TickerTechnicalsValidator tickerTechnicalsValidator;
	@Autowired
	private RSICalculator rsiCalculator;
	@Autowired
	private TickerUtilities tickerUtilities;
	
	@RequestMapping(value="/get-ticker-technicals", method=RequestMethod.POST)
	public @ResponseBody TickerTechnicalsResponse getTickerTechnicals(@RequestBody TickerTechnicalsRequest request) throws Exception {
		TickerTechnicalsResponse response = new TickerTechnicalsResponse();
		tickerTechnicalsValidator.validateTickerTechnicalsRequest(request);
		String ticker = request.getTicker();
		Stock stock = YahooFinance.get(ticker);
		if (tickerTechnicalsValidator.isValidStock(stock)) {
			populateSuccessfulTickerTechnicalsResponse(response, stock);
		} else {
			//TODO LOGGING
			response.setOperationStatus(generateFailureOperationStatus("Could not retrive technicals for " + request.getTicker()));
		}
		return response;
	}
	
	@RequestMapping(value="/get-rsi", method=RequestMethod.POST)
	public @ResponseBody RSIStatusResponse getRSIStatus(@RequestBody RSIStatusRequest request) {
		RSIStatusResponse response = new RSIStatusResponse();
		String ticker = request.getTicker();
		BigDecimal rsiValue = rsiCalculator.calculateRSI(ticker);
		tickerUtilities.getRSIStatus(rsiValue);
		
		response.setName(ticker);
		response.setRsiValue(rsiValue.toPlainString());
		response.setRsiStrength(tickerUtilities.getRSIStatus(rsiValue).name());
		response.setOperationStatus(generateSuccessfulOperationStatus());
		return response;
	}

	/**
	 * Set successful ticker technical response
	 * 
	 * @param response
	 * @param stock
	 */
	private void populateSuccessfulTickerTechnicalsResponse(TickerTechnicalsResponse response, Stock stock) {
		TickerTechnicals tickerTechnicals = new TickerTechnicals();
		tickerTechnicals.setName(stock.getName());
		tickerTechnicals.setAverageVolume(stock.getQuote().getAvgVolume());
		tickerTechnicals.setVolume(stock.getQuote().getVolume());
		tickerTechnicals.setPreviousDayClosingPrice(stock.getQuote().getPreviousClose());
		tickerTechnicals.setOpenPrice(stock.getQuote().getOpen());
		tickerTechnicals.setCurrentPrice(stock.getQuote().getPrice());
		response.setTickerTechnicals(tickerTechnicals);
		response.setOperationStatus(generateSuccessfulOperationStatus());
	}
	
	/**
	 * Returns a successful operation status
	 * 
	 * @return
	 */
	private OperationStatus generateSuccessfulOperationStatus() {
		OperationStatus operationStatus = new OperationStatus();
		operationStatus.setOperationStatus(OperationStatusEnum.SUCCESS.toString());
		operationStatus.setOperationMessage("SUCCESS");
		return operationStatus;
	}
	
	/**
	 * Returns a failure operation status with provided errorReason
	 * 
	 * @param errorReason
	 * @return
	 */
	private OperationStatus generateFailureOperationStatus(String errorReason) {
		OperationStatus operationStatus = new OperationStatus();
		operationStatus.setOperationStatus(OperationStatusEnum.FAILURE.toString());
		operationStatus.setOperationMessage(errorReason);
		return operationStatus;
	}
	
}
