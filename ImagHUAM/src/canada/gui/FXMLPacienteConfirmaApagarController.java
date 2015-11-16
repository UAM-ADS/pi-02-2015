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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLPacienteConfirmaApagarController implements Initializable {
    private FXMLPacienteController caller;
    private Stage root;

    @FXML
    private Button pacienteConfirmaApagarBtn;
    @FXML
    private Button pacienteCancelaApagar;
    
    public void setCaller(FXMLPacienteController value) { caller = value; }
    public void setStage(Stage value) { root = value; }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pacienteConfirmaApagar(ActionEvent event) {
        caller.confirmaApagar();
        root.close();
    }
    
}
