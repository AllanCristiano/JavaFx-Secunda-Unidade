package gui;

import java.util.ArrayList;
import java.util.List;

import gui.util.Erro;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Pessoa;

public class viewController {
	// ___________Variables_____________
	private String status;
	private List<Pessoa> listaPessoas = new ArrayList();
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
			for(Pessoa p : listaPessoas) {
				System.out.println(p.getCodigo() + " " + p.getNome());
			}
			
			
						
			status = "Cadatro Realizado";
			labelStatus.setText(String.format("Status: %s", status));
		}catch(Erro e) {
			status = e.getMessage();
			labelStatus.setText(String.format("Status: %s", status));
		}catch (Exception e) {
			status = e.getMessage();
			labelStatus.setText(String.format("Status: %s", status));
		}		
		
	}
}
