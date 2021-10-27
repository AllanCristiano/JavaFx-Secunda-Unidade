package gui;

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
	private Button btnCadastro;
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
			if (codigoBens == "" || codigoBens == null) {
				throw new Erro("Campo codigo Bens vazio");
			}
			if (nomeBens == "" || nomeBens == null) {
				throw new Erro("Campo nome Bens vazio");
			}
			if (txtValorBens.getText() == null || txtValorBens.getText() == "") {
				throw new Erro("Campo valor vazio");
			}
			if (txtCodPro.getText() == null || txtCodPro.getText() == "") {
				throw new Erro("Campo codigo proprietario vazio");
			}

			Double valor = Double.parseDouble(txtValorBens.getText());
			int codigoPro = Integer.parseInt(txtCodPro.getText());

			// ___________busca de usuario para adicionar os bens_____
			for (Pessoa p : listaPessoas) {
				if (p.getCodigo() == codigoPro) {
					int cod = Integer.parseInt(codigoBens);
					p.addBens(new Bens(cod, nomeBens, valor));

					listPessoa();
					status = "Cadastrado novo bem";
					status(status);
				}

			}

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
			if (codigoBens == "" || codigoBens == null) {
				throw new Erro("Campo codigo Bens vazio");
			}
			if (nomeBens == "" || nomeBens == null) {
				throw new Erro("Campo nome Bens vazio");
			}
			if (txtValorBens.getText() == null || txtValorBens.getText() == "") {
				throw new Erro("Campo valor vazio");
			}
			if (txtCodPro.getText() == null || txtCodPro.getText() == "") {
				throw new Erro("Campo codigo proprietario vazio");
			}
			Double valor = Double.parseDouble(txtValorBens.getText());
			int codigoPro = Integer.parseInt(txtCodPro.getText());

			// ___________busca de usuario para adicionar os bens_____
			for (Pessoa p : listaPessoas) {
				if (p.getCodigo() == codigoPro) {
					int cod = Integer.parseInt(codigoBens);

					// __________________funcao remover Bens_____________
					p.removerBens(cod, nomeBens, valor);

				}

			}

		} catch (Erro e) {
			status(e.getMessage());
		} catch (Exception e) {
			e.getMessage();
		} finally {
			status = "Bem Removido";
			status(status);
			listPessoa();
		}
	}

	// _____________cadastrar Pessoas______
	public void CadPessoas() {
		try {
			String codigo = txtCod.getText();
			String nome = txtName.getText().toUpperCase();
			// ____________controle de erros__________
			if (codigo.isEmpty() || codigo == null) {
				throw new Erro("Campo Codigo Vazio");
			}
			if (nome.isEmpty() || nome == null) {
				throw new Erro("Campo Nome Vazio");
			}
			if (!codigo.matches("[+-]?\\d*(\\.\\d+)?")) {
				throw new Erro("Campo Codigo Esperava um numero");
			}

			// __________convertendo o codigo para long_________
			Long cod = Long.valueOf(codigo);
			Pessoa pessoa = new Pessoa(cod, nome);
			// __________percorrer a lista para checar se o mesmo codigo ja
			// existe_______________
			for (Pessoa p : listaPessoas) {
				if (pessoa.getCodigo() == p.getCodigo()) {
					throw new Erro("O codigo ja esta presente na lista");
				}
			}

			// ______________adiciona uma pessoa a lista____________
			listaPessoas.add(pessoa);

			// ______________gera a lista visual____________________
			listPessoa();

			status = "Casdatro Realizado";
			status(status);
			txtName.setText("");
			txtCod.setText("");
		} catch (Erro e) {
			status(status);
		} catch (Exception e) {
			status(status);
		}

	}

	// _________________Excluir Pessoa_____________
	public void ExcluirPessoa() {
		try {
			Long codigo = Long.valueOf(txtCod.getText());
			for (Pessoa p : listaPessoas) {
				if (txtName.getText().equalsIgnoreCase(p.getNome()) && codigo == p.getCodigo()) {
					listaPessoas.remove(p);
					listPessoa();
				}
				status = "Excluido com sucesso";
				status(status);
				txtName.setText("");
				txtCod.setText("");
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// _______________________inicia junto do projeto
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listPessoa();
		
	}
	//______________________fechar o programa____________
	public void sair() {
		Stage stage = (Stage) btnClose.getScene().getWindow();		
		stage.close();
	}

	// ________________________listar as pessoas na tela
	public void listPessoa() {
		for (Pessoa p : listaPessoas) {
			listview.getItems().add(p);
		}

		obs = FXCollections.observableArrayList(listaPessoas);

		listview.setItems(obs);
	}

	// __________pesquisa comparando nome e codigo da pessoa
	public void Pesquisar() {
		try {

			Long codigo = Long.valueOf(txtCod.getText());
			for (Pessoa p : listaPessoas) {
				// __________________isolar a uma pessoa
				if (txtName.getText().equalsIgnoreCase(p.getNome()) && codigo == p.getCodigo()) {
					obs = FXCollections.observableArrayList(p.pesquisaPessoa());
					listview.setItems(obs);
					status = "Pesquisa Relizada";
					status(status);
					txtName.setText("");
					txtCod.setText("");
				}

			}

		} catch (Exception e) {
			// System.out.println(e.getMessage());
			if (e.getMessage().equals("For input string: \"\"")) {
				listPessoa();
			}
		}

	}

	public void status(String msg) {
		labelStatus.setText(String.format("Status: %s", msg.toUpperCase()));
	}
}
