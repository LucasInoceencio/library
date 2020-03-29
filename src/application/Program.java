package application;

import java.util.Date;
import java.util.TimeZone;

import model.Loan;
import model.Person;

public class Program {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Person p = new Person("Lucas");
        Loan l = new Loan(p);
        l.calcDeliveryDate();

        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

        l.calcDeliveryDate();

        System.out.println("Status inicial: " + l.getStatus());
        System.out.println("Encerrando emprestimo");
        System.out.println(l.endLoan());
        System.out.println("Status apos encerrado: " + l.getStatus());
        System.out.println(l.endLoan());

        Date d = new Date();
        System.out.println(d);
    }
}
