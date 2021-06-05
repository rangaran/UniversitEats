package proj;

import java.util.ArrayList;

public class RestaurantT {
	private double lon; //longitude of restaurant
	private double lat; //latidtude of restaurant
	private String name; //name of restaurant
	private double stars; //# of stars restaurant recieved
	private int price;//price range of restaurant
	private int index;// index
	private ArrayList<String> type; //type of cuisine served at restaurant
		
	/**
     * Initializes RestaurantT object
     */
	public RestaurantT() {
		this.lat =0.0;
		this.lon = 0.0;
		this.name = "";
		this.stars = 0.0;
		this.price = 0;
		this.index = -1;
		this.type = new ArrayList<String>();
	}

	/**
     * Mutator method that sets value of the latitude of the object
     *
     * @param  la the latitude
	 */
	public void setLat(double la) {
		this.lat = la;
		
	}

	/**
     * Mutator method that sets index of the object
     *
     * @param  i the index
	 */
	public void setInd(int i) {
		this.index = i;
	}

	/**
     * Mutator method that sets the longitude of the object
     *
     * @param  lo the longitude of restauarant
	 */
	public void setLon(double lo) {
		this.lon = lo;
		
	}

	/**
     * Mutator method that sets the name of the object
     *
     * @param  n the name
	 */
	public void setName(String n) {
		this.name = n;
		
	}

	/**
     * Mutator method that sets price of the object
     *
     * @param  p the price
	 */
	public void setPrice(int p) {
		this.price = p;
		
	}

	/**
     * Mutator method that sets the stars of the object
     *
     * @param  s the number of stars
	 */
	public void setStars(double s) {
		this.stars = s;
		
	}

	/**
     * Mutator method that sets the type of the object
     *
     * @param  s an arraylist of types of cuisine
	 */
	public void setType(ArrayList<String> s) {
		this.type = s;
		
	}

	/**
     * Mutator method that adds to the type arraylist i
     *
     * @param  i a type of cusine or category
	 */
	public void addType(String s) {
		this.type.add(s);
		
	}


	/**
     * Getter method that returns index of object
     *
     * @return this.index A double representing the objects current index
	 */
	public int getInd() {
		return this.index;
	}

	/**
     * Getter method that returns latitude of object
     *
     * @return this.lat A double representing the objects current lat value
	 */
	public double getLat() {
		return this.lat;
	}
	
	/**
     * Getter method that returns longitude of object
     *
     * @return this.lon A double representing the objects current longitude value
	 */
	public double getLon() {
		return this.lon;
	}
	

	/**
     * Getter method that returns name of object
     *
     * @return this.name A string representing the name of the restaurant
	 */
	public String getName() {
		return this.name;
	}
	
	/**
     * Getter method that returns stars of the object.
     *
     * @return this.stars  A double representing the number of stars recieved by the object
	 */
	public double getStars() {
		return this.stars;
	}

	
	/**
     * Getter method that returns price range of the object.
     *
     * @return this.price An integer which represents range of  the object
	 */
	public int getPrice() {
		return this.price;
	}

	/**
     * Getter method that returns an arraylist of types of cuisine.
     *
     * @return this.type An arraylist of String types 
	 */
	public ArrayList<String> getType() {
		return this.type;
		
	}
}

