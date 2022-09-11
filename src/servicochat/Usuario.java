/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicochat;

import java.io.Serializable;

/**
 *
 * @author Heck
 */
public class Usuario implements Serializable{ // Usado para armazenar usuarios conectados no servidor
    private final String nome;
    private final String id;

    public Usuario(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

}
