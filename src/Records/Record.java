package Records;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import File.FileProcess;

public abstract class Record { 
	
	private static int allowFilesOpened = 0;
	private static String extension_files;
	
	private static Queue<String> waitingFiles;
	private static HashSet<String> filesOpen;
	
	public static void loadParameters(Properties prop) {
		Record.allowFilesOpened = Integer.parseInt(prop.getProperty("app.maxFilesReader"));
		Record.extension_files = prop.getProperty("file.extension_input");
	}
	
	public static void initialize() {
		Record.waitingFiles = new LinkedList<>();
		Record.filesOpen = new HashSet<>();
	}
	
	public static void addQueueFiles(File[] files) {
		 for(File f : files) {
         	if(f.exists() && 
         			f.getName().contains(extension_files) 
         			&& !Record.waitingFiles.contains(f.getPath()) 
         			&& !Record.filesOpen.contains(f.getPath())) {
         		Record.waitingFiles.add(f.getPath());
         	}
         }
	}
	public static void removeFileOpened(File f) throws IOException {
		Files.deleteIfExists(Paths.get(f.getPath()));
		if(Record.waitingFiles.contains(f.getPath())) {
			Record.waitingFiles.remove(f.getPath());

		}
		
		if (Record.filesOpen.contains(f.getPath())) {
			Record.filesOpen.remove(f.getPath());
		}
		
		
	}
	
	public static File getFile() {
		if(Record.filesOpen.size() < Record.allowFilesOpened) {
			if(Record.waitingFiles.size() != 0) {
				String pathFile= Record.waitingFiles.peek();
				File fAux = new File(pathFile);
				if(fAux.canRead()) {
					Record.waitingFiles.remove(pathFile);
					Record.filesOpen.add(pathFile);
					return fAux;
				}
			}
			return null;
		}
		
		return null;
	}
	
	public static boolean emptyWaitingFiles() {
		return (Record.waitingFiles.size() == 0);
	}
}
