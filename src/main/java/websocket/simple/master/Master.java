package websocket.simple.master;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {

    private int port;
    public void setPort(int p){ this.port = p; }

    protected void start() {
        ServerSocket s;

        System.out.println("Server starting up on port "+port);
        System.out.println("(press ctrl-c to exit)");
        try {
            // create the main server socket
            s = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");
        for (;;) {
            try {
                Socket remote = s.accept();
                new ServerThread(remote).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        int p;
        try {
            p = Integer.parseInt(args[0]);
            Master ws = new Master();
            ws.setPort(p);
            ws.start();
        } catch(Exception e){
            System.out.println("Please input server port!");
            e.printStackTrace();
        }
    }
}
