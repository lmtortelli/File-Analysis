package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import File.FileProcess;
import Records.Record;


public class Main extends Thread {
	
	private static String path_input;
	private static String path_outpath;
	
	private static void ExecuteFiles(Properties prop) {
		//Create folder if doent exists
		path_outpath = System.getProperty(prop.getProperty("file.path"))+prop.getProperty("file.output_path");
		new File(path_input).mkdirs();
    	new File(path_outpath).mkdirs();
    	
		File[] listOfFiles;
		File folder = new File(path_input);
    	listOfFiles = folder.listFiles();

    	boolean finish = false;
    	
		while(!finish) {

			File f;
			Record.loadParameters(prop);
	        Record.addQueueFiles(listOfFiles);
		    
		    if((f = Record.getFile()) != null) {
				Runnable instanceFile = new FileProcess(f,path_outpath);
		 		new Thread(instanceFile).start();
		 		finish = Record.emptyWaitingFiles();
		    }
		    finish = Record.emptyWaitingFiles();
		}
	}

	public static void main(String[] args) {
		System.out.println("Running Sales Data Analysis - Lucas Tortelli");
		
		// Initialize control data access controls
		Record.initialize();
		try (InputStream input = new FileInputStream("resources/config/config.properties")) {
			Properties prop = new Properties();
            prop.load(input);
                        
            //Service Watching Folder
            WatchService watchService = FileSystems.getDefault().newWatchService();
            WatchKey key;
            
            path_input = System.getProperty(prop.getProperty("file.path"))+prop.getProperty("file.input_path");
            
            //Create input_path
            Path path = Paths.get(path_input);
            path.register(watchService,StandardWatchEventKinds.ENTRY_CREATE);
            
            // First excute previous exist files
            Main.ExecuteFiles(prop);
            
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                	WatchEvent.Kind kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    	Main.ExecuteFiles(prop);
                    }
                }
                key.reset();
            }
            
		}
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
        catch(Exception e) {
        	e.printStackTrace();
        }

	}

}
