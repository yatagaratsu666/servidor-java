/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.app.chat;

import com.mycompany.app.chat.network.SocketServer;
import java.io.IOException;

/**
 *
 * @author BRENDA
 */
public class AppChat {

   private static final int PORT = 5000;

    public static void main(String[] args) {
        
        try {
            System.out.println("comprobacion xq si");
            SocketServer server = new SocketServer(PORT);
            server.start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
