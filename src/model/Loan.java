package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.enums.LoanStatus;

public class Loan extends Entity {

    private static int deliveryTime = 72;
    private Person person;
    private List<Book> books = new ArrayList<>();
    private int numberRenewals;
    private Date deliveryDate;
    private Double lateFee;
    private LoanStatus status;

    public Loan() {
        super();
        this.numberRenewals = 0;
        this.deliveryDate = calcDeliveryDate();
        this.status = LoanStatus.ATIVO;
    }

    public Loan(Person person) {
        super();
        this.person = person;
        this.numberRenewals = 0;
        this.deliveryDate = calcDeliveryDate();
        this.status = LoanStatus.ATIVO;
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.HOUR_OF_DAY, deliveryTime);
        Date deliveryDate = cal.getTime();
        System.out.println(deliveryDate);
        return deliveryDate;
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
            increaseAllAvailableQuantity();
            setStatus(LoanStatus.ENCERRADO);
            person.removeAllBorrowedBook();
            return true;
        }
    }

}
