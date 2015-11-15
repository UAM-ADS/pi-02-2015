/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.utils;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author mgaldieri
 */
public class Helpers {
    public static Boolean validaCPF(String numeroCPF) {
        if (numeroCPF == null || numeroCPF.length() < 11) {
            return false;
        }
        String numeros = numeroCPF.substring(0, numeroCPF.length()-2);
        String verificadores = numeroCPF.substring(numeroCPF.length()-2);
        // Calcula os verificadores esperados
        String teste;
        // Calcula o primeiro digito verificador
        int multiplicador = 2;
        int soma = 0;
        for (int i=numeros.length()-1; i>=0; i--) {
            soma += multiplicador * Character.digit(numeros.charAt(i), 10);
            multiplicador++;
        }
        int resto = soma%11;
        if (resto < 2) {
            teste = "0";
        } else {
            teste = String.valueOf(11-resto);
        }
        // Calucla o segundo digito verificador
        numeros += teste;
        multiplicador = 2;
        soma = 0;
        for (int i=numeros.length()-1; i>=0; i--) {
            soma += multiplicador * Character.digit(numeros.charAt(i), 10);
            multiplicador++;
        }
        resto = soma%11;
        if (resto < 2) {
            teste += "0";
        } else {
            teste += String.valueOf(11-resto);
        }
        if (teste.equals(verificadores)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String formataCPF(String cpf) throws ParseException {
        MaskFormatter mask = new MaskFormatter("###.###.###-##");
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(cpf);
    }
}
