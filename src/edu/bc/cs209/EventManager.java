package edu.bc.cs209;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
 * Database of security events
 */
public class EventManager {
	private static final File INCIDENTS_FILE = new File("Seattle_911_Incidents.csv");

	/**
	 * Returns the number of the events in the input data
	 * @param fileName a .csv file containing the events, one per line
	 * @return the number of events in the file
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static int getTotalEvents(File fileName) throws FileNotFoundException {

		int count = 0;

		try (Scanner fileScanner = new Scanner(fileName)) { // 'try with resource' ensures the scanner is closed

			fileScanner.nextLine();
			while (fileScanner.hasNextLine()) {
				count++;
				fileScanner.nextLine();
			}

			return count;
		}
	}

	/**
	 * Load the database of events from a .csv file into an array
	 * @param fileName the name of the .csv file containing the events
	 * @return an array of Event objects, each containing one event from the file
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static Event[] loadArrays(File fileName) throws FileNotFoundException {
		int count = getTotalEvents(fileName); // TODO to avoid reading the file twice, use a mutable List instead of an array

		Event[] result = new Event[count];

		try (Scanner fileScanner = new Scanner(fileName)) {

			fileScanner.nextLine(); // TODO is this needed?

			for (int i=0; i<count; i++) {

				String line = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(line);

				lineScanner.useDelimiter(",");

				Event event = new Event();
				event.setId(lineScanner.nextInt());
				event.setType(lineScanner.next());
				event.setDate(lineScanner.next());
				event.setAddress(lineScanner.next());
				event.setSector(lineScanner.next());
				event.setZone(lineScanner.next());
				result[i] = event;
			}
		}

		return result;
	}

	/**
	 * Returns the statistics of the collection of events, i.e., 
	 * the number of events in each of the three years, 2015-2017
	 * @param events the list of events
	 * @return a human-readable report of the events' statistics
	 */
	public static String getEventStatistics(Event[] events) {

		int count2015 = 0;
		int count2016 = 0;
		int count2017 = 0;

		StringBuffer sb = new StringBuffer();

		for(int i=0; i<events.length; i++) {

			Event event = events[i];

			if(event.getDate().contains("/15 ")) {
				count2015++;
			} else if (event.getDate().contains("/16 ")) {
				count2016++;
			} else {
				count2017++; // TODO what if the date doesn't have '/17'?
			}

		}

		sb.append("2015: " + count2015 + " events");
		sb.append("\n2016: " + count2016 + " events");
		sb.append("\n2017: " + count2017 + " events");
		sb.append("Total Events: " + events.length);

		return sb.toString();
	}

	/**
	 * List the events that match the given date
	 * @param userDate the date to match
	 * @param events the database of events
	 */
	public static void printEventsDates(String userDate, Event[] events) {
		int count = 0;

		for (Event event : events) {

			if(event.getDate().contains(userDate)) {
				count++;
				System.out.println("Event-" + count +"--------------------------");
				System.out.println(event);
			}
		}

		if (count == 0) {
			System.out.println("No records Found.");
		} else {
			System.out.println(count + " events.");

		}
	}

	// TODO printEventsDates and printEventsTypes are similar
	// TODO factor out repeated code

	/**
	 * List the events that match the given type
	 * @param userType the type of event
	 * @param events the database of events
	 */
	public static void printEventsTypes(String userType, String userSector, Event[] events) {

		int count = 0;
		userType = userType.toUpperCase();
		userSector = userSector.toUpperCase();

		for (Event event: events) {

			if (event.getType().contains(userType) && event.getSector().contains(userSector)) {
				count++;
				System.out.println("Event-" + count +"--------------------------");
				System.out.println(event);
			}

		}

		if (count == 0) {
			System.out.println("No records Found.");
		} else {
			System.out.println(count + " events.");
		}
	}

	public static void main(String[] args) {

		int count;
		Event[] events=null;

		try {
			count = getTotalEvents(INCIDENTS_FILE);
			// TODO 'events' could be an object-level variable. Then it would not
			// TODO have to be passed to each method that uses it.
			events = loadArrays(INCIDENTS_FILE);

		}	catch (FileNotFoundException e) {
			System.out.println("File "+INCIDENTS_FILE.getName()+" is missing.");
			System.exit(1);
		}

		System.out.println(getEventStatistics(events));

		try (Scanner userScanner = new Scanner(System.in)) { // try-with-resources ensures the resource is closed
			int option = 0;
			while(option != 3) {


				System.out.println("+++++++++++++++++++++++++++++++++");
				System.out.println("Seattle 911 Event Search Manager: ");
				System.out.println("1- Search by Date");
				System.out.println("2- Search by Type");
				System.out.println("3- Quit");
				System.out.print("Choose a search operation: ");

				try {
					option = userScanner.nextInt();
				} catch	(InputMismatchException e) {
					System.out.println("Please enter a valid input!");
					userScanner.nextLine();
					continue;
				}

				if (option == 1) {

					System.out.print("Enter a date (dd/mm/yy): ");
					String userDate = userScanner.next();

					printEventsDates(userDate, events);

				} else if (option == 2) {

					System.out.print("Enter keyword for type: ");
					String userType = userScanner.next();

					System.out.print("Enter Sector: ");
					String userSector = userScanner.next();

					printEventsTypes(userType, userSector, events);

				} else if (option == 3) {

					System.out.print("Stay Safe!");

				} else {

					System.out.println("Please enter a valid input!");

				}

			}

		}

	}

}