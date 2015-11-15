/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.gui;

import canada.utils.Constants.Role;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mgaldieri
 */
public class FuncionarioRow {
    private final SimpleStringProperty nome;
    private final SimpleStringProperty cpf;
    private final SimpleStringProperty role;
    
    public FuncionarioRow(String nome, String cpf, Role role) {
        this.nome = new SimpleStringProperty(nome);
        this.cpf = new SimpleStringProperty(cpf);
        this.role = new SimpleStringProperty(role.toString());
    }
    
    public void setNome(String value) { nome.set(value); }
    public String getNome() { return nome.get(); }
    
    public void setCpf(String value) { cpf.set(value); }
    public String getCpf() { return cpf.get(); }
    
    public void setRole(String value) { role.set(value); }
    public String getRole() { return role.get(); }
}
