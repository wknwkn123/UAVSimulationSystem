//package websocket;
//
//import javax.websocket.Session;
//import java.util.Scanner;
//
//public class Client {
//
//    public static final String SERVER = "sync with andre";
//
//    public void connectToServer() {
//        ClientManager client = ClientManager.createClient();
//        String message;
//
//        // connect to server
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to Tiny Chat!");
//        System.out.println("What's your name?");
//        String user = scanner.nextLine();
//        Session session = client.connectToServer(ClientEndpoint.class, new URI(SERVER));
//        System.out.println("You are logged in as: " + user);
//
//        // repeatedly read a message and send it to the server (until quit)
//        do {
//            message = scanner.nextLine();
//            session.getBasicRemote().sendText(formatMessage(message, user));
//        } while (!message.equalsIgnoreCase("quit"));
//    }
//
//}