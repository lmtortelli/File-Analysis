package Data;

public class Cliente implements IData {
	private String cpf;
	private String name;
	private String salary;
	
	public Cliente(String cpf, String name, String salary) {
		this.cpf = cpf;
		this.name = name;
		this.salary = salary;
	}
	
}
