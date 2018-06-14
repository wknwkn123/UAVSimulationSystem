package websocket.simple.slave;

import java.io.IOException;
import java.net.Socket;

public class Slave {
    private int numOfFlight;
    private String serverAddress = "127.0.0.1";
    private int serverPort = 9991;

    public Slave(String serverAddress, int serverPort, int numOfFlight){
        this.numOfFlight = numOfFlight;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start(){
        for(int i = 0; i<numOfFlight; i++) {
            try {
                Socket socket = new Socket(serverAddress, serverPort);
                new ClientThread(socket, ""+i).start();
            } catch (Exception e) {
                System.out.println("can not listen to:" + e);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        try {
            String serverAddress = args[0];
            int serverPort = Integer.parseInt(args[1]);
            int numOfFlight = Integer.parseInt(args[2]);
            new Slave(serverAddress, serverPort, numOfFlight).start();
        } catch (Exception e){
            System.out.println("Usage: java slave.Slave [server address] [port] [# of threads]");
        }
    }

}