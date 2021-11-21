package ca.uwindsor.acc;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import textprocessing.BruteForceMatch;

public class Main {

	public static void main(String[] args) {
		char option = '\0';

		System.out.println("***********************************************************************************");
		System.out.println("					Welcome ");
		System.out.println("				Project Members: ");
		System.out.println("			  Anahita Sedighikasmaei, Tarun x ");
		System.out.println("**********************************************************************************");
		System.out.println("Select one item from menu:");
		System.out.println("A. Search By SymbolGraph and Regular expressions Pattern");
		System.out.println("B. Extract web page to text by Jsoup");
		System.out.println("C. Search keywords by BruteForce");
		System.out.println("D. Feature3");
		System.out.println("E. Exit");

		do {
			Scanner scanner = new Scanner(System.in);
			printDoubleLine();
			System.out.println("Enter an option (For Example: B):");
			printDoubleLine();
			option = scanner.next().charAt(0);
			System.out.print("\n");

			switch (Character.toLowerCase(option)) {
			case 'a':

				System.out.print("Enter your text for search (For Example: Nicole Kidman ,Nicole, Kidman or 2000):");
				System.out.print("\n");
				scanner = new Scanner(System.in);
				String searchInputStr = "";
				searchInputStr += scanner.nextLine();

				SymbolGraphSearch.SymbolGraphAndPatternSearch(searchInputStr);

				System.out.print("\n");
				break;
			case 'b':

				HTMLtoText.extactUlsAndCache();
				System.out.print("\n");
				System.out.println("Page extraction finished successfully");
				System.out.print("\n");
				break;
			case 'c':

				System.out.print("Enter keyword for Search by BruteForce Algorithm:");
				scanner = new Scanner(System.in);
				String keyword = "";
				keyword += scanner.nextLine();
				WebSearch.bruteForceSearch(keyword);

				System.out.print("\n");
				break;
			case 'd':

				System.out.print("C is running");
				System.out.print("\n");
				break;
			}
		} while (option != 'e');

		System.out.println("Thank you for using our services");

	}

	public static void extractDataWithJsoup(String href) {
		try {

			Document doc1 = Jsoup.connect(href).get();
			System.out.println("1");
			String title1 = doc1.title();
			var body1 = doc1.body().html();
			String innerHtml = Jsoup.parse(body1, "ISO-8859-1").select("body").text();
			System.out.println(innerHtml);
		} catch (IOException e) {
			// Your exception handling here
		}

	}

	private static void printDoubleLine() {
		System.out.println(
				"===============================================================================================");
	}
}
