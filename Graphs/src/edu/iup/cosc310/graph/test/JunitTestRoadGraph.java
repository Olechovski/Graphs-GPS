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
import edu.iup.cosc310.graph.util.GraphBuilder;

public class JunitTestRoadGraph {
		private static Graph<String, Road> cities;
		private static Costable<Road> coster;
		private static GraphBuilder<Road> gb;
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			gb = new GraphBuilder<Road>(false, true, "verticies.txt", "roadEdge.txt");
			cities = gb.getGraph();
			coster = gb.getCoster();
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
		assertEquals("Philadelphia Pittsburgh Detriot", gb.printPath(cityPath));
		cityPath = cities.breadthFirstSearch("Fort Wayne", "Toledo");
		assertEquals("Fort Wayne Chicago Ann Arbor Toledo", gb.printPath(cityPath));
		
		cityPath = cities.breadthFirstSearch("Philadelphia", "Philadelphia");
		assertEquals("Philadelphia", gb.printPath(cityPath));
	}

	@Test
	public void testDepthFirstSearch() {
		System.out.println("\n\nDepth First Search");
		List<String> cityPath = cities.depthFirstSearch("Philadelphia", "Detriot");
		assertEquals("Philadelphia Pittsburgh Cleveland Columbus Indianapolis Chicago Ann Arbor Detriot", gb.printPath(cityPath));
		
		cityPath = cities.depthFirstSearch("Fort Wayne", "Toledo");
		assertEquals("Fort Wayne Chicago Ann Arbor Detriot Pittsburgh Cleveland Columbus Toledo", gb.printPath(cityPath));
		
		cityPath = cities.depthFirstSearch("Philadelphia", "Philadelphia");
		assertEquals("Philadelphia", gb.printPath(cityPath));
	}

	@Test
	public void testBestSearch() {
		System.out.println("Best Search");
		List<String> cityPath = cities.bestSearch("Philadelphia", "Detriot", coster);
		assertEquals("Philadelphia Pittsburgh Cleveland Toledo Detriot", gb.printPath(cityPath));
		
		cityPath = cities.bestSearch("Fort Wayne", "Toledo", coster);
		assertEquals("Fort Wayne Indianapolis Columbus Toledo", gb.printPath(cityPath));
		
		cityPath = cities.bestSearch("Philadelphia", "Philadelphia", coster);
		assertEquals("Philadelphia", gb.printPath(cityPath));
	}
}