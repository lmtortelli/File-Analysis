package File;


/**
 * Entidade responsável por analisar o arquivo processado no momento, em tempo de execução cada arquivo contém somente uma instância dessa classe
 * @author lmtor
 *
 */
public class FileDataAnalysis {
	
	protected int qtdClient = 0;
	protected int qtdSalesman = 0;
	
	protected String idExpansiveSell = "";
	protected Float expansiveSell = null;
	
	protected Float wrostSell = null;
	protected String wrostSalesman;
	
	
	/**
	 * Realiza a adição no contador a cada cliente verificado
	 */
	public synchronized void analiseClient() {
		this.qtdClient++;

	}
	
	/**
	 * Realiza a adição no contador a cada vendedor validado
	 */
	public synchronized void analiseSalesman() {
		this.qtdSalesman++;
	}
	
	/**
	 * Analisa a venda a fim de estabelecer os critérios delegados, neste caso o ID da venda mais cara e o Pior Vendedor
	 * @param id
	 * @param price
	 * @param salesman
	 */
	public synchronized void analiseSell(String id, float price, String salesman) {
		if((this.expansiveSell == null) || (this.expansiveSell < price)){
			this.expansiveSell = price;
			this.idExpansiveSell = id;
		}
		
		
		if ((wrostSell == null) || (price < this.wrostSell)) {
			this.wrostSell = price;
			this.wrostSalesman = salesman;
		}
		
	}
	
	
	/**
	 * Retorna a quantidade de vendedores registrados
	 * @return
	 */
	public int getQtdSalesman() {
		return this.qtdSalesman;
	}
	
	/**
	 * Retorna a quantidade de clientes registrados
	 * @return
	 */
	public int getQtdClients() {
		return this.qtdClient;
	}
	
	/**
	 * Retorna o valor da venda mais cara
	 * @return
	 */
	public float getExpansiveSell() {
		return this.expansiveSell;
	}
	
	/**
	 * Retorna o valor da pior venda
	 * @return
	 */
	public float getWrostSell() {
		return this.wrostSell;
	}
	
	/**
	 * Introduz o retorno já no padrão estabelecido para o arquivo de saída da análise
	 */
	@Override
	public String toString() {
		return "Quantidade Clientes " + this.qtdClient + "\n" +
				"Quantidade de Vendedores " + this.qtdSalesman + "\n" +
				"ID Venda mais cara " + this.idExpansiveSell + "\n" +
				"Pior Vendedor " + this.wrostSalesman + "\n";
	}

}
