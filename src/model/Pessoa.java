package model;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	private Long codigo;
	private String nome;
	
	private List<Bens> listabens = new  ArrayList();
	
	public Pessoa() {}

	public Pessoa(Long codigo, String pessoa) {
		super();
		this.codigo = codigo;
		this.nome = pessoa;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String pessoa) {
		this.nome = pessoa;
	}
	
	public void addBens(Bens bem) {
		listabens.add(bem);
	}

	@Override
	public String toString() {
		return "Codigo: " + codigo + ", Nome: " + nome;
	}
	
	
	
	
	
	

}
