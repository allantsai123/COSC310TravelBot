import java.util.*;

public class ParserDictionary {
    public static List<String> greet = Arrays.asList("good morning", "good afternoon", "good evening", "hi", "hello", "hey", "greetings", "howdy");
    public static List<String> leave = Arrays.asList("good night", "bye", "goodbye", "farewell", "see ya", "see you later", "take care", "ciao");
    public static List<String> comeBack = Arrays.asList("please help", "come back", "please come back", "i need help", "that's rude");
    public static List<String> Mdest = Arrays.asList("Mexico");
    public static List<String> Cdest = Arrays.asList("Canada");
    public static List<String> citiesC = Arrays.asList("Regina","Ottawa","Victoria","Winnipeg","Halifax","Quebec City","Montreal","Saskatoon", "Edmonton", "Vancouver", "Calgary", "Kelowna", "Toronto");
    public static List<String> citiesM = Arrays.asList("Mexico City", "Tijuana", "Juarez", "Cancun", "Mexicali", "Chihuahua","Leon","Culiacan");
    public static List<String> askForCities = Arrays.asList("cities", "places", "towns", "destinations");
    public static List<String> thanks = Arrays.asList("thanks", "thank you", "appreciated");
    public static List<String> travelMethods = Arrays.asList("fly", "flight", "plane", "boat", "cruise", "bus", "drive", "car");
    public static List<String> distance = Arrays.asList("far", "long", "distance");
    public static List<String> food = Arrays.asList("eat", "food", "restaurant", "dine", "lunch", "dinner", "bar");
    public static List<String> budget = Arrays.asList("budget", "afford", "cost");
    public static List<String> activities = Arrays.asList("things to do", "activities","do there","what to do");
    public static List<String> getAround = Arrays.asList("get around");
    public static List<String> bookInfo = Arrays.asList("passport", "reservation", "book", "booking");
    public static List<String> lang = Arrays.asList("english", "spanish", "french");
    public static List<String> weather = Arrays.asList("weather", "temperature", "rain", "sun", "warm", "cool", "time of year");
    public static List<String> seasons = Arrays.asList("spring", "summer", "fall", "winter");
    public static List<String> wiki = Arrays.asList("tell me more information","more info", "tell me more", "about","information","info","wiki");
    public static List<String> Directions = Arrays.asList("how to get","get there","direction to","direct","direction");
    public static List<String> streetView = Arrays.asList("show me", "look like","picture of","image","show");
    public static List<String> translate = Arrays.asList("translate","other language");
  // Stores the phrase lists as a list of phrases, each phrase being a list of words in the phrase
    private static HashMap<List<String>, List<List<String>>> cachedLists = new HashMap<>();

    public static List<List<String>> getTokenizedPhraseList(List<String> phraseList) {
        List<List<String>> tokenizedPhraseList;

        // Check if result is cached
        if (!cachedLists.containsKey(phraseList)) { // Not found in cache
            tokenizedPhraseList = tokenizePhrases(phraseList);  // Break down the phrases into a list of tokenized phrases
            cachedLists.put(phraseList, tokenizedPhraseList); // Save the result
        } else {
            tokenizedPhraseList = cachedLists.get(phraseList); // Result found in cache
        }

        return tokenizedPhraseList;
    }

    // Creates a list of tokenized phrases
    // Each phrase is itself a list of tokens (words)
    private static List<List<String>> tokenizePhrases(List<String> phraseList) {
        List<List<String>> tokenizedPhraseList = new ArrayList<List<String>>();

        for (String phrase : phraseList) { // For every phrase in the list
            // Break down the phrase into tokens (words)
            // then add the list of tokens to our list of tokenized phrases
            tokenizedPhraseList.add(Regex.tokenizeOnWhitespace(phrase));
        }

        return tokenizedPhraseList;
    }
}