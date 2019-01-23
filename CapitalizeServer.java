import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SOURCE:  http://cs.lmu.edu/~ray/notes/javanetexamples/
 *
 * A server program which accepts requests from clients to capitalize strings.
 * When clients connect, a new thread is started to handle an interactive dialog
 * in which the client sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform dependent.
 * If you ran it from a console window with the "java" interpreter, Ctrl+C will
 * shut it down.
 */

public class CapitalizeServer {

    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        int bWidth = Integer.parseInt(args[1]);
        int bHeight = Integer.parseInt(args[2]);
        String[] colors = new String[100];
        for(int i = 3; i < args.length; i++){
            colors[i-3] = args[i];
        }
        System.out.println("bWidth: " + bWidth + " , bHeight: " + bHeight);
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++,colors).start();
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private String password = "aKLASUgfokblasdfkokasdfkmaskdkliskLKHN";
        private Socket socket;
        private int clientNumber;
        private String[] colors = new String[100];

        public Capitalizer(Socket socket, int clientNumber,String[] colors_list) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.colors = colors_list;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Validating Client-Server password...");
                String compare = in.readLine();
                if (compare.equals(password)) {
                    // Send a welcome message to the client.
                    out.println("Hello, you are client #" + clientNumber + ".");
                    out.println("The list of colours are as follows: ");
                    int i = 0;
                    while(this.colors[i] != null && i < this.colors.length){
                        out.print(this.colors[i] + " ");
                        i++;
                    }
                    out.println("");
    //                out.println("Enter a line with only a period to quit\n");

                    // Get messages from the client, line by line; return them
                    // capitalized
                    while (true) {
                        String input = in.readLine();
                        if (input == null || input.equals(".")) {
                            break;
                        }
                        out.println(input.toUpperCase());
                    }
                } else {
                    out.println("Not using Client: Access Denied.");
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
    
}

