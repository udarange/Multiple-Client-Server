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
public class TCPClient2 {
    public static void main(String[] args) throws IOException, InterruptedException {
//        ClientImpl client = new ClientImpl(new Socket("localhost", PORT));
//        client.clientGenerator();

        ///////////////////////////////////////////////////////////////////
        int PORT = 4455;
        try {
            Socket socket = new Socket("localhost", PORT);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream dIn = new DataInputStream(in);
            DataOutputStream dOut = new DataOutputStream(out);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            String line = null;
            while (true) {
//                line = keyboard.readLine();
//                System.out.println("SEND TO SERVER: "+ line);
//                dOut.writeUTF(line);
//                dOut.flush();

                line = dIn.readUTF();
                System.out.println("RECEIVED FROM SERVER: " + line);
            }
        } catch (Exception e) {
        }
        ///////////////////////////////////////////////////////////////////

    }
}
