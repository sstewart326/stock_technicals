package com.stock.technicals.service.persistence;

import java.util.List;

import com.stock.technicals.service.entity.Ticker;

public interface VolumeSimulatorDAO {

	public void saveExistantTickerDetails(Ticker ticker);
	
	public void saveTickerDetails(Ticker ticker);
	
	public List<Ticker> getTickerList();
}
