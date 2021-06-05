package proj;

import java.util.ArrayList;
import java.util.Stack;

public class DijkstraSP {
	private ArrayList<String> topList; // Final output of restaurants (name)
    private ArrayList<Double> topDist; // Final output of restaurants (distance)
    private double[] distTo;          // distTo[v] = distance of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private ArrayList<RestaurantT> restaurantSet; // Set of restaurant vertices

    /**
     * Computes a shortest-paths tree from the source vertex to every other
     * vertex in the edge-weighted digraph.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source location
     * @throws IllegalArgumentException if an edge weight is negative
     */
    public DijkstraSP(EdgeWeightedDigraph G, RestaurantT s) {
    	// Check for invalid weight values
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
        // Initialize fields
        restaurantSet = G.getRestaurants();
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
    	topList = new ArrayList<String>();
        topDist = new ArrayList<Double>();
        validateVertex(s);

        // Set init distance to infinity and 0
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s.getInd()] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s.getInd(), distTo[s.getInd()]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(restaurantSet.get(v)))
                relax(e);
        }


        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e) {
        RestaurantT v = e.from(), w = e.to();
        if (distTo[w.getInd()] > distTo[v.getInd()] + e.weight()) {
            distTo[w.getInd()] = distTo[v.getInd()] + e.weight();
            edgeTo[w.getInd()] = e;
            if (pq.contains(w.getInd())) pq.decreaseKey(w.getInd(), distTo[w.getInd()]);
            else                pq.insert(w.getInd(), distTo[w.getInd()]);
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex  s to vertex  v.
     * @param  v the destination vertex
     * @return the length of a shortest path from the source vertex  s to vertex  v;
     *          Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless  0 <= v < V
     */
    public double distTo(RestaurantT v) {
        validateVertex(v);
        return distTo[v.getInd()];
    }

    /**
     * Returns true if there is a path from the source vertex  s} to vertex  v.
     *
     * @param  v the destination vertex
     * @return  true} if there is a path from the source vertex
     *          s} to vertex  v;  false} otherwise
     * @throws IllegalArgumentException unless  0 <= v < v
     */
    public boolean hasPathTo(RestaurantT v) {
        validateVertex(v);
        return distTo[v.getInd()] < Double.POSITIVE_INFINITY;
    }

    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, RestaurantT s) {

        // check that edge weights are nonnegative
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s.getInd()] != 0.0 || edgeTo[s.getInd()] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s.getInd()) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(restaurantSet.get(v))) {
                RestaurantT w = e.to();
                if (distTo[v] + e.weight() < distTo[w.getInd()]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            RestaurantT v = e.from();
            if (restaurantSet.get(w) != e.to()) return false;
            if (distTo[v.getInd()] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(RestaurantT v) {
        int V = distTo.length;
        if (v.getInd() < 0 || v.getInd() >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    /**
     * Returns an output object with all the required data for the
     * final 20 restaurants with shortest distance from the source
     * 
     * @return Data of up to the top 20 restaurants closest to the source
     */
    public FinalOutput updateFinal(){
    	int options = 0;
    	int minimum = 0;
    	ArrayList<Integer> finIndexes = new ArrayList<Integer>(); 
    	while (options < 20 && options < distTo.length) {
    		for (int i = 0; i < distTo.length - 1; i++) {
    			if (!finIndexes.contains(i)) {
    				if (distTo[i] < distTo[minimum]) {
    					minimum = i;
    				}
    			}
    		}
    		finIndexes.add(minimum);
    		topList.add(restaurantSet.get(minimum).getName());
    		topDist.add(distTo[minimum]);
    		minimum = 0;
    		options++;
    	}
    	
		return new FinalOutput(topList, topDist);
    }
}