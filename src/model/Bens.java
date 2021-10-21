package model;

public class Bens {
	private Integer codigo;
	private String name;
	private Double valor;
	
	public Bens() {}

	public Bens(Integer codigo, String name, Double valor) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.valor = valor;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
	

}
