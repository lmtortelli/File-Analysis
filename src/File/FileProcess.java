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


/**
 * Responsável por consumir cada linha de dados dos arquivos de entrada e após análise criar o arquivo de saída.
 * @author lmtor
 *
 */
public class FileProcess implements Runnable  {
	
	private File file;
	private String outputPath;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public FileProcess(File file, String outputPath) {
		this.outputPath = outputPath;
		this.file = file;
	}
	
	/**
	 * Responsável por análisar cada linha do arquivo, delegando dessa maneira uma Thread para cada tarefa. Esta abordagem ocorreu para simular
	 * um produtor/consumidor não criando dessa maneira uma fila de espera e extraíndo o máximo proveito dos recursos computacionais.
	 */
	@Override
    public void run()  
    { 
		try {
			FileDataAnalysis analyzer = new FileDataAnalysis();
			String text;
			ArrayList<Thread> threads = new ArrayList<>();
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
			writer = new BufferedWriter(new FileWriter(this.outputPath+"\\"
										+this.file.getName().replace(".dat", "")+".done.dat"));
			
			
			
			while ((text = this.reader.readLine()) != null) {
				Runnable rowData = new DataFactory(analyzer,text);
				Thread tAux = new Thread(rowData);
        		threads.add(tAux);
				tAux.start();
		    }
			
			
			for(Thread t : threads ) {
				t.join();
			}
			
			reader.close();
	        System.out.println(this.file.getName() + " Analysis Done");
	        
	        writer.write(analyzer.toString());
	        writer.close();
	        
	        
	        Record.removeFileOpened(file);
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }	
}
