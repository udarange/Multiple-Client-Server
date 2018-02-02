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
/**
 * TCP Server impl
 * Created by archana on 1/30/18.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TCPServer {
    public static void main(String argv[]) throws Exception {
        int PORT = 4455;
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server Started!!!");

        ArrayList<Socket> activeClients = new ArrayList<Socket>();
        System.out.println(">>>>>>>>>>> " + Arrays.toString(activeClients.toArray()));

        while (true) {
            Socket s = serverSocket.accept();
            activeClients.add(s);
            System.out.println(">>>>>>>>>>> " + Arrays.toString(activeClients.toArray()));

            notifyClient(activeClients);
            listenToClient(activeClients);
        }
    }

    /**
     * listen PULL request
     */
    private static void listenToClient(ArrayList<Socket> activeClients) {
        Iterator<Socket> activeClient = activeClients.iterator();

        while (activeClient.hasNext()) {
            try {
                Socket socket = activeClient.next();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                String isPull = inputStream.readUTF();//reading
                if (isPull.equals("PULL")){
                    System.out.println("UPDATED");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * notify when updated data available
     */
    public static void notifyClient(ArrayList<Socket> activeClients) {
        Iterator<Socket> activeClient = activeClients.iterator();

        while (activeClient.hasNext()) {
            try {
                Socket socket = activeClient.next();
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                outputStream.writeUTF("NOTIFY");//writing
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * weather parameter update
     */
    public static void updateWeatherIndex() {


    }
}
