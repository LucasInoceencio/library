package model;

public class Adress extends Entity {

    private String publicPlace;
    private int number;
    private String neighborhood;
    private String complement;
    private String cep;
    private String city;
    private String state;

    public Adress() {
    }

    public Adress(int idAdress, String publicPlace, int number, String neighborhood, String complement, String cep, String city, String state) {
        super(idAdress);
        this.publicPlace = publicPlace;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.cep = cep;
        this.city = city;
        this.state = state;
    }
    
    public Adress(String publicPlace, int number, String neighborhood, String complement, String cep, String city, String state) {
        super();
        this.publicPlace = publicPlace;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.cep = cep;
        this.city = city;
        this.state = state;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Adress{" + "publicPlace=" + publicPlace + ", number=" + number + ", neighborhood=" + neighborhood + ", complement=" + complement + ", cep=" + cep + ", city=" + city + ", state=" + state + '}';
    }
}
