package model;

import java.util.ArrayList;
import java.util.List;

public class Person extends Entity {
	
	private String name;
	private String cpf;
	private List<String> phones = new ArrayList<>();
	private Adress adress;
	private String email;
	private List<Book> borrowedBooks = new ArrayList<>();
	
	public Person() {}
	
	public Person(String name, String cpf, Adress adress, String email) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.adress = adress;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<String> getPhones() {
		return phones;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void addPhone(String phone) {
		phones.add(phone);
	}
	
	public void removePhone(String phone) {
		phones.remove(phone);
	}
	
	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void addBorrowedBook(Book book) {
		borrowedBooks.add(book);
	}
	
	public void removeBorrowedBook(Book book) {
		borrowedBooks.remove(book);
	}

}
