package savings;

import java.util.Scanner;

public class Hourly extends Employee {

	public Hourly(String lastname, String firstname, String middlename, 
			double salary, double savingsPercent, String login) {
		super(lastname, firstname, middlename, salary, savingsPercent, login);
	}

	@Override
	public double GetSavings() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nHours " + firstname + " " + lastname 
				       + " worked during Pay Period: ");
		int hours = sc.nextInt();
		double total = (hours * salary) / savingsPercent;
		System.out.println();
		return total;
	}
}