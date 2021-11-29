package ca.uwindsor.acc;

import static java.lang.System.*;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		out.println("Java Web Scraping....");
		
		String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";

		Document doc = Jsoup.parse(html);
		
		out.println("\nParsed HTML string: ");
		out.println(doc);
		
		/*
		 * Parsing a body fragment
				Problem
				You have a fragment of body HTML (e.g. a div containing a couple of p tags; 
				as opposed to a full HTML document) that you want to parse. Perhaps it was 
				provided by a user submitting a comment, or editing the body of a page in a CMS.
		*/
		String htmlBody = "<div><p>Lorem ipsum.</p>";

		Document bodyParse = Jsoup.parseBodyFragment(htmlBody);
		out.println(bodyParse);
		
		
		// Loading content from a URL
		out.println("Parsing from a URL: ");
		
		Document urlBody = Jsoup.connect("https://jsoup.org/cookbook/input/load-document-from-url").get();
		String urlTitle = urlBody.title();
		out.println(urlTitle);
		
		parseURL("http://scikit-learn.org/stable/tutorial/statistical_inference/settings.html");
		
		
		// Loading data from an external file
			// 1. Load the external file
		File inputFile = new File("C:\\Users\\tarun\\Downloads\\Typing Practice.htm");
			// 2. Load the file into the HTML parser
		Document fileURL = Jsoup.parse(inputFile, "UTF-8", "https://www.keybr.com/");		
																// Load the URL and specify the kind of
																// encoding 
		out.println(fileURL.title());
		
		parseURL("http://scikit-learn.org/stable/tutorial/statistical_inference/settings.html", 20000);
		
	}
	
	public static void parseURL(String url) throws IOException {
		out.println("Parsing URL.....");
		try {
			
			// Connect to the URL first
			Document parseTitle = Jsoup.connect(url)
					.timeout(30000)		// Have the connection timeout after 30s
					.get();
			
			// Retrieve the title
			String urlTitle = parseTitle.title();
			
			// Print the title of the page to the user
			out.println("Title: " + urlTitle);
			
		}catch(Exception e) {
			out.println("Error, parsing URL. Check that this url "
					+ "is a valid URL and that you are connected to the internet.");
			e.printStackTrace();
		}
	}
	
	// Overload the parseURL function parseURL(String url, int timeout)
	public static void parseURL(String url , int timeout) throws IOException{
		out.println("Parsing URL.....");
			
;		try {
	
			if (timeout < 15000) {
				out.println("Parsing a url requires more time (i.e. >=16000ms )");
			}else {
				Document jsoupURL = Jsoup.connect(url)
					     .timeout(timeout)
					     .get();
			
				String documentTitle = jsoupURL.title();
				out.printf("URL Document Title: %s", documentTitle);
			}
		}catch(Exception e) {
			out.println("Error, parsing URL. Check that this url "
					+ "is a valid URL and that you are connected to the internet.");
		}finally {
			out.println("Execution Completed");
		}
	}
	
	
	// Method to parse an HTML document from file input
	public static void fileParseInput(String path) throws IOException {
		try {
			// Create a new File object
			File inputFile = new File(path);
			
			// Create the Document object to parse the HTML document
			Document inputFileParse = Jsoup.parse(inputFile, "UTF-8");
			
			// Display the title and text within the document of the document
			String documentTitle = inputFileParse.title();
			
			out.println("Title: " + documentTitle);
			
		}catch(Exception e) {
			out.println("Error parsing this document, make sure that this is an HTML file"
					+ " and that your input path points correctly to that file");
		}finally {
			out.println("Program Execution finished");
		}
	}

}