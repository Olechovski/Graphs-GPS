package edu.iup.cosc310.graph.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EdgeReader {
	private Scanner input;
	public EdgeReader(String fileName) {
		try {
			input = new Scanner(new File(fileName));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String[] readEdges() {
		while(input.hasNext()) {
			return input.nextLine().split(",[ ]*");
		}
		return null;
	}

}
