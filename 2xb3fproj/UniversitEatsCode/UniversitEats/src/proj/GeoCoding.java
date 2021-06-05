package proj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class GeoCoding {
	
	public static double[] ForwardGeoCode(String address) throws IOException {
		
		//declaring lat/long values
        //need to be converted to doubles to return double array
        String latitude = "";
        String longitude = "";
		
        //Formatting the address for the http req
		address = address.replaceAll(" ","+");
		
		//formatted http request
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyBxoVvMPP2cVR69klAfxFJaHlGVhIme4Ws";
		
		//java URL type
		URL requesturl = new URL(url);
		
		//establishing connection
		HttpURLConnection connection = (HttpURLConnection) requesturl.openConnection();
		
		//get value
        connection.setRequestMethod("GET");
        
        
        
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while((line = rd.readLine()) != null) {
        	if (line.contains("\"location\" : {")) {
        		latitude = rd.readLine();
        		longitude = rd.readLine();
        		
        	}
        }
        rd.close();
        if (latitude.equals("") || longitude.equals("")) {
            double[] a = {100.0, 100.0};
            return a;
        }
        //formatting into double to return double array
        latitude = latitude.trim();
        longitude = longitude.trim();
        latitude = latitude.substring(8, latitude.length()-1);
        longitude = longitude.substring(8);
		
		double latitudenum = Double.parseDouble(latitude);
		double longitudenum = Double.parseDouble(longitude);
		
		double[] returnarray = {latitudenum, longitudenum};
		
		return returnarray;
	
		
	}

}
