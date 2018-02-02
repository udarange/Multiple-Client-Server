/*
 * (C) Copyright 2010-2018 hSenid Mobile Solutions (Pvt) Limited.
 * All Rights Reserved.
 *
 * These materials are unpublished, proprietary, confidential source code of
 * hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
 * of hSenid Mobile Solutions (Pvt) Limited.
 *
 * hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
 * property rights in these materials.
 */

package Server;

import java.io.*;
import java.net.Socket;

/**
 * Thread impl
 * Created by archana on 1/30/18.
 */
public class ClientHandler implements Runnable {
    private static Socket serverSocket;

    ClientHandler(Socket s) {
        serverSocket = s;
    }

    public void run() {
        try {
            InputStream in = serverSocket.getInputStream();
            OutputStream out = serverSocket.getOutputStream();

            DataInputStream dIn = new DataInputStream(in);
            DataOutputStream dOut = new DataOutputStream(out);

            String line = null;
            while (true) {
                line = dIn.readUTF();
                System.out.println("RECEIVED FROM CLIENT: " + line);

                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                String line2 = keyboard.readLine(); //The user types a message for the client

                System.out.println("SEND TO CLIENT: " + line2);
                dOut.writeUTF(line2);
                dOut.flush();

                System.out.println("waiting for the next msg....");
            }
        } catch (Exception e) {
        }
    }
}
