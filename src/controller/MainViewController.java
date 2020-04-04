package controller;

import application.BookFX;
import application.MainFX;
import dao.BookDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.Publisher;

public class MainViewController implements Initializable {

    @FXML
    private AnchorPane apMain;

    @FXML
    private TabPane tpMain;

    @FXML
    private Tab tBook;

    @FXML
    private TableView<Book> tvBooks;

    @FXML
    private TableColumn<Book, String> tcBookId;

    @FXML
    private TableColumn<Book, String> tcBookName;

    @FXML
    private TableColumn<Author, String> tcBookAuthor;

    @FXML
    private TableColumn<Publisher, String> tcBookPublisher;

    @FXML
    private TableColumn<Book, Date> tcBookDatePublication;

    @FXML
    private TableColumn<Book, String> tcBookIsbn10;

    @FXML
    private TableColumn<Book, String> tcBookIsbn13;

    @FXML
    private TableColumn<Book, Integer> tcBookAvailableQuantity;

    @FXML
    private Button btnAddBook;

    @FXML
    private Button btnEditBook;

    @FXML
    private Button btnDeleteBook;

    @FXML
    private Button btnFindBooks;

    @FXML
    void actionAddBook(ActionEvent event) {

    }

    @FXML
    void actionDeleteBook(ActionEvent event) {
        deleteBook();
    }

    @FXML
    void actionEditBook(ActionEvent event) {
        editBook();
    }

    @FXML
    void actionFindBooks(ActionEvent event) {
        findBooks();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnFindBooks.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                findBooks();
            }
        });

        btnDeleteBook.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                deleteBook();
            }
        });

        System.out.println("initialize");
        tcBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcBookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tcBookPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        tcBookDatePublication.setCellValueFactory(new PropertyValueFactory<>("datePublication"));
        tcBookIsbn10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
        tcBookIsbn13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));
        tcBookAvailableQuantity.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));
        try {
            ArrayList<Book> booksList = BookDAO.retrieveAll();
            tvBooks.setItems(FXCollections.observableArrayList(booksList));
            tvBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteBook() {
        if (tvBooks.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Atenção");
            a.setHeaderText("Nenhum item escolhido.");
            a.setContentText("É necessário escolher um livro para deletar.");
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Cuidado");
            a.setHeaderText("Essa ação é irreversível.");
            a.setContentText("Realmente deseja excluir de forma definitiva esse livro?");
            if (a.showAndWait().get() == ButtonType.OK) {
                Book b = tvBooks.getSelectionModel().getSelectedItem();
                try {
                    BookDAO.updateExcluded(b);
                } catch (SQLException ex) {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert c = new Alert(Alert.AlertType.ERROR);
                    c.setTitle("Erro");
                    c.setHeaderText("Erro ao excluir livro");
                    c.setContentText(ex.getMessage());
                    c.showAndWait();
                }
                tvBooks.getSelectionModel().clearSelection();
                tvBooks.getItems().clear();
                tvBooks.refresh();
                findBooks();
            }
        }
    }

    public void findBooks() {
        try {
            ArrayList<Book> booksList = BookDAO.retrieveAll();
            tvBooks.setItems(FXCollections.observableArrayList(booksList));
            tvBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editBook() {
        if (tvBooks.getSelectionModel().getSelectedItem() != null) {
            BookFX book = new BookFX(tvBooks.getSelectionModel().getSelectedItem());
            book.start(new Stage());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção!");
            alert.setHeaderText("Escolha um livro para editar.");
            alert.setContentText("É necessário escolher um livro para realizar a edição!");
            alert.show();
        }
    }

    public void close() {
        MainFX.getStage().close();
    }
}
