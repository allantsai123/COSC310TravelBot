========================================================================================================
COSC310 Project

Allan
	
Project Description: 
        Develop an interactive conversational travel agent that responds to user questions using Java. The user can ask the chatbot any question that is related to the trip such as weather of the destination, cost of the vacation, and transportation for the trip, etc. 
	 
========================================================================================================
For the final Project:

	Added Public APIs:
	-Google Geocoding
		This API is used to return an address into geo-coordinates, the coordinates are use to calculate the distance between two places, and interact with other APIs like: Google Places,Street View and Directions.

	-Google Places 
		This API allows the travelbot retrieve some useful information of the destination such as local weather, resturent, and places to visit.

		sample output:
			Travel Bot:	Welcome to our travel center. How can I help?
			User: go to vancouver canada
			Travel Bot: Vancouver, Canada is very nice!
			User: what can i eat tehre?
			Travel Bot: Let me look that up... Bridges Restaurant Granville Island, Public Market, JAPADOG

	-Google Directions
		This API interact with Google Geocoding to get a step by step direction to the destination.

		sample output:
			Travel Bot:Greetings, How can I help?
			User:go to vancouver canada
			Travel Bot:Vancouver, Canada is a wonderful place!	
			User: direct me theere	
			Travel Bot: Here are the directions from Kelowna to your destination: Head south on Water St toward Queensway for 0.5 km.Turn right onto Harvey Ave/BC-97 SContinue to follow BC-97 S for 16.8 km.Take the BC-97C W ramp to Merritt/Kamloops/Hope for 0.8 km.Merge onto BC-97C for 105 km.Turn left to merge onto BC-5 S toward Yellowhead Highway S/Vancouver for 109 km. Continue onto BC-3 W for 6.5 km.Continue onto Trans-Canada HwyPartial toll road for 143 km. Take exit Exit 27 toward First Ave for 0.3 km.Turn left onto E 1st Ave for 3.6 km. Continue onto Terminal Ave for 1.8 km. Turn right onto Quebec St for 0.4 km. Continue onto Expo Blvd for 1.3 km. Turn left onto Nelson St for 0.1 km.Take the 1st left onto Pacific BlvdDestination will be on the left for 0.3 km.Your destination is at 777 Pacific Blvd, Vancouver. Total distance is 389 km and will take 4 hours 17 mins to drive there.

	-Wikipedia API    
		will retrieve the information from wikipeida and output the content of the wikipedia, so the user can have more information on the destenation.

		 sample output:
			 User: more info on my destination
			 Travel Bot:Vancouver (/væ??ku?v?r/ or /væn?ku?v?r/), officially the City of Vancouver, is a coastal seaport city on the mainland of British Columbia, Canada. The 2011 census recorded 603,502 people in the city, making it the eighth largest Canadian municipality. Vancouver is one of the most ethnically and linguistically diverse cities in Canada; 52% of its residents have a first language other than English. The Greater Vancouver area of around 2.4 million inhabitants is the third most populous metropolitan area in the country and the most populous in Western Canada.The City of Vancouver encompasses a land area of about 114 square kilometres, giving it a population density of about 5,249 people per square kilometre (13,590 per square mile). Vancouver is the most densely populated Canadian municipality, and the fourth most densely populated city over 250,000 residents in North America, behind New York City, San Francisco, and Mexico City.
			 Retrieve From Wikipedia

	-Google Street View 
		will get a picture of the geo-coordiantes and output it onto a window. this api let the user be albe to see the destenation. 

	-Google Translate
		


For Assignment 3:

Run the MainGUI.java file which contains the Chat windows and the main class which handles user input and output. Also a minor change in the Parsers and add more destinations and more replies for the chatbot. using fuzzy substring match to do spell check and update more cities in Canada.

Clones before Assignment 3:

The Program class controls the ChatBot program. The class handles user input, which can be provided by Text File or in the Console. 

The input is then passed to the Parser class to determine what type of "input" the user provided. The parseUserMessage method cleans up the input and tokenizes the string. It then runs through all the possible input cases to determine what the user said. It does this by comparing words in the string to keywords that are defined in the ParsedInputDictionary class. If the input matches one of the cases the appropriate method sets the type of input, according the the lists defined in the ParsedInputType class.

The input is matched to keywords using a fuzzy substring distance measure based on levenshtein distance. The most closely matched keyword is the one we assume the user meant.

The input type is then passed to the TravelAgent class, which again uses cases to know what input was provided by the user. When a matching case is found the ResponseMaker class is called and the appropriate method starts to build a response. The responses are built in a few different ways. For trivial statements, such as things that the agent cannot handle, the ResponseMaker class will return the message coded into the method. The Responses class holds lists of more complex responses. The class uses a RNG to pick one of the responses from the correct list, and if necessary replaces keywords (such as <Dest>) with stored user input. The final way that responses are built is using the Location class. When the user provided a destination, the Location class uses the Google API's to query information that might be relevant to a tourist. This class finds distances between places, temperatures, estimates costs, and find locations based on user keywords (such as "food" to find restaurants"). Once the ResponseMaker has built the string it returns the information to the TravelAgent class to display for the user.

The org.json package has classes built by Douglas Crockford (https://github.com/douglascrockford/JSON-js) to allow the prorgam to interact with the Google API's.

========================================================================================================
