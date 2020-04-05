package model;

import java.util.ArrayList;

public class Person extends Entity {

    private String name;
    private String cpf;
    private ArrayList<Phone> phones;
    private ArrayList<Adress> adresses;
    private String email;

    public Person() {
    }
    
    public Person(int idPerson, String name, String cpf, String email) {
        super(idPerson);
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phones = new ArrayList<>();
        this.adresses = new ArrayList<>();
    }

    public Person(String name, String cpf, String email) {
        super();
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phones = new ArrayList<>();
        this.adresses = new ArrayList<>();
    }
    
    public Person(int idPerson, String name) {
        super(idPerson);
        this.name = name;
        this.phones = new ArrayList<>();
        this.adresses = new ArrayList<>();
    }

    public Person(String name) {
        super();
        this.name = name;
        this.phones = new ArrayList<>();
        this.adresses = new ArrayList<>();
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

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public ArrayList<Adress> getAdresses() {
        return adresses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
    }
    
    public void addAdress(Adress adress) {
        adresses.add(adress);
    }
    
    public void removeAdress(Adress adress) {
        adresses.remove(adress);
    }
    
    @Override
    public String toString() {
        return name;
    }

}
