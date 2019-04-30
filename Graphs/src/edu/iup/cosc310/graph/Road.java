package edu.iup.cosc310.graph;

/**
 * Road classs is used to define a 
 * road with speedLimit and distance.
 * 
 * 
 * @author Eric Olechovski	
 *
 */
public class Road {
	
	private double speedLimit;
	private double distance;

	
	public Road(double speedLimit, double distance) {
		super();
		this.speedLimit = speedLimit;
		this.distance = distance;
	}
	public double getspeedLimit() {
		return speedLimit;
	}
	public double getDistance() {
		return distance;
	}
	
	
	
	

}
