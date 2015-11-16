/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada;

import canada.utils.Constants.Sexo;

/**
 *
 * @author mgaldieri
 */
public class Paciente {
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;
    private Sexo sexo; 
    private String cartao;
    
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
    
    public void setSexo(Sexo value) { sexo = value; }
    public Sexo getSexo() { return sexo; }
    
    public void setCartao(String value) { cartao = value; }
    public String getCartao() { return cartao; }
    
    // Ctor
    public Paciente() {
        
    }
    
    // Ctor
    public Paciente(String nome, String cpf, String endereco, String telefone, String email, Sexo sexo, String cartao) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.sexo = sexo;
        this.cartao = cartao;
    }
}
