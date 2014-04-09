import java.util.*;

public class TravelAgent {
    private ResponseMaker responseMaker = new ResponseMaker();
    private Location l;
    String input = "";

    // Agent state
    private ArrayList<ParsedInput> previousInputs = new ArrayList<>();
    public HashMap<String, String> savedInputs = new HashMap<>();
    private boolean userHasGreeted = false;
    private boolean userHasSaidFarewell = false;

    public ResponseMaker getResponseMaker() {
        return responseMaker;
    }

    public void resetState() {
        // TODO reset state, delete memory etc
    }

    public String getResponse(ParsedInput parsedInput) throws Exception {
        String response = "";

        // TravelBot doesn't have time for annoying users ;) (unless they're sorry)
        if (userHasSaidFarewell && (parsedInput.getType() != ParsedInputType.PleaseComeBack)) {
            return Responses.getRandomResponse(Responses.sorrybusys);
        }

        // Save all user entered variables
        savedInputs.putAll(parsedInput.inputs);
        // Check which kind of question or statement the user inputted
        
        switch (parsedInput.getType()) {

            case SetDestination:
                response = responseMaker.getDestinationInfo(savedInputs.get("destination"), savedInputs.get("city"));
                l = new Location(savedInputs.get("destination"));
                break;
           //translate
            case Translate:
            	String sen = getSenToTranslate(input);
            	response=responseMaker.getTranslate(sen);
            	break;
                
            case TooLong:
                response = "Sorry, your message is too long. I don't have time to read that.";
                break;

            case Greeting:
                response = greeting(parsedInput);
                break;

            case Directions:
            	response=responseMaker.getDirections();
            	break;
            	
            case streetView:
            	//System.out.println(savedInputs.get("city"));
            	
            	if(savedInputs.get("city").equalsIgnoreCase("vancouver")){
            		response = responseMaker.getstreetView("Robinson Street, Vancouver");
            	}
            	if(savedInputs.get("city").equalsIgnoreCase("Mexico City")){
            		response = responseMaker.getstreetView("Centro, Mexico");
            	}else{
            	
            	response = responseMaker.getstreetView(savedInputs.get("city"));
            	}
            	//System.out.println(savedInputs.get("city"));
            	break;
            	
            case Wiki:
        
            	response = responseMaker.getWikiInfo(savedInputs.get("city"));
            	
            	break;
                
            case Food:
                response = responseMaker.getLocalFood();
                break;

            case Farewell:
                response = farewell(parsedInput);
                break;

            case PleaseComeBack:
                response = pleaseComeBack(parsedInput);
                break;

            case Activity:
                response = responseMaker.getActivities();
                break;

            case GetKeyword:
                response = responseMaker.getKeywordPlaces(savedInputs.get("keyword"));

            case ListCities:
                response = responseMaker.getCities();
                break;

            // How the user wants to get to destination
            case TravelMethod:
                response = responseMaker.getTravelMethod(savedInputs.get("travel method"), savedInputs.get("city"));
                break;

            case Distance:
                if (savedInputs.get("city2") != null) {
                    l = new Location(savedInputs.get("city"), savedInputs.get("city2"));
                    response = responseMaker.getDistances(l.origin, savedInputs.get("city2"));
                } else {
                    Location l2 = new Location(savedInputs.get("city"));
                    response = responseMaker.getDistances(l2.origin, savedInputs.get("city"));
                }
                break;

            case GetAround:
                response = responseMaker.getAround(savedInputs.get("city"));
                break;

            case Accomodations:
                response = responseMaker.getGenAccommodation();
                break;

            case Budget:
                int amount = Integer.valueOf(savedInputs.get("budget"));
                response = responseMaker.getBudgetAccom(amount, savedInputs.get("city"));
                break;

            case Language:
                response = responseMaker.getLanguages(savedInputs.get("destination"));
                break;

            case CheckWeather:
                response = responseMaker.getWeather(savedInputs.get("city"));
                break;

            case Thanks:
                response = responseMaker.getYoureWelcome();
                break;

            case SimpleYes:
                response = responseMaker.getSimpleYes();
                break;

            case SimpleNo:
                response = responseMaker.getSimpleNo();
                break;

            case DontUnderstand:
                response = responseMaker.getDontKnow();
                break;

            case None:
            default:
                response = "...";
                break;

            case Debug_Enable:
                response = "Debug enabled.";
                IORW.debugOn = true;
                break;

            case Debug_Reset:
                resetState();
                response = "State reset.";
                break;

            case Debug_ShowStats:
                response = getDebugStats();
                break;
        }

        // Save valid inputs to memory
        if (parsedInput.getType().isWellFormed()) {
            previousInputs.add(parsedInput);
        }
        return response;
    }
    private String getSenToTranslate(String inputSen){
    	System.out.println("THis will be output" +inputSen);
    	int index=-1;
    	int len = 9;
    	index = inputSen.indexOf("translate");
    	if(index==-1){
    		index=inputSen.indexOf("say");
    		len = 3;
    	}
    	String sen="";
    	if(index != -1){
    		sen = inputSen.substring(index+len+1,inputSen.length());
    	}
    	System.out.println("this is the sentence: "+sen);
    	return sen;
	}
    
    private String getDebugStats() {
        String stats = "";

        for (Map.Entry<String, String> entry : savedInputs.entrySet()) {
            stats += entry.getKey() + " = " + entry.getValue() + "\r\n";
        }

        return stats;
    }

    private String greeting(ParsedInput parsedInput) {
        if (userHasGreeted) {
            return responseMaker.getGreetingRepeat();
        } else {
            userHasGreeted = true;
            return responseMaker.getGreeting(savedInputs.get("username"));
        }
    }

    private String farewell(ParsedInput parsedInput) {
        userHasSaidFarewell = true;
        return responseMaker.getFarewell(savedInputs.get("username"));
    }
    
    private String pleaseComeBack(ParsedInput parsedInput) {
        userHasSaidFarewell = false;
        return responseMaker.getImBack();
    }
}