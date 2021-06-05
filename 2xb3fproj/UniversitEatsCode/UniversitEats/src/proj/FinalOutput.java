package proj;

import java.util.ArrayList;

/**
 * Class to represent the final output; consist of List of Names and corresponding distances
 *
 */
public class FinalOutput {
	private static ArrayList<String> finNames;
	private static ArrayList<Double> finDistances;
	
	public FinalOutput(ArrayList<String> names, ArrayList<Double> dists) {
		finNames = new ArrayList<String>();
		finDistances = new ArrayList<Double>();

		finNames = names;
		finDistances = dists;
	}
	
	public FinalOutput() {
		finNames = new ArrayList<String>();
		finDistances = new ArrayList<Double>();
	}
	
    /**
     * Returns the top 20 restaurants with shortest distance from the source
     * 
     * @return Names of up to the top 20 restaurants closest to the source
     */
	public ArrayList<String> names() {
		return finNames;
	}
    /**
     * Returns the top 20 restaurant distances closes to the source
     * 
     * @return Distances of up to the top 20 restaurants closest to the source
     */
	public ArrayList<Double> dists() {
		return finDistances;
	}

}
