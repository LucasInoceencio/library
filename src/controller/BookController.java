
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Book;

public class BookController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<?> cbAuthor;

    @FXML
    private Button btnAddPublisher;

    @FXML
    private ComboBox<?> cbPublisher;

    @FXML
    private DatePicker dpDatePublication;

    @FXML
    private TextField tfisbn10;

    @FXML
    private TextField isbn13;

    @FXML
    private ComboBox<?> cbLanguage;

    @FXML
    private ComboBox<?> cbGenre;

    @FXML
    private TextField tfAvailableQuantity;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    void actionAddPublisher(ActionEvent event) {

    }

    @FXML
    void actionAddAuthor(ActionEvent event) {

    }
    
    @FXML
    void actionCancel(ActionEvent event) {

    }

    @FXML
    void actionSave(ActionEvent event) {

    }
    
    private static Book book;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initBook();
    }   
    
    public void initBook() {
        tfName.setText(book.getName());
    }

    public static Book getBook() {
        return book;
    }

    public static void setBook(Book book) {
        BookController.book = book;
    }
    
}
