package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import File.FileProcess;
import File.FileDataAnalysis;

/**
 * Classe respons�vel por converter os dados brutos de entrada a sequencias bem definidas de acordo com as especifica��es
 * Uma entrada do tipo 002�2345675434544345�Jose da Silva�Rural � reconhecida como ID CNPJ/CPF NOME OCUPA��O. Utiliza propriedades para delimitar
 * o caracter separador. "Default = �"
 * @author lmtor
 *
 */
public class DataFactory implements Runnable {
	
	private String regex;
	private FileDataAnalysis analyzer;
	String dataRow;
	
	public DataFactory() {
		this.loadProperties();
	}
	
	/**
	 * Cria a inst�ncia do analisados espec�fico do arquivo aberto, e conjuntamente uma linha consumida do arquivo
	 * @param analyzer
	 * @param dataRow
	 */
	public DataFactory(FileDataAnalysis analyzer, String dataRow ) {
		this.dataRow = dataRow;
		this.analyzer = analyzer;
		this.loadProperties();
	}
	
	/**
	 * Carrega as propriedades do aplicativo
	 */
	private void loadProperties() {
		try (InputStream input = new FileInputStream("resources/config/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.regex = prop.getProperty("data.separator");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * Realiza o processamento da linha de dado introduzida, delimitando-a sua an�lise de forma correta
	 */
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
	
	/**
	 * Criado exclusivamente para fins de test de convers�o, retorna o tipo de servi�o de acordo com a String informada dentro de um padr�o estabelecido
	 * @param dataTest
	 * @return
	 */
	public String StringType(String dataTest) {
		String[] data = dataTest.split(this.regex);
		
		switch(data[0]) {
			case "001":{
				return "SALESMAN";
			}
			case "002":{
				return "CLIENT";
			}
			case "003":{
				return "SELL";
			}
		}
			
		return null;
	}

	/**
	 * Realiza a convers�o do tipo servi�o Venda a fim de obter o pre�o total de venda
	 * @param sell
	 * @return
	 */
	private float getPriceSell(String sell) {
		float totalSell = 0;
		
		// Clear  [ ] caracter
		sell = sell.replace("[", "");
		sell = sell.replace("]", "");
		
		String[] sAux = sell.split(",");
		
		for (int i = 0 ; i < sAux.length ; i ++) {
			String[] aux = sAux[i].split("-");
			totalSell+= Float.parseFloat(aux[1]) * Float.parseFloat(aux[2]);
		}
		
		return totalSell;
	}

	@Override
	public void run() {
		this.processData();
	}
}
