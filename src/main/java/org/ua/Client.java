package org.ua;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by andreyz on 13.11.16.
 */
public class Client {

    private static final int SERVSOCKET = 9797;

    public static void main(String[] args) throws Exception {

        // args[0] - remote Server IP address
        Socket socket = new Socket(args[0], SERVSOCKET);

        BufferedReader getMEssage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader setMessage = new BufferedReader(new InputStreamReader(System.in));

        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        String msgFromServer;
        while (!"stop".equals(msgFromServer = getMEssage.readLine())) {

            System.out.println("Server msg : " + msgFromServer);
            // args[1] - User Name
            String servMsg = args[1] + " : " + setMessage.readLine();
            pw.println(servMsg);
            pw.flush();
        }

    }
}
