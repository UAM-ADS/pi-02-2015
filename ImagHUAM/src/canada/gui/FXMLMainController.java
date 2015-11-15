/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.gui;

import canada.utils.Constants;
import canada.ConnectionManager;
import canada.UserManager;
import canada.User;
import canada.gui.FuncionarioRow;
import canada.utils.Constants.Role;
import java.io.IOException;

import java.sql.Connection;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author mgaldieri
 */
public class FXMLMainController implements Initializable {
    
    private Stage root;
    private Connection conn;
    private User currentUser;
    private UserManager userManager;
    private ObservableList<FuncionarioRow> adminFuncionarioList;
    
    @FXML
    private Label mainVersionLabel;
    @FXML
    private Label helloLabel;
    @FXML
    private Button logoutBtn;
    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab tabAdmin;
    @FXML
    private Tab tabAtendente;
    @FXML
    private Tab tabSaude;
    @FXML
    private TextField adminBuscaCpfTxt;
    @FXML
    private Button adminBuscaCpfBtn;
    @FXML
    private Button adminNovoBtn;
    @FXML
    private TableView<FuncionarioRow> adminFuncTable;
    @FXML
    private TableColumn<FuncionarioRow, String> adminNomeColumn;
    @FXML
    private TableColumn<FuncionarioRow, String> adminCpfColumn;
    @FXML
    private TableColumn<FuncionarioRow, String> adminPapelColumn;
    
    @FXML
    private void logout(ActionEvent event) {
        currentUser.setLogado(false);
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ajustar varsão do software
        mainVersionLabel.setText("versão "+Constants.Versao.full());
        
        // Obter referência ao banco de dados e usuário atual
        conn = ConnectionManager.gerenciador().getConnection();
        userManager = UserManager.gerenciador(conn);
        currentUser = userManager.getCurrentUser();
        
        helloLabel.setText("Olá, "+currentUser.getNome());
        
        // Libera as abas de acordo com as permissões do usuário
        switch (currentUser.getPapel()) {
            case ADMIN:
                tabSaude.setDisable(true);
                tabAtendente.setDisable(true);
                try {
                    populateFuncTable();
                } catch (SQLException e) {
                    System.out.println("Erro ao preecher tabela de funcionários. "+e);
                }
                break;
            case SAUDE:
                tabAdmin.setDisable(true);
                tabAtendente.setDisable(true);
                break;
            case ATENDENTE:
                tabAdmin.setDisable(true);
                tabSaude.setDisable(true);
                break;
        }
    }
    
    private void populateFuncTable() throws SQLException {
        adminNomeColumn.setCellValueFactory(new PropertyValueFactory<FuncionarioRow, String>("nome"));
        adminCpfColumn.setCellValueFactory(new PropertyValueFactory<FuncionarioRow, String>("cpf"));
        adminPapelColumn.setCellValueFactory(new PropertyValueFactory<FuncionarioRow, String>("role"));
        try {
            reloadFuncTable();
        } catch (SQLException e) {
            System.out.println("Erro carregando lista de funcionários. "+e);
        }
    }
    
    public void reloadFuncTable() throws SQLException {
        adminFuncionarioList = FXCollections.observableArrayList();
        String sql = "SELECT nome, cpf, permissao FROM Funcionario";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Role papel;
            switch(rs.getInt("permissao")) {
                case 0:
                    papel = Role.ADMIN;
                    break;
                case 1:
                    papel = Role.SAUDE;
                    break;
                case 2:
                    papel = Role.ATENDENTE;
                    break;
                default:
                    papel = Role.ATENDENTE;
            }
            adminFuncionarioList.add(new FuncionarioRow(rs.getString("nome"), rs.getString("cpf"), papel));
            adminFuncTable.setItems(adminFuncionarioList);
        }
    }

    @FXML
    private void adminBuscaFunc(ActionEvent event) {
        User funcionario = userManager.buscaPorCPF(adminBuscaCpfTxt.getText());
        if (funcionario == null) {
            // Mostra o alerta de funcionario inexistente
        } else {
            try {
                if (root == null) {
                    root = (Stage) adminBuscaCpfBtn.getScene().getWindow();
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFuncionario.fxml"));
                Scene funcScene = new Scene(loader.load());
                Stage funcStage = new Stage();
                funcStage.initModality(Modality.WINDOW_MODAL);
                funcStage.initOwner(root);
                funcStage.setScene(funcScene);
                FXMLFuncionarioController funcController = loader.<FXMLFuncionarioController>getController();
                funcController.setStage(funcStage);
                funcController.setUser(funcionario);
                funcController.setIsEditing(true);
                funcController.setCaller(this);
                funcStage.show();
            } catch (IOException e) {
                System.out.println("Erro ao abrir janela para preenchimento de dados de funcionário. "+e);
            }
        }
    }

    @FXML
    private void adminNovoFunc(ActionEvent event) {
        // Carrega a janela para preenchimento dos dados de funcionário
        try {
            if (root == null) {
                root = (Stage) adminNovoBtn.getScene().getWindow();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFuncionario.fxml"));
            Scene funcScene = new Scene(loader.load());
            Stage funcStage = new Stage();
            funcStage.initModality(Modality.WINDOW_MODAL);
            funcStage.initOwner(root);
            funcStage.setScene(funcScene);
            FXMLFuncionarioController funcController = loader.<FXMLFuncionarioController>getController();
            funcController.setStage(funcStage);
            funcController.setIsEditing(false);
            funcController.setCaller(this);
            funcStage.show();
        } catch (IOException e) {
            System.out.println("Erro ao abrir janela para preenchimento de dados de funcionário. "+e);
        }
    }
    
}
