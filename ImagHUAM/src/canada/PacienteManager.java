/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import java.sql.Connection;

import canada.ConnectionManager;
import canada.utils.Constants;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mgaldieri
 */
public class PacienteManager {
    private static PacienteManager gerenciador;
    private static Connection conn;
    
    private PacienteManager() {
        this.conn = ConnectionManager.gerenciador().getConnection();
    }
    
    public static PacienteManager gerenciador() {
        if (gerenciador == null) {
            gerenciador = new PacienteManager();
        }
        return gerenciador;
    }
    
    public Paciente buscaPorCpf(String cpf) {
        String pattern = "[\\.-]";
        cpf = cpf.replaceAll(pattern, "");
        
        // Executa a busca no BD pelo CPF
        try {
            String sql = "SELECT * FROM Paciente WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Paciente paciente = new Paciente();
            paciente.setNome(rs.getString("nome"));
            paciente.setCpf(rs.getString("cpf"));
            paciente.setEndereco(rs.getString("endereco"));
            paciente.setTelefone(rs.getString("telefone"));
            paciente.setEmail(rs.getString("email"));
            paciente.setCartao(rs.getString("cartao"));
            switch (rs.getInt("sexo")) {
                case 0:
                    paciente.setSexo(Constants.Sexo.MASCULINO);
                    break;
                case 1:
                    paciente.setSexo(Constants.Sexo.FEMININO);
                    break;
            }
            return paciente;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar paciente no banco de dados. "+e);
            return null;
        }
    }
    
    public boolean savePaciente(Paciente paciente) {
        try {
            // Verifica se o usuário já existe
            String firstSql = "SELECT cpf FROM Paciente WHERE cpf=?";
            PreparedStatement firstStmt = conn.prepareStatement(firstSql);
            firstStmt.setString(1, paciente.getCpf());
            ResultSet rs = firstStmt.executeQuery();
            if (rs.next()) {
                String sql = "UPDATE Paciente"
                        + " SET nome=?, cpf=?, endereco=?, telefone=?, email=?, cartao=?, sexo=?"
                        + " WHERE cpf=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, paciente.getNome());
                stmt.setString(2, paciente.getCpf());
                stmt.setString(3, paciente.getEndereco());
                stmt.setString(4, paciente.getTelefone());
                stmt.setString(5, paciente.getEmail());
                stmt.setString(6, paciente.getCartao());
                stmt.setInt(7, paciente.getSexo().getSexo());
                stmt.setString(8, paciente.getCpf());
                stmt.executeUpdate();
                conn.commit();
            } else {
                String sql = "INSERT INTO Paciente (nome, cpf, endereco, telefone, email, cartao, sexo)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, paciente.getNome());
                stmt.setString(2, paciente.getCpf());
                stmt.setString(3, paciente.getEndereco());
                stmt.setString(4, paciente.getTelefone());
                stmt.setString(5, paciente.getEmail());
                stmt.setString(6, paciente.getCartao());
                stmt.setInt(7, paciente.getSexo().getSexo());
                stmt.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao gravar paciente no banco de dados. "+e);
            return false;
        }
        return true;
    }
    
    public boolean removePaciente(Paciente paciente) {
        try {
            String sql = "DELETE FROM Paciente WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getCpf());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro removendo paciente do banco de dados. "+e);
            return false;
        }
    }
}
