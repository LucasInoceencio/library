package model;

import java.util.Date;
import java.util.List;

import model.enums.Genre;
import model.enums.Language;

public class Book extends Entity {

	private String name;
	private Author author;
	private Publisher publisher;
	private Language language;
	private String isbn10;
	private String isbn13;
	private Date datePublication;
	private List<Genre> genre;
	private int availableQuantity;
	
	public Book() {}
	
	public Book(String name, Author author, Publisher publisher, Language language, String isbn10, String isbn13,
			Date datePublication, List<Genre> genre, int availableQuantity) {
		super();
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.language = language;
		this.isbn10 = isbn10;
		this.isbn13 = isbn13;
		this.datePublication = datePublication;
		this.genre = genre;
		this.availableQuantity = availableQuantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public List<Genre> getGenre() {
		return genre;
	}

	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}

	public int getQuantity() {
		return availableQuantity;
	}

	public void setQuantity(int quantity) {
		this.availableQuantity = quantity;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
	public void increaseAvailableQuantity() {
		this.availableQuantity++;
	}
	
	public void decreaseAvailableQuantity() {
		this.availableQuantity--;
	}
	
}
