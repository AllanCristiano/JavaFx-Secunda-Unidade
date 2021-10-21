package gui;

import gui.util.Erro;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class viewController {
	// ___________Variables_____________
	private String status;
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
			
			if(codigo.isEmpty() || codigo == null) {
				throw new Erro("Campo Codigo Vazio");
			}if(nome.isEmpty() || nome == null) {
				throw new Erro("Campo Nome Vazio");			
			}if(!codigo.matches("[+-]?\\d*(\\.\\d+)?")) {
				throw new Erro("Campo Codigo Esperava um numero");
			}
			status = "Cadatro Realizado";
			labelStatus.setText(String.format("Status: %s", status));
		}catch(Erro e) {
			status = e.getMessage();
			labelStatus.setText(String.format("Status: %s", status));
		}
		
	}
}
