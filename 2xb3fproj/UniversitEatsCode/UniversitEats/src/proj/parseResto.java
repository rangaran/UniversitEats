package proj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;

public class parseResto {
	private static List<RestaurantT> list;
	
	public void parse(String state) throws IOException {
		list = new ArrayList <RestaurantT>();
		FileInputStream input = null;
		Scanner scan = null;
		try {
		    input = new FileInputStream("business.csv");
		    scan = new Scanner(input, "UTF-8");
		    scan.nextLine();
		while (scan.hasNextLine()) {
			
		    String line = scan.nextLine();
		    String [] p = line.split(",", -1);
		
		    if (!p[1].equals("")&& !p[1].equals("None")&& 
		    		!p[1].equals("0") && p[5].equals(state)) {
		        	
		        	RestaurantT obj = new RestaurantT();
		        	ArrayList<String> categories = new ArrayList<String>();
		        	for (int i =9 ; i <p.length; i++) {
		        		p[i]=p[i].replace("\"", "");
		        		p[i]=p[i].replace(" ", "");
		        		categories.add(p[i]);
		        	}
		        	
		        	if(categories.contains("Restaurants")) {
		        		obj.setLat(Double.parseDouble(p[2]));
		        		obj.setLon(Double.parseDouble(p[3]));
		        		obj.setName(p[8]);
		        		obj.setStars(Double.parseDouble(p[7]));
		        		obj.setPrice(Integer.parseInt(p[1]));
		        		obj.setType(categories);
		        		
		        		list.add(obj);
		        	}
		        }
		    }
		    
		} finally {
			
		    if (input != null) {
		        input.close();
		    }
		    
		    if (scan != null) {
		    	
		        scan.close();
		    }
		     
		}
		}
		
		public ArrayList<RestaurantT> cuisine(String cu){
			ArrayList<RestaurantT> finalList = new ArrayList<RestaurantT>();
			int totalS = list.size();
			for(int i = 0; i < totalS;  i++) {
				if (list.get(i).getType().contains(cu)) {
					finalList.add(list.get(i));
				}
				
			}
			return finalList;
		}
		
}
