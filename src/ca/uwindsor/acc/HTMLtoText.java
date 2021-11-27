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


	public static void main(String[] args) {
		try {
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}