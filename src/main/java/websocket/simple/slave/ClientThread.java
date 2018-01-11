package websocket.simple.slave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket server;
    private String id;

    public ClientThread(Socket server, String id) {
        this.server = server;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter out = new PrintWriter(server.getOutputStream());


            System.out.println("I am"+ id);
            out.flush();
            out.println(this.id);
            out.flush();

            while(! in.readLine().equals("GO")){}
            for(int i=0; i<5; i++) {
                out.println("" + id + " going " + i);
                out.flush();
                System.out.println("" + id + " going " + i);
                Thread.sleep(500);
            }
            out.println();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        e.printStackTrace();
    }
    }
}
