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
import java.util.Iterator;
import java.util.Scanner;

public class TCPServer {
    private static String weatherIndex;
    private static final int PORT = 4455;
    private static ArrayList<ConnectedClient> connectedClientList = new ArrayList<ConnectedClient>();

    public static void main(String argv[]) throws Exception {
        System.out.println("Server Started!!!");

        /* thread for client accept() */
        final Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(PORT);
                    while (true) {
                        Socket s = serverSocket.accept();
                        ConnectedClient newClient = new ConnectedClient();
                        newClient.setSocket(s);
                        newClient.setClientId(1);
                        connectedClientList.add(newClient);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();


//        int xx =0;
        while (true) {
//            System.out.println(++xx + " SOCKET LIST: " + Arrays.toString(connectedClientList.toArray()));

            Scanner scanner = new Scanner(System.in);
            System.out.print("Input weather : ");
            String weatherState = scanner.nextLine();

            updateWeatherIndex(weatherState);
            notifyClient(connectedClientList);
            listenToPullReq(connectedClientList);
        }
    }

    /**
     * listen PULL request
     */
    private static void listenToPullReq(ArrayList<ConnectedClient> activeClients) {
        Iterator<ConnectedClient> activeClient = activeClients.iterator();

        while (activeClient.hasNext()) {
            try {
                ConnectedClient connectedClient = activeClient.next();
                DataInputStream inputStream = new DataInputStream(connectedClient.getSocket().getInputStream());
                DataOutputStream outputStream = new DataOutputStream(connectedClient.getSocket().getOutputStream());

                String isPull = inputStream.readUTF();//reading
                if (isPull.equals("y")) {    // Client request an update
                    outputStream.writeUTF("WEATHER CONDITION : " + weatherIndex);//writing
                    outputStream.flush();
                } else {
                    outputStream.writeUTF("null");//writing
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
    static void updateWeatherIndex(String s) {
        weatherIndex =s;
    }
}
