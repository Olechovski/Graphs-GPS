package edu.iup.cosc310.graph.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.iup.cosc310.graph.bo.ALGraph;
import edu.iup.cosc310.graph.bo.Costable;
import edu.iup.cosc310.graph.bo.Graph;
import edu.iup.cosc310.graph.io.EdgeReader;
import edu.iup.cosc310.graph.io.VertexReader;

public class JunitTestDirectedGraph {
	private static Graph<String, Integer> cities;
	private static Costable<Integer> coster = new Costable<Integer>(){
		@Override
		public double getCost(Integer weight) {
			return weight;
		}

	};
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cities = new ALGraph<String, Integer>(true, true);

		VertexReader vr = new VertexReader("verticies.txt");
		String vertex;
		while ((vertex = vr.readVerticies()) != null) {
			cities.addVertex(vertex);
		}	

		EdgeReader er = new EdgeReader("edges.txt");
		String[] edge;
		while ((edge = er.readEdges()) != null) {
			String fromVertex = edge[0];
			String toVertex = edge[1];
			int weight = Integer.parseInt(edge[2]);
			cities.addEdge(fromVertex, toVertex, weight);
		}					
	}
	
	public static String printPath(List<String> cityPath){
		String path = "";
		if (cityPath == null) {
			System.out.println("\nNo path found");
			path = "No Path Found";
		}
		else {
			System.out.print("\nPath found:");
			for (String city : cityPath) {
				System.out.print(" " + city);
				path = path + city + " ";
			}
		}
		return path.trim();
	}
	
	
	// Workaround to have JUnit 5 like tests for exceptions
	@FunctionalInterface
	interface Runner {
		public void run();
	}

	public static Class catchException(Runner runner) {
		try {
			runner.run();
			return null;
		} catch (Exception e) {
			return e.getClass();
		}
	}

	@Test
	public void testBreadthFirstSearch() {
		System.out.println("\n\nBreadth First Search");
		List<String> cityPath = cities.breadthFirstSearch("Philadelphia", "Cleveland");
		assertEquals("Philadelphia Pittsburgh Cleveland", printPath(cityPath));
		
		cityPath = cities.breadthFirstSearch("Cleveland", "Philadelphia");
		assertEquals("No Path Found", printPath(cityPath));
		
	}

	@Test
	public void testDepthFirstSearch() {
		System.out.println("\n\nDepth First Search");
		List<String> cityPath = cities.depthFirstSearch("Philadelphia", "Cleveland");
		assertEquals("Philadelphia Pittsburgh Cleveland", printPath(cityPath));
		
		cityPath = cities.depthFirstSearch("Cleveland", "Philadelphia");
		assertEquals("No Path Found", printPath(cityPath));
	}

	@Test
	public void testBestSearch() {
		System.out.println("Best Search");
		assertEquals(RuntimeException.class,catchException(()-> {cities.bestSearch("Philadelphia", "Cleveland", coster);}));
	}
}
