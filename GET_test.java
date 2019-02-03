import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;


public class test {

	public static void main(String[] args) {

		String line = " color= red contains= 2,2 refersTo= the man";
		//Open our scanner for string parsing
		Scanner s = new Scanner(line);
		//initialize variables
		String ignore = "";
		String color = "";
		String contains = "";
		String message = "";
		//GETTING THE CORRECT INPUT------------------------------------------------------
		if(line.contains("color= ")){
			ignore = s.next();
			color = s.next();
			if(line.contains("contains= ")){
				ignore = s.next();
				contains = s.next();
				if(line.contains("refersTo= ")){
					ignore = s.next();
					while(s.hasNext()){
						message = message + " " + s.next();
						message = message.trim();
					}
				}else{
					message = null;
				}
			}else{
				contains = null;
				if(line.contains("refersTo= ")){
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
			if(line.contains("contains= ")){
				ignore = s.next();
				contains = s.next();
				if(line.contains("refersTo= ")){
					ignore = s.next();
					while(s.hasNext()){
						message = message + " " + s.next();
						message = message.trim();
					}
				}else{
					message = null;
				}
			}else{
				contains = null;
				if(line.contains("refersTo= ")){
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
		System.out.println("Color:" + color + " | Contains:" + contains + " | message:" + message);
	}

}
