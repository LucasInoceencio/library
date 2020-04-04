package model;

import dao.UserDAO;
import java.sql.SQLException;
import jdbc.DBConfig;

public class User extends Entity {

    private String username;
    private String password;
    private Person person;

    public User() {
    }

    public User(int idUser, String username, Person person) {
        super(idUser);
        this.username = username;
        this.person = person;
    }

    public User(int idUser, String username, String password) {
        super(idUser);
        this.username = username;
        this.password = password;
    }

    public User(int pkUser, String username) {
        super(pkUser);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public int getPersonId() {
        return getPerson().getId();
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    public static boolean logar(String username, String password) throws SQLException {
        User user = UserDAO.retrieveByUser(username);
        DBConfig.idUserLogged = user.getId();
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", person=" + person + '}';
    }

}
