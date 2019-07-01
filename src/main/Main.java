package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import File.FileAnalysisReader;
import Records.Record;


public class Main extends Thread {
	
	private static String path_input;
	private static String path_outpath;

	public static void main(String[] args) {
		Record.initialize();
		try (InputStream input = new FileInputStream("resources/config/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            File[] listOfFiles;
            
            System.out.println("Running Sales Data Analysis - Lucas Tortelli");
            
            while(true) {
            	Record.loadParameters(prop);
            	path_input = System.getProperty(prop.getProperty("file.path"))+prop.getProperty("file.input_path");
            	path_outpath = System.getProperty(prop.getProperty("file.path"))+prop.getProperty("file.output_path");
            	new File(path_input).mkdirs();
            	new File(path_outpath).mkdirs();
            	File f;
            	
            	
            	File folder = new File(path_input);
            	listOfFiles = null;
            	listOfFiles = folder.listFiles();
                //System.out.println(listOfFiles.toString());
                Record.addQueueFiles(listOfFiles);
                
				if((f = Record.getFile()) != null) {
					Runnable instanceFile = new FileAnalysisReader(f,path_outpath);
	         		new Thread(instanceFile).start();
                }
         		
               
            }
            
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }

	}

}
