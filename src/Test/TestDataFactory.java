package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Data.DataFactory;

class TestDataFactory {

	@Test
	void TestClientTypeService() {
		DataFactory df = new DataFactory();
		
		String client = "002Á2345675434544345ÁJose da SilvaÁRural";
		String salesman = "001Á3245678865434ÁPauloÁ40000.99";
		String expectedValue = "CLIENT";
		
		assertEquals(expectedValue, df.StringType(client));
		assertNotEquals(expectedValue, df.StringType(salesman));
		
	}
	
	@Test
	void TestSalesmanTypeService() {
		DataFactory df = new DataFactory();
		
		String client = "002Á2345675434544345ÁJose da SilvaÁRural";
		String salesman = "001Á3245678865434ÁPauloÁ40000.99";
		String expectedValue = "SALESMAN";
		
		assertEquals(expectedValue, df.StringType(salesman));
		assertNotEquals(expectedValue, df.StringType(client));
		
	}
	
	@Test
	void TestSellTypeService() {
		DataFactory df = new DataFactory();
		
		String sell = "003Á10Á[1-10-1,2-30-0.50,3-40-0.10,3-40-0.10,3-40-0.10]ÁJurandir";
		String salesman = "001Á3245678865434ÁPauloÁ40000.99";
		String expectedValue = "SELL";
		
		assertEquals(expectedValue, df.StringType(sell));
		assertNotEquals(expectedValue, df.StringType(salesman));
		
	}

}
