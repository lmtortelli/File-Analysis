package File;

public class FileDataAnalysis {
	
	private int qtdClient = 0;
	private int qtdSalesman = 0;
	
	private String idExpansiveSell = "";
	private Float expansiveSell = null;
	
	private Float wrostSell = null;
	private String wrostSalesman;
	
	public synchronized void analiseClient() {
		this.qtdClient++;
		// Logic for client
	}
	
	public synchronized void analiseSalesman() {
		this.qtdSalesman++;
	}
	
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
	
	@Override
	public String toString() {
		return "Quantidade Clientes " + this.qtdClient + "\n" +
				"Quantidade de Vendedores " + this.qtdSalesman + "\n" +
				"ID Venda mais cara " + this.idExpansiveSell + "\n" +
				"Pior Vendedor " + this.wrostSalesman + "\n";
	}

}
