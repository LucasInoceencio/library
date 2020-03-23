package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Loan extends Entity {
	
	private Person person;
	private List<Book> books = new ArrayList<>();
	private int numberRenewals;
	private Date deliveryDate;
	private Double lateFee;
}
