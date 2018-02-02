package Client.TCPClientImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * TCP Client impl
 * Created by archana on 1/30/18.
 */
public class ClientImpl {
    private Socket socket = null;

    // constructor
    public ClientImpl(Socket socket) {
        this.socket = socket;
    }

    /**
     * Server pull request
     */
    public void clientGenerator() throws IOException, InterruptedException {
        while (true) {
            String listenString = listen();
            if (listenString!=null) {
                System.out.println("RESPONSE FROM SERVER: " + listenString);
                // Thread.sleep(1000);
            }
        }
    }

    /**
     * Server pull request
     */
    private void pull() throws IOException {
        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

        /* pull request write() */
        outToServer.writeBytes("PULL\n");
    }

    /**
     * Server notify listen
     */
    private String listen() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader inFromServer = new BufferedReader(inputStreamReader);

        String fromServerACK = inFromServer.readLine();
        if (fromServerACK.equals("NOTIFY")) {
            pull(); // Client request updated data
            fromServerACK = inFromServer.readLine();
        }else {
            fromServerACK = null;
        }

        /* read updated weather index and send it to main */
        return fromServerACK;
    }

}
