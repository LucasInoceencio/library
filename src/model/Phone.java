package model;

public class Phone extends Entity {

    private String ddd;
    private String number;

    public Phone() {
    }

    public Phone(int idPhone, String ddd, String number) {
        super(idPhone);
        this.ddd = ddd;
        this.number = number;
    }

    public Phone(String ddd, String number) {
        this.ddd = ddd;
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" + "ddd=" + ddd + ", number=" + number + '}';
    }

}
