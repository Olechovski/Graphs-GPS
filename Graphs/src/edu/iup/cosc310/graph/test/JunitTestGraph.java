/**
 * 
 */
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

/**
 * @author Eric Olechovski
 *
 */
public class JunitTestGraph {

	private static Graph<String, Integer> cities;
	private static Costable<Integer> coster = new Costable<Integer>(){
		@Override
		public double getCost(Integer weight) {
			return weight;
		}

	};
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cities = new ALGraph<String, Integer>(false, true);


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
		assertEquals("Fort Wayne Chicago Ann Arbor Toledo", printPath(cityPath));
		
		cityPath = cities.bestSearch("Philadelphia", "Philadelphia", coster);
		assertEquals("Philadelphia", printPath(cityPath));
	}
}
