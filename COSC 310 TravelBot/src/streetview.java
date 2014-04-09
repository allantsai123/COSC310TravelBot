import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;


public class streetview {

	//Street view API
    //http://maps.googleapis.com/maps/api/streetview?size=400x400&location=40.720032,-73.988354&fov=90&heading=235&pitch=10&sensor=false
	    public static void main(String[] args) throws IOException {
	        display("Centro, Mexico");
	        
	    }
	
	
	
    public static void display(String dest) throws IOException{
    	
    
    	double[] geoDest = geocode(dest);
    	URL url =null;
    	
		try {
			geoDest = geocode(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Image image = null;
    	url = new URL("http://maps.googleapis.com/maps/api/streetview?size=640x640&location="+ geoDest[0] + "," + geoDest[1] +"&fov=110&heading=235&pitch=10&sensor=false");
    	image = ImageIO.read(url);
    	
    	JFrame frame = new JFrame();
    	frame.setSize(640,640);
    	JLabel label = new JLabel(new ImageIcon(image));
    	frame.add(label);
    	frame.setVisible(true);
    	
    }
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
}
