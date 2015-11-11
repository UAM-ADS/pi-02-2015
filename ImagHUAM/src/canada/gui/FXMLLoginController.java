/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import canada.ConnectionManager;
import canada.UserManager;
import canada.User;
import java.io.IOException;

import java.sql.Connection;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField loginUserTxt;
    @FXML
    private TextField loginPassTxt;
    @FXML
    private Button loginOkBtn;
    @FXML
    private Button loginCancelBtn;
    @FXML
    private Label loginWrongLabel;
    
    private User currentUser;
    private ConnectionManager connManager;
    private UserManager userManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connManager = ConnectionManager.gerenciador();
        userManager = UserManager.gerenciador(connManager.getConnection());
    }   

    @FXML
    private void login(ActionEvent event) throws IOException {
        currentUser = userManager.loadUser(loginUserTxt.getText(), loginPassTxt.getText());
        if (currentUser == null) {
            loginWrongLabel.setVisible(true);
        } else {
            URL location = getClass().getResource("FXMLMain.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent main = (Parent) loader.load(location.openStream());
            Scene scene = new Scene(main);
            Stage stage = (Stage) loginOkBtn.getScene().getWindow();
            stage.setScene(scene);
            
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    private void loginCancel(ActionEvent event) {
        System.exit(0);
    }
    
}
