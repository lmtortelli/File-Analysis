package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Data.DataFactory;
import File.FileDataAnalysis;

class TestFileDataAnalysis{


	@Test
	void TestQtdOFSalesman() {
		FileDataAnalysis analyzer = new FileDataAnalysis();
		
		int expectedValue = 5;
		
		for (int i = 0 ; i < 5 ; i++) {
			analyzer.analiseSalesman();
		}
		
		assertEquals(expectedValue,analyzer.getQtdSalesman());
	}
	
	@Test
	void TestQtdClients() {
		FileDataAnalysis analyzer = new FileDataAnalysis();
		
		int expectedValue = 5;
		
		for (int i = 0 ; i < 5 ; i++) {
			analyzer.analiseClient();
		}
		
		assertEquals(expectedValue,analyzer.getQtdClients());
	}
	

	@Test
	void TestBetterAndWrostSell() {
		FileDataAnalysis analyzer = new FileDataAnalysis();
		
		int wrostSellPrice = 100;
		int expansiveSellPrice = 1000;
		int mediumSell = 500;
		
		analyzer.analiseSell("1", wrostSellPrice, "Salesman Teste 1");
		analyzer.analiseSell("2", expansiveSellPrice, "Salesman Teste 2");
		analyzer.analiseSell("3", mediumSell, "Salesman Teste 3");
		
		assertEquals(wrostSellPrice,analyzer.getWrostSell());
		assertEquals(expansiveSellPrice,analyzer.getExpansiveSell());
	}
	

}
