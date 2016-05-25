package savings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

/* Makes Employee a Superclass. */

public abstract class Employee implements Serializable {

	public abstract double 			GetSavings();
	static int			 			nextID = 0;
	protected final int 			id;
	protected String 				lastname, firstname, middlename;
	protected String 				login;
	protected double 				salary;
	protected double 				savingsPercent;
	protected Boolean 				isEmployee;
	protected String 				sDate;
	protected Date 					d 	= new Date();
	protected SimpleDateFormat 		df 	= new SimpleDateFormat("dd-MM-yyyy");

//-----------------------------------------------------------------------------
	
	/* New Employee/Boss - First Constructor: Assigns new employee an 
	 * incremented UID after retrieving the next ID from the next employee 
	 * and the remaining user input (name, login, etc.)
	 */
	
	public Employee(String lastname, String firstname, String middlename, 
					double salary, double savingsPercent, String login) {
		
		id = nextID; nextID++;
		
		this.lastname 		= lastname;
		this.firstname 		= firstname;
		this.middlename 	= middlename;
		this.salary 		= salary;
		this.savingsPercent = savingsPercent;
		this.login 			= login;
		
		isEmployee 			= true;
		sDate 				= df.format(d);
		
		//System.out.println(toString() );
	}
	
	/*Employee - Second Constructor: Retrieves existing data. 
	 * Initializes it based on the existing file and adds it to the array list. Uses this
	 * constructor if the input from main matches the input listed in this
	 * constructor.
	 */
	
	public Employee(int id, String lastname, String firstname,
			String middlename, String login, double salary,
			double savingsPercent, Boolean isEmployee, String sDate) {	

		this.id 			= id;
		this.lastname 		= lastname;
		this.firstname 		= firstname;
		this.middlename 	= middlename;
		this.login 			= login;
		this.salary 		= salary;
		this.savingsPercent = savingsPercent;
		this.isEmployee 	= isEmployee;
		this.sDate 			= sDate;
		
	}
	
//-----------------------------------------------------------------------------
	
	public String toString() {
		String sTo = String.format("%05d %s %s %s %s %.2f %.2f %b %s",
				id, lastname, firstname, middlename, login, salary, 
				savingsPercent, isEmployee, sDate);
		return sTo;
	}
	
//-----------------------------------------------------------------------------

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLogin() {
		return login;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public void setSavingsPercent(double savingsPercent) {
		this.savingsPercent = savingsPercent;
	}
}