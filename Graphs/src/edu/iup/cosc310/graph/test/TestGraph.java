package edu.iup.cosc310.graph.test;

import java.util.Iterator;
import java.util.List;

import edu.iup.cosc310.graph.bo.ALGraph;
import edu.iup.cosc310.graph.bo.Costable;
import edu.iup.cosc310.graph.bo.Graph;
import edu.iup.cosc310.graph.io.EdgeReader;
import edu.iup.cosc310.graph.io.VertexReader;

/**
 * Test program to test given graph cases.
 * 
 * @author dtsmith
 * 
 * edited Eric Olechovski
 *
 */
public class TestGraph {
	private Graph<String, Integer> cities;

	public static void main(String[] args) {
		TestGraph tg = new TestGraph();

		tg.cities = new ALGraph<String, Integer>(false, true);
		
		tg.loadVerticies(args[0]);
		tg.loadEdges(args[1]);
		
		Costable<Integer> coster = new Costable<Integer>(){

			@Override
			public double getCost(Integer weight) {
				return weight;
			}
			
		};
		
		System.out.println("BreadthFirstSearch");
		List<String> cityPath = tg.cities.breadthFirstSearch("Philadelphia", "Detriot");
		printPath(cityPath);
		
		cityPath = tg.cities.breadthFirstSearch("Fort Wayne", "Toledo");
		printPath(cityPath);
		
	
		System.out.println("\n\n\nDepthFirstSearch");
		cityPath = tg.cities.depthFirstSearch("Philadelphia", "Detriot");
		printPath(cityPath);
		
		
		cityPath = tg.cities.depthFirstSearch("Fort Wayne", "Toledo");
		printPath(cityPath);
		
		
		System.out.println("\n\n\nBestSearch");
		cityPath = tg.cities.bestSearch("Philadelphia", "Detriot", coster);
		printPath(cityPath);
		
		
		cityPath = tg.cities.bestSearch("Fort Wayne", "Toledo", coster);
		printPath(cityPath);

	}
	
	private void loadEdges(String fileName) {
		EdgeReader er = new EdgeReader(fileName);
		String[] edge;
		while ((edge = er.readEdges()) != null) {
			String fromVertex = edge[0];
			String toVertex = edge[1];
			int weight = Integer.parseInt(edge[2]);
			cities.addEdge(fromVertex, toVertex, weight);
		}		
	}

	private void loadVerticies(String fileName) {
		VertexReader vr = new VertexReader(fileName);
		String vertex;
		while ((vertex = vr.readVerticies()) != null) {
			cities.addVertex(vertex);
		}		
	}

	public static void printPath(List<String> cityPath){
		if (cityPath == null) {
			System.out.println("No path found");
		}
		else {
			System.out.print("\nPath found:");
			for (String city : cityPath) {
				System.out.print(" " + city);
			}
		}
		
	}
}
