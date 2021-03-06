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

package Client;

import java.io.*;
import java.net.Socket;

/**
 * create a new client
 * Created by archana on 1/30/18.
 */
public class TCPClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int PORT = 4455;
        try {
            Socket socket = new Socket("localhost", PORT);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream dIn = new DataInputStream(in);
            DataOutputStream dOut = new DataOutputStream(out);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            String inputString = null;
            while (true) {
                System.out.println("\nListening...");
                inputString = dIn.readUTF();
                if (inputString.equals("NOTIFY")) {
                    System.out.println("RECEIVED FROM SERVER: " + inputString);
                    System.out.print("Do you want updated Weather Index (y/n): ");
                    dOut.writeUTF(keyboard.readLine()); //send PULL or null | keyboard input
                    dOut.flush();
                    inputString = dIn.readUTF();
                    if (!inputString.equals("null")) {
                        System.out.println("RECEIVED FROM SERVER: " + inputString);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
