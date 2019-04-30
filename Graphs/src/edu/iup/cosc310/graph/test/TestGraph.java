package edu.iup.cosc310.graph.test;

import java.util.Iterator;
import java.util.List;
import edu.iup.cosc310.graph.ALGraph;
import edu.iup.cosc310.graph.Costable;
import edu.iup.cosc310.graph.Graph;

/**
 * Test program to test given graph cases.
 * 
 * @author dtsmith
 * 
 * edited Eric Olechovski
 *
 */
public class TestGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph<String, Integer> cities = new ALGraph<String, Integer>(false, true);
		cities.addVertex("Chicago");
		cities.addVertex("Ann Arbor");
		cities.addVertex("Detriot");
		cities.addVertex("Toledo");
		cities.addVertex("Cleveland");
		cities.addVertex("Pittsburgh");
		cities.addVertex("Philadelphia");
		cities.addVertex("Columbus");
		cities.addVertex("Indianapolis");
		cities.addVertex("Fort Wayne");
		cities.addEdge("Chicago", "Ann Arbor", 260);
		cities.addEdge("Chicago", "Fort Wayne", 148);
		cities.addEdge("Chicago", "Indianapolis", 180);
		cities.addEdge("Indianapolis", "Fort Wayne", 120);
		cities.addEdge("Detriot", "Ann Arbor", 50);
		cities.addEdge("Toledo", "Ann Arbor", 40);
		cities.addEdge("Detriot", "Toledo", 60);
		cities.addEdge("Cleveland", "Toledo", 120);
		cities.addEdge("Columbus", "Toledo", 155);
		cities.addEdge("Indianapolis", "Columbus", 180);
		cities.addEdge("Cleveland", "Columbus", 150);
		cities.addEdge("Cleveland", "Pittsburgh", 130);
		cities.addEdge("Columbus", "Pittsburgh", 180);
		cities.addEdge("Philadelphia", "Pittsburgh", 180);
		cities.addEdge("Detriot", "Pittsburgh", 400);
		
		Costable<Integer> coster = new Costable<Integer>(){

			@Override
			public double getCost(Integer weight) {
				return weight;
			}
			
		};
		
		System.out.println("BreadthFirstSearch");
		List<String> cityPath = cities.breadthFirstSearch("Philadelphia", "Detriot");
		printPath(cityPath);
		
		cityPath = cities.breadthFirstSearch("Fort Wayne", "Toledo");
		printPath(cityPath);
		
	
		System.out.println("\n\n\nDepthFirstSearch");
		cityPath = cities.depthFirstSearch("Philadelphia", "Detriot");
		printPath(cityPath);
		
		
		cityPath = cities.depthFirstSearch("Fort Wayne", "Toledo");
		printPath(cityPath);
		
		
		System.out.println("\n\n\nBestSearch");
		cityPath = cities.bestSearch("Philadelphia", "Detriot", coster);
		printPath(cityPath);
		
		
		cityPath = cities.bestSearch("Fort Wayne", "Toledo", coster);
		printPath(cityPath);

	}
	
	/**
	 * Displays existing path
	 * 
	 * @param cityPath
	 */
	public static void printPath(List<String> cityPath){
		
		if (cityPath == null) {
			System.out.println("No path found");
		} else {
			System.out.print("\nPath found:");
			for (String city : cityPath) {
				System.out.print(" " + city);
				
			}
		}
		
	}
}
