import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CapitalizeClient {
    public static void main(String[] args) throws Exception {
//        System.out.println("Enter the IP address of a machine running the capitalize server:");
        System.out.println("Enter the IP address of server: ");
        Scanner initial_input = new Scanner(System.in);
        String serverAddress = initial_input.nextLine();
        //String serverAddress = "127.0.0.1";//new Scanner(System.in).nextLine();
        System.out.println("Enter the port you wish to connect to: ");
        int port_server = Integer.parseInt(initial_input.nextLine());
        Socket socket = new Socket(serverAddress, port_server);

        // Streams for conversing with server
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println(in.readLine());
        out.println("aKLASUgfokblasdfkokasdfkmaskdkliskLKHN");

        // Consume and display welcome message from the server

        System.out.println(in.readLine());
        System.out.println(in.readLine());
        System.out.println(in.readLine());
        /*
        while(in.ready()){
            System.out.println(in.readLine());
        }
        */
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter a string to send to the server (empty to quit):");
            String message = scanner.nextLine();
            if (message == null || message.isEmpty()) {
                break;
            }
            out.println(message);
            System.out.println(in.readLine());
        }
    }
}

