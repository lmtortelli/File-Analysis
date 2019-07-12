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


/**
 * Classe responsável pela manutenabilidade da quantidade de arquivos que podem ser abertos e análisados ao mesmo tempo, com os arquivos ja abertos
 * 
 * @author lmtor
 *
 */
public abstract class Record { 
	
	private static int allowFilesOpened = 0;
	private static String extension_files;
	
	private static Queue<String> waitingFiles;
	private static HashSet<String> filesOpen;
	
	
	/**
	 * Responsável por carregar os parâmetros obtidos do arquivo de properties
	 * @param prop
	 */
	public static void loadParameters(Properties prop) {
		Record.allowFilesOpened = Integer.parseInt(prop.getProperty("app.maxFilesReader"));
		Record.extension_files = prop.getProperty("file.extension_input");
	}
	
	/**
	 * Inicializa as estruturas de controle de código
	 */
	public static void initialize() {
		Record.waitingFiles = new LinkedList<>();
		Record.filesOpen = new HashSet<>();
	}
	
	/**
	 * Adiciona novo arquivo a fila de Arquivos, caso ele não existe na fila de arquivos em espera nem na fila de arquivos em análise.
	 * @param files
	 */
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
	
	/**
	 * Remove arquivo da fila de em análise, consequentemente deletando o arquivo do diretório de entrada.
	 * @param f
	 * @throws IOException
	 */
	public static void removeFileOpened(File f) throws IOException {
		Files.deleteIfExists(Paths.get(f.getPath()));
		if(Record.waitingFiles.contains(f.getPath())) {
			Record.waitingFiles.remove(f.getPath());

		}
		
		if (Record.filesOpen.contains(f.getPath())) {
			Record.filesOpen.remove(f.getPath());
		}
		
		
	}
	
	/**
	 * Retorna um arquivo e adiona o mesmo a fila de arquivos em análise, removendo-o da fila de arquivos em espera.
	 * @return
	 */
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
