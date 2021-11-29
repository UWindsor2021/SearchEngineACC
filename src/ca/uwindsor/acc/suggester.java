package ca.uwindsor.acc;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class suggester {
	public static void suggest(String text1) throws IOException {

	File dir = new File("");
    Directory directory = FSDirectory.open(dir);

    SpellChecker spellChecker = new SpellChecker(directory);
    spellChecker.indexDictionary(
    new PlainTextDictionary(new File("dictionary.txt")));
    System.out.println("Enter the Keyword");
    String wordForSuggestions = text1;
    int suggestionsNumber = 5;
    String[] suggestions = spellChecker.
        suggestSimilar(wordForSuggestions, suggestionsNumber);
    if (suggestions!=null && suggestions.length>0) {
    	System.out.print("Did you mean:");
        for (String word : suggestions) {
            System.out.println(word);
        }
    }
    else {
        System.out.println("No suggestions found for word:"+wordForSuggestions);
    }
}
}