package model;

import java.util.ArrayList;
import java.util.List;


public class Pessoa implements Comparable<Pessoa> {

    private Long codigo;
    private String nome;
    private List<Bens> listabens = new ArrayList<>();

    public Pessoa() {
    }

    public Pessoa(Long codigo, String pessoa) {
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

    public String addBens(Bens bem) {

        for (Bens b : listabens) {
            if (b.getCodigo().equals(bem.getCodigo()) && b.getName().equalsIgnoreCase(bem.getName()) && b.getValor().equals(bem.getValor())) {
                return "Usuario já possui esse Bem";
            }
        }
        listabens.add(bem);
        return "Bem Cadastrado";
    }

    //________________remover itens________________
    public String removerBens(int cod, String name, double valor) {
        for (Bens b : listabens) {

            if (b.getCodigo() == cod && b.getName().equalsIgnoreCase(name) && b.getValor() == valor) {
                listabens.remove(b);
                return "Bem removido";
            }


        }
        return "Bem não encontrado";


    }

    public List<?> pesquisaPessoa() {
        List<String> pessoa = new ArrayList<>();
        pessoa.add("Codigo: " + codigo + "    Nome: " + nome);
        for (Bens b : listabens) {
            pessoa.add("Codigo: " + b.getCodigo() + "   Nome: " + b.getName().toUpperCase() + "   Valor R$: " + b.getValor());
        }
        return pessoa;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + "   Nome: " + nome + "   ValorBens R$: "
                + String.format("%.2f", somaBens());
    }

    public Double somaBens() {
        double sum = 0;
        for (Bens b : listabens) {
            sum += b.getValor();

        }
        return sum;
    }

    //________________________Gerador de String de cada pessoa________________
    public String salvar() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCodigo() + "&&&").append(getNome());
        for (Bens b : listabens) {
            sb.append("&&&" + b.getCodigo() + "&&&" + b.getName() + "&&&" + b.getValor());
        }

        return sb.toString();
    }

    // Organizar a lista
    @Override
    public int compareTo(Pessoa p1) {
        return codigo.compareTo(p1.getCodigo());
        //nome.compareToIgnoreCase(p1.getNome());
    }
}