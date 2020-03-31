package model;

public class Author extends Person {

    public Author() {
        super();
    }

    public Author(int idAuthor, String name, String cpf, String email) {
        super(idAuthor, name, cpf, email);
    }
    
    public Author(String name, String cpf, String email) {
        super(name, cpf, email);
    }
    
    public Author(int idAuthor, String name) {
        super(idAuthor, name);
    }
    
    public Author(String name) {
        super(name);
    }

}
