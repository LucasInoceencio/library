package controller;

import application.LoanFX;
import application.PersonFX;
import dao.BookDAO;
import dao.LoanDAO;
import dao.PersonDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Book;
import model.Loan;
import model.Person;

public class LoanController implements Initializable {

    private static Loan loan;

    public static Loan getLoan() {
        return loan;
    }

    public static void setLoan(Loan loan) {
        LoanController.loan = loan;
    }

    @FXML
    private ComboBox<Person> cbPerson;

    @FXML
    private Button btnAddPerson;

    @FXML
    private TextField tfStatus;

    @FXML
    private TextField tfDeliveryDate;

    @FXML
    private TextField tfNumberRenewals;

    @FXML
    private TextField tfLateFee;

    @FXML
    private TextField tfDeliveredDate;

    @FXML
    private ComboBox<Book> cbFirstBook;

    @FXML
    private ComboBox<Book> cbSecondBook;

    @FXML
    private ComboBox<Book> cbThirdBook;

    @FXML
    private Button btnFirstClear;

    @FXML
    private Button btnSecondClear;

    @FXML
    private Button btnThirdClear;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEndLoan;

    @FXML
    private Button btnRenewLoan;

    @FXML
    void actionAddPerson(ActionEvent event) {
        createPerson();
    }

    @FXML
    void actionCancel(ActionEvent event) {
        close();
    }

    @FXML
    void actionEndLoan(ActionEvent event) {

    }

    @FXML
    void actionFirstClear(ActionEvent event) {
        clearFirstBook();
    }

    @FXML
    void actionRenewLoan(ActionEvent event) {

    }

    @FXML
    void actionSave(ActionEvent event) {
        createLoan();
    }

    @FXML
    void actionSecondClear(ActionEvent event) {
        clearSecondBook();
    }

    @FXML
    void actionThirdClear(ActionEvent event) {
        clearThirdBook();
    }

    //Init
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSave.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createLoan();
            }
        });

        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                close();
            }
        });

        btnRenewLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {

            }
        });

        btnEndLoan.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {

            }
        });

        btnFirstClear.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                clearFirstBook();
            }
        });

        btnSecondClear.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                clearSecondBook();
            }
        });

        btnThirdClear.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                clearThirdBook();
            }
        });

        btnAddPerson.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createPerson();
            }
        });

        if (loan == null) {
            btnRenewLoan.setDisable(true);
            btnEndLoan.setDisable(true);
        } else {
            initLoan();
            btnSave.setDisable(true);
            cbPerson.setDisable(true);
            btnAddPerson.setDisable(true);
            cbFirstBook.setDisable(true);
            cbSecondBook.setDisable(true);
            cbThirdBook.setDisable(true);
            btnFirstClear.setDisable(true);
            btnSecondClear.setDisable(true);
            btnThirdClear.setDisable(true);
        }

        try {
            ObservableList<Person> personsList = FXCollections.observableArrayList(PersonDAO.retrieveAllExcluded(false));
            cbPerson.setItems(personsList);
        } catch (SQLException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar pessoas.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }

        try {
            ObservableList<Book> booksList = FXCollections.observableArrayList(BookDAO.retrieveAllExcluded(false));
            cbFirstBook.setItems(booksList);
            cbSecondBook.setItems(booksList);
            cbThirdBook.setItems(booksList);
        } catch (SQLException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Logic
    private void createLoan() {
        if (mandatoryFieldsNotFilled()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
            alert.showAndWait();
        } else if (containsEqualsBooks()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Livros iguais.");
            alert.setContentText("Não é possível pegar livros iguais no mesmo empréstimo.");
            alert.showAndWait();
        } else {
            Loan auxLoan = new Loan(
                    cbPerson.getSelectionModel().getSelectedItem()
            );
            chosenBooks().forEach(chosenBook -> {
                auxLoan.addBook(chosenBook);
            });
            try {
                LoanDAO.create(auxLoan);
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao criar empréstimo.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void createPerson() {
        PersonFX person = new PersonFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            refreshPersons();
        });
        person.start(stage);
    }

    private void initLoan() {
        cbPerson.getSelectionModel().select(loan.getPerson());
        tfDeliveryDate.setText(String.valueOf(loan.getDeliveryDate()));
        tfNumberRenewals.setText(String.valueOf(loan.getNumberRenewals()));
        tfLateFee.setText("R$ " + String.valueOf(loan.getLateFee()));
        tfStatus.setText(String.valueOf(loan.getStatus()));
        if (loan.getDeliveredDate() != null) {
            tfDeliveredDate.setText(String.valueOf(loan.getDeliveredDate()));
        }
        int lengthListBooks = loan.getBooks().size();
        switch (lengthListBooks) {
            case 1:
                cbFirstBook.getSelectionModel().select(loan.getBooks().get(0));
                break;
            case 2:
                if (loan.getBooks().get(0) != null) {
                    cbFirstBook.getSelectionModel().select(loan.getBooks().get(0));
                }   if (loan.getBooks().get(1) != null) {
                    cbSecondBook.getSelectionModel().select(loan.getBooks().get(1));
                }   break;
            case 3:
                if (loan.getBooks().get(0) != null) {
                    cbFirstBook.getSelectionModel().select(loan.getBooks().get(0));
                }   if (loan.getBooks().get(1) != null) {
                    cbSecondBook.getSelectionModel().select(loan.getBooks().get(1));
                }   if (loan.getBooks().get(2) != null) {
                    cbThirdBook.getSelectionModel().select(loan.getBooks().get(2));
                }   break;
            default:
                break;
        }

    }

    private void refreshPersons() {
        try {
            ObservableList<Person> personsList = FXCollections.observableArrayList(PersonDAO.retrieveAllExcluded(false));
            cbPerson.setItems(personsList);
        } catch (SQLException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar pessoas.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }
    }

    private void refreshBooks() {
        try {
            ObservableList<Book> booksList = FXCollections.observableArrayList(BookDAO.retrieveAllExcluded(false));
            cbFirstBook.setItems(booksList);
            cbSecondBook.setItems(booksList);
            cbThirdBook.setItems(booksList);
        } catch (SQLException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean mandatoryFieldsNotFilled() {
        if (cbPerson.getSelectionModel().isEmpty() || cbFirstBook.getSelectionModel().isEmpty()) {
            return true;
        }
        return false;
    }

    private ArrayList<Book> chosenBooks() {
        ArrayList<Book> listBooks = new ArrayList<>();
        if (!cbFirstBook.getSelectionModel().isEmpty()) {
            listBooks.add(cbFirstBook.getSelectionModel().getSelectedItem());
        }
        if (!cbSecondBook.getSelectionModel().isEmpty()) {
            listBooks.add(cbSecondBook.getSelectionModel().getSelectedItem());
        }
        if (!cbThirdBook.getSelectionModel().isEmpty()) {
            listBooks.add(cbThirdBook.getSelectionModel().getSelectedItem());
        }
        return listBooks;
    }

    private boolean containsEqualsBooks() {
        if (cbFirstBook.getSelectionModel().isEmpty() && cbSecondBook.getSelectionModel().isEmpty()) {
            return false;
        } else if (cbSecondBook.getSelectionModel().isEmpty() && cbThirdBook.getSelectionModel().isEmpty()) {
            return false;
        } else if (cbFirstBook.getSelectionModel().isEmpty() && cbThirdBook.getSelectionModel().isEmpty()) {
            return false;
        } else if (cbFirstBook.getSelectionModel().isEmpty()) {
            if (cbSecondBook.getSelectionModel().getSelectedItem().getId() == cbThirdBook.getSelectionModel().getSelectedItem().getId()) {
                return true;
            }
        } else if (cbSecondBook.getSelectionModel().isEmpty()) {
            if (cbFirstBook.getSelectionModel().getSelectedItem().getId() == cbThirdBook.getSelectionModel().getSelectedItem().getId()) {
                return true;
            }
        } else if (cbThirdBook.getSelectionModel().isEmpty()) {
            if (cbFirstBook.getSelectionModel().getSelectedItem().getId() == cbSecondBook.getSelectionModel().getSelectedItem().getId()) {
                return true;
            }
        } else {
            if (cbFirstBook.getSelectionModel().getSelectedItem().getId() == cbSecondBook.getSelectionModel().getSelectedItem().getId()
                    || cbFirstBook.getSelectionModel().getSelectedItem().getId() == cbThirdBook.getSelectionModel().getSelectedItem().getId()
                    || cbSecondBook.getSelectionModel().getSelectedItem().getId() == cbThirdBook.getSelectionModel().getSelectedItem().getId()) {
                return true;
            }
        }
        return false;
    }

    private void close() {
        LoanFX.getStage().close();
    }

    private void clearFirstBook() {
        cbFirstBook.getSelectionModel().clearSelection();
    }

    private void clearSecondBook() {
        cbSecondBook.getSelectionModel().clearSelection();
    }

    private void clearThirdBook() {
        cbThirdBook.getSelectionModel().clearSelection();
    }

}
