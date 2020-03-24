package application;

import java.util.TimeZone;

import model.Loan;
import model.Person;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person p = new Person("Lucas");
		Loan l = new Loan(p);
		l.calcDeliveryDate();
		
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		
		l.calcDeliveryDate();
		
		System.out.println(l.getStatus());
		
		l.endLoan();
		
		System.out.println(l.getStatus());
		System.out.println(l.getLateFee());
	}

}
