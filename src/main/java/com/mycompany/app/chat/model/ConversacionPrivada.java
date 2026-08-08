/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.model;

/**
 *
 * @author BRENDA
 */

import java.util.ArrayList;
import java.util.List;

public class ConversacionPrivada {

    private final String usuario1;

    private final String usuario2;

    private final List<Mensaje> mensajes;

    public ConversacionPrivada(String usuario1, String usuario2) {

        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.mensajes = new ArrayList<>();

    }

    public String getUsuario1() {
        return usuario1;
    }

    public String getUsuario2() {
        return usuario2;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

}
