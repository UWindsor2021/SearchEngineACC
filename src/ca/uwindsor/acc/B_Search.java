package ca.uwindsor.acc;

import java.io.*;   
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.html.HTMLEditorKit;

import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import graphs.In;
import textprocessing.BoyerMoore;

public class B_Search extends HTMLEditorKit.ParserCallback {

	public static void wordSuggestion(String input) throws IOException
	{
		String searchinput = input;
		String regex = "^"+searchinput+"(.+)"; //this regex search for word which were similar to searching input.
		Document doc = Jsoup.connect("https://www.dictionary.com/browse").get(); // Parse the words from Dictionary.com
		String str = doc.text();
		StringTokenizer token = new StringTokenizer(str);
		while (token.hasMoreTokens()) 
		{
			String tokenstr = token.nextToken();
			Pattern p = Pattern.compile(regex);
			Matcher match = p.matcher(tokenstr);			
				if(match.matches())//.matches check for condition
				{
						System.out.println(tokenstr);		
				}	
			
        }
}

	public static void mainCall() throws IOException {
		int convertedtextfiles = 0;
		File dir = new File("W3C Web Pages/");
		File dirtext = new File("W3C Web Pages/convertedtextfiles/");
		for (File file : dir.listFiles()) {
			String ztext = dir + "\\convertedtextfiles";//if find this text folder then increase pointer
			if (file.toString().contains(ztext)) {
				convertedtextfiles++;
			}
		}
		if (convertedtextfiles == 0)//poniter is 0 means text files should need to generate so it executes code to convert HTML to Text 
		{
			System.out.println("Converting HTML files to text files");
			for (File file : dir.listFiles()) {
				FileReader in = new FileReader(file);
				new File(dir + "/convertedtextfiles/").mkdir();
				Html2Text parser = new Html2Text();
				parser.parse(in);
				in.close();
				String textHTML = parser.getText();
				BufferedWriter writerTxt = new BufferedWriter(
						new FileWriter(dir + "/convertedtextfiles/" + file.getName() + ".text"));
				writerTxt.write(textHTML);
				writerTxt.close();
			}
			System.out.println("Done");
			System.out.println("_________________________________________________________");
			System.out.println();
			System.out.println("");
		} 
		else {
			System.out.println("Small Database is setup");
			System.out.println("_________________________________________________________");
		}
		// _____________Files converted to text files__________________
		System.out.println("Select any option : ");
		System.out.println("1 Search");
		System.out.println("2 URL");
		System.out.print("Choose one : ");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int number= sc.nextInt();
		if(number==2)
		{
			System.out.print("Enter URL : ");
			@SuppressWarnings("resource")
			Scanner sc1 = new Scanner(System.in);
			String searchurl = sc1.next();
			System.out.println("Details appeared in URL : "+searchurl);
			File dirurl = new File(searchurl);
			// File which contain contact number and email id --->W3C Web Pages\convertedtextfiles\About W3C Standards.htm.text
			In intel = new In(dirurl);
		    String tex=intel.readAll().toString();
			Pattern r = Pattern.compile("(\\()?(\\d){3}(\\))?[- ](\\d){3}-(\\d){4}");//regex to find contact numbers
		      Pattern r1 = Pattern.compile("([\\w\\-]?([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");//regex to find Email id's
		      Matcher m = r.matcher(tex);
		      Matcher m1 = r1.matcher(tex);
		    	  while (m.find( ))
			      {
		    		  System.out.println("Number Found : " + m.group(0));
			      }
		    	  System.out.println("_________________________________");
		    	  System.out.println();
			      while (m1.find( ))
			      {
			    	  System.out.println("e-Mail Found : " + m1.group(0));
			      }
		}
		else 
		{
			System.out.print("Keyword : ");
			String searchinput = sc.next();
			System.out.println("Suggesting------------------");
			wordSuggestion(searchinput);
			long offset1;
			LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
			for (File file : dirtext.listFiles()) {
				int count1 = 0;
				String filename = file.getName();
				In in = new In(dirtext + "\\" + filename);
				String txt = in.readAll().toString().replaceAll("[^a-zA-Z]", "");//replace all non alphabetic things into empty string
				for (int i = 0; i < txt.length(); i++) {
					BoyerMoore boyermoore1 = new BoyerMoore(searchinput);
					offset1 = boyermoore1.search(txt);
					if (txt.length() != offset1) {
						count1++;
					}
				}
				if (count1 > 0) {
					Map<String, Integer> unSortedMap = new HashMap<>();
					unSortedMap.put(filename, count1);
					unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
					//We learned this concept from https://howtodoinjava.com/sort/java-sort-map-by-values/
				}

			}
			System.out.println("Search Results : " + reverseSortedMap+"\n");//it will print files which contain search word and sort that files into alphabetical order.
		}
	}
}
