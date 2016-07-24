package com.etf.volume.simulator.service.persistence;

import java.util.List;

import com.etf.volume.simulator.service.entity.Ticker;

public interface VolumeSimulatorDAO {

	public void saveExistantTickerDetails(Ticker ticker);
	
	public void saveTickerDetails(Ticker ticker);
	
	public List<Ticker> getTickerList();
}
