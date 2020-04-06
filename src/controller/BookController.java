package controller;

import application.AuthorFX;
import application.BookFX;
import application.PublisherFX;
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
import javafx.stage.WindowEvent;
import model.Author;
import model.Book;
import model.Publisher;
import model.enums.Genre;
import model.enums.Language;

public class BookController implements Initializable {

    private static Book book;

    public static Book getBook() {
        return book;
    }

    public static void setBook(Book book) {
        BookController.book = book;
    }

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
        newPublisher();
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
        if (mandatoryFieldsBookNotFilled()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
            alert.showAndWait();
        } else if (isbn10ExceededSize()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo ISBN10 não pode ter mais do que 10 caracteres.");
            alert.showAndWait();
        } else if (isbn13ExceededSize()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo ISBN13 não pode ter mais do que 13 caracteres.");
            alert.showAndWait();
        } else {
            newBook();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ObservableList<Author> authorList = FXCollections.observableArrayList(AuthorDAO.retrieveAllExcluded(false));
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
            ObservableList<Publisher> publisherList = FXCollections.observableArrayList(PublisherDAO.retrieveAllExcluded(false));
            cbPublisher.setItems(publisherList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar editoras.");
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
                newPublisher();
            }
        });

        btnSave.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                if (mandatoryFieldsBookNotFilled()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
                    alert.showAndWait();
                } else if (isbn10ExceededSize()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo ISBN10 não pode ter mais do que 10 caracteres.");
                    alert.showAndWait();
                } else if (isbn13ExceededSize()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo ISBN13 não pode ter mais do que 13 caracteres.");
                    alert.showAndWait();
                } else {
                    newBook();
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                    stage.close();
                }
            }
        });

        btnAddAuthor.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                newAuthor();
            }
        });
    }

    private void initBook() {
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

    private void close() {
        BookFX.getStage().close();
    }

    private void newAuthor() {
        AuthorFX author = new AuthorFX();
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            refreshAuthors();

        });
        author.start(stage);
    }

    private void newPublisher() {
        PublisherFX publisher = new PublisherFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            refreshPublishers();
        });
        publisher.start(stage);
    }

    private void refreshPublishers() {
        try {
            ObservableList<Publisher> publisherList = FXCollections.observableArrayList(PublisherDAO.retrieveAllExcluded(false));
            cbPublisher.setItems(publisherList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar autores.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }
    }

    private void refreshAuthors() {
        try {
            ObservableList<Author> authorList = FXCollections.observableArrayList(AuthorDAO.retrieveAllExcluded(false));
            cbAuthor.setItems(authorList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar autores.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }
    }

    private void newBook() {
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

    private boolean mandatoryFieldsBookNotFilled() {
        return (tfName.getText().equals("") || cbPublisher.getSelectionModel().getSelectedItem() == null
                || cbAuthor.getSelectionModel().getSelectedItem() == null || tfIsbn10.getText().equals("")
                || tfIsbn13.getText().equals("") || cbLanguage.getSelectionModel().getSelectedItem() == null
                || cbGenre.getSelectionModel().getSelectedItem() == null || dpDatePublication.getValue() == null
                || tfAvailableQuantity.getText().equals(""));
    }

    private boolean isbn10ExceededSize() {
        return (tfIsbn10.getText().length() > 10);
    }

    private boolean isbn13ExceededSize() {
        return (tfIsbn13.getText().length() > 13);
    }

    private LocalDate convertToLocalDate(java.util.Date aux) {
        Instant instant = Instant.ofEpochMilli(aux.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }
}
