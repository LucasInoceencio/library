package model;

import java.util.ArrayList;
import java.util.List;

public class LegalPerson extends Entity {

    private String companyName;
    private String tradingName;
    private String cnpj;
    private List<String> phones = new ArrayList<>();
    private Adress adress;
    private String email;

    public LegalPerson() {
    }

    public LegalPerson(int idLegalPerson, String companyName, String tradingName, String cnpj, Adress adress, String email) {
        super(idLegalPerson);
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.cnpj = cnpj;
        this.adress = adress;
        this.email = email;
    }
    
    public LegalPerson(int idLegalPerson, String companyName, String tradingName, String cnpj, String email) {
        super(idLegalPerson);
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.cnpj = cnpj;
        this.email = email;
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

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
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

}
