package com.stock.technicals.service.domain.generator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import com.etf.volume.simulator.domain.ticker.technicals.TickerTechnicalsRequest;
import com.stock.technicals.service.TestBase;

public class TickerTechnicalsRequestTest extends TestBase {

	@Test
	public void requestFormat() throws JAXBException {
		TickerTechnicalsRequest request = new TickerTechnicalsRequest();
		request.setTicker("EFX");
		
		JAXBContext context = JAXBContext.newInstance(TickerTechnicalsRequest.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(request, System.out);
	}
}
