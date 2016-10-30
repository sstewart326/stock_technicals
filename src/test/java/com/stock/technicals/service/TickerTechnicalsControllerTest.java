package com.stock.technicals.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsResponse;
import com.stock.technicals.service.controller.TickerTechnicalsController;


public class TickerTechnicalsControllerTest extends TestBase {

	@Autowired
	TickerTechnicalsController tickerTechnicalsController;
	
	@Test
	public void testTickerTechnicalsController_Success() throws Exception {
		TickerTechnicalsRequest request = new TickerTechnicalsRequest();
		request.setTicker("EFX");
		TickerTechnicalsResponse response = tickerTechnicalsController.getTickerTechnicals(request);
		System.out.println(response);
	}
}
