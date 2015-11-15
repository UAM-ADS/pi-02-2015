/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.utils;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mgaldieri
 */
public class Constants {
    public enum Versao {
        MAJOR('0'),
        MINOR('1'),
        FIX('0');
        private char valor;
        
        private Versao(char valor) {
            this.valor = valor;
        }
                
        public static String full() {
            return ""+Versao.MAJOR.valor+'.'+Versao.MINOR.valor+'.'+Versao.FIX.valor;
        }
    }
    
    public enum TabIndex {
        ADMIN(0),
        ATENDENTE(1),
        SAUDE(2);
        private int index;
        
        private TabIndex(int index) {
            this.index = index;
        }
        
        public int getIndex() {
            return index;
        }
    }
    
    public enum Role {
        ADMIN(0),
        SAUDE(1),
        ATENDENTE(2);
        private int role;
        
        private Role(int role) {
            this.role = role;
        }
        
        public int getRole() {
            return role;
        }
        
        public String toString() {
            String roleName;
            switch (role) {
                case 0:
                    roleName = "Admin";
                    break;
                case 1:
                    roleName = "Atendente";
                    break;
                case 2:
                    roleName = "Saúde";
                    break;
                default:
                    roleName = "Inexistente";
                    break;
            }
            return roleName;
        }
    }
    
    // Configurações do banco de dados local
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/.canada/imaghuam.db";
    
    // Credenciais do usuário inicial
    public static final String masterName = "Administrador";
    public static final String masterUser = "admin";
    public static final String masterPass = "admin123";
    
    // Lista de estados brasileiros
    public static final List<String> listaUF = Arrays.asList(
        "AC",
        "AL",
        "AP",
        "AM",
        "BA",
        "CE",
        "DF",
        "ES",
        "GO",
        "MA",
        "MT",
        "MS",
        "MG",
        "PA",
        "PB",
        "PR",
        "PE",
        "PI",
        "RJ",
        "RN",
        "RS",
        "RO",
        "RR",
        "SC",
        "SP",
        "SE",
        "TO");
}