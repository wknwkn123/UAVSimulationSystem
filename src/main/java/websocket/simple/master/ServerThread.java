package websocket.simple.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread {
    private Socket remoteClient;
    private String id;
    public ServerThread(Socket remoteClient){
        System.out.println("new one come");
        this.remoteClient = remoteClient;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(remoteClient.getInputStream()));
            PrintWriter out = new PrintWriter(remoteClient.getOutputStream());
            id = in.readLine();
            System.out.println("id: "+id);
            out.flush();
            out.println("GO");
            String msg;
            do {
                msg = in.readLine();
                System.out.println(msg);
            } while(! (msg == null));

            remoteClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}