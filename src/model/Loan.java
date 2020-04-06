package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.enums.LoanStatus;

public class Loan extends Entity {

    private static int deliveryTime = 72;
    private Person person;
    private ArrayList<Book> books;
    private int numberRenewals;
    private Date deliveryDate;
    private Date deliveredDate;
    private Double lateFee;
    private LoanStatus status;

    public Loan(int idLoan, int numberRenewals, Date deliveryDate, LoanStatus status) {
        super(idLoan);
        this.numberRenewals = numberRenewals;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.books = new ArrayList<>();
    }

    public Loan(int numberRenewals, Date deliveryDate, LoanStatus status) {
        super();
        this.numberRenewals = numberRenewals;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.books = new ArrayList<>();
    }

    public Loan(int idLoan, Person person, int numberRenewals, Date deliveryDate, Date deliveredDate, Double lateFee, LoanStatus status) {
        super(idLoan);
        this.person = person;
        this.numberRenewals = numberRenewals;
        this.deliveryDate = deliveryDate;
        this.deliveredDate = deliveredDate;
        this.lateFee = lateFee;
        this.status = status;
        this.books = new ArrayList<>();
    }

    public Loan(int idLoan, Person person, int numberRenewals, Date deliveryDate, Double lateFee, LoanStatus status) {
        super(idLoan);
        this.person = person;
        this.numberRenewals = numberRenewals;
        this.deliveryDate = deliveryDate;
        this.lateFee = lateFee;
        this.status = status;
        this.books = new ArrayList<>();
    }

    public Loan(Person person, int numberRenewals, Date deliveryDate, LoanStatus status) {
        super();
        this.person = person;
        this.numberRenewals = numberRenewals;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.books = new ArrayList<>();
    }

    public Loan(Person person) {
        super();
        this.person = person;
        this.numberRenewals = 0;
        this.deliveryDate = this.calcDeliveryDate();
        this.status = LoanStatus.ATIVO;
        this.books = new ArrayList<>();
        this.lateFee = 0.0;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getNumberRenewals() {
        return numberRenewals;
    }

    public void setNumberRenewals(int numberRenewals) {
        this.numberRenewals = numberRenewals;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public void addBook(Book book) {
        books.add(book);
        book.decreaseAvailableQuantity();
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.increaseAvailableQuantity();
    }

    public Date calcDeliveryDate() {
        Date currentDate = new Date();
        Calendar auxCal = Calendar.getInstance();
        auxCal.setTime(currentDate);
        auxCal.add(Calendar.HOUR_OF_DAY, deliveryTime);
        Date auxDeliveryDate;
        switch (auxCal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                auxCal.add(Calendar.HOUR_OF_DAY, 24);
                auxDeliveryDate = auxCal.getTime();
                break;
            case 7:
                auxCal.add(Calendar.HOUR_OF_DAY, 48);
                auxDeliveryDate = auxCal.getTime();
                break;

            default:
                auxDeliveryDate = auxCal.getTime();
                break;
        }
        return auxDeliveryDate;
    }

    public boolean renewLoan() {
        if (this.numberRenewals <= 2 && status.toString().equalsIgnoreCase("ATIVO")) {
            this.numberRenewals++;
            this.deliveryDate = calcDeliveryDate();
            return true;
        }
        System.out.println("Quantidade máxima de renovações atingida!");
        return false;
    }

    public void increaseAllAvailableQuantity() {
        books.forEach((item) -> {
            item.increaseAvailableQuantity();
        });
    }

    public void verifyDeliveryDate() {
        Date currentDate = new Date();
        if (currentDate.after(deliveryDate)) {
            lateFee = 5.00;
        }
    }

    public boolean endLoan() {
        if (status.toString().equalsIgnoreCase("ENCERRADO")) {
            return false;
        } else {
            verifyDeliveryDate();
            deliveredDate = new Date();
            increaseAllAvailableQuantity();
            setStatus(LoanStatus.ENCERRADO);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Loan{" + "person=" + person + ", books=" + books + ", numberRenewals=" + numberRenewals + ", deliveryDate=" + deliveryDate + ", lateFee=" + lateFee + ", status=" + status + '}';
    }

}
