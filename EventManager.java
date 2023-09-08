package finalProject;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class EventManager {

	//gets the number of events in the file
	public static int getTotalEvents(File fileName) {

		int count = 0;

		try {
			Scanner fileScanner = new Scanner(fileName);

			fileScanner.nextLine();
			while(fileScanner.hasNextLine()) {
				count++;
				fileScanner.nextLine();
			}

		}catch(FileNotFoundException e) {
			System.out.print("File not found!");
		}

		return count;

	}

	//loads the five arrays with the various information and tells the user how many events happened per year
	public static void loadArrays(File fileName, int[] ids, String[] eventTypes, String[] dates, String[] addresses, String[] sectors, String[] zones) {

		int count2015 = 0;
		int count2016 = 0;
		int count2017 = 0;

		try {

			Scanner fileScanner = new Scanner(fileName);
			fileScanner.nextLine();

			for(int i=0; i<ids.length; i++) {

				String line = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(line);

				lineScanner.useDelimiter(",");
				ids[i] = lineScanner.nextInt();
				eventTypes[i] = lineScanner.next();
				dates[i] = lineScanner.next();
				addresses[i] = lineScanner.next();
				sectors[i] = lineScanner.next();
				zones[i] = lineScanner.next();

				if(dates[i].contains("/15 ")) {
					count2015++;
				}else if(dates[i].contains("/16 ")) {
					count2016++;
				}else {
					count2017++;
				}

			}

			System.out.println("2015: " + count2015 + " events");
			System.out.println("2016: " + count2016 + " events");
			System.out.println("2017: " + count2017 + " events");

		}catch(FileNotFoundException e) {
			System.out.print("File not found!");
		}

	}

	//provides the list of events for the user specified date 
	public static void printEventsDates(String userDate, String[] eventTypes, String[] dates, String[] addresses, String[] sectors, String[] zones) {

		int count = 0;
		for(int i=0; i<dates.length; i++) {

			if(dates[i].contains(userDate)) {

				count++;
				System.out.println("Event-" + count +"--------------------------");

				System.out.println("Type: " + eventTypes[i]);

				Scanner dateScanner = new Scanner(dates[i]);
				String date = dateScanner.next();
				String time = dateScanner.next();

				System.out.println("Date: " + date);
				System.out.println("Time: " + time);
				System.out.println("Address: " + addresses[i]);
				System.out.println("Sector: " + sectors[i]);
				System.out.println("Zone: " + zones[i]);

			}

		}

		if(count == 0) {

			System.out.println("No records Found.");

		}else {

			System.out.println(count + " events.");

		}

	}

	//provides the list of events for the user specified type and sector
	public static void printEventsTypes(String userType, String userSector, String[] eventTypes, String[] dates, String[] addresses, String[] sectors, String[] zones) {

		int count = 0;
		userType = userType.toUpperCase();
		userSector = userSector.toUpperCase();

		for(int i=0; i<eventTypes.length; i++) {

			if(eventTypes[i].contains(userType) && sectors[i].contains(userSector)) {

				count++;
				System.out.println("Event-" + count +"--------------------------");

				System.out.println("Type: " + eventTypes[i]);

				Scanner dateScanner = new Scanner(dates[i]);
				String date = dateScanner.next();
				String time = dateScanner.next();

				System.out.println("Date: " + date);
				System.out.println("Time: " + time);
				System.out.println("Address: " + addresses[i]);
				System.out.println("Sector: " + sectors[i]);
				System.out.println("Zone: " + zones[i]);

			}

		}

		if(count == 0) {

			System.out.println("No records Found.");

		}else {

			System.out.println(count + " events.");

		}

	}

	
	public static void main(String[] args) {

		File incidents = new File("Seattle_911_Incidents.csv");
		int count = getTotalEvents(incidents);

		System.out.println("Total Events: " + count);

		int[] ids = new int[count];
		String[] eventTypes = new String[count];
		String[] dates = new String[count];
		String[] addresses = new String[count];
		String[] sectors= new String[count];
		String[] zones = new String[count];
		loadArrays(incidents, ids, eventTypes, dates, addresses, sectors, zones);

		Scanner userScanner = new Scanner(System.in);
		int option = 0;
		while(option != 3) {

			try {

				System.out.println("+++++++++++++++++++++++++++++++++");
				System.out.println("Seattle 911 Event Search Manager: ");
				System.out.println("1- Search by Date");
				System.out.println("2- Search by Type");
				System.out.println("3- Quit");
				System.out.print("Choose a search operation: ");

				option = userScanner.nextInt();

				if(option == 1) {

					System.out.print("Enter a date (dd/mm/yy): ");
					String userDate = userScanner.next();

					printEventsDates(userDate, eventTypes, dates, addresses, sectors, zones);

				}else if(option == 2) {

					System.out.print("Enter keyword for type: ");
					String userType = userScanner.next();

					System.out.print("Enter Sector: ");
					String userSector = userScanner.next();

					printEventsTypes(userType, userSector, eventTypes, dates, addresses, sectors, zones);

				}else if(option == 3){

					System.out.print("Stay Safe!");

				}else {

					System.out.println("Please enter a valid input!");

				}

			} catch(InputMismatchException e) {
				System.out.println("Please enter a valid input!");
				userScanner.nextLine();
			}

		}

	}

}
