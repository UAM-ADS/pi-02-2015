/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.gui;

import canada.ConnectionManager;
import canada.Paciente;
import canada.PacienteManager;
import canada.utils.Constants.Sexo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLPacienteController implements Initializable {
    private Stage root;
    private Paciente paciente;
    private Connection conn;
    private FXMLMainController caller;
    private PacienteManager pacienteManager;
    private boolean isEditing;
    @FXML
    private Label pacienteLabel;
    @FXML
    private TextField pacienteNomeTxt;
    @FXML
    private TextField pacienteCpfTxt;
    @FXML
    private TextField pacienteEmailTxt;
    @FXML
    private TextField pacienteEnderecoTxt;
    @FXML
    private TextField pacienteTelefoneTxt;
    @FXML
    private TextField pacienteCartaoTxt;
    @FXML
    private Button pacienteApagarBtn;
    @FXML
    private Button pacienteCancelarBtn;
    @FXML
    private Button pacienteSalvarBtn;
    @FXML
    private ChoiceBox<Sexo> pacienteSexoChoiceBox;
    
    public void setStage(Stage value) { root = value; }
    public void setUser(Paciente value) { paciente = value; }
    public void setCaller(FXMLMainController value) { caller = value; }
    public void setPaciente(Paciente value) { paciente = value; }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConnectionManager.gerenciador().getConnection();
        pacienteManager = PacienteManager.gerenciador();
        ObservableList<Sexo> choiceList = FXCollections.observableArrayList(
                Sexo.MASCULINO,
                Sexo.FEMININO);
        pacienteSexoChoiceBox.setItems(choiceList);
    }
    
    public void setIsEditing(boolean value) {
        isEditing = value;
        if (isEditing) {
            pacienteLabel.setText("Editar Paciente");
            pacienteNomeTxt.setText(paciente.getNome());
            pacienteCpfTxt.setText(paciente.getCpf());
            pacienteEmailTxt.setText(paciente.getEmail());
            pacienteEnderecoTxt.setText(paciente.getEndereco());
            pacienteTelefoneTxt.setText(paciente.getTelefone());
            pacienteCartaoTxt.setText(paciente.getCartao());
            pacienteSexoChoiceBox.setValue(paciente.getSexo());
        } else {
            pacienteLabel.setText("Novo Paciente");
            pacienteApagarBtn.setDisable(true);
            pacienteApagarBtn.setVisible(false);
        }
    }
    
    public void confirmaApagar() {
        if (paciente != null) {
            boolean ok = pacienteManager.removePaciente(paciente);
            if (ok) {
                try {
                    caller.reloadPacienteTable();
                } catch (SQLException e) {
                    System.out.println("Erro carregando tabela de pacientes. "+e);
                }
                root.close();
            } else {
                System.out.println("Erro apagando paciente.");
            }
        }
    }

    @FXML
    private void pacienteApagar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPacienteConfirmaApagar.fxml"));
            Scene pacienteScene = new Scene(loader.load());
            Stage pacienteStage = new Stage();
            pacienteStage.initModality(Modality.WINDOW_MODAL);
            pacienteStage.initOwner(root);
            pacienteStage.setScene(pacienteScene);
            FXMLPacienteConfirmaApagarController pacienteController = loader.<FXMLPacienteConfirmaApagarController>getController();
            pacienteController.setStage(pacienteStage);
            pacienteController.setCaller(this);
            pacienteStage.show();
        } catch (IOException e) {
            System.out.println("Erro carregando janela de confitrmação de remoção de usuário. "+e);
        }
    }

    @FXML
    private void pacienteSalvar(ActionEvent event) {
        if (paciente == null) {
            paciente = new Paciente();
        }
        paciente.setNome(pacienteNomeTxt.getText());
        paciente.setCpf(pacienteCpfTxt.getText());
        paciente.setEmail(pacienteEmailTxt.getText());
        paciente.setEndereco(pacienteEnderecoTxt.getText());
        paciente.setTelefone(pacienteTelefoneTxt.getText());
        paciente.setCartao(pacienteCartaoTxt.getText());
        paciente.setSexo(pacienteSexoChoiceBox.getValue());
        pacienteManager.savePaciente(paciente);
        try {
            caller.reloadPacienteTable();
        } catch (SQLException e) {
            System.out.println("Erro carregando lista de pacientes. "+e);
        }
        root.close();
    }

    @FXML
    private void pacienteCancela(ActionEvent event) {
        root.close();
    }
}
