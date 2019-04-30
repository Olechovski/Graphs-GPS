package edu.iup.cosc310.graph.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * An adjacency list implementation for a generic graph.
 * 
 * @author dtsmith
 * 
 * Edited : Eric Olechovski
 *
 * @param <V> data type for the vertices of the graph
 * @param <W> data type for the weight of edges of a graph.  Use Boolean for an unweighted graph
 */
public class ALGraph<V, W> implements Graph<V, W> {
	private boolean directed;
	private boolean weighted;

	/**
	 * Inner class to represent a vertex.
	 * 
	 * Value attribute is the vertex as seen by the applications
	 * using the ALGraph. 
	 */
	private class Vertex {
		private V value;
		private Map<V, Edge> adjacencies = new TreeMap<V, Edge>();

		public Vertex(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public void addEdge(Vertex to, W weight) {
			adjacencies.put(to.value, new Edge(this, to, weight));
		}

		public Edge getEdge(V to) {
			return adjacencies.get(to);
		}

		public W getWeight(V to) {
			Edge edge = getEdge(to);

			if (edge == null) {
				return null;
			} else {
				return edge.weight;
			}
		}

		public Iterator<Edge> edges() {
			return adjacencies.values().iterator();
		}
	}

	/**
	 * Inner class to represent an edge.
	 * 
	 * Weight attribute is the weight of the edge as seen by the applications
	 * using the ALGraph. 
	 */
	private class Edge {
		private Vertex vertexFrom;
		private Vertex vertexTo;
		private W weight;

		public Edge(Vertex vertexFrom, Vertex vertexTo, W weight) {
			super();
			this.vertexFrom = vertexFrom;
			this.vertexTo = vertexTo;
			this.weight = weight;
		}

		public Vertex getVertexFrom() {
			return vertexFrom;
		}

		public Vertex getVertexTo() {
			return vertexTo;
		}

		public W getWeight() {
			return weight;
		}

		public void setWeight(W weight) {
			this.weight = weight;			
		}
	}

	private Map<V, Vertex> verticies = new HashMap<V, Vertex>();

	/**
	 * Constructor
	 * 
	 * @param directed - indicates if the graph is directed
	 */
	public ALGraph(boolean directed, boolean weighted) {
		super();
		this.directed = directed;
		this.weighted = weighted;
	}

	/**
	 * Returns true if the graph is directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * Returns true if the graph is directed
	 */
	public boolean isWeighted() {
		return weighted;
	}

	/**
	 * Add a vertex to the graph.  Value is the the value stored at the vertex. Values must be
	 * unique within the graph.
	 * 
	 * @param value the value stored at the vertex
	 */
	public void addVertex(V value) {
		verticies.put(value, new Vertex(value));
	}

	private Vertex getVertex(V value) {
		return verticies.get(value);
	}

	/**
	 * Add an unweighted edge between two vertices.  The values stored is each vertex is used to identify
	 * the vertices.  If the graph is undirected then two edges will be added; an edge from the to vertex
	 * to the from vertex will also be added.
	 * 
	 * @param from value of the from vertex
	 * @param to value of the to vertex
	 */
	public void addEdge(V from, V to) {
		if (isWeighted()) {
			throw new RuntimeException("Weight must be provided for a weighted graph");
		}

		addEdge(from, to, (W) new Boolean(true));
	}

	/**
	 * Add a weighted edge between two vertices.  The values stored is each vertex is used to identify
	 * the vertices.  If the graph is undirected then two edges will be added; an edge from the to vertex
	 * to the from vertex will also be added.
	 * 
	 * @param from value of the from vertex
	 * @param to value of the to vertex
	 * @param weight the weight stored on the edge
	 */
	public void addEdge(V from, V to, W weight) {
		if (!weighted && !(weight instanceof Boolean)) {
			throw new RuntimeException("Weight must be of type Boolean for unweighted graphs");
		}

		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		Vertex vertexTo = getVertex(to);

		if (vertexTo == null) {
			throw new RuntimeException("Invalid to vertex " + to);
		}

		Edge edge = this.getEdge(vertexFrom, vertexTo);

		if (edge != null) {
			edge.setWeight(weight);
			if (!isDirected()) {
				edge = this.getEdge(vertexTo, vertexFrom);
				edge.setWeight(weight);
			}
		} else {			
			vertexFrom.addEdge(vertexTo, weight);

			if (!isDirected()) {
				vertexTo.addEdge(vertexFrom, weight);
			}
		}
	}




	/**
	 * Inner class to iterate over the adjacency list of a vertex
	 *
	 */
	private class AdjacencyIterator implements Iterator<V> {
		private Iterator<Edge> edgeIterator;

		public AdjacencyIterator(Vertex from) {
			edgeIterator = from.adjacencies.values().iterator();
		}

		public boolean hasNext() {
			return edgeIterator.hasNext();
		}

		public V next() {
			Edge nextEdge = edgeIterator.next();
			return nextEdge.vertexTo.value;
		}

		public void remove() {
			throw new RuntimeException("Unsupported operation");
		}
	}

	/**
	 * Create an iterator of vertices that are adjacent to a given vertex.  The given vertex is 
	 * identified by the value of that vertex.  The iterator will return the value of the vertices
	 * for which there exists an edge from the from vertex to a to vertex.
	 * 
	 * @param from the value of the vertex from which to identify adjacent vertices
	 * @return an iterator of vertex values
	 */
	public Iterator<V> adjacentTo(V from) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		return new AdjacencyIterator(vertexFrom);
	}

	private Iterator<Edge> edges(V from) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		return vertexFrom.edges();
	}

	/**
	 * Get the weight of an edge between two vertices.  The vertices are identified by their values.
	 * Returns null if an edge does not exist between the vertices.
	 * 
	 * @param from value of the from vertex
	 * @param to value of the to vertex
	 * @return
	 */
	public W getWeight(V from, V to) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			return null;
		}

		return vertexFrom.getWeight(to);
	}

	private Edge getEdge(Vertex vertexFrom, Vertex to) {
		if (vertexFrom == null) {
			return null;
		}

		return vertexFrom.getEdge(to.value);
	}


	@Override
	public Iterator<V> breadthFirstIterator(V start) {
		return new BreathIterator(start);
	}


	private class BreathIterator implements Iterator<V>{

		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		Set<Vertex> visited = new HashSet<Vertex>();

		public BreathIterator(V start){
			Vertex v = getVertex(start);

			if (v == null) {
				throw new RuntimeException("Invalid from vertex " + start);
			}


			queue.add(v);
			visited.add(v);
		}
		@Override
		public boolean hasNext() {

			return !queue.isEmpty();
		}

		@Override
		public V next() {

			Vertex v =  queue.removeFirst();
			for (Iterator<Edge> edges = v.adjacencies.values().iterator(); edges.hasNext();){
				Edge edge = edges.next();
				if (!visited.contains(edge.vertexTo)){
					queue.add(edge.vertexTo);
					visited.add(edge.vertexTo);
				}

			}
			return v.value;
		}

	}

	/**
	 * Return a list containing the path found between two vertices using a breath first search
	 * @param start - starting vertex
	 * @param end - ending vertex
	 * @return list containing the found path
	 */
	public List<V> breadthFirstSearch(V start, V end) {
		ArrayList<V> path = new ArrayList<V>();
		LinkedList<ArrayList<V>> queue = new LinkedList<ArrayList<V>>();
		Set<V> visited = new HashSet<V>();

		Vertex v = getVertex(start);

		if (v == null) {
			throw new RuntimeException("Invalid from vertex " + start);
		}

		path.add(v.value);

		queue.add(path);
		visited.add(v.value);

		while (!queue.isEmpty()){
			path = queue.removeFirst();
			for (Iterator<V> iter = adjacentTo(path.get(path.size()-1)); iter.hasNext();){
				V value = iter.next();
				if (!visited.contains(value)){
					ArrayList<V> newPath = (ArrayList<V>) path.clone();
					newPath.add(value);
					if (value.equals(end)){
						return newPath;
					}
					queue.add(newPath);
					visited.add(value);
				}
			}
		}

		return null;
	}

	/**
	 * Return a list containing the path found between two vertices using a depth first search
	 * @param start - starting vertex
	 * @param end - ending vertex
	 * @return list containing the found path
	 */

	public List<V> depthFirstSearch(V start, V end) {
		ArrayList<V> path = new ArrayList<V>();
		Set<V> visited = new HashSet<V>();

		visited.add(start);
		path.add(start);

		if (start.equals(end)){
			return path;
		}

		return depthFirstSearch (path, visited, end);



	}

	private List<V> depthFirstSearch(ArrayList<V> path, Set<V> visited, V end){
		for (Iterator<V> iter = adjacentTo(path.get(path.size()-1)); iter.hasNext();){
			V value = iter.next();
			if (!visited.contains(value)){
				ArrayList<V> newPath = (ArrayList<V>) path.clone();
				newPath.add(value);
				visited.add(value);
				if (value.equals(end)){
					return newPath;
				}
				else{
					List<V> foundPath = depthFirstSearch(newPath, visited, end);
					if (foundPath != null) {
						return foundPath;
					}
				}
			}



		}


		return null;

	}




	/**
	 * Return a list containing the path found between two vertices using a best first search. 
	 * A Costable is used to get the cost of an edge
	 * @param start - starting vertex
	 * @param end - ending vertex
	 * @param coster - costable used to the cost of an edge
	 * @return list containing the found path
	 */

	public List<V> bestSearch(V start, V end, Costable<W> coster) {

		Set<V> vms = new HashSet<V>();
		Map<V, Double> dist = new HashMap<V,Double>(); 
		Map<V, ArrayList<V>> paths = new HashMap<V, ArrayList<V>>();

		// assigns max value to every vertex as well as adding each to the vms(visited map set)
		for (Iterator<V> iter = verticies.keySet().iterator(); iter.hasNext();){
			V vertex = iter.next();
			dist.put(vertex, Double.MAX_VALUE);
			vms.add(vertex);
		}
		// initialize the start to have smallest cost(/weight)
		dist.put(start, 0.0);

		ArrayList<V> path = new ArrayList<V>();
		path.add(start);
		paths.put(start, path);


		while (!vms.isEmpty()){
			Double smallest = Double.MAX_VALUE;
			V smallestVertex = null;

			// loop to find in vms vertex with smallest known distance -> smallest
			for (Iterator<V> iter = vms.iterator(); iter.hasNext();){
				V vertex = iter.next();
				if(smallest > dist.get(vertex)){
					smallest = dist.get(vertex);
					smallestVertex = vertex;
				}
			}

			// remove smallest from vms
			vms.remove(smallestVertex);

			// loop over adj to find smallest edge(weight)
			for (Iterator<V> iter = adjacentTo(smallestVertex); iter.hasNext();){
				V vertex = iter.next();
				// the cost(distance) between two smallestVertex and vertex
				Double cost = coster.getCost(getWeight(smallestVertex, vertex));
				// the current distance to get to vertex
				Double adjDist = dist.get(vertex);

				// if (dist to adj not known or dist to smallest + edge smallest to adj < dist to adj)
				if(dist.get(smallestVertex) + cost < adjDist){
					dist.put(vertex, dist.get(smallestVertex) + cost);
					ArrayList<V> newPath = (ArrayList<V>) (paths.get(smallestVertex).clone());
					newPath.add(vertex);
					paths.put(vertex, newPath);
				}

			}


		}
		return paths.get(end);

	}



}
