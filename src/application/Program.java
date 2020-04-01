package application;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.LegalPersonDAO;
import dao.LoanDAO;
import dao.PersonDAO;
import dao.PublisherDAO;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import model.Adress;
import model.Author;
import model.Book;
import model.LegalPerson;
import model.Loan;
import model.Person;
import model.Phone;
import model.Publisher;
import model.enums.Genre;
import model.enums.Language;

public class Program {

    public static void main(String[] args) throws SQLException {
//        Date d1 = Date.from(Instant.parse("2012-06-01T00:00:00Z"));
//        Phone p1 = new Phone("64", "990907656");
//        Adress a1 = new Adress("Av Cirilo Lopes de Morais", 15, "Centro", "Shopping Tropical", "75680001", "Caldas Novas", "Goias");
//        Author at1 = new Author("Ray Bradbury");
//        at1.addAdress(a1);
//        at1.addPhone(p1);
//        
//        Phone p2 = new Phone("64", "35658978");
//        Adress a2 = new Adress("Av das Nacoes", 1002, "Itaguai III", "Piso 5", "75690000", "Caldas Novas", "Goias");
//        Publisher pu1 = new Publisher("Biblioteca Azul LTDA", "Biblioteca Azul", "25369865000125", "contato@bibliotecaazul.com.br");
//        pu1.addAdress(a2);
//        pu1.addPhone(p2);
//        
//        Book b1 = new Book("Fahrenheit 451", at1, pu1, Language.PORTUGUES, "8525052248", "9788525052247", d1, Genre.FICCAO_CIENTIFICA, 3);
//
//        BookDAO.create(b1);
//        System.out.println(b1);

//        Book b = BookDAO.retrieve(2);
//        Person p = PersonDAO.retrieve(1);
//        Loan l = new Loan(p);
//        l.addBook(b);
//        LoanDAO.create(l);
//        BookDAO.updateExcluded(b);

        Loan l = LoanDAO.retrieve(3);
        System.out.println(l);
    }
}
