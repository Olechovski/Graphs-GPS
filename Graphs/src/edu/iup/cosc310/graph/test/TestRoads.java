package edu.iup.cosc310.graph.test;

import java.util.List;

import edu.iup.cosc310.graph.bo.ALGraph;
import edu.iup.cosc310.graph.bo.Costable;
import edu.iup.cosc310.graph.bo.Graph;
import edu.iup.cosc310.graph.bo.Road;

/**
 * This is a test class for testing the 
 * Road object and finding the fastes route from
 * one city to another.
 * 
 * @author Eric Olechovski
 *
 */

public class TestRoads {

	public static void main(String[] args) {
		Graph<String, Road> cities = new ALGraph<String, Road>(false, true);
		
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
		cities.addEdge("Chicago", "Ann Arbor", new Road(25,260));
		cities.addEdge("Chicago", "Fort Wayne", new Road(80,148));
		cities.addEdge("Chicago", "Indianapolis", new Road(25,180));
		cities.addEdge("Indianapolis", "Fort Wayne", new Road(25,120));
		cities.addEdge("Detriot", "Ann Arbor", new Road(25,50));
		cities.addEdge("Toledo", "Ann Arbor", new Road(25,40));
		cities.addEdge("Detriot", "Toledo", new Road(25,60));
		cities.addEdge("Cleveland", "Toledo", new Road(25,120));
		cities.addEdge("Columbus", "Toledo", new Road(25,155));
		cities.addEdge("Indianapolis", "Columbus", new Road(80,180));
		cities.addEdge("Cleveland", "Columbus",new Road(80,150));
		cities.addEdge("Cleveland", "Pittsburgh", new Road(80,130));
		cities.addEdge("Columbus", "Pittsburgh", new Road(80,180));
		cities.addEdge("Philadelphia", "Pittsburgh", new Road(25,180));
		cities.addEdge("Detriot", "Pittsburgh", new Road(25,400));
		
		
		Costable<Road> coster = new Costable<Road>(){

			@Override
			public double getCost(Road road) {
				double time = road.getDistance() / road.getspeedLimit();
				return time;
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
