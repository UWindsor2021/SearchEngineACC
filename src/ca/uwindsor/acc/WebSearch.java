package ca.uwindsor.acc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import textprocessing.BruteForceMatch;
import textprocessing.In;

public class WebSearch {

	public static void bruteForceSearch(String searchInput) {
		try {
			// write code for reading text file
			
			StringBuilder sb= new StringBuilder();
			File dir = new File("./Links/Cache/");
			File[] files = dir.listFiles();
			for (File f : files) {
				if (f.isFile()) {
					BufferedReader inputStream = null;

					try {
						inputStream = new BufferedReader(new FileReader(f));
						String line;

						while ((line = inputStream.readLine()) != null) {
							// the using the text file content for search
							sb.append(line);
						}
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
					}
				}
			}
			
	
			 List<Integer> matchedIndexes = bruteForceStringMatcher(sb.toString(), searchInput);
			 if(matchedIndexes.isEmpty()) {
					System.out.println("No item found...");
			 }else
			 {
				 for(Integer matchedIndex : matchedIndexes){
			            System.out.println("Pattern found at "+matchedIndex);
			        }
			 }
			 
		} catch (IOException e) {
		}
		
	}

	


	public static List<Integer> bruteForceStringMatcher(String text, String pattern) {

		char[] textArray = text.toCharArray();
		char[] patternArray = pattern.toCharArray();

		int textLength = textArray.length;
		int patternLength = patternArray.length;

		List<Integer> matchedIndexes = new ArrayList<>();

		int textIndex = 0;

		for (textIndex = 0; textIndex < textLength; textIndex++) {
			int textIndexLocal = textIndex;
			Boolean match = true;
			int matchedIndex = textIndex;
			for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
				if (textArray[textIndexLocal] != patternArray[patternIndex]) {
					match = false;
					break;
				}
				textIndexLocal = textIndexLocal + 1;
			}
			if (match)
				matchedIndexes.add(matchedIndex);
		}
		return matchedIndexes;
	}

	public static void main(String[] args) throws IOException {

		

	}

}
