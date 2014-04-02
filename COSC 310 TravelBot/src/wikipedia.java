import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;


public class wikipedia {	
	public static String getInfo(String place){

		if(place.equals("Cancun")){
			place = "Cancún";
		}else if (place.equals("Culiacan")){
			place = "Culiacán";
		}else if(place.equals("Leon")){
			place = "León,_Guanajuato";
		}

		String url = "http://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exlimit=10&exintro=&explaintext=&titles="+place;

		try {
			Scanner scan = new Scanner(new URL(url).openStream());
			String str = new String();
			while (scan.hasNext()) {
				str += scan.nextLine() + "\n";
			}
			scan.close();

	        JSONObject json = new JSONObject(str);
	        JSONObject query = json.getJSONObject("query");
		    JSONObject pages = query.getJSONObject("pages");
		    JSONObject nestedObject = null;

		    @SuppressWarnings("static-access")
		    String[] keys = pages.getNames(pages);

		    for (int i = 0; i < keys.length; i++){
		    	try{
		    		nestedObject = pages.getJSONObject(keys[i]);
		    		if (nestedObject.has("pageid"))
		    			break;
		    	} catch (JSONException e) {}
		    }
		    String s = nestedObject.getString("extract");

		//    int index = s.indexOf("\n");

//		    // If the returned info is longer then one paragraph this trims off the extra
//		    // details to make the information more concise.
//		    if(index>0){
//		    	s = s.substring(0, index);
//		    }

		    return s;
		 } catch (MalformedURLException e) {
	        } catch (IOException e) {
	        } catch (NullPointerException e) {
	        }
			return null;
		}
	}