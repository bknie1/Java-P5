/** @author Brandon Knieriem
 * @date	04/06/2016
 * @modules	Savings.java, Employee.java, Salary.java, Hourly.java
 */

package savings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class Savings.
 */
public class Savings {

	/** The id. */
	int 						id;
	
	/** The lastname. */
	static String 				lastname;
	
	/** The firstname. */
	static String 				firstname;
	
	/** The middlename. */
	static String 				middlename;
	
	/** The login. */
	static String 				login;
	
	/** The current login. */
	static String				currentLogin;
	
	/** The salary. */
	static double 				salary;
	
	/** The savingspercent. */
	static double 				savingspercent;
	
	/** The is employee. */
	Boolean 					isEmployee;
	
	/** The emp check. */
	static Boolean 				empCheck = false;
	
	/** The boss check. */
	static Boolean				bossCheck = false;
	
	/** The date. */
	String 						date;
	
	/** The emp list. */
	static ArrayList<Employee> 	empList = new ArrayList<Employee>();
	
	/** The term list. */
	static ArrayList<Employee> 	termList = new ArrayList<Employee>();
	
	/** The sc in. */
	static Scanner 				scIn;
	
	/** The sc read. */
	Scanner 					scRead;
	
	/** The pw. */
	static PrintWriter 			pw;
	
	/** The boss menu. */
	private static String bossMenu = "\n\tSavings System Menu\n"
			+ "1. Enter Employees\n"
			+ "2. List Employees\n"
			+ "3. Change Employee Data\n"
			+ "4. Terminate Employee\n"
			+ "5. Report Employee Savings\n"
			+ "0. Exit System\n"
			+ "User Selection: ";
	
	/** The emp menu. */
	private static String empMenu = "\n\tSavings System Menu\n"
			+ "1. List Personal Data\n"
			+ "2. Report Employee Savings\n"
			+ "3. Termination\n"
			+ "0. Exit System\n"
			+ "User Selection: ";
	
	/** The change menu. */
	private static String changeMenu = 
			  "1. Last Name\n"
			+ "2. Salary\n"
			+ "0. Return to Main Menu\n"
			+ "User Selection: ";
	
	/**
	 * Instantiates a new savings.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public Savings() throws IOException, ClassNotFoundException {
		try {
			/* Just like putting file inside of Scanner.
			 * Reading in the file as objects and storing them in empList*/
			FileInputStream fis = new FileInputStream("Employee.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Employee.nextID = ois.readInt();
			empList  = (ArrayList<Employee>)ois.readObject();
			ois.close();
			}
		catch(FileNotFoundException e) {
			scIn = new Scanner(System.in);
			System.out.println("ERROR: File not found. "
							 + "Please enter Salary Boss credentials: ");
			System.out.print("Last Name: ");
			lastname 		= scIn.next().trim();
			System.out.print("First Name: ");
			firstname 		= scIn.next().trim();
			System.out.print("Middle Initial: ");
			middlename 		= scIn.next().trim();
			System.out.print("Login (6+ Characters): ");
			login 			= scIn.next().trim();
			System.out.print("Salary: ");
			salary 			= scIn.nextDouble();
			System.out.print("Savings %: ");
			savingspercent 	= scIn.nextDouble();

			Salary bossEmp = new Salary(lastname, firstname, middlename, 
					salary, savingspercent, login);
			
			empList.add(bossEmp);
		}
		try {
			FileOutputStream fos = new FileOutputStream("Employee.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeInt(Employee.nextID);
			oos.writeObject(empList);
			oos.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: I/O Exception.");
		}
	}
		
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws ClassNotFoundException, 
												  IOException {
		System.out.println("\tEmployee Management System");
		Savings s = new Savings();
		for(;;) {
			doLogin();
			if(empCheck == true) {break;}
			denied();
		}
		if(bossCheck == true) {doBossMenu();}
		if(empCheck == true && bossCheck == false) {doEmpMenu();}
	}

/**
 * Boss Specific Menu
 *
 * @throws IOException Signals that an I/O exception has occurred.
 */
//-----------------------------------------------------------------------------
	private static void doBossMenu() throws IOException {
		int iUSel = 9;
		try {
			do {
				Scanner sc = new Scanner(System.in);
				System.out.print(bossMenu);
				iUSel = 0;
				iUSel = sc.nextInt();
				switch (iUSel) {
					//Enter
					case 1  : 	doEnter();
								break;
					//List
					case 2  : 	doList();
							 	break;
					//Change
					case 3  : 	doChange();
							 	break;
					//Terminate
					case 4  : 	doTerminate();
							 	break;
					//Report
					case 5  : 	doReport();
							 	break;
					//Exit
					case 0  : 	System.exit(0);
					
					default : 	System.out.println("Invalid selection.");
				}
			} while (iUSel != 0);
		}
		finally {
			FileOutputStream fos = new FileOutputStream("Employee.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeInt(Employee.nextID);
			oos.writeObject(empList);
			oos.close();
			
			FileOutputStream tfos = new FileOutputStream("Terminated.txt");
			ObjectOutputStream toos = new ObjectOutputStream(tfos);
			toos.writeObject(termList);
			toos.close();
		}
	}
	
	/**
	 * Employee Specific Menu
	 */
	private static void doEmpMenu() {
		int iUSel = 9;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.print(empMenu);
			iUSel = 0;
			iUSel = sc.nextInt();
			switch (iUSel) {
				//List
				case 1  : 	doListPersonal();
						 	break;
				//Report
				case 2  : 	doPersonalReport();
						 	break;
				//Terminate Self
				case 3  : 	doTerminateSelf();
						 	break;
				//Exit
				case 0  : 	System.exit(0);
				
				default : 	System.out.println("Invalid selection.");
			}
		} while (iUSel != 0);
	}

/**
 * Employee Login Menu
 */
//-----------------------------------------------------------------------------
	private static void doLogin() {
		Scanner sc = new Scanner(System.in);
		String tempLogin;
		
		System.out.print("Enter your login: ");
		tempLogin = sc.next().trim();

		for(Employee e : empList) {
			if(e.login.equals(tempLogin) ) {
				System.out.println("Login successful. "
								 + "Welcome back " + e.firstname + ".");
				currentLogin = tempLogin;
				empCheck = true;
				if(e.id == 0) {bossCheck = true;}
				break;
			}
		}
	}
	
	/**
	 * Enter Employee Menu
	 */
	private static void doEnter() {
		
		int iUSel = 0;
		int matchcount = 0;
		scIn = new Scanner(System.in);
		
		for(;;){
			System.out.print("Enter a Salary (1) or Hourly Employee (2): ");
			iUSel = scIn.nextInt();
			if(iUSel == 1 || iUSel == 2) {break;}
			else {System.out.println("ERROR: Invalid selection.");}
		}
		
		System.out.print("\nEnter Last Name: ");
		lastname 		= scIn.next().trim();
		System.out.print("\nEnter First Name: ");
		firstname 		= scIn.next().trim();
		System.out.print("\nEnter Middle Initial: ");
		middlename 		= scIn.next().trim();
		
		/*Unique Login Assurance - Could have used Contains?*/
		for(;;) {
			matchcount = 0;
			for(;;) {
				System.out.print("\nEnter Login (6+ Characters): ");
				login 			= scIn.next().trim();
				if (login.length() >= 6) {break;}
				else System.out.println("ERROR: Login less than 6 characters.");
			}
			
			for (Employee e : empList) {
				if (e.login.equals(login) ) {matchcount++;}
			}
			if(matchcount == 0) {break;}
			System.out.println("ERROR: Match detected. Use another login.");
		}
		
		if(iUSel == 1) {System.out.print("\nEnter Salary: ");}
		if(iUSel == 2) {System.out.print("Enter Hourly Rate: ");}
		salary 			= scIn.nextDouble();
		System.out.print("\nEnter Savings %: ");
		savingspercent 	= scIn.nextDouble();
		
		if(iUSel == 1) {
		Employee newEmp = new Salary(lastname, firstname, middlename, 
				salary, savingspercent, login);
		empList.add(newEmp);
		}
		if(iUSel == 2) {
		Employee newEmp = new Hourly(lastname, firstname, middlename, 
				salary, savingspercent, login);
		empList.add(newEmp);
		}
		
		try {
			pw = new PrintWriter(new File("Employee.txt") );
			pw.println(Employee.nextID);
			for(Employee e : empList) {
				pw.println(e.toString() );
			}
			pw.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: I/O Exception.");
		}
	}
	
	/**
	 * Boss List Employees
	 */
	private static void doList() {
		for(Employee e : empList) {System.out.println(e.toString() );}
	}
	
	/**
	 * Employee Personal Information List
	 */
	private static void doListPersonal() {
		for(Employee e : empList) {
			if(e.login.equals(currentLogin) ) {
				System.out.println(e.toString() );}
			}
	}
	
	/**
	 * Change Employee Menu
	 */
	private static void doChange() {
		int iUSel = 9;
		String editUser;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\tEmployee Change Menu");
		System.out.print("Who would you like to edit (login)? ");
		editUser = sc.next();
		do {
			System.out.println();
			System.out.print(changeMenu);
			iUSel = 0;
			System.out.print("Selection: ");
			iUSel = sc.nextInt();
			switch (iUSel) {
				//Last Name
				case 1  : for(Employee e : empList) {
							if(e.login.equals(editUser) ) {
								System.out.println("Current Credentials:\n");
								System.out.println(e.toString() );
								System.out.print("Change last name: ");
								e.lastname = sc.next();
							}
						  }
						  break;
				//Salary
				case 2  : for(Employee e : empList) {
							if(e.login.equals(editUser) ) {
								System.out.println("Current Credentials:\n");
								System.out.println(e.toString() );
								System.out.print("Change salary/rate: ");
								e.salary = sc.nextInt();
							}
						  }
					 	  break;
				//Exit
				case 0  : break;
				
				default : System.out.println("Invalid selection.");
			}
		} while (iUSel != 0);
	}
	
	/**
	 * Boss Terminate Menu for all Employees
	 */
	private static void doTerminate() {
		int sel;
		String answer;
		Scanner sc = new Scanner(System.in);

		System.out.println("Do you wish to terminate someone ()? ");
		System.out.println	("0. No\n"
				+ 			 "1. Yes");
		System.out.print("Selection: ");
		sel = sc.nextInt();
		if(sel == 1) {
			System.out.print("Who do you wish to terminate (login)? ");
			answer = sc.next();
			for(Employee e : empList) {
				if(answer == e.login) {e.isEmployee = false;}
				System.out.println("Terminated.");
				termList.add(e);
				empList.remove(e);
			}
		}
		else System.out.println("Returning to main menu.");
	}
	
	/**
	 * Terminate one's own employement.
	 */
	private static void doTerminateSelf() {
		int answer;
		Scanner sc = new Scanner(System.in);
		System.out.print("Do you wish to terminate yourself (yes/no)? ");
		System.out.println	("0. No\n"
				+ 			 "1. Yes");
		System.out.print("Selection: ");
		answer = sc.nextInt();
		if(answer == 1) {
			for(Employee e : empList) {
				if(currentLogin == e.login) {e.isEmployee = false;}
				System.out.println("Terminated.");
				termList.add(e);
				empList.remove(e);
			}
		}
		else System.out.println("Returning to main menu.");
	}
	
	/**
	 * Boss Report Menu
	 */
	private static void doReport() {
		double savings;
		System.out.println("\tSavings Contribution Report\n");
		for(Employee e : empList) {
			savings = e.GetSavings();
			System.out.print(e.lastname + ", " + e.firstname);
			System.out.printf("\tSavings: $%.2f\n", savings);
		}
	}
	
	/**
	 * Generate a Personal Report.
	 */
	private static void doPersonalReport() {
		double savings;
		System.out.println("\tPersonal Savings Contribution Report\n");
		for(Employee e : empList) {
			if(e.login.equals(currentLogin) ) {
				savings = e.GetSavings();
				System.out.print(e.lastname + ", " + e.firstname);
				System.out.printf("\tSavings: $%.2f\n", savings);
			}
		}
	}
	
	/**
	 * Denied.
	 */
	private static void denied() {
		System.out.println("ERROR: Access denied. Try again.");
	}
}
