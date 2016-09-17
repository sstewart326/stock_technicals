package com.etf.volume.simulator.service.controller.persistence;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.etf.volume.simulator.service.controller.TestBase;
import com.stock.technicals.service.entity.Price;
import com.stock.technicals.service.entity.PullDate;
import com.stock.technicals.service.entity.Ticker;
import com.stock.technicals.service.entity.Volume;
import com.stock.technicals.service.persistence.VolumeSimulatorDAO;

public class HibernateTest extends TestBase {

	@Autowired
	VolumeSimulatorDAO volumeSimulatorDAO;
	
	@Test
	public void persistenceTest() {
		Ticker ticker = new Ticker();
		PullDate pullDate = new PullDate();
		Price price = new Price();
		Volume volume = new Volume();
		Set<PullDate> pullDates = new HashSet<PullDate>();

		price.setCurrentPrice(100);
		price.setOpenPrice(50);
		price.setPreviousDayClosingPrice(25);
		price.setPullDate(pullDate);
		
		volume.setAverageVolume(1000);
		volume.setCurrentVolume(5000);
		volume.setPullDate(pullDate);
		
		pullDate.setVolume(volume);
		pullDate.setPrice(price);
		Date date = new Date(System.currentTimeMillis());
		pullDate.setPullDate(date);
		pullDate.setTicker(ticker);
		
		pullDates.add(pullDate);
		ticker.setCompanyName("Shawn's Test");
		ticker.setPullDates(pullDates);
		ticker.setTicker("SHAWN");
		
		this.volumeSimulatorDAO.saveTickerDetails(ticker);
	}
}
