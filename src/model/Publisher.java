package model;

public class Publisher extends LegalPerson {

    public Publisher() {
        super();
    }

    public Publisher(int idPublisher, String companyName, String tradingName, String cnpj, Adress adress, String email) {
        super(idPublisher, companyName, tradingName, cnpj, adress, email);
    }

    public Publisher(int idPublisher, String companyName, String tradingName, String cnpj, String email) {
        super(idPublisher, companyName, tradingName, cnpj, email);
    }
    
}
