package gui;

import gui.util.Erro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.Bens;
import model.Pessoa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.*;

public class viewController implements Initializable {
    private final List<Pessoa> listaPessoas = new ArrayList<>();
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
            controllErroBens(codigoBens, nomeBens);

            Double valor = Double.parseDouble(txtValorBens.getText());
            int codigoPro = Integer.parseInt(txtCodPro.getText());

            // ___________busca de usuario para adicionar os bens_____

            for (Pessoa p : listaPessoas) {
                if (p.getCodigo() == codigoPro) {
                    int cod = Integer.parseInt(codigoBens);
                    String nomeProp = p.getNome();

                    alertaBens(nomeBens, cod, valor, nomeProp, "Cadastrar");
                    status(p.addBens(new Bens(cod, nomeBens.toUpperCase(), valor)));
                    listPessoa();

                    return;

                }
            }

            throw new Erro("Codigo Proprietario não encontrado");

        } catch (Erro e) {
            status(e.getMessage());
        } catch (NumberFormatException e) {
            status(e.getMessage());
        } catch (Exception e) {
            e.getMessage();
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

            for (Pessoa p : listaPessoas) {
                if (p.getCodigo() == codigoPro) {
                    int cod = Integer.parseInt(codigoBens);
                    String nomeProp = p.getNome();

                    // __________________funcao remover Bens_____________
                    alertaBens(nomeBens, cod, valor, nomeProp, "Remover");
                    status(p.removerBens(cod, nomeBens, valor));
                    listPessoa();
                    return;

                }
            }

            throw new Erro("Codigo Proprietario não encontrado");
        } catch (Erro e) {
            status(e.getMessage());
        } catch (Exception e) {
            e.getMessage();
        } finally {
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

            // __________percorrer a lista para checar se o mesmo codigo ja existe_______________
            for (Pessoa p : listaPessoas) {
                if (pessoa.getCodigo() == p.getCodigo()) {
                    throw new Erro("Codigo presente na lista");

                }
            }

            alerta(nome, cod, "Cadastrar");

            // ______________adiciona uma pessoa a lista____________
            listaPessoas.add(pessoa);
            txtName.setText("");
            txtCod.setText("");

            // ______________gera a lista visual____________________
            listPessoa();

            throw new Erro("Pessoa Cadastrada");

        } catch (Erro e) {
            status(e.getMessage());
        } catch (Exception e) {
            e.getMessage();
        }

    }

    // _________________Excluir Pessoa_____________
    @SuppressWarnings("unlikely-arg-type")
    public void ExcluirPessoa() {

        try {
            String codP = txtCod.getText();
            String nomeP = txtName.getText();

            // ____________controle de erros__________
            controllErroPessoa(codP, nomeP);

            // _____________exluir pessoa_____________
            Long codigo = Long.valueOf(txtCod.getText());
            String nome = txtName.getText();

            for (Pessoa p : listaPessoas) {

                if (codigo == p.getCodigo() && nome.equalsIgnoreCase(p.getNome())) {

                    alerta(nomeP, codigo, "Remover");

                    listaPessoas.remove(p);
                    txtName.setText("");
                    txtCod.setText("");
                    listPessoa();

                    throw new Erro("Pessoa Removida");
                }

            }
            if (!listaPessoas.contains(nome) && !listaPessoas.contains(codigo)) {
                throw new Erro("Pessoa não consta na lista");
            }
        } catch (Erro e) {
            status(e.getMessage());
        } catch (Exception e) {
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

        StringBuilder listaFim = new StringBuilder();
        // _______________percorre a lista de pessoas gerando strings
        for (Pessoa p : listaPessoas) {
            listaFim.append(p.salvar()).append("###");
        }
        salvar(listaFim.toString());
        // ______________________fechar o programa____________
        // Stage stage = (Stage) btnClose.getScene().getWindow();
        // stage.close();
        System.exit(0);
    }

    // ________________________listar as pessoas na tela
    public void listPessoa() {
        Collections.sort(listaPessoas);
        listaPessoas.forEach(p -> {
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
                    txtCod.setText("");
                    txtName.setText("");
                    throw new Erro("Pesquisa realizada");
                }

            }
            throw new Erro("Pessoa não encontrada");
        } catch (Erro e) {
            status(e.getMessage());
        } catch (Exception e) {
            e.getMessage();
        }

    }

    // __________________gera o arquivo txt e salva quando nescessario____________
    public void salvar(String pessoaBens) {
        String path = "bd_pessoas.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(pessoaBens);
            bw.newLine();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    // _______________carregar os dados do txt_____________
    public void carregarDadostxt() {
        String path = "bd_pessoas.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String texto = br.readLine();
            String[] usuarios = texto.split("###");

            for (String s : usuarios) {
                String[] pes = s.split("&&&");
                Long codigo = Long.valueOf(pes[0]);
                Pessoa pessoa = new Pessoa(codigo, pes[1]);
                listaPessoas.add(pessoa);
                for (int i = 2; i < pes.length; i += 3) {
                    int codigoB = Integer.parseInt(pes[i]);
                    double valorB = Double.parseDouble(pes[i + 2]);
                    String name = pes[i + 1];
                    Bens bens = new Bens(codigoB, name, valorB);
                    pessoa.addBens(bens);
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    // ______________________msg de erro ou sucesso____
    public void status(String msg) {
        labelStatus.setText(String.format("Status: %s", msg.toUpperCase()));
    }

    // ___________controle Error
    public void controllErroPessoa(String codigo, String name) throws Erro {
        if (codigo.isEmpty()) {
            throw new Erro("Campo Codigo Vazio");
        }
        if (name.isEmpty()) {
            throw new Erro("Campo Nome Vazio");
        }
        if (!codigo.matches("[+-]?\\d*(\\.\\d+)?")) {
            throw new Erro("Campo Codigo Esperava um numero");
        }
        if (Long.valueOf(codigo) < 0) {
            throw new Erro("Codigo deve ser positivo");
        }

    }

    // ___________controle Error
    public void controllErroBens(String codigoBens, String nomeBens) throws Erro {
        if (codigoBens.equalsIgnoreCase("")) {
            throw new Erro("Campo codigo Bens vazio");
        }
        if (nomeBens.equalsIgnoreCase("")) {
            throw new Erro("Campo nome Bens vazio");
        }
        if (txtValorBens.getText() == null || txtValorBens.getText().equalsIgnoreCase("")) {
            throw new Erro("Campo Valor Vazio");
        }
        if (txtCodPro.getText() == null || txtCodPro.getText().equalsIgnoreCase("")) {
            throw new Erro("Campo Codigo Proprietario Vazio");
        }
    }

    // Alerts CadPessoa E RemovePessoa

    public void alerta(String nome, Long codigo, String stf) throws Erro {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(stf + " Pessoa");
        alert.setHeaderText("Deseja " + stf + " a seguinte Pessoa");
        alert.setContentText("Codigo: " + codigo + ", Nome: " + nome);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            throw new Erro("Operação cancelada");
        }
    }

    // Alerts Bens
    public void alertaBens(String nome, int codigo, double valor, String namePro, String stf) throws Erro {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(stf + " Bens");
        alert.setHeaderText("Deseja " + stf + " o seguinte Bem para: " + namePro);
        alert.setContentText("Codigo: " + codigo + ", Nome: " + nome + " valor: R$" + valor);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            throw new Erro("Operação cancelada");
        }
    }
}
