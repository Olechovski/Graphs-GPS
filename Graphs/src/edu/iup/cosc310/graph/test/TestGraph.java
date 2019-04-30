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
		
		Graph<String, Integer> cities2 = new ALGraph<String, Integer>(false, true);
		cities2.addVertex("A");
		cities2.addVertex("B");
		cities2.addVertex("C");
		cities2.addVertex("D");
		cities2.addVertex("E");
		cities2.addVertex("F");
		cities2.addVertex("G");
		cities2.addEdge("A", "B", 1);
		cities2.addEdge("A", "C", 5);
		cities2.addEdge("B", "C", 2);
		cities2.addEdge("B", "D", 3);
		cities2.addEdge("B", "E", 1);
		cities2.addEdge("C", "F", 3);
		cities2.addEdge("C", "G", 2);
		
		
		System.out.println("depth");
		List<String> cityPat = cities2.bestSearch("A", "G", coster);
		printPath(cityPat);
		
		
//		for (Iterator<String> iter = cities.adjacentTo("Columbus"); iter.hasNext();) {
//			String city = iter.next();
//			Integer distance = cities.getWeight("Columbus", city);
//			System.out.printf("%s %d\n", city, distance);
//		}
		
		
//		for (Iterator<String> iter = cities.breadthFirstIterator("Columbus"); iter.hasNext();) {
//			String city = iter.next();
//			Integer distance = cities.getWeight("Columbus", city);
//			System.out.printf("%s %d\n", city, distance);
//		}
		
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
