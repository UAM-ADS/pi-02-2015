/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import java.sql.Connection;
import java.sql.DriverManager;

import canada.utils.Constants;
import java.sql.SQLException;

/**
 *
 * @author mgaldieri
 */
public class ConnectionManager {
    private static ConnectionManager gerenciador;
    private static Connection conn;
    
    private ConnectionManager() {}
    
    public static ConnectionManager gerenciador() {
        if (gerenciador == null) {
            try {
                // Inicia a conexão com o banco de dados
                Class.forName(Constants.JDBC_DRIVER);
                conn = DriverManager.getConnection(Constants.DB_URL);
            } catch (ClassNotFoundException | SQLException e) {
                gerenciador = null;
            }
            gerenciador = new ConnectionManager();
        }
        return gerenciador;
    }
    
    public Connection getConnection() {
        return conn;
    }
}
