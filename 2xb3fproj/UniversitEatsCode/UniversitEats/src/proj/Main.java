package proj;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static FinalOutput main(String[] args) throws IOException {


        priceratesort q = new priceratesort();
        q.parsing(args[0], args[1]); // STATE/PROVINCE, CUISINE TYPE
        ArrayList<RestaurantT> restaurants = new ArrayList<RestaurantT>();
        restaurants = q.getsubset();
        if(restaurants == null)
            return null;
        for (RestaurantT r : restaurants) {
        	r.setInd(restaurants.indexOf(r));
        }
        RestaurantT startNode = new RestaurantT();
        startNode.setLat(Double.parseDouble(args[2])); // SET START LATITUDE
        startNode.setLon(Double.parseDouble(args[3])); // SET START LONGITUDE
        startNode.setName("START POSITION");  // SET DEFAULT NAME
        restaurants.add(startNode); // ADD TO THE LIST OF RESTAURANT NODES
        startNode.setInd(restaurants.size() - 1); // SET THE INDEX TO BE THE LAST OF THE LIST
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(restaurants); // CREATE THE GRAPH
        
        for (int i = 0; i < restaurants.size() - 1; i++) {
            DirectedEdge e = new DirectedEdge(startNode, restaurants.get(i));
            graph.addEdge(e);
        }
        
        DijkstraSP sp = new DijkstraSP(graph, restaurants.get(restaurants.size()-1));

        FinalOutput finOut = new FinalOutput();
        finOut = sp.updateFinal();
        
        return finOut;

    }
}