package com.stock.technicals.service.util;

import java.math.BigDecimal;

import com.stock.technicals.service.constants.RsiStatus;

public class TickerUtilities {

	private static final Integer OVERBOUGHT = 70;
	private static final Integer NEUTRAL = 50;
	private static final Integer OVERSOLD = 30;

	public RsiStatus getRSIStatus(BigDecimal rsiValue) {
		if (rsiValue.doubleValue() > OVERBOUGHT) {
			return RsiStatus.OVERBOUGHT;
		} else if (rsiValue.doubleValue() > NEUTRAL) {
			return RsiStatus.TRENDING_UP;
		} else if (rsiValue.doubleValue() > OVERSOLD) {
			return RsiStatus.TRENDING_DOWN;
		} else {
			return RsiStatus.OVERSOLD;
		}
	}
}
