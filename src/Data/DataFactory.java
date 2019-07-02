package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import File.FileProcess;
import File.FileDataAnalysis;

public class DataFactory implements Runnable {
	
	private String regex;
	private FileDataAnalysis analyzer;
	String dataRow;
	
	public DataFactory(FileDataAnalysis analyzer, String dataRow ) {
		this.dataRow = dataRow;
		this.analyzer = analyzer;
		try (InputStream input = new FileInputStream("resources/config/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.regex = prop.getProperty("data.separator");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public void processData() {
		
		String[] data = this.dataRow.split(this.regex);
		
		switch(data[0]) {
			case "001":{
				this.analyzer.analiseSalesman();
				break;
			}
			case "002":{
				this.analyzer.analiseClient();
				break;
			}
			case "003":{
				this.analyzer.analiseSell(data[1], this.getPriceSell(data[2]), data[3]);
				break;
			}
			
		}
	}

	private float getPriceSell(String sell) {
		float totalSell = 0;
		// Clear  [ ] caracter
		sell = sell.replace("[", "");
		sell = sell.replace("]", "");
		
		String[] sAux = sell.split(",");
		
		String[] idItems = sAux[0].split("-");
		String[] qtdItems = sAux[1].split("-");;
		String[] priceItems = sAux[2].split("-");
		
		for (int i = 0; i < qtdItems.length ; i++) {
			totalSell+= Float.parseFloat(qtdItems[i]) * Float.parseFloat(priceItems[i]);
		}
		
		return totalSell;
	}

	@Override
	public void run() {
		this.processData();
	}
}
