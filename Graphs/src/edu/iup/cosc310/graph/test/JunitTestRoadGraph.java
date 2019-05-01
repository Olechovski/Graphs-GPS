package edu.iup.cosc310.graph.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.iup.cosc310.graph.bo.ALGraph;
import edu.iup.cosc310.graph.bo.Costable;
import edu.iup.cosc310.graph.bo.Graph;
import edu.iup.cosc310.graph.bo.Road;
import edu.iup.cosc310.graph.io.EdgeReader;
import edu.iup.cosc310.graph.io.VertexReader;

public class JunitTestRoadGraph {
	private static Graph<String, Road> cities;
	private static Costable<Road> coster = new Costable<Road>(){
		@Override
		public double getCost(Road road) {
			double time = road.getDistance() / road.getspeedLimit();
			return time;
		}
		
	};
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cities = new ALGraph<String, Road>(false, true);

		VertexReader vr = new VertexReader("verticies.txt");
		String vertex;
		while ((vertex = vr.readVerticies()) != null) {
			cities.addVertex(vertex);
		}	

		EdgeReader er = new EdgeReader("roadEdge.txt");
		String[] edge;
		while ((edge = er.readEdges()) != null) {
			String fromVertex = edge[0];
			String toVertex = edge[1];
			double speedLimit = Double.parseDouble(edge[2]);
			double distance = Double.parseDouble(edge[3]);
			cities.addEdge(fromVertex, toVertex, new Road(speedLimit, distance));
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
		List<String> cityPath = cities.breadthFirstSearch("Philadelphia", "Detriot");
		assertEquals("Philadelphia Pittsburgh Detriot", printPath(cityPath));
		cityPath = cities.breadthFirstSearch("Fort Wayne", "Toledo");
		assertEquals("Fort Wayne Chicago Ann Arbor Toledo", printPath(cityPath));
		
		cityPath = cities.breadthFirstSearch("Philadelphia", "Philadelphia");
		assertEquals("Philadelphia", printPath(cityPath));
	}

	@Test
	public void testDepthFirstSearch() {
		System.out.println("\n\nDepth First Search");
		List<String> cityPath = cities.depthFirstSearch("Philadelphia", "Detriot");
		assertEquals("Philadelphia Pittsburgh Cleveland Columbus Indianapolis Chicago Ann Arbor Detriot", printPath(cityPath));
		
		cityPath = cities.depthFirstSearch("Fort Wayne", "Toledo");
		assertEquals("Fort Wayne Chicago Ann Arbor Detriot Pittsburgh Cleveland Columbus Toledo", printPath(cityPath));
		
		cityPath = cities.depthFirstSearch("Philadelphia", "Philadelphia");
		assertEquals("Philadelphia", printPath(cityPath));
	}

	@Test
	public void testBestSearch() {
		System.out.println("Best Search");
		List<String> cityPath = cities.bestSearch("Philadelphia", "Detriot", coster);
		assertEquals("Philadelphia Pittsburgh Cleveland Toledo Detriot", printPath(cityPath));
		
		cityPath = cities.bestSearch("Fort Wayne", "Toledo", coster);
		assertEquals("Fort Wayne Indianapolis Columbus Toledo", printPath(cityPath));
		
		cityPath = cities.bestSearch("Philadelphia", "Philadelphia", coster);
		assertEquals("Philadelphia", printPath(cityPath));
	}
}