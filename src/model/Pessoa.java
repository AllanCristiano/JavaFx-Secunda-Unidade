package model;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	private Long codigo;
	private String nome;

	private List<Bens> listabens = new ArrayList();

	public Pessoa() {
	}

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

	public List pesquisaPessoa() {
		List pessoa = new ArrayList<>();
		pessoa.add("Codigo: " + codigo + " Nome: " + nome);
		for (Bens b : listabens) {
			pessoa.add("Codigo: " + b.getName() + " Nome: " + b.getName() + " Valor : " + b.getValor());
		}
		return pessoa;
	}

	@Override
	public String toString() {
		return "Codigo: " + codigo + "   Nome: " + nome + "   ValorBens R$: " + somaBens();
	}

	public Double somaBens() {
		double sum = 0;
		for (Bens b : listabens) {
			sum += b.getValor();

		}
		return sum;

	}

}
