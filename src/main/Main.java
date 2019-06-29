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


public class Main extends Thread {
	
	private static String path_input;
	private static String extension_files;
	private static HashSet<String> filesOpen;

	public static void main(String[] args) {
		filesOpen = new HashSet<>();
		try (InputStream input = new FileInputStream("resources/config/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            
            System.out.println("Running Sales Data Analysis - Lucas Tortelli");
            
            while(true) {
            	path_input = System.getProperty(prop.getProperty("file.input_path"));
            	extension_files = prop.getProperty("file.extension_input");
                
            	//FIX
            	File folder = new File(path_input+"/New");
                File[] listOfFiles = folder.listFiles();

                for(File f : listOfFiles) {
                	if(f.getName().contains(extension_files) && !filesOpen.contains(f.getName())) {
                		filesOpen.add(f.getName());
                		Runnable instanceFile = new FileAnalysisReader(f);
                		new Thread(instanceFile).start(); 
                	}
                }
            }
            
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }

	}

}
