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
import edu.iup.cosc310.graph.util.GraphBuilder;

public class JunitTestDirectedGraph {
	private static Graph<String, Integer> cities;
	private static Costable<Integer> coster;
	private static GraphBuilder<Integer> gb;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gb = new GraphBuilder<Integer>(true, true, "verticies.txt", "edges.txt");
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
		List<String> cityPath = cities.breadthFirstSearch("Philadelphia", "Cleveland");
		assertEquals("Philadelphia Pittsburgh Cleveland", gb.printPath(cityPath));
		
		cityPath = cities.breadthFirstSearch("Cleveland", "Philadelphia");
		assertEquals("No Path Found", gb.printPath(cityPath));
		
	}

	@Test
	public void testDepthFirstSearch() {
		System.out.println("\n\nDepth First Search");
		List<String> cityPath = cities.depthFirstSearch("Philadelphia", "Cleveland");
		assertEquals("Philadelphia Pittsburgh Cleveland", gb.printPath(cityPath));
		
		cityPath = cities.depthFirstSearch("Cleveland", "Philadelphia");
		assertEquals("No Path Found", gb.printPath(cityPath));
	}

	@Test
	public void testBestSearch() {
		System.out.println("Best Search");
		assertEquals(RuntimeException.class,catchException(()-> {cities.bestSearch("Philadelphia", "Cleveland", coster);}));
	}
}
