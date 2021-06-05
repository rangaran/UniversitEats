package proj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class to represent digraph of restaurant nodes
 */
public class EdgeWeightedDigraph {
    private final int V;                // number of vertices in digraph
    private int E;                      // number of edges in digraph
    private HashMap<RestaurantT, ArrayList<DirectedEdge>> adj;    // adjacency list for vertex v
    private ArrayList<RestaurantT> restaurants; // Set of restaurant vertices
    
    /**
     * Initializes Graph given a set of restaurant vertices
     *
     * @param  restNodes the set of vertices
     * @throws IllegalArgumentException if size of restNodes < 0
     */
    public EdgeWeightedDigraph(ArrayList<RestaurantT> restNodes) {
        if (restNodes == null) throw new IllegalArgumentException("argument is null");
        try {
        	this.restaurants = restNodes;
            if (restNodes.size() < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            this.V = restNodes.size();
            this.adj = new HashMap<RestaurantT, ArrayList<DirectedEdge>>();
            for (RestaurantT v : restaurants) {
                this.adj.put(v, new ArrayList<DirectedEdge>());
            }
            this.E = 0;
        }   
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(RestaurantT v) {
        if (!restaurants.contains(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between 0
     *         and V-1
     */
    public void addEdge(DirectedEdge e) {
        RestaurantT v = e.from();
        RestaurantT w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj.get(v).add(e);
        E++;
    }


    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> adj(RestaurantT v) {
        validateVertex(v);
        return adj.get(v);
    }


    /**
     * Returns all directed edges in this edge-weighted digraph.
     *
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
        ArrayList<DirectedEdge> list = new ArrayList<DirectedEdge>();
        for (RestaurantT v : adj.keySet()) {
            for (DirectedEdge e : adj.get(v)) {
                list.add(e);
            }
        }
        return list;
    } 
    
    /**
     * Returns the list of restaurant vertices in the digraph
     * @return all restaurant vertices
     */
    public ArrayList<RestaurantT> getRestaurants() {
    	return this.restaurants;
    }


}
