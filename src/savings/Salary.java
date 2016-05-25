package savings;

public class Salary extends Employee {

	public Salary (String lastname, String firstname, String middlename, 
			double salary, double savingsPercent, String login) {
		super(lastname, firstname, middlename, salary, savingsPercent, login);
		
	}

	@Override
	public double GetSavings() {
		double total = (salary / 24) + (salary / savingsPercent);
		return total;
	}
}