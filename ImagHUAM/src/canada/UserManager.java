/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import canada.User;
import canada.utils.Constants;
import canada.utils.Constants.Role;
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
    
    public boolean saveUser(User user) {
        try {
            // Verifica se o usuário já existe
            String firstSql = "SELECT cpf FROM Funcionario WHERE cpf=?";
            PreparedStatement firstStmt = conn.prepareStatement(firstSql);
            firstStmt.setString(1, user.getCpf());
            ResultSet rs = firstStmt.executeQuery();
            if (rs.next()) {
                String sql = "UPDATE Funcionario"
                        + " SET nome=?, cpf=?, endereco=?, telefone=?, email=?, login=?, especialidade=?, registro=?, permissao=?"
                        + " WHERE cpf=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, user.getNome());
                stmt.setString(2, user.getCpf());
                stmt.setString(3, user.getEndereco());
                stmt.setString(4, user.getTelefone());
                stmt.setString(5, user.getEmail());
                stmt.setString(6, user.getLogin());
                stmt.setString(7, user.getEspecialidade());
                stmt.setString(8, user.getRegistro());
                stmt.setInt(9, user.getPapel().getRole());
                stmt.setString(10, user.getCpf());
                stmt.executeUpdate();
            } else {
                String sql = "INSERT INTO Funcionario (nome, cpf, endereco, telefone, email, login, especialidade, registro, permissao)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, user.getNome());
                stmt.setString(2, user.getCpf());
                stmt.setString(3, user.getEndereco());
                stmt.setString(4, user.getTelefone());
                stmt.setString(5, user.getEmail());
                stmt.setString(6, user.getLogin());
                stmt.setString(7, user.getEspecialidade());
                stmt.setString(8, user.getRegistro());
                stmt.setInt(9, user.getPapel().getRole());
                stmt.executeUpdate();
            }
            if (user.getSenha() != null) {
                String pwdSql = "UPDATE Funcionario SET senha=? WHERE cpf=?";
                PreparedStatement pwdStmt = conn.prepareStatement(pwdSql);
                pwdStmt.setString(1, Crypto.md5String(user.getSenha()));
                pwdStmt.setString(2, user.getCpf());
                pwdStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao gravar usuário no banco de dados. "+e);
            return false;
        }
        return true;
    }
    
    public boolean removeUser(User user) {
        try {
            String sql = "DELETE FROM Funcionario WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getCpf());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro removendo usuário do banco de dados. "+e);
            return false;
        }
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public User buscaPorCPF(String cpf) {
        String pattern = "[\\.-]";
        cpf = cpf.replaceAll(pattern, "");
        
        // Executa a busca no BD pelo CPF
        try {
            String sql = "SELECT * FROM Funcionario WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            User funcionario = new User(conn);
            funcionario.setNome(rs.getString("nome"));
            funcionario.setCpf(rs.getString("cpf"));
            funcionario.setEndereco(rs.getString("endereco"));
            funcionario.setTelefone(rs.getString("telefone"));
            funcionario.setEmail(rs.getString("email"));
            funcionario.setLogin(rs.getString("login"));
            funcionario.setEspecialidade(rs.getString("especialidade"));
            funcionario.setRegistro(rs.getString("registro"));
            switch (rs.getInt("permissao")) {
                case 0:
                    funcionario.setPapel(Role.ADMIN);
                    break;
                case 1:
                    funcionario.setPapel(Role.SAUDE);
                    break;
                case 2:
                    funcionario.setPapel(Role.ATENDENTE);
                    break;
            }
            return funcionario;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário no banco de dados. "+e);
            return null;
        }
    }
}
