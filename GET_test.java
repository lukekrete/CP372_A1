		Scanner s = new Scanner(line);
		String ignore = s.next();
		String color = s.next();
		ignore = s.next();
		String contains = s.next();
		ignore = s.next();
		String message = "";
		while(s.hasNext()){
			message = message + " " + s.next();
		}
		message = message.trim();
		System.out.println("Color:" + color + " | Contains:" + contains + " | message:" + message);
