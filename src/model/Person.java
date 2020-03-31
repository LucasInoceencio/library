package model;

import java.util.ArrayList;

public class Person extends Entity {

    private String name;
    private String cpf;
    private ArrayList<Phone> phones;
    private Adress adress;
    private String email;

    public Person() {
    }

    public Person(String name, String cpf, Adress adress, String email) {
        super();
        this.name = name;
        this.cpf = cpf;
        this.adress = adress;
        this.email = email;
        this.phones = new ArrayList<>();
    }

    public Person(String name) {
        super();
        this.name = name;
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

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
    }
}
