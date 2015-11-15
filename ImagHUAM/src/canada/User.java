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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mgaldieri
 */
public class User {
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;
    private String login;
    private String senha;
    private String especialidade;
    private String registro;
    private Role papel;
    private Boolean logado;
    
    private Connection conn;
    
    public User(Connection c) {
        conn = c;
    }
    
    public void setNome(String value) { nome = value; }
    public String getNome() { return nome; }
    
    public void setCpf(String value) { cpf = value; }
    public String getCpf() { return cpf; }
    
    public void setEndereco(String value) { endereco = value; }
    public String getEndereco() { return endereco; }
    
    public void setTelefone(String value) { telefone = value; }
    public String getTelefone() { return telefone; }
    
    public void setEmail(String value) { email = value; }
    public String getEmail() { return email; }
    
    public void setLogin(String value) { login = value; }
    public String getLogin() { return login; }
    
    public void setSenha(String value) { senha = value; }
    public String getSenha() { return senha; }
    
    public void setEspecialidade(String value) { especialidade = value; }
    public String getEspecialidade() { return especialidade; }
    
    public void setRegistro(String value) { registro = value; }
    public String getRegistro() { return registro; }
    
    public void setPapel(Role value) { papel = value; }
    public Role getPapel() { return papel; }
    
    public void setLogado(Boolean value) { logado = value; }
    public Boolean isLogado() { return logado; }

    public Boolean autentica() {
        return null;
    }

    public Boolean sair() {
        return null;
    }
}
