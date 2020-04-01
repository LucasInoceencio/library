package model;

import java.util.ArrayList;

public class LegalPerson extends Entity {

    private String companyName;
    private String tradingName;
    private String cnpj;
    private ArrayList<Phone> phones;
    private ArrayList<Adress> adresses;
    private String email;

    public LegalPerson() {
    }

    public LegalPerson(int idLegalPerson, String companyName, String tradingName, String cnpj, String email) {
        super(idLegalPerson);
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.cnpj = cnpj;
        this.email = email;
        this.adresses = new ArrayList<>();
        this.phones = new ArrayList<>();
    }

    public LegalPerson(String companyName, String tradingName, String cnpj, String email) {
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.cnpj = cnpj;
        this.email = email;
        this.phones = new ArrayList<>();
        this.adresses = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
        return "LegalPerson{" + "companyName=" + companyName + ", tradingName=" + tradingName + ", cnpj=" + cnpj + ", phones=" + phones + ", adresses=" + adresses + ", email=" + email + '}';
    }

}
