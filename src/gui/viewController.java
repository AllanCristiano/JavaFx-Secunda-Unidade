package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Erro;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Pessoa;

public class viewController implements Initializable {
	// ___________Variables_____________
	private String status;
	private List<Pessoa> listaPessoas = new ArrayList();
	@FXML
	private ListView<Pessoa> listview = new ListView();
	
	private ObservableList<Pessoa> obs;
	// ___________Labels_____________
	@FXML
	private Label labelStatus;

	// ___________TextFields_____________

	@FXML
	private TextField txtCod;
	@FXML
	private TextField txtName;

	// ___________Buttons_____________

	@FXML
	private Button btnCadastro;
	@FXML
	private Button btnPesquisa;
	@FXML
	private Button btnExcluir;

	// ___________Controllers_____________

	public void CadPessoas() {
		try {
			String codigo = txtCod.getText();
			String nome = txtName.getText().toUpperCase();
			//____________controle de erros__________
			if(codigo.isEmpty() || codigo == null) {
				throw new Erro("Campo Codigo Vazio");
			}if(nome.isEmpty() || nome == null) {
				throw new Erro("Campo Nome Vazio");			
			}if(!codigo.matches("[+-]?\\d*(\\.\\d+)?")) {
				throw new Erro("Campo Codigo Esperava um numero");
			}
			
			//__________convertendo o codigo para long_________
			Long cod = Long.valueOf(codigo);
			Pessoa pessoa = new Pessoa(cod, nome);			
			//__________percorrer a lista para checar se o mesmo codigo ja  existe_______________
			for(Pessoa p : listaPessoas) {
				if(pessoa.getCodigo() == p.getCodigo()) {
					throw new Erro("O codigo ja esta presente na lista");
				}
			}
			
			//______________adiciona uma pessoa a lista____________
			listaPessoas.add(pessoa);
			
			//______________gera a lista visual____________________
			listPessoa();
			
			
						
			status = "Casdatro Realizado";
			labelStatus.setText(String.format("Status: %s", status.toUpperCase()));
		}catch(Erro e) {
			status = e.getMessage();
			labelStatus.setText(String.format("Status: %s", status.toUpperCase()));
		}catch (Exception e) {
			status = e.getMessage();
			labelStatus.setText(String.format("Status: %s", status.toUpperCase()));
		}		
		
	}
	//_________________Excluir Pessoa_____________
	public void ExcluirPessoa() {		
		try {
			Long codigo = Long.valueOf(txtCod.getText());
			for(Pessoa p : listaPessoas) {
				if(txtName.getText().equalsIgnoreCase(p.getNome()) && codigo == p.getCodigo()) {
					listaPessoas.remove(p);
					listPessoa();	
				}
				status = "Excluido com sucesso";
				labelStatus.setText(String.format("Status: %s", status.toUpperCase()));
			}
		}catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listPessoa();		
	}
	
	public void listPessoa() {
		for(Pessoa p : listaPessoas) {
			listview.getItems().add(p);
		}
		
		obs = FXCollections.observableArrayList(listaPessoas);
		
		listview.setItems(obs);
	}

	
}
