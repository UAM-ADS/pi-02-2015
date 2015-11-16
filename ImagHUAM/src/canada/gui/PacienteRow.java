/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.gui;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mgaldieri
 */
public class PacienteRow {
    private final SimpleStringProperty nome;
    private final SimpleStringProperty cpf;
    private final SimpleStringProperty cartao;
    
    public PacienteRow(String nome, String cpf, String cartao) {
        this.nome = new SimpleStringProperty(nome);
        this.cpf = new SimpleStringProperty(cpf);
        this.cartao = new SimpleStringProperty(cartao);
    }
    
    public void setNome(String value) { nome.set(value); }
    public String getNome() { return nome.get(); }
    
    public void setCpf(String value) { cpf.set(value); }
    public String getCpf() { return cpf.get(); }
    
    public void setCartao(String value) { cartao.setValue(value); }
    public String getCartao() { return cartao.getValue(); }
}
