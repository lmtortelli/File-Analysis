package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Data.DataFactory;

public class FileAnalysisReader implements Runnable  {
	
	File file;
	BufferedReader reader;
	
	public FileAnalysisReader(File file) {
		this.file = file;
	}
	
	@Override
    public void run()  
    { 
		try {
			FileProcess analyzer = new FileProcess();
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
			String text;
			int i = 0;
			while ((text = this.reader.readLine()) != null) {
				Runnable rowData = new DataFactory(analyzer,text);
        		new Thread(rowData).start(); 
		    }
	        System.out.println(this.file.getName() + "Analysis Done");
	        //this.file.delete();
	        System.out.println("File Removed");
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }	
}
