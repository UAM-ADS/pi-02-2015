/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canada.exceptions;

/**
 *
 * @author mgaldieri
 */
public class UsuarioInexistenteException extends Exception {
    public UsuarioInexistenteException(String msg) {
        super(msg);
    }
}
