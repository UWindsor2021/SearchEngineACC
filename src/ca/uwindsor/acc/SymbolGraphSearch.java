package ca.uwindsor.acc;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graphs.Graph;

import graphs.StdOut;
import graphs.SymbolGraph;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import graphs.In;

public class SymbolGraphSearch {
	
	public static void SymbolGraphAndPatternSearch(String input) {

		System.out.println(input);

		// Input is number so the search method will be finding year with pattern
		if (isNumeric(input)) {
			findMovieByProductionYear(input);
		} else {
			String delimiter = "[,/]";
			if (input.contains(" ")) {
				input = changeInputStrForSearch(input);
				delimiter = "/";
			}

			SymbolGraph sg = new SymbolGraph("movies.txt", delimiter);
			printSingleLine();

			// Finding Movie names by symbol graph
			findMovie(input, sg);
		}

	}

	private static String changeInputStrForSearch(String input) {
		String[] result = input.split(" ");
		input = result[1] + ", " + result[0];
		return input;
	}

	private static void findMovie(String source, SymbolGraph sg) {
		try {
			Graph G = sg.G();

			// search in graph for name
			if (sg.contains(source)) {
				// found the index
				int s = sg.index(source);
				printSingleLine();
				StdOut.println(source);

				// show he vertices adjacent to the index found for the name
				for (int v : G.adj(s)) {
					StdOut.println("   " + sg.name(v));
				}
			} else {
				StdOut.println("Cannot find any film for this actor '" + source + "'\n");
			}
		} catch (Exception e) {
			// StdOut.println("input does not contain '" + source + "'\n");
		}

	}

	public static void findMovieByProductionYear(String year) {
		// String to be scanned to find the pattern.
		try {
			// A Space Travesty (2000)
			In in = new In("movies.txt");
			var content = in.readAll();
			String pattern = "(.+)(\\(" + year + "\\))";

			// Create a Pattern object
			Pattern r = Pattern.compile(pattern);

			// Now create matcher object.
			Matcher m = r.matcher(content);
			int i = 1;
			boolean found = false;
			while (m.find()) {
				found = true;
				System.out.println(i + " " + m.group(1));
				i++;
			}

			if (!found) {
				System.out.println("Found no result for search: \"" + year + "\"");
			}
		} catch (Throwable e) {
			System.out.print("\n");
			System.out.println("No data found ..." + e);
		}

	}

	public static boolean isNumeric(String string) {
		int intValue;

		System.out.println(String.format("Parsing string: \"%s\"", string));

		if (string == null || string.equals("")) {
			System.out.println("String cannot be parsed, it is null or empty.");
			return false;
		}

		try {
			intValue = Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}



	private static void printSingleLine() {
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
	}
}
