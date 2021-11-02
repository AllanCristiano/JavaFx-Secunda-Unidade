package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Erro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bens;
import model.Pessoa;

public class viewController implements Initializable {
	// ___________Variables_____________
	private String status;
	private List<Pessoa> listaPessoas = new ArrayList<>();
	@FXML
	private ListView<Pessoa> listview = new ListView<>();

	private ObservableList<Pessoa> obs;
	// ___________Labels_____________
	@FXML
	private Label labelStatus;

	// ___________TextFields_____________

	@FXML
	private TextField txtCod;
	@FXML
	private TextField txtName;

	// ___________TextFields Bens_____________

	@FXML
	private TextField txtCodBens;
	@FXML
	private TextField txtNameBens;
	@FXML
	private TextField txtValorBens;
	@FXML
	private TextField txtCodPro;

	// ___________Buttons Pessoas_____________

	@FXML
	public Button btnCadastro;
	@FXML
	private Button btnPesquisa;
	@FXML
	private Button btnExcluir;
	@FXML
	private Button btnClose;

	// ___________Buttons Bens_____________

	@FXML
	private Button btnCadastroBem;
	@FXML
	private Button btnExcluirBem;

	// ___________Controllers_____________
	
		

	// _____________cadatrar Bens________

	public void CadastrarBens() {
		try {
			
			String codigoBens = txtCodBens.getText();
			String nomeBens = txtNameBens.getText();

			// ________controle de erros________
			controllErroBens(codigoBens, nomeBens);

			Double valor = Double.parseDouble(txtValorBens.getText());
			int codigoPro = Integer.parseInt(txtCodPro.getText());

			// ___________busca de usuario para adicionar os bens_____
					
			listaPessoas.forEach(p -> {
				if (p.getCodigo() == codigoPro) {
					int cod = Integer.parseInt(codigoBens);
					p.addBens(new Bens(cod, nomeBens.toUpperCase(), valor));

					listPessoa();
					status = "Cadastrado novo bem";
					status(status);
				}
			});

		} catch (Erro e) {
			status(e.getMessage());
		} catch (NumberFormatException e) {
			status(e.getMessage());
		}
	}

	// _____________Remover bens__________________
	public void removerBem() {
		try {

			String codigoBens = txtCodBens.getText();
			String nomeBens = txtNameBens.getText();

			// ________controle de erros________
			controllErroBens(codigoBens, nomeBens);
			
			
			
			Double valor = Double.parseDouble(txtValorBens.getText());
			int codigoPro = Integer.parseInt(txtCodPro.getText());

			// ___________busca de usuario para remover os bens_____
				
			listaPessoas.forEach(p -> {
				if (p.getCodigo() == codigoPro) {
					int cod = Integer.parseInt(codigoBens);

					// __________________funcao remover Bens_____________
					p.removerBens(cod, nomeBens, valor);

				}
			});
			status = "Bem Removido";
			status(status);
			

		} catch (Erro e) {
			status(e.getMessage());
		} catch (Exception e) {
			e.getMessage();
		}finally {
			listPessoa();
		}
	}

	// _____________cadastrar Pessoas______
	public void CadPessoas() {
		try {
			String codigo = txtCod.getText();
			String nome = txtName.getText();
			// ____________controle de erros__________
			controllErroPessoa(codigo, nome);
			// __________convertendo o codigo para long_________
			Long cod = Long.valueOf(codigo);
			Pessoa pessoa = new Pessoa(cod, nome.toUpperCase());
			// __________percorrer a lista para checar se o mesmo codigo ja
			// existe_______________
			for (Pessoa p : listaPessoas) {
				if (pessoa.getCodigo() == p.getCodigo()) {
					throw new Erro("Codigo presente na lista");
					
				}
			}
			
			// ______________adiciona uma pessoa a lista____________
			listaPessoas.add(pessoa);
			status = "Casdatro Realizado";
			status(status);
			txtName.setText("");
			txtCod.setText("");

			

			// ______________gera a lista visual____________________
			listPessoa();

			
		} catch (Erro e) {
			status(e.getMessage());
		} catch (Exception e) {
			status(status);
		}

	}

	// _________________Excluir Pessoa_____________
	public void ExcluirPessoa() {
		try {
			String codP = txtCod.getText();
			String nomeP = txtName.getText();
			// ____________controle de erros__________
			controllErroPessoa(codP, nomeP);
			
			
			//_____________exluir pessoa_____________
			Long codigo = Long.valueOf(txtCod.getText());
			String nome = txtName.getText();
			for (Pessoa p : listaPessoas) {
				if (codigo == p.getCodigo() && nome.equalsIgnoreCase(p.getNome())) {
					listaPessoas.remove(p);
					listPessoa();
				}
				status = "Excluido com sucesso";
				status(status);
				txtName.setText("");
				txtCod.setText("");
			}
		} catch (Erro e) {
			status(e.getMessage());
		}catch (Exception e) {
			e.getMessage();
		}
	}

	// _______________________inicia junto do projeto
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		carregarDadostxt();
		listPessoa();
		
	}
	// finaliza programa
	public void sair() {
		
		String listaFim = "";
		//_______________percorre a lista de pessoas gerando strings
		for(Pessoa p: listaPessoas) {
			listaFim += p.salvar() + "###";
		}
		salvar(listaFim);	
		//______________________fechar o programa____________
		Stage stage = (Stage) btnClose.getScene().getWindow();		
		stage.close();
	}

	// ________________________listar as pessoas na tela
	public void listPessoa() {
		listaPessoas.forEach(p ->{
			listview.getItems().add(p);
		});

		obs = FXCollections.observableArrayList(listaPessoas);

		listview.setItems(obs);
	}

	// __________pesquisa comparando nome e codigo da pessoa
	
	@SuppressWarnings("unchecked")
	public void Pesquisar() {
		try {
			String codP = txtCod.getText();
			String nomeP = txtName.getText();
			
			listPessoa();
			// ____________controle de erros__________
			controllErroPessoa(codP, nomeP);

			Long codigo = Long.valueOf(txtCod.getText());
			for (Pessoa p : listaPessoas) {
				// __________________isolar a uma pessoa
				if (txtName.getText().equalsIgnoreCase(p.getNome()) && codigo == p.getCodigo()) {
					obs = (ObservableList<Pessoa>) FXCollections.observableArrayList(p.pesquisaPessoa());
					listview.setItems(obs);
					status = "Pesquisa Relizada";
					status(status);
					txtName.setText("");
					txtCod.setText("");
				}

			}

		} catch (Erro e) {
			status(e.getMessage());
		}catch (Exception e) {
			e.getMessage();
		}

	}
	//__________________gera o arquivo txt e salva quando nescessario____________
	public void salvar(String pessoaBens) {
		String path = "bd_pessoas.txt";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			bw.write(pessoaBens);
			bw.newLine();
		} catch (Exception e) {
			e.getMessage();
		}
		
	}
	//_______________carregar os dados do txt_____________
	public void carregarDadostxt() {
		String path = "bd_pessoas.txt";
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			String texto = br.readLine();
			String[] usuarios = texto.split("###");
			
			for(String s: usuarios) {
				String[] pes = s.split("&&&");
				Long codigo = Long.valueOf(pes[0]);
				Pessoa pessoa = new Pessoa(codigo, pes[1]);
				listaPessoas.add(pessoa);
				for(int i = 2; i < pes.length; i+= 3) {
					int codigoB = Integer.parseInt(pes[i]);
					double valorB = Double.parseDouble(pes[i+2]);
					String name = pes[i+1];
					Bens bens = new Bens(codigoB, name, valorB);
					pessoa.addBens(bens);
				}
			}
			
			br.close();
		} catch (Exception e) {
			e.getMessage();
		}
		
	}
	//______________________msg de erro ou sucesso____
	public void status(String msg) {
		labelStatus.setText(String.format("Status: %s", msg.toUpperCase()));
	}
	//___________controle erros
	public void controllErroPessoa(String codigo, String name) throws Erro {
		if (codigo.isEmpty() || codigo == null) {
			throw new Erro("Campo Codigo Vazio");
		}
		if (name.isEmpty() || name == null) {
			throw new Erro("Campo Nome Vazio");
		}
		if (!codigo.matches("[+-]?\\d*(\\.\\d+)?")) {
			throw new Erro("Campo Codigo Esperava um numero");
		}

	}
	
	public void controllErroBens(String codigoBens, String nomeBens) throws Erro {
		if (codigoBens == "" || codigoBens == null) {
			throw new Erro("Campo codigo Bens vazio1");
		}
		if (nomeBens == "" || nomeBens == null) {
			throw new Erro("Campo nome Bens vazio");
		}
		if (txtValorBens.getText() == null || txtValorBens.getText() == "") {
			throw new Erro("Campo Valor Vazio");
		}
		if (txtCodPro.getText() == null || txtCodPro.getText() == "") {
			throw new Erro("Campo Codigo Proprietario Vazio");
		}
	}
}
