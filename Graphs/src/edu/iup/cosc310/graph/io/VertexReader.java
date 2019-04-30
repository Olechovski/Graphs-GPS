package edu.iup.cosc310.graph.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VertexReader {
	private Scanner input;
	
	public VertexReader(String fileName) {
		try {
			input = new Scanner(new File(fileName));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String readVerticies() {
		while(input.hasNext()) {
			return input.nextLine();
		}
		return null;
	}

}
