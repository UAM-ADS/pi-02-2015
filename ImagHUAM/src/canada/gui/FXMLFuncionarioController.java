/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.gui;

import canada.ConnectionManager;
import canada.User;
import canada.UserManager;
import canada.utils.Constants.Role;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLFuncionarioController implements Initializable {
    
    private boolean isEditing;
    private Stage root;
    private User funcionario;
    private Connection conn;
    private FXMLMainController caller;
    private UserManager userManager;

    @FXML
    private TextField funcNomeTxt;
    @FXML
    private TextField funcCpfTxt;
    @FXML
    private TextField funcEndTxt;
    @FXML
    private TextField funcLoginTxt;
    @FXML
    private TextField funcTelTxt;
    @FXML
    private ChoiceBox<Role> funcPermissaoChoice;
    @FXML
    private TextField funcEspecTxt;
    @FXML
    private TextField funcRegistroTxt;
    @FXML
    private Button funcApagarBtn;
    @FXML
    private Label adminFuncTitleLabel;
    @FXML
    private TextField funcEmailTxt;
    @FXML
    private Button funcSalvaBtn;
    @FXML
    private Button funcCancelaBtn;
    @FXML
    private PasswordField funcPassTxt;
    
    public boolean getIsEditing() { return isEditing; }
    
    public void setStage(Stage value) { root = value; }
    public void setUser(User value) { funcionario = value; }
    public void setCaller(FXMLMainController value) { caller = value; }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConnectionManager.gerenciador().getConnection();
        userManager = UserManager.gerenciador(conn);
        ObservableList<Role> choiceList = FXCollections.observableArrayList(
                Role.ADMIN,
                Role.ATENDENTE,
                Role.SAUDE);
        funcPermissaoChoice.setItems(choiceList);
    }

    @FXML
    private void funcSalva(ActionEvent event) {
        if (funcionario == null) {
            funcionario = new User(conn);
        }
        funcionario.setNome(funcNomeTxt.getText());
        funcionario.setCpf(funcCpfTxt.getText());
        funcionario.setEmail(funcEmailTxt.getText());
        funcionario.setEndereco(funcEndTxt.getText());
        funcionario.setTelefone(funcTelTxt.getText());
        funcionario.setLogin(funcLoginTxt.getText());
        funcionario.setRegistro(funcRegistroTxt.getText());
        funcionario.setEspecialidade(funcEspecTxt.getText());
        funcionario.setPapel(funcPermissaoChoice.getValue());
        if (!funcPassTxt.getText().equals("")) {
            funcionario.setSenha(funcPassTxt.getText());
        }
        userManager.saveUser(funcionario);
        try {
            caller.reloadFuncTable();
        } catch (SQLException e) {
            System.out.println("Erro carregando lista de funcionários. "+e);
        }
        root.close();
    }

    @FXML
    private void funcCancela(ActionEvent event) {
        root.close();
    }

    @FXML
    private void funcApagar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmaApagar.fxml"));
            Scene confScene = new Scene(loader.load());
            Stage confStage = new Stage();
            confStage.initModality(Modality.WINDOW_MODAL);
            confStage.initOwner(root);
            confStage.setScene(confScene);
            FXMLConfirmaApagarController funcController = loader.<FXMLConfirmaApagarController>getController();
            funcController.setStage(confStage);
            funcController.setCaller(this);
            confStage.show();
        } catch (IOException e) {
            System.out.println("Erro carregando janela de confitrmação de remoção de usuário. "+e);
        }
    }
    
    public void setIsEditing(boolean value) {
        isEditing = value;
        if (isEditing) {
            adminFuncTitleLabel.setText("Editar Funcionário");
            funcNomeTxt.setText(funcionario.getNome());
            funcCpfTxt.setText(funcionario.getCpf());
            funcEmailTxt.setText(funcionario.getEmail());
            funcEndTxt.setText(funcionario.getEndereco());
            funcTelTxt.setText(funcionario.getTelefone());
            funcLoginTxt.setText(funcionario.getLogin());
            funcRegistroTxt.setText(funcionario.getRegistro());
            funcEspecTxt.setText(funcionario.getEspecialidade());
            funcPermissaoChoice.setValue(funcionario.getPapel());
        } else {
            adminFuncTitleLabel.setText("Novo Funcionário");
            funcApagarBtn.setDisable(true);
            funcApagarBtn.setVisible(false);
        }
    }
    
    public void confirmaApagar() {
        if (funcionario != null) {
            boolean ok = userManager.removeUser(funcionario);
            if (ok) {
                try {
                    caller.reloadFuncTable();
                } catch (SQLException e) {
                    System.out.println("Erro carregando tabela de funcionários. "+e);
                }
                root.close();
            } else {
                System.out.println("Erro apagando usuário.");
            }
        }
    }
}
