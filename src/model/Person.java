package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {
	
	private Long id;
	private String name;
	private String cpf;
	private List<String> phones = new ArrayList<>();
	private Adress adress;
	private String email;
	private Date dateHourInclusion;
	private Date dateHourChange;
	private Date dateHourDeletion;
	private User userWhoIncluded;
	private User userWhoChanged;
	private User userWhoDeleted;
	private boolean excluded;
	
	public Person() {}
	
	public Person(Long id, String name, String cpf, List<String> phones, Adress adress, String email) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.phones = phones;
		this.adress = adress;
		this.email = email;
	}
	
	public Person(String name, String cpf, Adress adress, String email) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.adress = adress;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDateHourInclusion() {
		return dateHourInclusion;
	}

	public void setDateHourInclusion(Date dateHourInclusion) {
		this.dateHourInclusion = dateHourInclusion;
	}

	public Date getDateHourChange() {
		return dateHourChange;
	}

	public void setDateHourChange(Date dateHourChange) {
		this.dateHourChange = dateHourChange;
	}

	public Date getDateHourDeletion() {
		return dateHourDeletion;
	}

	public void setDateHourDeletion(Date dateHourDeletion) {
		this.dateHourDeletion = dateHourDeletion;
	}

	public User getUserWhoIncluded() {
		return userWhoIncluded;
	}

	public void setUserWhoIncluded(User userWhoIncluded) {
		this.userWhoIncluded = userWhoIncluded;
	}

	public User getUserWhoChanged() {
		return userWhoChanged;
	}

	public void setUserWhoChanged(User userWhoChanged) {
		this.userWhoChanged = userWhoChanged;
	}

	public User getUserWhoDeleted() {
		return userWhoDeleted;
	}

	public void setUserWhoDeleted(User userWhoDeleted) {
		this.userWhoDeleted = userWhoDeleted;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

}
