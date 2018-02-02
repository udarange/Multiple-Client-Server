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
        ArrayList<ConnectedClient> connectedClientList = new ArrayList<ConnectedClient>();

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server Started!!!");

//        ConnectedClient connectedClient = new ConnectedClient();
        System.out.println("SOCKET LIST: " + Arrays.toString(connectedClientList.toArray()));

        while (true) {
            ConnectedClient newClient = new ConnectedClient();
            Socket s = serverSocket.accept();
            newClient.setSocket(s);
            newClient.setClientId(1);
            connectedClientList.add(newClient);
            
            System.out.println("SOCKET LIST: " + Arrays.toString(connectedClientList.toArray()));

            notifyClient(connectedClientList);
            listenToClient(connectedClientList);
        }
    }

    /**
     * listen PULL request
     */
    private static void listenToClient(ArrayList<ConnectedClient> activeClients) {
        String weatherIndex = updateWeatherIndex();
        Iterator<ConnectedClient> activeClient = activeClients.iterator();

        while (activeClient.hasNext()) {
            try {
                ConnectedClient connectedClient = activeClient.next();
                DataInputStream inputStream = new DataInputStream(connectedClient.getSocket().getInputStream());
                DataOutputStream outputStream = new DataOutputStream(connectedClient.getSocket().getOutputStream());

                String isPull = inputStream.readUTF();//reading
                if (isPull.equals("PULL")) {
                    outputStream.writeUTF("WEATHER INDEX: " + weatherIndex);//writing
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * notify when updated data available
     */
    private static void notifyClient(ArrayList<ConnectedClient> activeClients) {
        Iterator<ConnectedClient> activeClient = activeClients.iterator();

        while (activeClient.hasNext()) {
            try {
                ConnectedClient connectedClient = activeClient.next();
                DataOutputStream outputStream = new DataOutputStream(connectedClient.getSocket().getOutputStream());

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
    private static String updateWeatherIndex() {
        return "Partly Cloudy";
    }
}
