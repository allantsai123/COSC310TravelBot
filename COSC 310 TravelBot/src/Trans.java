import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;


public class Trans {

	public static String translate(String input) throws Exception{
		
	    Translate.setClientId("07a1e875-81d0-44ee-b349-f3e5ed5fb1d1");
        Translate.setClientSecret("qonxDFtYZ1sqb6S5UDN+6BrfeAfYxr5iFbAhAIhZ7lE=");
        
        String translatedText = "";
     
		translatedText = "The French translation is: ";
		
		//System.out.println("huh:"+input);
		
		translatedText += Translate.execute("Kelowa is an awesome place.", Language.ENGLISH, Language.FRENCH);
			 
        
		return translatedText;
	}

}