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

    static class Pin{
        int x_coord;
        int y_coord;

            Pin(int x, int y){
                x_coord = x;
                y_coord = y;
            }
    }

    /* Linked list Node*/
    static class Note { 
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
    public static LinkedList<Note> board = new LinkedList<Note>();
    public static ArrayList<Pin> pins_list = new ArrayList<Pin>();
    
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
                new Client(listener.accept(), clientNumber++, colors, bWidth, bHeight).start();
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
    public static class Client extends Thread {
        private String password = "aKLASUgfokblasdfkokasdfkmaskdkliskLKHN";
        private Socket socket;
        private int clientNumber;
        private String colors = "";
        private String bWidth;
        private String bHeight;

        public Client(Socket socket, int clientNumber, String colors_list, String width, String height) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.colors = colors_list;
            this.bWidth = width;
            this.bHeight = height;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

    public String GET(String request){
        //System.out.println("GET from client");
        //String line = " color= red contains= 2 2 refersTo= the man";
        //Open our scanner for string parsing
        String output = "";
        int getallpins = 0;
        int getallnotes = 0;
        Scanner s = new Scanner(request);
        //initialize variables
        String ignore = "";
        String color = "";
        int contains_x = -1;
        int contains_y = -1;
        String message = "";
        //GETTING THE CORRECT INPUT------------------------------------------------------
        //if the request is to get all pins
        if(request.contains("PINS")){
            for(int i = 0; i < pins_list.size(); i++){
                output = output + "{" + pins_list.get(i).x_coord + "," + pins_list.get(i).y_coord + "}" + " ------";
            }
            getallpins = 1;
        //if it's not to get all pins
        }else if(request.contains("ALL")){
            for(int i = 0; i < board.size(); i++){
                output = output + board.get(i).message + "------";
            }
            getallnotes = 1;
        }else{
            if(request.contains("color= ")){
                ignore = s.next();
                color = s.next();
                if(request.contains("contains= ")){
                    ignore = s.next();
                    contains_x = Integer.parseInt(s.next());
                    contains_y = Integer.parseInt(s.next());
                    if(request.contains("refersTo= ")){
                        ignore = s.next();
                        while(s.hasNext()){
                            message = message + " " + s.next();
                            message = message.trim();
                        }
                    }else{
                        message = null;
                    }
                }else{
                    contains_x = -1;
                    contains_y = -1;
                    if(request.contains("refersTo= ")){
                        ignore = s.next();
                        while(s.hasNext()){
                            message = message + " " + s.next();
                            message = message.trim();
                        }
                    }else{
                        message = null;
                    }
                }
            }else{
                color = null;
                if(request.contains("contains= ")){
                    ignore = s.next();
                    contains_x = Integer.parseInt(s.next());
                    contains_y = Integer.parseInt(s.next());
                    if(request.contains("refersTo= ")){
                        ignore = s.next();
                        while(s.hasNext()){
                            message = message + " " + s.next();
                            message = message.trim();
                        }
                    }else{
                        message = null;
                    }
                }else{
                    contains_x = -1;
                    contains_y = -1;
                    if(request.contains("refersTo= ")){
                        ignore = s.next();
                        while(s.hasNext()){
                            message = message + " " + s.next();
                            message = message.trim();
                        }
                    }else{
                        message = null;
                    }
                }
            }
        //------------------------------------------------------------------------------------------
        //System.out.println("Color:" + color + " | Contains:" + contains + " | message:" + message);
        }
        LinkedList<Note> match = new LinkedList<Note>();
        if(getallpins == 0 && getallnotes == 0){
            //System.out.println("Color: " + color);
            if(color != null){
                for(int i =0; i < board.size(); i++){
                    if(board.get(i).colour.equals(color)){
                        match.add(board.get(i));
                    }
                }
                if(contains_x != -1 && contains_y != -1){
                    for(int i = 0; i < match.size(); i++){
                        if(match.get(i).x != contains_x && match.get(i).y != contains_y){
                            match.remove(i);
                        }
                    }
                    if(message != null){
                        for(int i = 0; i < match.size(); i++){
                            if(!match.get(i).message.contains(message)){
                                match.remove(i);
                            }
                        }
                    }
                }
            }else{
                //need to add for all colours
                if(contains_x != -1 && contains_y != -1){
                    for(int i =0; i < board.size(); i++){
                        if(board.get(i).x == contains_x && board.get(i).y == contains_y){
                            match.add(board.get(i));
                        }
                    }
                    if(message != null){
                        for(int i = 0; i < match.size(); i++){
                            if(!match.get(i).message.contains(message)){
                                match.remove(i);
                            }
                        }
                    }
                }else{
                    if(message != null){
                        for(int i = 0; i < board.size(); i++){
                            if(board.get(i).message.contains(message)){
                                match.add(board.get(i));
                            }
                        }
                    }
                }
            }

            for(int i = 0; i < match.size(); i++){
                output = output + match.get(i).message + "------";
            }

        }

        //PRINT THE OUTPUT TO THE CLIENT
        //System.out.println(output);
        return output;

    }
    //WORKS
    public void POST(String request) {
        //System.out.println("POST from client");
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

    //WORKS
    public static int PIN(String request){
        int exists = 0;
        Scanner line = new Scanner(request);
        int x = Integer.parseInt(line.next());
        int y = Integer.parseInt(line.next());
        //create new pin with coordinates from client
        Pin newPin = new Pin(x,y);

        for(int i = 0; i < pins_list.size(); i++){
            if(pins_list.get(i).x_coord == x && pins_list.get(i).y_coord == y){
                exists = 1;
            }
        }


        Note current;
        if(exists == 0){
            for(int i = 0; i <board.size(); i++){
                current = board.get(i);
                //ensure that the pin is within the bounds of the current note
                if(x >= current.x && x <= current.x + current.width && y >= current.y && y <= current.y + current.height){
                    current.status++;
                    current.pins.add(newPin);
                }
            }
            pins_list.add(newPin);
            //log(pins_list.get(0).x_coord + ", " + pins_list.get(0).y_coord);
        }
        return exists;
    }

    //WORKS
    public int UNPIN(String request){
        Scanner line = new Scanner(request);
        int success = 0;
        int x = Integer.parseInt(line.next());
        int y = Integer.parseInt(line.next());
        int i = 0, found = 0;
        for (i = 0; i < pins_list.size(); i++) {
            if(pins_list.get(i).x_coord == x && pins_list.get(i).y_coord == y) {
                pins_list.remove(i);
                //out.println("Message successfully pinned.");
                success = 1;
                found = 1;
            }
        }
        //If the pin exists in the pin list
        if (found == 1) {
            Note current;
            for (i = 0; i < board.size(); i++) {
                //set current note
                current = board.get(i);
                for (int j = 0; j < current.pins.size(); j++) {
                    //check to see if that note is pinned by the target pin
                    if (current.pins.get(j).x_coord == x && current.pins.get(j).y_coord == y) {
                        current.pins.remove(j);
                        if(current.status > 0) {
                            //System.out.println(current.status);
                            current.status--;
                            //System.out.println(current.status);
                        }
                    }
                }
            }
        }else{
            success = 0;
            //out.println("Pin not found.");
        }
        return success;
    }
    //I THINK WORKS?
    public void CLEAR(){
        Note current;
        for (int i = 0; i < board.size(); i++){
            current = board.get(i);
            //determine if the note is pinned or not
            if (current.status < 1){
                //delete it if it's not pinned
                //System.out.println("Delete: " + current.message);
                board.remove(i);
                i--;

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
                            String sendit = GET(line.nextLine());
                            //System.out.println(line.nextLine());
                            out.println(sendit);

                        } else if (command.equals("PIN")) {
                            int v = PIN(line.nextLine());
                            //System.out.println(line.nextLine());
                            if(v == 0){
                                out.println("Pin successfully placed.");
                            }else{
                                out.println("Pin already exists.");
                            }
                        } else if (command.equals("UNPIN")) {
                            int v = UNPIN(line.nextLine());
                            if(v == 0){
                                out.println("Pin not found.");
                            }else{
                                out.println("Pin successfully removed.");
                            }
                            //System.out.println(line.nextLine());
                            //out.println("Message successfully unpinned");

                        } else if (command.equals("CLEAR")) {
                            CLEAR();
                            out.println("All unpinned notes cleared.");
            
                        } else if (command.equals("DISCONNECT")) {
                            break;
                        }
                    }
                    

                    
                } else {
                    out.println("Not using Client: Access Denied.");

            }} catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
                
            } finally {
                try {
                    socket.close();

                } catch (Exception e) {
                    log("Couldn't close the socket.");

                }
                log("Connection with client#" + clientNumber + " closed.");
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
