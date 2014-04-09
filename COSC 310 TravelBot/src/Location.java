import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    LocationFactory lf = new LocationFactory();
    public HashMap<String, ArrayList<PlaceLoc>> places = new HashMap<>();
    public HashMap<String, String> placesAddress = new HashMap<>();
    public String origin = "Kelowna BC Canada";
    public String destination;
    public double tempInCelcius;
    public String weatherDescription;
    public double distanceFromOrigin;

    public Location(String destination) {
        this.destination = destination;
        lf.build(this);
    }

    public Location(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
        lf.build(this);
    }

    public String estimateTravelCost() {
        return "Driving to " + this.destination + ", from " + this.origin + " would cost approximately $" + lf.round(this.distanceFromOrigin / 2.02, 2);
    }

    public String estimateFlightCost() {
        return "Flying to " + this.destination + ", from " + this.origin + " would cost approximately $" + lf.round(this.distanceFromOrigin / 2.02, 2);
    }

    //Direction API
    public String getDirections(String dir){
    	try{
    		if(placesAddress.containsKey(dir)){
    		
    			return lf.getDirections(this, placesAddress.get(dir));
    		}
    	}catch(NullPointerException e){
    		return "Tell me where you would like to go before I find you the directions.";
    	}
    	return "Tell me where you would like to go before I find you the directions.";
    }
    
    //places API
    public String getPlaces(String keyword) {
    	System.out.println("This is in the location.java: "+keyword);
    	  if (!places.containsKey(keyword)) {
              if (!lf.getPlaces(this, keyword))
                  return null;
          } 
    
            ArrayList<PlaceLoc> pl = places.get(keyword);
          for(PlaceLoc p : pl){
            	placesAddress.put(p.getName(), p.getAddress());
            }
          	int r = new java.util.Random().nextInt(pl.size());
            String name = pl.get(r).getName();
            System.out.println("name:"+name);

            return name;
         
            
          
    }
}