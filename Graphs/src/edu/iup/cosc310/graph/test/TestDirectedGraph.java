package edu.iup.cosc310.graph.test;

import java.util.List;

import edu.iup.cosc310.graph.bo.ALGraph;
import edu.iup.cosc310.graph.bo.Costable;
import edu.iup.cosc310.graph.bo.Graph;

/**
 * This is a test class to test the paths
 * of a directed graph.
 * 
 * 
 * @author Eric Olechovski
 *
 */
public class TestDirectedGraph {

	public static void main(String[] args) {
		// directed and weighted graph
		Graph<Integer, Integer> numVertex = new ALGraph<Integer, Integer>(true, true);
		
		numVertex.addVertex(0);
		numVertex.addVertex(1);
		numVertex.addVertex(2);
		numVertex.addVertex(3);
		numVertex.addVertex(4);
		numVertex.addEdge(0, 1, 10);
		numVertex.addEdge(0, 3, 30);
		numVertex.addEdge(0, 4, 40);
		numVertex.addEdge(1, 2, 50);
		numVertex.addEdge(2, 4, 10);
		numVertex.addEdge(3, 2, 20);
		numVertex.addEdge(3, 4, 60);
		
		Costable<Integer> coster = new Costable<Integer>(){

			@Override
			public double getCost(Integer weight) {
				return weight;
			}
			
		};
		
		
		System.out.println("BreadthFirstSearch");
		List<Integer> numPath = numVertex.breadthFirstSearch(0, 4);
		printPath(numPath);
		
		numPath = numVertex.breadthFirstSearch(1, 0);
		printPath(numPath);
		
	
		System.out.println("\n\n\nDepthFirstSearch");
		numPath = numVertex.depthFirstSearch(0, 4);
		printPath(numPath);
		
		
		numPath = numVertex.depthFirstSearch(1, 0);
		printPath(numPath);
		
		
		System.out.println("\n\n\nBestSearch");
		numPath = numVertex.bestSearch(0, 4, coster);
		printPath(numPath);
		
		
		try{
			numPath = numVertex.bestSearch(1, 0, coster);
			printPath(numPath);
		}
		catch(Exception e){
			System.out.println("\nThe path from verticies 1 to 0 does not exist");
		}
		
		// Since graph is directional add edge from 1 to 0
		numVertex.addEdge(1, 0, 10);
		numPath = numVertex.bestSearch(1, 0, coster);
		printPath(numPath);
		
		

	}
	
	/**
	 * Displays existing path
	 * 
	 * @param numPath
	 */
	public static void printPath(List<Integer> numPath){
		
		if (numPath == null) {
			System.out.println("\nNo path found");
		} else {
			System.out.print("\nPath found:");
			for (Integer num : numPath) {
				System.out.print(" " + num);
				
			}
		}
		
	}

}
