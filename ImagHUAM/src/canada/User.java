/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import canada.utils.Constants.Role;
import canada.utils.Crypto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mgaldieri
 */
public class User {
    private String nome;
    private String login;
    private String senha;
    private Role papel;
    private Boolean logado;
    
    private Connection conn;
    
    public User(Connection c) {
        conn = c;
    }
    
    public void setNome(String value) { nome = value; }
    public String getNome() { return nome; }
    
    public void setLogin(String value) { login = value; }
    public String getLogin() { return login; }
    
    public void setSenha(String value) { senha = value; }
    public String getSenha() { return senha; }
    
    public void setPapel(Role value) { papel = value; }
    public Role getPapel() { return papel; }
    
    public void setLogado(Boolean value) { logado = value; }
    public Boolean isLogado() { return logado; }
    
    public Boolean salva() {
        try {
            String sql = "INSERT INTO usuario (nome, login, senha, admin, salario)"
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, login);
            stmt.setString(3, Crypto.md5String(senha));
            stmt.setInt(4, papel.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Boolean autentica() {
        return null;
    }

    public Boolean sair() {
        return null;
    }
}
