package ca.uwindsor.acc;


import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Html2Text extends HTMLEditorKit.ParserCallback {
 StringBuffer s;

 public Html2Text() {}

 public void parse(Reader in) throws IOException {
   s = new StringBuffer();
   ParserDelegator delegator = new ParserDelegator();
   // the third parameter is TRUE to ignore charset directive
   delegator.parse(in, this, Boolean.TRUE);
 }

 public void handleText(char[] text, int pos) {
   s.append(text);
 }

 public String getText() {
   return s.toString();
 }

 public static void main (String[] args) {
   try {
     // the HTML to convert
	//URL  url = new URL("http://www.windsorstar.com/story.html?id=10392258");
	//URL  url = new URL("http://blogs.windsorstar.com/entertainment/agw-wins-three-major-awards");
	URL  url = new URL("http://blogs.windsorstar.com/news/woman-to-be-charged-with-child-abandonment-after-infants-found-in-apartment-stairwell");
	   
    URLConnection conn = url.openConnection();
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String inputLine;
    String finalContents = "";
    while ((inputLine = reader.readLine()) != null) {
     finalContents += "\n" + inputLine.replace("<br", "\n<br");
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter("testHtml.html"));
    writer.write(finalContents);
    writer.close();
    System.out.println(finalContents);

     FileReader in = new FileReader("testHtml.html");
     Html2Text parser = new Html2Text();
     parser.parse(in);
     in.close();
     String textHTML = parser.getText();
     System.out.println(textHTML);
     
     // Write the text to a file  
     BufferedWriter writerTxt = new BufferedWriter(new FileWriter("testHtml.txt"));
     writerTxt.write(textHTML);
     writerTxt.close();

   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }

 
 public static void extactUlsAndCache() {
		try {
			// write code for reading html files
			File dir = new File("./Links/");
			File[] files = dir.listFiles();
			for (File f : files) {
				if (f.isFile()) {
					BufferedReader inputStream = null;

					try {
						inputStream = new BufferedReader(new FileReader(f));
						String line;

						while ((line = inputStream.readLine()) != null) {
							// the using the text= file content for search
							if (isUrlValid(line)) {
								saveWebContentInCacheFile(line);
							}

						}
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
					}
				}
			}

		} catch (IOException e) {
		}
	}
	private static void saveWebContentInCacheFile(String url ) {
		String textFilePath ="./Links/Cache/";
		BufferedWriter bw = null;
		try {

			Document doc = Jsoup.connect(url).get();

			String pagePath = textFilePath + doc.title() + ".txt";// doc.text();
			System.out.println(pagePath);
			File file = new File(pagePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(doc.body().text());
			System.out.println("File written Successfully");

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				System.out.println("Error in closing the BufferedWriter" + ex);
			}
		}
	}

	private static boolean isUrlValid(String url) {
		String pattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(url);
		if (m.find()) {
			return true;
		}
		return false;
	}

}