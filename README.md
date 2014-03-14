========================================================================================================
COSC310 Assignment 3

Allan
	
Project Description: 
        Develop an interactive conversational travel agent that responds to user questions using Java. The user can ask the chatbot any question that is related to the trip such as weather of the destination, cost of the vacation, and transportation for the trip, etc. 
	 
========================================================================================================

FILES/FOLDERS DESCRIPTION

For Assignment 3:

Run the MainGUI.java file which contains the Chat windows and the main class which handles user input and output. Also a minor change in the Parsers and add more destinations and more replies for the chatbot.

From Before: 

The Program class controls the ChatBot program. The class handles user input, which can be provided by Text File or in the Console. 

The input is then passed to the Parser class to determine what type of "input" the user provided. The parseUserMessage method cleans up the input and tokenizes the string. It then runs through all the possible input cases to determine what the user said. It does this by comparing words in the string to keywords that are defined in the ParsedInputDictionary class. If the input matches one of the cases the appropriate method sets the type of input, according the the lists defined in the ParsedInputType class.

The input is matched to keywords using a fuzzy substring distance measure based on levenshtein distance. The most closely matched keyword is the one we assume the user meant.

The input type is then passed to the TravelAgent class, which again uses cases to know what input was provided by the user. When a matching case is found the ResponseMaker class is called and the appropriate method starts to build a response. The responses are built in a few different ways. For trivial statements, such as things that the agent cannot handle, the ResponseMaker class will return the message coded into the method. The Responses class holds lists of more complex responses. The class uses a RNG to pick one of the responses from the correct list, and if necessary replaces keywords (such as <Dest>) with stored user input. The final way that responses are built is using the Location class. When the user provided a destination, the Location class uses the Google API's to query information that might be relevant to a tourist. This class finds distances between places, temperatures, estimates costs, and find locations based on user keywords (such as "food" to find restaurants"). Once the ResponseMaker has built the string it returns the information to the TravelAgent class to display for the user.

The org.json package has classes built by Douglas Crockford (https://github.com/douglascrockford/JSON-js) to allow the prorgam to interact with the Google API's.

========================================================================================================
