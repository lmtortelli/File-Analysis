package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Data.DataFactory;

class TestDataFactory {

	@Test
	void TestClientTypeService() {
		DataFactory df = new DataFactory();
		
		String client = "002�2345675434544345�Jose da Silva�Rural";
		String salesman = "001�3245678865434�Paulo�40000.99";
		String expectedValue = "CLIENT";
		
		assertEquals(expectedValue, df.StringType(client));
		assertNotEquals(expectedValue, df.StringType(salesman));
		
	}
	
	@Test
	void TestSalesmanTypeService() {
		DataFactory df = new DataFactory();
		
		String client = "002�2345675434544345�Jose da Silva�Rural";
		String salesman = "001�3245678865434�Paulo�40000.99";
		String expectedValue = "SALESMAN";
		
		assertEquals(expectedValue, df.StringType(salesman));
		assertNotEquals(expectedValue, df.StringType(client));
		
	}
	
	@Test
	void TestSellTypeService() {
		DataFactory df = new DataFactory();
		
		String sell = "003�10�[1-10-1,2-30-0.50,3-40-0.10,3-40-0.10,3-40-0.10]�Jurandir";
		String salesman = "001�3245678865434�Paulo�40000.99";
		String expectedValue = "SELL";
		
		assertEquals(expectedValue, df.StringType(sell));
		assertNotEquals(expectedValue, df.StringType(salesman));
		
	}

}
