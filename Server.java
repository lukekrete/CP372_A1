import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;

public class PINServer {

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
        String bWidth = args[1];
        String bHeight = args[2];
        String colors = "";
        for(int i = 3; i < args.length; i++){
            colors = colors + args[i] + " ";
        }
        
        System.out.println("The P.I.N server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                new PIN(listener.accept(), clientNumber++, colors, bWidth, bHeight).start();
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
    private static class PIN extends Thread {
        private String password = "aKLASUgfokblasdfkokasdfkmaskdkliskLKHN";
        private Socket socket;
        private int clientNumber;
        private String colors = "";
        private String bWidth;
        private String bHeight;

        public PIN(Socket socket, int clientNumber, String colors_list, String width, String height) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.colors = colors_list;
            this.bWidth = width;
            this.bHeight = height;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        
    private class Pin{
        int x_coord;
        int y_coord;

            Pin(int x, int y){
                x_coord = x;
                y_coord = y;
            }
    }

    /* Linked list Node*/
    private class Note { 
        String message;
        String colour;
        int status;//unpinned = 0, pinned > 0
        LinkedList<Pin> pins;
        int x;
        int y;
        int height;
        int width;
  
        // Constructor to create a new node 
        // Next is by default initialized 
        // as null 
        Note(String m, String c, int x_2, int y_2, int w, int h) {
             message = m;
             colour = c;
             status = 0;  //unpinned = 0, pinned > 0
             pins = new LinkedList<Pin>();
             x = x_2;
             y = y_2;
             height = h;
             width = w;
        } 
    } 

    LinkedList<Note> board = new LinkedList<Note>();
    ArrayList<Pin> pins_list = new ArrayList<Pin>();

    public void GET(String request){

    }

    public void POST(String request) {
        Scanner line = new Scanner(request);
        int x_coord = Integer.parseInt(line.next());
        int y_coord = Integer.parseInt(line.next());
        int width = Integer.parseInt(line.next());
        int height = Integer.parseInt(line.next());
        String color = line.next();
        String message = "";

        //RE-CONCAT THE MESSAGE
        while(line.hasNext()) {
            message = message + line.next() + " ";
        }
        //just to omit having an extra space at the end
        //message = message + parts[i+1];
        //create the new note with the info we've retrieved
        Note newNote = new Note(message, color, x_coord, y_coord, width, height);
        //add new note to the board
        board.add(newNote);
    }

    public void PIN(String request){
        String[] parts = request.split(" ");
        String[] coords = parts[1].split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        //create new pin with coordinates from client
        Pin newPin = new Pin(x,y);

        Note current;
        for(int i = 0; i <board.size(); i++){
            current = board.get(i);
            //ensure that the pin is within the bounds of the current note
            if(x >= current.x && x <= current.x + current.width && y >= current.y && y <= current.y + current.height){
                current.status++;
                current.pins.add(newPin);
            }
        }
        pins_list.add(newPin);
    }

    public void UNPIN(String request){

    }

    public void CLEAR(String request){
        Note current;
        for (int i = 0; i < board.size(); i++){
            current = board.get(i);
            //determine if the note is pinned or not
            if (current.status == 0){
                //delete it if it's not pinned
                board.remove(i);
            }
        }
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


                String compare = in.readLine();
                if (compare.equals(password)) {
                    out.println(colors);
                    out.println(bWidth);
                    out.println(bHeight);

                    // Get messages from the client
                    

                    while (true) {
                        String input = in.readLine();
                        Scanner line = new Scanner(input);

                        String command = line.next();
                        if (command.equals("POST")) {
                            POST(line.nextLine());
                            out.println("Message successfully posted.");

                        } else if (command.equals("GET")) {
                            System.out.println(line.nextLine());
                            out.println("Messages found.");

                        } else if (command.equals("PIN")) {
                            System.out.println(line.nextLine());
                            out.println("Message successfully pinned.");

                        } else if (command.equals("UNPIN")) {
                            System.out.println(line.nextLine());
                            out.println("Message successfully unpinned");

                        } else if (command.equals("CLEAR")) {
                            out.println("All unpinned notes cleared.");
            
                        } else if (command.equals("DISCONNECT")) {
                            try {
                                socket.close();

                            } catch (Exception e) {
                                log("Couldn't close the socket.");

                            }
                        }
                    }
                    

                    
                } else {
                    out.println("Not using Client: Access Denied.");

            }} catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
                
            } //finally {
                log("Connection with client#" + clientNumber + " closed.");
            //}
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
