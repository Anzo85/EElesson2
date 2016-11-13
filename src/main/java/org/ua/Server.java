package org.ua;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


public class Server implements Runnable {

    private final static int PORT = 9797;

    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Server is running ");

        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            Socket socket = ss.accept();
            System.out.println("Client " + socket.getRemoteSocketAddress() + " connected ");
            new Thread(new Server(socket)).start();
        }
    }

    public void run() {

        ReentrantLock lock = new ReentrantLock();

        lock.lock();

        try {

            BufferedReader getMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader setMessage = new BufferedReader(new InputStreamReader(System.in));

            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("Server connected");
            pw.flush();

            String msgFromClient;
            while (!"stop".equals(msgFromClient = getMessage.readLine())) {
                System.out.println(" Address  : " + socket.getRemoteSocketAddress() + " " + msgFromClient);
                String msgFromServer = setMessage.readLine();
                pw.println(msgFromServer);
                pw.flush();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}

