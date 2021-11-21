package ca.uwindsor.acc;

import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLtoText {
	StringBuffer s;

	public static String extractPageContentToTextWithJsoup(String href) {

		String innerHtml = "";
		try {

			Document doc = Jsoup.connect(href).get();
			String title = doc.title();
			var body = doc.body().html();
			innerHtml = Jsoup.parse(body, "ISO-8859-1").select("body").text();

			// System.out.println(innerHtml);
		} catch (IOException e) {
			// Your exception handling here
		}
		return innerHtml;
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
							if (HTMLtoText.isUrlValid(line)) {
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
	public static void saveWebContentInCacheFile(String url ) {
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

	public static boolean isUrlValid(String url) {
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

	public static void extractDataWithJsoup(String href) {
		Document doc = null;
		try {
//	         doc = Jsoup.connect(href).timeout(10*1000).userAgent
//	             ("Mozilla").ignoreHttpErrors(true).get(); 

			doc = Jsoup.connect(href).get();
		} catch (IOException e) {
			// Your exception handling here
		}
		if (doc != null) {
			String title = doc.title();
			String text = doc.body().text();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();
				String linkOuterHtml = link.outerHtml();
				String linkInnerHtml = link.html();
				System.out.println(linkHref);
				System.out.println(linkText);
				System.out.println(linkOuterHtml);
				System.out.println(linkInnerHtml);
			}
		}
	}

	public static void main(String[] args) {
		try {
			extactUlsAndCache();
			//saveWebContentInCacheFile("https://stackoverflow.com/questions/22523770/short-way-to-ask-for-confirmation-java");
			//isUrlValid("https://www.amazon.ca/");
			// the HTML to convert
			// URL url = new URL("http://www.windsorstar.com/story.html?id=10392258");
			// URL url = new
			// URL("http://blogs.windsorstar.com/entertainment/agw-wins-three-major-awards");
			// saveWebContentInDraftFile("https://www.amazon.ca/", "C:/Test/");

			//extractDataWithJsoup("https://www.amazon.ca/");
//			URL url = new URL(
//					"http://blogs.windsorstar.com/news/woman-to-be-charged-with-child-abandonment-after-infants-found-in-apartment-stairwell");
//
//			URLConnection conn = url.openConnection();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//			String inputLine;
//			String finalContents = "";
//			while ((inputLine = reader.readLine()) != null) {
//				finalContents += "\n" + inputLine.replace("<br", "\n<br");
//			}
//			BufferedWriter writer = new BufferedWriter(new FileWriter("testHtml.html"));
//			writer.write(finalContents);
//			writer.close();
//			System.out.println(finalContents);
//
//			FileReader in = new FileReader("testHtml.html");
//			HTMLtoText parser = new HTMLtoText();
//			parser.parse(in);
//			in.close();
//			String textHTML = parser.getText();
//			System.out.println(textHTML);
//
//			// Write the text to a file
//			BufferedWriter writerTxt = new BufferedWriter(new FileWriter("testHtml.txt"));
//			writerTxt.write(textHTML);
//			writerTxt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}