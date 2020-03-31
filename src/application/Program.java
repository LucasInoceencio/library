package application;

import controller.dao.LegalPersonDAO;
import java.sql.SQLException;
import model.Adress;
import model.LegalPerson;
import model.Phone;

public class Program {

    public static void main(String[] args) throws SQLException {

//        Phone p1 = new Phone("64", "999807645");
//        Phone p2 = new Phone("62", "34534044");
//        
//        Adress a1 = new Adress("Alameda das Acacias", 523, "Centro", "Portao de madeira", "75690000", "Caldas Novas", "Goias");
//        
//        LegalPerson lp = new LegalPerson("Editora Cerrado LTDA", "Editora Cerrado", "12589652000185", "contato@editoracerrado.com.br");
//        lp.setAdress(a1);
//        lp.addPhone(p1);
//        lp.addPhone(p2);
//        
//        LegalPersonDAO.create(lp);

        LegalPerson lp = LegalPersonDAO.retrieve(3);
        System.out.println(lp);
        lp.setCompanyName("Cerrado e Cia LTDA");
        LegalPersonDAO.update(lp);
        lp = LegalPersonDAO.retrieve(3);
        System.out.println(lp);
    }
}
