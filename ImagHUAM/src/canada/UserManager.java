/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import canada.User;
import canada.utils.Constants;
import canada.utils.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mgaldieri
 */
public class UserManager {
    private static UserManager gerenciador;
    private static Connection conn;
    private static User currentUser;
    
    private UserManager(Connection c) {
        this.conn = c;
    }
    
    public static UserManager gerenciador(Connection c) {
        if (gerenciador == null) {
            gerenciador = new UserManager(c);
        }
        return gerenciador;
    }
    
    public User loadUser(String login, String password) {
        try {
            String sql = "SELECT * FROM Funcionario WHERE login=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next() || !rs.getString("senha").equals(Crypto.md5String(password))) {
                return null;
            } else {
                User user = new User(conn);
                user.setNome(rs.getString("nome"));
                user.setLogin(rs.getString("login"));
                user.setLogado(true);
                switch (rs.getInt("permissao")) {
                    case 0:
                        user.setPapel(Constants.Role.ADMIN);
                        break;
                    case 1:
                        user.setPapel(Constants.Role.SAUDE);
                        break;
                    case 2:
                        user.setPapel(Constants.Role.ATENDENTE);
                        break;
                }
                currentUser = user;
                return currentUser;
            }
        } catch (SQLException e) {
            return null;
        }
        
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
}
