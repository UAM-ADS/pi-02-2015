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

import java.sql.Connection;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author mgaldieri
 */
public class FXMLMainController implements Initializable {
    
    @FXML
    private Label mainVersionLabel;
    @FXML
    private Label helloLabel;
    
    private Connection conn;
    private User currentUser;
    private UserManager userManager;
    
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
    }
    
}
