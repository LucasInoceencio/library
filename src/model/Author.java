package model;

public class Author extends Person {

    public Author() {
        super();
    }
    
    public Author(int idAuthor, String name) {
        super(idAuthor, name);
    }
    
    public Author(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return super.getName();
    }

}
