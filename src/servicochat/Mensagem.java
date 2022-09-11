/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicochat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Heck
 */
public class Mensagem implements Serializable{
    
    private final String id_remetente;
    private final String id_destinatario;
    private final String texto;
    private final LocalDate data;

    public Mensagem(String id_remetente, String id_destinatario, String texto, LocalDate data) {
        this.id_remetente = id_remetente;
        this.id_destinatario = id_destinatario;
        this.texto = texto;
        this.data = data;
    }

    public String getId_remetente() {
        return id_remetente;
    }

    public String getId_destinatario() {
        return id_destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDate getData() {
        return data;
    }
    
}
