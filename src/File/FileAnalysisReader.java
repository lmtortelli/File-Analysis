package File;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Data.DataFactory;
import Records.Record;

public class FileAnalysisReader implements Runnable  {
	
	private File file;
	private String outputPath;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public FileAnalysisReader(File file, String outputPath) {
		this.outputPath = outputPath;
		this.file = file;
	}
	
	@Override
    public void run()  
    { 
		try {
			FileProcess analyzer = new FileProcess();
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
			writer = new BufferedWriter(new FileWriter(this.outputPath+"\\"
										+this.file.getName().replace(".dat", "")+".done.dat"));
			
			String text;
			ArrayList<Thread> threads = new ArrayList<>();
			while ((text = this.reader.readLine()) != null) {
				Runnable rowData = new DataFactory(analyzer,text);
				Thread tAux = new Thread(rowData);
        		threads.add(tAux);
				tAux.start();
		    }
			
			for(Thread t : threads ) {
				t.join();
			}
			
			
	        System.out.println(this.file.getName() + "Analysis Done");
	        
	        writer.write(analyzer.toString());
	        reader.close();
	        
	        Record.removeFileOpened(file);
	        
		} catch (FileNotFoundException e) {
		    
		} catch (IOException e) {
		    
		} catch (InterruptedException e) {
			
		}
    }	
}
