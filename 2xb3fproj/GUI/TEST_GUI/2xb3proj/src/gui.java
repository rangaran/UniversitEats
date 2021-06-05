import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import proj.*;
public class gui {
	//constructor to make the gui
	private JFrame frame;
	//what the user gets as the restaurant and the distance away from it
	private JLabel restaurant;
	private JLabel distance;
	//is used to give a different restaurant if the user rerolls
	private int accumulator;
	//The list of restaurants and the distance away associated with it.
	private ArrayList<String> finalrestaurant;
	private ArrayList<Double> finaldistance;
	private int checker = 0;
	//If the user improperly uses our GUI, we tell them what went wrong.
	private JLabel errormsg;
	//giving the user only a selected drop down menu of items to choose from for state and food choice.
	private JComboBox<String> foodchoice;
	private JComboBox<String> statein;
	//The latitude and longitude that will be passed into another module to calculate the nearest restaurant
	public double[] latlong;
	//The sentence to be passed into the function to find the latitude and longitude. The sentence holds the address, city, and state.
	public String sentence;
	//The inputted values the user gives for us to work with
	private JTextField city;
	private JTextField address;
	private String calcState;
	private String calcFood;
	private String calcCity;
	private String calcAddress;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 880, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//The action button that generates the restaurant and the associated distance from the user to said restaurant.
		JButton btnNewButton = new JButton("Generate A Restaurant");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//storing the inputted values
				calcFood = (String) foodchoice.getSelectedItem();
				calcState = (String) statein.getSelectedItem();
				calcCity = city.getText();
				calcAddress = address.getText();
				
				
				//If the user generates an actual restaurant, no longer need to tell them the previous error if there was one.
				errormsg.setText("");
				//The sentence to be passed into the function to find the latitude and longitude. The sentence holds the address, city, and state.
				sentence = calcAddress + ", " + calcCity + ", " + calcState;
				
				//getting latitude and longitude
				try {
					latlong = GeoCoding.ForwardGeoCode(sentence);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//empty input error
				if (Double.compare(latlong[0], 37.2268149) == 0 && Double.compare(latlong[1], -95.7048514) == 0) {
					errormsg.setText("You did not enter a working address!");
					return;
				}
				//incorrect input error
				if (Double.compare(latlong[0], 100.0) == 0 && Double.compare(latlong[1], 100.0) == 0) {
					errormsg.setText("You did not enter a working address!");
					return;
				}
				//things to pass into Main.main to get the final restaurant list
				String[] arr = new String[4];
				arr[0] = calcState;
				arr[1] = calcFood;
				arr[2] = Double.toString(latlong[0]);
				arr[3] = Double.toString(latlong[1]);
				
				try {
					FinalOutput output = Main.main(arr);
					finalrestaurant = output.names();
					finaldistance = output.dists();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (finalrestaurant == (null)) {
					errormsg.setText("There are no restaurants nearby with your specifications!");
					return;
				}
				//Giving the user the restaurant and distance to it calculated through the algorithms.
				restaurant.setText((String) finalrestaurant.get(0));
				distance.setText(String.valueOf(finaldistance.get(0))+ " km"); //distance.setText(Integer.toString(finaldistance[0]));
				//if the user rerolls, give another restaurant
				accumulator = 1;
				//ensures a restaurant has been given first
				checker = 1;
				//To aid testing. Resetting inputted values.
				foodchoice.setSelectedItem(null);
				statein.setSelectedItem(null);
				city.setText("");
				address.setText("");
			}
		});
		btnNewButton.setBounds(36, 474, 379, 160);
		frame.getContentPane().add(btnNewButton);
		
		//The soon to be outputted restaurant
		restaurant = new JLabel ("TBD");
		restaurant.setFont(new Font("Tahoma", Font.PLAIN, 17));
		restaurant.setBounds(230,355,360,53);
		frame.getContentPane().add(restaurant);
		
		//Text to show on GUI
		JLabel foodlbl = new JLabel("Food Preference:");
		foodlbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		foodlbl.setBounds(22, 162, 264, 119);
		frame.getContentPane().add(foodlbl);
		
		JLabel title = new JLabel("UniversitEATS TEST GUI");
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		title.setBounds(312, 42, 278, 66);
		frame.getContentPane().add(title);
		
		JLabel restaurantlbl = new JLabel("Restaurant:");
		restaurantlbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		restaurantlbl.setBounds(22, 361, 159, 39);
		frame.getContentPane().add(restaurantlbl);
		
		JLabel distancelbl = new JLabel("Distance:");
		distancelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		distancelbl.setBounds(22, 410, 148, 25);
		frame.getContentPane().add(distancelbl);
		
		//The soon to be outputted distance
		distance = new JLabel("TBD");
		distance.setFont(new Font("Tahoma", Font.PLAIN, 17));
		distance.setBounds(230, 410, 360, 25);
		frame.getContentPane().add(distance);
		
		//Reroll button
		JButton gonext = new JButton("Retry (Only Press If Already Generated A Restaurant You Did Not Like)");
		gonext.setFont(new Font("Tahoma", Font.PLAIN, 8));
		gonext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ensures another restaurant can be given if the first restaurant was not liked
				if (checker >= 1 && checker < 20) {
					checker += 1;
					restaurant.setText((String) finalrestaurant.get(accumulator));
					distance.setText(String.valueOf(finaldistance.get(accumulator))+ " km");
					accumulator += 1;
				}
				//There is nothing to give the user as they did not do the initial search
				else if (checker == 0){
					errormsg.setText("Stop! Make Sure you generated a restaurant first!");
				}
				//Rerolled too much, the list does not have enough restaurants.
				else if (checker >= 20) {
					errormsg.setText("You're being too indecisive! You are going to that restaurant!");
				}
			}
		});
		gonext.setBounds(464, 476, 366, 158);
		frame.getContentPane().add(gonext);
		
		//Tell the user what went wrong with the GUI.
		errormsg = new JLabel("");
		errormsg.setFont(new Font("Tahoma", Font.PLAIN, 20));
		errormsg.setBounds(36, 312, 794, 39);
		frame.getContentPane().add(errormsg);
		
		//Available food items to input
		foodchoice = new JComboBox<String>();
		foodchoice.addItem("Chinese");
		foodchoice.addItem("Indian");
		foodchoice.addItem("Mexican");
		foodchoice.addItem("Japanese");
		foodchoice.addItem("Seafood");
		foodchoice.addItem("Vietnamese");
		foodchoice.addItem("Sandwiches");
		foodchoice.addItem("Salad");
		foodchoice.addItem("Italian");
		foodchoice.addItem("ChickenWings");
		foodchoice.addItem("ComfortFood");
		foodchoice.addItem("Desserts");
		foodchoice.addItem("Korean");
		foodchoice.addItem("American(Traditional)");
		foodchoice.addItem("Canadian(New)");
		foodchoice.addItem("Pizza");
		foodchoice.addItem("Thai");
		foodchoice.addItem("Caribbean");
		foodchoice.addItem("MiddleEastern");
		foodchoice.addItem("Vegetarian");
		foodchoice.setSelectedItem(null);
		foodchoice.setBounds(230, 215, 420, 21);
		frame.getContentPane().add(foodchoice);
		
		//Text to show on GUI
		JLabel statelbl = new JLabel("State:");
		statelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		statelbl.setBounds(22, 165, 159, 39);
		frame.getContentPane().add(statelbl);
		
		//Available states to input
		statein = new JComboBox<String>();
		statein.addItem("AB");
		statein.addItem("AK");
		statein.addItem("AL");
		statein.addItem("AR");
		statein.addItem("AZ");
		statein.addItem("BAS");
		statein.addItem("BC");
		statein.addItem("CA");
		statein.addItem("CON");
		statein.addItem("CT");
		statein.addItem("DOW");
		statein.addItem("DUR");
		statein.addItem("FL");
		statein.addItem("GA");
		statein.addItem("IL");
		statein.addItem("NC");
		statein.addItem("NE");
		statein.addItem("NJ");
		statein.addItem("NM");
		statein.addItem("NV");
		statein.addItem("NY");
		statein.addItem("OH");
		statein.addItem("ON");
		statein.addItem("PA");
		statein.addItem("QC");
		statein.addItem("SC");
		statein.addItem("TN");
		statein.addItem("TX");
		statein.addItem("UT");
		statein.addItem("VA");
		statein.addItem("VT");
		statein.addItem("WA");
		statein.addItem("WI");
		statein.addItem("XGL");
		statein.addItem("XGM");
		statein.addItem("XWY");
		statein.setSelectedItem(null);
		statein.setBounds(230, 178, 420, 21);
		frame.getContentPane().add(statein);
		
		//Text to show on GUI
		JLabel addresslbl = new JLabel("Address:");
		addresslbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addresslbl.setBounds(22, 110, 159, 25);
		frame.getContentPane().add(addresslbl);
		
		JLabel citylbl = new JLabel("City:");
		citylbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		citylbl.setBounds(22, 140, 159, 25);
		frame.getContentPane().add(citylbl);
		
		//inputted city
		city = new JTextField();
		city.setBounds(230, 147, 420, 19);
		frame.getContentPane().add(city);
		city.setColumns(10);
		
		//inputted address
		address = new JTextField();
		address.setBounds(230, 117, 420, 19);
		frame.getContentPane().add(address);
		address.setColumns(10);
	}
}
