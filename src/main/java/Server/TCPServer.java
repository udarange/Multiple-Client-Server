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
        ArrayList<DataOutputStream> clientsOut = new ArrayList<DataOutputStream>();
        ArrayList<DataInputStream> clientsIn = new ArrayList<DataInputStream>();
        System.out.println(">>>>>>>>>>> " + Arrays.toString(clientsOut.toArray()));

        while (true) {
            Socket s = serverSocket.accept();
            activeClients.add(s);

            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            clientsOut.add(outputStream);
            clientsIn.add(inputStream);
            System.out.println(">>>>>>>>>>> " + Arrays.toString(activeClients.toArray()));

            notifyClient(clientsOut);
            listenToClient(clientsIn);

//            Thread thread = new Thread(new ClientHandler(s));
//            thread.start();
        }
    }

    /**
     * listen PULL request
     */
    private static void listenToClient(ArrayList<DataInputStream> clientsIn) {
        Iterator<DataInputStream> it = clientsIn.iterator();

        while (it.hasNext()) {
            try {
                DataInputStream iss = it.next();
                String isPull = iss.readUTF();//reading
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
    public static void notifyClient(ArrayList<DataOutputStream> activeClients) {
        Iterator<DataOutputStream> ot = activeClients.iterator();

        while (ot.hasNext()) {
            try {
                DataOutputStream oss = ot.next();
                oss.writeUTF("NOTIFY");//writing
                oss.flush();
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
