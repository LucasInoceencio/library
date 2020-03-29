package model;

public class Adress extends Entity {

    private String publicPlace;
    private int number;
    private String neighborhood;
    private String complement;
    private String cep;

    public Adress() {
    }

    public Adress(int idAdress, String publicPlace, int number, String neighborhood, String complement, String cep) {
        super(idAdress);
        this.publicPlace = publicPlace;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.cep = cep;
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

}
