import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocationFactory {
    public void build(Location loc) {
        setWeather(loc);
        setDistance(loc);
        getPlaces(loc, "food");
        getPlaces(loc, "lodging");
    }

    /**
     * @param args
     * @throws IOException
     * @throws MalformedURLException
     */
    /*
     * Return values could be to any decimal place, we only want two.
	 */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private static boolean setDistance(Location loc) {
		/*
		 * Base URL for distance query. Takes an origin and destination parameter to constructing the String 'url'.
		 * URLEncoder is used to deal with spaces and such.
		 */
        String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + URLEncoder.encode(loc.origin) + "&destinations=" + URLEncoder.encode(loc.destination) + "&mode=driving&sensor=false&language=en-EN";
		
       // System.out.println(url);
		/*
		 *  We call the openStream() method from the URL class, and read the input line by line with the Scanner class.
		 *  On error return 0
		 */
        try {
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";
            }
		 /*
		  *  org.Json library. A JSON object is created from the above String.
		  *  
		  *  Example of the JSON object return can be seen by navigating to the following URL.
		  *  http://maps.googleapis.com/maps/api/distancematrix/json?origins=Kelowna%2C+BC&destinations=Vernon%2C+BC&mode=driving&sensor=false&language=en-EN
		  */
            JSONObject json = new JSONObject(str);
            if (json.getString("status").equalsIgnoreCase("ok")) {
                if (json.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getString("status").equalsIgnoreCase("ZERO_RESULTS")) {
                    return false;
                }
                JSONObject j1 = json.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
                JSONObject distance = j1.getJSONObject("distance");
                double distanceInMeters = distance.getDouble("value");
			/*
			 * Convert to KM before returning.
			 */
                loc.distanceFromOrigin = round((distanceInMeters / 1000), 2);
                return true;
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    /**
     * Had to comment out since it fails when the API has no information for the location.
     *
     * @param s
     * @return
     * @throws IOException
     */
    private static boolean setWeather(Location loc) {
		/*
		 * Construct URL from parameters, open the stream, read it, and create a JSON object from it.
		 */
        if (loc.destination == null) return false;
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(loc.destination);

        try {
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";
            }
		 /* Navigate JSON to find temperature and description.
		  * 
		  * Sample URL
		  * http://api.openweathermap.org/data/2.5/weather?q=kelowna,bc
		  */
            JSONObject json = new JSONObject(str);
            double tempInKelvin = json.getJSONObject("main").getDouble("temp");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            loc.tempInCelcius = round((tempInKelvin - 273.15), 2);
            loc.weatherDescription = description;
            return true;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
	 
		/*
		 * Return 0 in error case;
		 */
        return false;
    }
   
    //geocoding API
    private static double[] geocode(String s) throws IOException {
        String geocodeUrl = "http://maps.googleapis.com/maps/api/geocode/json?address=";
        geocodeUrl += URLEncoder.encode(s) + "&sensor=true";
        Scanner scan = new Scanner(new URL(geocodeUrl).openStream());
        String str = new String();
        while (scan.hasNext()) {
            str += scan.nextLine() + "\n";

        }
        JSONObject jsonObject = new JSONObject(str);
        if (jsonObject.getString("status").equals("OK")) {
            //geocode address.
            JSONArray routes = jsonObject.getJSONArray("results");
            JSONObject geometry = routes.getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            double[] loc = {lat, lng};
            return loc;
        }
        return new double[]{0, 0};
    }
       
    //directions API
    public static String getDirections(Location loc, String dest){
    	String directions = "";
    	try {
    		double[] geoOrigin = geocode(loc.origin);
    		double[] geoDest = geocode(dest);
    		
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
            geoOrigin[0] +","+ geoOrigin[1] + "&destination=" + geoDest[0] + "," + geoDest[1] +
            "&sensor=false&key=AIzaSyB8uxek_r9kgGZvM4pJOI20R04Y8RsLxj0";
          
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";
            }
            scan.close();
            
            JSONObject json = new JSONObject(str);

            if (json.getString("status").equalsIgnoreCase("ok")) {
            	
            	JSONArray route = json.getJSONArray("routes");
            	JSONArray legs = route.getJSONObject(0).getJSONArray("legs");
            	JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
            	
            	JSONObject direc;
            	
            	JSONObject dist;
            	
            	int index = 0;
            	while (!steps.isNull(index) && index < steps.length()) {
                    direc = steps.getJSONObject(index);	//  directions
                    dist = direc.getJSONObject("distance");// distance
                    
                    String step = direc.get("html_instructions") + " for "+ dist.get("text") +".\n"; 
                    
                    step = step.replaceAll("\\<[^>]*>","");  
                    directions += step; 
                    index++;
                }
                directions += "Your destination is at " + dest + ".\n";
                directions += "Total distance is " +legs.getJSONObject(0).getJSONObject("distance").getString("text") +
                		" and will take "+ legs.getJSONObject(0).getJSONObject("duration").getString("text") + " to drive there.";
                return directions;
            }
    	} catch (IOException e) {
            return null;
        } catch (NullPointerException e){
        	return null;
        }
    	return null;
    }
    
    
    
    //places API
    public static boolean getPlaces(Location loc, String keyword) {
        ArrayList toReturn = new ArrayList();

        try {
            double[] geo = geocode(loc.destination);
            //System.out.println(geo[0]+","+geo[1]);
  
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                    "location=" + geo[0] + "," + geo[1] + "&types=" + keyword +
                    "&radius=6000&sensor=false&key=AIzaSyD-GnR8Af9fm57GuOz9kdLTzjPMjfPeXiQ";
   
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";

            }
            scan.close();
            
            JSONObject json = new JSONObject(str);
            if (json.getString("status").equalsIgnoreCase("ok")) {
                JSONArray j = json.getJSONArray("results");
                int index = 0;
                JSONObject tmp;
                while (!j.isNull(index) && index < 4) {
                    tmp = j.getJSONObject(index);
                    String name = tmp.getString("name");
                    String address = tmp.getString("vicinity");
                    PlaceLoc place = new PlaceLoc(name,address);                   
                    
                    toReturn.add(place);// + ", " + tmp.getString("vicinity")); Trimmed this off to just get place names.
                    
                    index++;
                }
                
                loc.places.put(keyword, toReturn);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
  
}