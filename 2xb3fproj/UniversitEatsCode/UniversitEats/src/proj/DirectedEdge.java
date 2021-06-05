package proj;

public class DirectedEdge { 
    private final RestaurantT v;
    private final RestaurantT w;
    private final double weight;

    /**
     * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
     * the given {@code weight}.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws IllegalArgumentException if either v or w are null objects
     * @throws IllegalArgumentException if weight is negative
     */
    public DirectedEdge(RestaurantT v, RestaurantT w) {
        if (v == null) throw new IllegalArgumentException("Vertex names must be non-null objects");
        if (w == null) throw new IllegalArgumentException("Vertex names must be non-null objects");
        this.v = v;
        this.w = w;
        // calculate the distance via the haversine formula
        double earthRad = 6371.0;
        double lat1 = Math.toRadians(v.getLat());
        double lat2 = Math.toRadians(w.getLat());
        double deltaLat = lat2 - lat1;
        double deltaLon = Math.toRadians(w.getLon() - v.getLon());
        double a = Math.pow(Math.sin(deltaLat/2), 2.0) + Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin(deltaLon/2), 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        this.weight = earthRad * c;
        if (this.weight < 0) throw new IllegalArgumentException("Weight cannot be negative");
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public RestaurantT from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public RestaurantT to() {
        return w;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }
}
