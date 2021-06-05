package proj;

import java.io.IOException;
import java.util.ArrayList;

public class priceratesort {

	private ArrayList<RestaurantT> r;
	private ArrayList<RestaurantT> finalq;

	/**
	 * Computes a reduced subset using price and rating standards
	 *
	 * @param state   containing the state of the user
	 * @param cuisine which is the cuisine choice of the user
	 * @throws IOException If the parsing using the state runs into any issue
	 */
	public void parsing(String state, String cuisine) throws IOException {
		r = new ArrayList<RestaurantT>();
		finalq = new ArrayList<RestaurantT>();
		parseResto q = new parseResto();
		q.parse(state);
		double t;
		// Price sort
		ArrayList<RestaurantT> x = q.cuisine(cuisine);
		for (int i = 0; i < x.size(); i++) {
			t = x.get(i).getPrice();
			if (t == 1 || t == 2) {
				// Cheap restaurants with price range 1-2
				r.add(x.get(i));

			}
		}

		// Rating sort
		double rr;

		for (int i = 0; i < r.size(); i++) {
			rr = r.get(i).getStars();
			if (rr >= 4 && rr <= 5) {
				// Cheap restaurants with high rating
				finalq.add(r.get(i));

			}
		}

		if (finalq.size() < 20) {
			r = new ArrayList<RestaurantT>();
			finalq = new ArrayList<RestaurantT>();

			// Price sort with higher prices but high rating
			for (int i = 0; i < x.size(); i++) {
				t = x.get(i).getPrice();
				if (t == 3 || t == 4) {

					r.add(x.get(i));

				}
			}

			// Rating sort with higher prices but high rating

			for (int i = 0; i < r.size(); i++) {
				rr = r.get(i).getStars();
				if (rr >= 4 && rr <= 5) {

					finalq.add(r.get(i));

				}
			}

		}

		if (finalq.size() < 20) {
			r = new ArrayList<RestaurantT>();
			finalq = new ArrayList<RestaurantT>();
			// Price sort
			for (int i = 0; i < x.size(); i++) {
				t = x.get(i).getPrice();
				if (t == 3 || t == 4) {
					// Expensive restaurants
					r.add(x.get(i));

				}
			}

			// Rating sort

			for (int i = 0; i < r.size(); i++) {
				rr = r.get(i).getStars();
				if (rr < 4) {
					// Expensive restaurants with low rating
					finalq.add(r.get(i));

				}
			}

		}

	}

	/**
	 * Getter function for the reduced subset
	 * 
	 * @return the final subset with respect to price and rating
	 */
	public ArrayList<RestaurantT> getsubset() {

		// If the cuisine subset is very small then the subset used will always
		// have size less than 20
		if (finalq.size() < 20)
			return null;
		return finalq;
	}

}
