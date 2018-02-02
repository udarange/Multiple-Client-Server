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

import java.net.Socket;

/**
 * java bean class for connected client
 * Created by archana on 2/1/18.
 */
public class ConnectedClient {
    private Socket socket;
    private int clientId;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getClientId() {
        return clientId;
    }
}
