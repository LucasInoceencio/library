package application;

import controller.dao.LegalPersonDAO;
import controller.dao.PublisherDAO;
import java.sql.SQLException;
import model.Adress;
import model.LegalPerson;
import model.Phone;
import model.Publisher;
import model.enums.Language;

public class Program {

    public static void main(String[] args) throws SQLException {

//        Phone p1 = new Phone("64", "999807645");
//        Phone p2 = new Phone("62", "34534044");
//        
//        Adress a1 = new Adress("Alameda das Acacias", 523, "Centro", "Portao de madeira", "75690000", "Caldas Novas", "Goias");
//        
//        Publisher lp = new Publisher("Editora Cerrado LTDA", "Editora Cerrado", "12589652000185", "contato@editoracerrado.com.br");
//        lp.addAdress(a1);
//        lp.addPhone(p1);
//        lp.addPhone(p2);
//        
//        PublisherDAO.create(lp);
//        System.out.println(lp);
//        Publisher lp = PublisherDAO.retrieve(2);
//        PublisherDAO.updateExcluded(lp);
//        System.out.println(lp);
//        lp.getAdresses().forEach(adress -> {
//            System.out.println(adress.getId());
//        });
//        lp.setCompanyName("Cerrado, Ipe e Cia LTDA");
//        PublisherDAO.update(lp);
//        lp = LegalPersonDAO.retrieve(4);
//        System.out.println(lp);
        System.out.println(Language.INGLES.getId());
        System.out.println(Language.PORTUGUES.getId());
        System.out.println(Language.CHINES.getId());
        System.out.println(Language.getById(1));
    }
}
