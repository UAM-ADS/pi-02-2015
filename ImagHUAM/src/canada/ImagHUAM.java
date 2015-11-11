/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import canada.utils.Constants;
import canada.utils.Crypto;
import canada.ConnectionManager;

/**
 *
 * PROJETO INTERDISCIPLINAR III (ON) - 201520.00028.01
 * Sistema Imagiologia HUAM
 * @version 0.1.0
 *
 * @custom.equipe Canadá
 * @author Mauricio de Camargo Galdieri
 * @custom.ra 20458437
 * @author Lorenna Borges
 * @custom.ra 20467242
 * @author William Junior Dias
 * @custom.ra 20421593
 * @author Andressa Pinheiro Guerra
 * @custom.ra 20460231
 * @author Thiago Ribeiro Baptista
 * @custom.ra 20468075
 */
public class ImagHUAM extends Application {
    private Connection conn;  // Conexão com banco de dados
    private ConnectionManager connManager;
    
    @Override
    public void start(Stage stage) throws Exception {
        // Obtém uma referência à conexão com o banco de dados
        conn = ConnectionManager.gerenciador().getConnection();
                
        // Inicia o banco de dados
        initDB();
        
        // Apresenta a tela de login
        Parent root = FXMLLoader.load(getClass().getResource("gui/FXMLLogin.fxml"));     
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Imagiologia HUAM - v"+Constants.Versao.full());
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void initDB() throws SQLException {
        // Cria as tabelas
        String sql = new Scanner(this.getClass().getResourceAsStream("initdb.sql"), "UTF-8").useDelimiter("\\A").next();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.execute();
        
         // Verifica se o usuário inicial existe
        sql = "SELECT * FROM Funcionario WHERE login=?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, Constants.masterUser);
        // Se o usuário não existe
        if (!stmt.executeQuery().next()) {
            // Cria o usuário inicial
            sql = "INSERT INTO Funcionario (id_funcionario, nome, login, senha, permissao) VALUES (1, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Constants.masterName);
            stmt.setString(2, Constants.masterUser);
            stmt.setString(3, Crypto.md5String(Constants.masterPass));
            stmt.setInt(4, Constants.Role.ADMIN.getRole());
            stmt.executeUpdate();
        }
    }
    
}
