package controller;

import application.BookFX;
import application.LoanFX;
import application.MainFX;
import application.PersonFX;
import application.ProfileFX;
import application.PublisherFX;
import dao.BookDAO;
import dao.LoanDAO;
import dao.PersonDAO;
import dao.PublisherDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Author;
import model.Book;
import model.Loan;
import model.Person;
import model.Publisher;
import model.enums.Genre;
import model.enums.Language;
import model.enums.LoanStatus;

public class MainController implements Initializable {

    // General Properties
    @FXML
    private Button btnMyProfile;

    @FXML
    void actionMyProfile(ActionEvent event) {
        myProfile();
    }

    //Properties tab Book
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
    private TableColumn<Genre, ?> tcBookGenre;

    @FXML
    private TableColumn<Language, ?> tcBookLanguage;

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
        createBook();
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

    // Properties tab Loan
    @FXML
    private TableView<Loan> tvLoans;

    @FXML
    private TableColumn<Loan, Integer> tcLoanId;

    @FXML
    private TableColumn<Person, String> tcLoanPerson;

    @FXML
    private TableColumn<Loan, Integer> tcLoanNumberRenewals;

    @FXML
    private TableColumn<Loan, Date> tcLoanDeliveryDate;

    @FXML
    private TableColumn<Loan, Date> tcLoanDeliveredDate;

    @FXML
    private TableColumn<Loan, Double> tcLoanLateFee;

    @FXML
    private TableColumn<LoanStatus, String> tcLoanStatus;

    @FXML
    private Button btnAddLoan;

    @FXML
    private Button btnViewLoan;

    @FXML
    private Button btnDeleteLoan;

    @FXML
    private Button btnFindLoans;

    @FXML
    private Button btnEndLoan;

    @FXML
    private Button btnRenewLoan;

    @FXML
    void actionAddLoan(ActionEvent event) {
        createLoan();
    }

    @FXML
    void actionEndLoan(ActionEvent event) {
        endLoan();
    }

    @FXML
    void actionDeleteLoan(ActionEvent event) {
        deleteLoan();
    }

    @FXML
    void actionFindLoans(ActionEvent event) {
        findLoans();
    }

    @FXML
    void actionRenewLoan(ActionEvent event) {
        renewLoan();
    }

    @FXML
    void actionViewLoan(ActionEvent event) {
        viewLoan();
    }

    // Properties tab Person
    @FXML
    private TableView<Person> tvPersons;

    @FXML
    private TableColumn<Person, Integer> tcPersonId;

    @FXML
    private TableColumn<Person, String> tcPersonName;

    @FXML
    private TableColumn<Person, String> tcPersonCpf;

    @FXML
    private TableColumn<Person, String> tcPersonEmail;

    @FXML
    private Button btnAddPerson;

    @FXML
    private Button btnDeletePerson;

    @FXML
    private Button btnEditPerson;

    @FXML
    private Button btnFindPerson;

    @FXML
    void actionAddPerson(ActionEvent event) {
        createPerson();
    }

    @FXML
    void actionDeletePerson(ActionEvent event) {
        deletePerson();
    }

    @FXML
    void actionEditPerson(ActionEvent event) {
        editPerson();
    }

    @FXML
    void actionFindPersons(ActionEvent event) {
        findPersons();
    }

    //Tab Publishers
    @FXML
    private TableView<Publisher> tvPublishers;

    @FXML
    private TableColumn<Publisher, String> tcPublisherId;

    @FXML
    private TableColumn<Publisher, String> tcPublisherCompanyName;

    @FXML
    private TableColumn<Publisher, String> tcPublisherTradingName;

    @FXML
    private TableColumn<Publisher, String> tcPublisherCnpj;

    @FXML
    private TableColumn<Publisher, String> tcPublisherEmail;

    @FXML
    private Button btnAddPublisher;

    @FXML
    private Button btnEditPublisher;

    @FXML
    private Button btnDeletePublisher;

    @FXML
    private Button btnFindPublisher;

    @FXML
    void actionAddPublisher(ActionEvent event) {
        createPublisher();
    }

    @FXML
    void actionDeletePublisher(ActionEvent event) {
        deletePublisher();
    }

    @FXML
    void actionEditPublisher(ActionEvent event) {
        editPublisher();
    }

    @FXML
    void actionFindPublisher(ActionEvent event) {
        findPublishers();
    }

    //General Logic
    private void myProfile() {
        ProfileFX profile = new ProfileFX(null);
        Stage stage = new Stage();
        profile.start(stage);
    }

    // Logic Books
    private void createBook() {
        BookFX book = new BookFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            findBooks();
        });
        book.start(stage);
    }

    private void deleteBook() {
        if (tvBooks.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum item escolhido.");
            alert.setContentText("É necessário escolher um livro para deletar.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Essa ação é irreversível.");
            alert.setContentText("Realmente deseja excluir de forma definitiva esse livro?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Book b = tvBooks.getSelectionModel().getSelectedItem();
                try {
                    BookDAO.updateExcluded(b);
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                    alertDAO.setTitle("Erro");
                    alertDAO.setHeaderText("Erro ao excluir livro.");
                    alertDAO.setContentText(ex.getMessage());
                    alertDAO.showAndWait();
                }
                tvBooks.getSelectionModel().clearSelection();
                tvBooks.getItems().clear();
                tvBooks.refresh();
                findBooks();
            }
        }
    }

    private void findBooks() {
        try {
            ArrayList<Book> booksList = BookDAO.retrieveAllExcluded(false);
            tvBooks.setItems(FXCollections.observableArrayList(booksList));
            tvBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editBook() {
        if (tvBooks.getSelectionModel().getSelectedItem() != null) {
            BookFX book = new BookFX(tvBooks.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setOnCloseRequest((WindowEvent we) -> {
                findBooks();
            });
            book.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção!");
            alert.setHeaderText("Escolha um livro para editar.");
            alert.setContentText("É necessário escolher um livro para realizar a edição!");
            alert.show();
        }
    }

    // Logic Loan
    private void createLoan() {
        LoanFX loan = new LoanFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            findLoans();
        });
        loan.start(stage);
    }

    private void deleteLoan() {
        if (tvLoans.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum item escolhido.");
            alert.setContentText("É necessário escolher um empréstimo para deletar.");
            alert.showAndWait();
        } else if (tvLoans.getSelectionModel().getSelectedItem().getStatus().toString().equalsIgnoreCase("ATIVO")) {
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("O empréstimo está ativo.");
            alertDAO.setContentText("Para deletar um empréstimo é necessário que o mesmo esteja encerrado.");
            alertDAO.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Essa ação é irreversível.");
            alert.setContentText("Realmente deseja excluir de forma definitiva esse empréstimo?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Loan l = tvLoans.getSelectionModel().getSelectedItem();
                try {
                    LoanDAO.updateExcluded(l);
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                    alertDAO.setTitle("Erro");
                    alertDAO.setHeaderText("Erro ao excluir empréstimo.");
                    alertDAO.setContentText(ex.getMessage());
                    alertDAO.showAndWait();
                }
                tvLoans.getSelectionModel().clearSelection();
                tvLoans.getItems().clear();
                tvLoans.refresh();
                findLoans();
            }
        }
    }

    private void endLoan() {
        if (tvLoans.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum item escolhido.");
            alert.setContentText("É necessário escolher um empréstimo para encerrar.");
            alert.showAndWait();
        } else if (tvLoans.getSelectionModel().getSelectedItem().getStatus().toString().equalsIgnoreCase("ENCERRADO")) {
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("O empréstimo está encerrado.");
            alertDAO.setContentText("Só é possível encerrar empréstimos que estejam com o status Ativo.");
            alertDAO.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Essa ação é irreversível.");
            alert.setContentText("Realmente deseja encerrar de forma definitiva esse empréstimo?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    Loan auxLoan = LoanDAO.retrieve(tvLoans.getSelectionModel().getSelectedItem().getId());
                    if (auxLoan.endLoan()) {
                        try {
                            LoanDAO.update(auxLoan);
                            findLoans();
                        } catch (SQLException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                            alertDAO.setTitle("Erro");
                            alertDAO.setHeaderText("Erro ao comunicar com banco de dados.");
                            alertDAO.setContentText(ex.getMessage());
                            alertDAO.showAndWait();
                        }
                    } else {
                        Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                        alertDAO.setTitle("Erro");
                        alertDAO.setHeaderText("O empréstimo está encerrado.");
                        alertDAO.setContentText("Só é possível encerrar empréstimos que estejam com o status Ativo.");
                        alertDAO.showAndWait();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                    alertDAO.setTitle("Erro");
                    alertDAO.setHeaderText("Erro ao comunicar com banco de dados.");
                    alertDAO.setContentText(ex.getMessage());
                    alertDAO.showAndWait();
                }

            }
        }
    }

    private void findLoans() {
        try {
            ArrayList<Loan> loansList = LoanDAO.retrieveAllExcluded(false);
            tvLoans.setItems(FXCollections.observableArrayList(loansList));
            tvLoans.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void renewLoan() {
        if (tvLoans.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum item escolhido.");
            alert.setContentText("É necessário escolher um empréstimo para renovar.");
            alert.showAndWait();
        } else if (tvLoans.getSelectionModel().getSelectedItem().getStatus().toString().equalsIgnoreCase("ENCERRADO")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("O empréstimo está encerrado.");
            alert.setContentText("Só é possível renovar empréstimos que estejam com o status Ativo.");
            alert.showAndWait();
        } else if (tvLoans.getSelectionModel().getSelectedItem().getNumberRenewals() >= 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Não é possível renovar o empréstimo.");
            alert.setContentText("Quantidade máxima de renovações atingidas.");
            alert.showAndWait();
        } else {
            if (tvLoans.getSelectionModel().getSelectedItem().renewLoan()) {
                try {
                    LoanDAO.update(tvLoans.getSelectionModel().getSelectedItem());
                    findLoans();
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                    alertDAO.setTitle("Erro");
                    alertDAO.setHeaderText("Erro ao comunicar com banco de dados.");
                    alertDAO.setContentText(ex.getMessage());
                    alertDAO.showAndWait();
                }
            }
        }
    }

    private void viewLoan() {
        if (tvLoans.getSelectionModel().getSelectedItem() != null) {
            LoanFX loan = new LoanFX(tvLoans.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setOnCloseRequest((WindowEvent we) -> {
                findLoans();
            });
            loan.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção!");
            alert.setHeaderText("Escolha um empréstimo para visualizar.");
            alert.setContentText("É necessário escolher um empréstimo para realizar a visualização!");
            alert.show();
        }
    }

    // Logic Person
    private void createPerson() {
        PersonFX person = new PersonFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            findPersons();
        });
        person.start(stage);
    }

    private void deletePerson() {
        if (tvPersons.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum item escolhido.");
            alert.setContentText("É necessário escolher uma pessoa para deletar.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Essa ação é irreversível.");
            alert.setContentText("Realmente deseja excluir de forma definitiva essa pessoa?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Person p = tvPersons.getSelectionModel().getSelectedItem();
                try {
                    PersonDAO.updateExcluded(p);
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                    alertDAO.setTitle("Erro");
                    alertDAO.setHeaderText("Erro ao excluir pessoa.");
                    alertDAO.setContentText(ex.getMessage());
                    alertDAO.showAndWait();
                }
                tvPersons.getSelectionModel().clearSelection();
                tvPersons.getItems().clear();
                tvPersons.refresh();
                findPersons();
            }
        }
    }

    private void editPerson() {
        if (tvPersons.getSelectionModel().getSelectedItem() != null) {
            PersonFX person = new PersonFX(tvPersons.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setOnCloseRequest((WindowEvent we) -> {
                findPersons();
            });
            person.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção!");
            alert.setHeaderText("Escolha uma pessoa para editar.");
            alert.setContentText("É necessário escolher uma pessoa para realizar a edição!");
            alert.show();
        }
    }

    private void findPersons() {
        try {
            ArrayList<Person> personsList = PersonDAO.retrieveAllExcluded(false);
            tvPersons.setItems(FXCollections.observableArrayList(personsList));
            tvPersons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Logic Publisher
    private void createPublisher() {
        PublisherFX publisher = new PublisherFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            findPublishers();
        });
        publisher.start(stage);
    }

    private void deletePublisher() {
        if (tvPublishers.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum item escolhido.");
            alert.setContentText("É necessário escolher uma editora para deletar.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Essa ação é irreversível.");
            alert.setContentText("Realmente deseja excluir de forma definitiva essa editora?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Publisher p = tvPublishers.getSelectionModel().getSelectedItem();
                try {
                    PublisherDAO.updateExcluded(p);
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                    alertDAO.setTitle("Erro");
                    alertDAO.setHeaderText("Erro ao excluir editora.");
                    alertDAO.setContentText(ex.getMessage());
                    alertDAO.showAndWait();
                }
                tvPublishers.getSelectionModel().clearSelection();
                tvPublishers.getItems().clear();
                tvPublishers.refresh();
                findPublishers();
            }
        }
    }

    private void editPublisher() {
        if (tvPublishers.getSelectionModel().getSelectedItem() != null) {
            PublisherFX person = new PublisherFX(tvPublishers.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setOnCloseRequest((WindowEvent we) -> {
                findPublishers();
            });
            person.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção!");
            alert.setHeaderText("Escolha uma editora para editar.");
            alert.setContentText("É necessário escolher uma editora para realizar a edição!");
            alert.show();
        }
    }

    private void findPublishers() {
        try {
            ArrayList<Publisher> publishersList = PublisherDAO.retrieveAllExcluded(false);
            tvPublishers.setItems(FXCollections.observableArrayList(publishersList));
            tvPublishers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // General methods
    private void close() {
        MainFX.getStage().close();
    }

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //General
        btnMyProfile.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                myProfile();
            }
        });

        //Book
        btnAddBook.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createBook();
            }
        });
        btnDeleteBook.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                deleteBook();
            }
        });
        btnEditBook.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                editBook();
            }
        });
        btnFindBooks.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                findBooks();
            }
        });

        //Person
        btnAddPerson.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createPerson();
            }
        });
        btnDeletePerson.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                deletePerson();
            }
        });
        btnEditPerson.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                editPerson();
            }
        });
        btnFindPerson.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                findPersons();
            }
        });

        //Loan
        btnAddLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createLoan();
            }
        });

        btnEndLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                endLoan();
            }
        });

        btnDeleteLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                deleteLoan();
            }
        });

        btnFindLoans.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                findLoans();
            }
        });

        btnRenewLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                renewLoan();
            }
        });

        btnViewLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                viewLoan();
            }
        });

        //Publisher
        btnAddPublisher.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createPublisher();
            }
        });
        btnDeletePublisher.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                deletePublisher();
            }
        });
        btnEditPublisher.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                editPublisher();
            }
        });
        btnFindPublisher.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                findPublishers();
            }
        });

        //Init
        tcBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcBookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tcBookPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        tcBookDatePublication.setCellValueFactory(new PropertyValueFactory<>("datePublication"));
        tcBookIsbn10.setCellValueFactory(new PropertyValueFactory<>("isbn10"));
        tcBookIsbn13.setCellValueFactory(new PropertyValueFactory<>("isbn13"));
        tcBookGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tcBookLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
        tcBookAvailableQuantity.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));
        try {
            ArrayList<Book> booksList = BookDAO.retrieveAllExcluded(false);
            tvBooks.setItems(FXCollections.observableArrayList(booksList));
            tvBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tcLoanId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcLoanPerson.setCellValueFactory(new PropertyValueFactory<>("person"));
        tcLoanNumberRenewals.setCellValueFactory(new PropertyValueFactory<>("numberRenewals"));
        tcLoanDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        tcLoanDeliveredDate.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
        tcLoanLateFee.setCellValueFactory(new PropertyValueFactory<>("lateFee"));
        tcLoanStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            ArrayList<Loan> loansList = LoanDAO.retrieveAllExcluded(false);
            tvLoans.setItems(FXCollections.observableArrayList(loansList));
            tvLoans.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tcPersonId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPersonName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPersonEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcPersonCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        try {
            ArrayList<Person> personsList = PersonDAO.retrieveAllExcluded(false);
            tvPersons.setItems(FXCollections.observableArrayList(personsList));
            tvPersons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tcPublisherId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPublisherCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tcPublisherTradingName.setCellValueFactory(new PropertyValueFactory<>("tradingName"));
        tcPublisherCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        tcPublisherEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        try {
            ArrayList<Publisher> publishersList = PublisherDAO.retrieveAllExcluded(false);
            tvPublishers.setItems(FXCollections.observableArrayList(publishersList));
            tvPublishers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
