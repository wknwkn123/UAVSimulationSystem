package websocket.simple_v2.server;

import java.util.Scanner;
import javax.websocket.DeploymentException;

public class Server {

    public static void main(String[] args) {

        org.glassfish.tyrus.server.Server server;
        server = new org.glassfish.tyrus.server.Server("localhost", 9001, "/ws", null , ServerEndpoint.class);

        try {
            server.start();
            System.out.println("Press any key to stop the server..");
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

}