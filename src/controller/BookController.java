package controller;

import application.AuthorFX;
import application.BookFX;
import dao.AuthorDAO;
import dao.BookDAO;
import dao.PublisherDAO;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.Publisher;
import model.enums.Genre;
import model.enums.Language;

public class BookController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<Author> cbAuthor;

    @FXML
    private Button btnAddAuthor;

    @FXML
    private ComboBox<Publisher> cbPublisher;

    @FXML
    private Button btnAddPublisher;

    @FXML
    private DatePicker dpDatePublication;

    @FXML
    private TextField tfIsbn10;

    @FXML
    private TextField tfIsbn13;

    @FXML
    private ComboBox<Language> cbLanguage;

    @FXML
    private ComboBox<Genre> cbGenre;

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
        newAuthor();
    }

    @FXML
    void actionCancel(ActionEvent event) {
        close();
    }

    @FXML
    void actionSave(ActionEvent event) {
        if (verifyArguments()) {
            newBook();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
            alert.showAndWait();
        }
    }

    private static Book book;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ObservableList<Author> authorList = FXCollections.observableArrayList(AuthorDAO.retrieveAll());
            cbAuthor.setItems(authorList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar autores.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }

        try {
            ObservableList<Publisher> publisherList = FXCollections.observableArrayList(PublisherDAO.retrieveAll());
            cbPublisher.setItems(publisherList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar autores.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }

        ObservableList<Genre> genreList = FXCollections.observableArrayList(Genre.getAll());
        cbGenre.setItems(genreList);

        ObservableList<Language> languageList = FXCollections.observableArrayList(Language.getAll());
        cbLanguage.setItems(languageList);

        if (book != null) {
            initBook();
        }

        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                close();
            }
        });

        btnAddPublisher.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {

            }
        });

        btnSave.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                if (verifyArguments()) {
                    newBook();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
                    alert.showAndWait();
                }
            }
        });

        btnAddAuthor.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                newAuthor();
            }
        });
    }

    public void initBook() {
        tfName.setText(book.getName());
        cbAuthor.getSelectionModel().select(book.getAuthor());
        cbPublisher.getSelectionModel().select(book.getPublisher());
        tfIsbn10.setText(book.getIsbn10());
        tfIsbn13.setText(book.getIsbn13());
        cbGenre.getSelectionModel().select(book.getGenre());
        cbLanguage.getSelectionModel().select(book.getLanguage());
        tfAvailableQuantity.setText(String.valueOf(book.getAvailableQuantity()));
        dpDatePublication.setValue(convertToLocalDate(book.getDatePublication()));
    }

    public static Book getBook() {
        return book;
    }

    public static void setBook(Book book) {
        BookController.book = book;
    }

    public void close() {
        BookFX.getStage().close();
    }
    
    public void newAuthor() {
        AuthorFX author = new AuthorFX();
        author.start(new Stage());
    }

    public void newBook() {
        Book newBook = new Book(
                tfName.getText(),
                cbAuthor.getSelectionModel().getSelectedItem(),
                cbPublisher.getSelectionModel().getSelectedItem(),
                cbLanguage.getSelectionModel().getSelectedItem(),
                tfIsbn10.getText(),
                tfIsbn13.getText(),
                Date.valueOf(dpDatePublication.getValue()),
                cbGenre.getSelectionModel().getSelectedItem(),
                Integer.parseInt(tfAvailableQuantity.getText())
        );
        if (book != null) {
            try {
                newBook.setId(book.getId());
                BookDAO.update(newBook);
                close();
            } catch (SQLException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                alertDAO.setTitle("Erro");
                alertDAO.setHeaderText("Erro ao atualizar o livro.");
                alertDAO.setContentText(ex.getMessage());
                alertDAO.showAndWait();
            }
        } else {
            try {
                BookDAO.createOnlyBook(newBook);
            } catch (SQLException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                alertDAO.setTitle("Erro");
                alertDAO.setHeaderText("Erro ao criar o livro.");
                alertDAO.setContentText(ex.getMessage());
                alertDAO.showAndWait();
            }
        }
    }

    public boolean verifyArguments() {
        return !(tfName.getText().equals("") || cbPublisher.getSelectionModel().getSelectedItem() == null
                || cbAuthor.getSelectionModel().getSelectedItem() == null || tfIsbn10.getText().equals("")
                || tfIsbn13.getText().equals("") || cbLanguage.getSelectionModel().getSelectedItem() == null
                || cbGenre.getSelectionModel().getSelectedItem() == null || dpDatePublication.getValue() == null
                || tfAvailableQuantity.getText().equals(""));
    }

    public LocalDate convertToLocalDate(java.util.Date aux) {
        Instant instant = Instant.ofEpochMilli(aux.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }
}
