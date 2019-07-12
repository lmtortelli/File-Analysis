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
 * Classe responsável por converter os dados brutos de entrada a sequencias bem definidas de acordo com as especificações
 * Uma entrada do tipo 002ç2345675434544345çJose da SilvaçRural é reconhecida como ID CNPJ/CPF NOME OCUPAÇÃO. Utiliza propriedades para delimitar
 * o caracter separador. "Default = ç"
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
	 * Cria a instância do analisados específico do arquivo aberto, e conjuntamente uma linha consumida do arquivo
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
	 * Realiza o processamento da linha de dado introduzida, delimitando-a sua análise de forma correta
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
	 * Criado exclusivamente para fins de test de conversão, retorna o tipo de serviço de acordo com a String informada dentro de um padrão estabelecido
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
	 * Realiza a conversão do tipo serviço Venda a fim de obter o preço total de venda
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
