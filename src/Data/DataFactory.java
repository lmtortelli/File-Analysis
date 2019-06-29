package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import File.FileAnalysisReader;
import File.FileProcess;

public class DataFactory implements Runnable {
	
	private String regex;
	private FileProcess analyzer;
	String dataRow;
	
	public DataFactory(FileProcess analyzer, String dataRow ) {
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
	
	public IData getData() {
		
		String[] data = this.dataRow.split(this.regex);
		//System.out.println(data[0]);
		
		switch(data[0]) {
			case "001":{
				break;
			}
			case "002":{
				break;
			}
			case "003":{
				break;
			}
			
		}
		return null;
	}

	@Override
	public void run() {
		this.getData();
	}
}
