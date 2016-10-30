package util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.technicals.service.TestBase;
import com.stock.technicals.service.util.RSICalculator;

public class RSICalculatorTest extends TestBase {
	
	@Autowired
	private RSICalculator rsiCalculator;

	@Test
	public void testCalculateRSI() {
		System.out.println(rsiCalculator.calculateRSI("EFX"));
	}
}
