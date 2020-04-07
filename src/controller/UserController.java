package controller;

import application.PersonFX;
import application.UserFX;
import dao.PersonDAO;
import dao.UserDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Cryptography;
import model.Person;

public class UserController implements Initializable {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserController.user = user;
    }

    @FXML
    private TextField tfUsername;

    @FXML
    private ComboBox<Person> cbPerson;

    @FXML
    private Button btnAddPerson;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    void actionAddPerson(ActionEvent event) {
        createPerson();
    }

    @FXML
    void actionCancel(ActionEvent event) {
        close();
    }

    @FXML
    void actionSave(ActionEvent event) {
        createUser();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAddPerson.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                createPerson();
            }
        });

        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                close();
            }
        });

        btnSave.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {

            }
        });

        try {
            ObservableList<Person> personsList = FXCollections.observableArrayList(PersonDAO.retrieveAllExcluded(false));
            cbPerson.setItems(personsList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar autores.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }
    }

    private void close() {
        UserFX.getStage().close();
    }

    private void createPerson() {
        PersonFX person = new PersonFX(null);
        Stage stage = new Stage();
        stage.setOnCloseRequest((WindowEvent we) -> {
            refreshPreson();
        });
        person.start(stage);
    }

    private void refreshPreson() {
        try {
            ObservableList<Person> personsList = FXCollections.observableArrayList(PersonDAO.retrieveAllExcluded(false));
            cbPerson.setItems(personsList);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alertDAO = new Alert(Alert.AlertType.ERROR);
            alertDAO.setTitle("Erro");
            alertDAO.setHeaderText("Erro ao buscar autores.");
            alertDAO.setContentText(ex.getMessage());
            alertDAO.showAndWait();
        }
    }

    private void createUser() {
        if (mandatoryFieldsNotFilled()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Erro ao preencher campos.");
            alert.setContentText("Existem campos obrigatórios que não foram preenchidos.");
            alert.show();
        } else if (!verifyPassword()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Verifique a nova senha.");
            alert.setContentText("O campo senha não é igual ao campo confirmar senha.");
            alert.show();
        } else {
            User auxUser = new User();
            String hash = new Cryptography().createHash(pfPassword.getText());
            if (hash != null) {
                auxUser.setUsername(tfUsername.getText());
                auxUser.setPassword(hash);
                auxUser.setPerson(cbPerson.getSelectionModel().getSelectedItem());
                try {
                    UserDAO.create(auxUser);
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                    stage.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                    alert.setContentText(ex.getMessage());
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText("Erro de senha.");
                alert.setContentText("Erro ao criptografar a senha!");
                alert.show();
            }
        }
    }

    private boolean verifyPassword() {
        return (pfPassword.getText().equals(pfConfirmPassword.getText()));
    }

    private boolean mandatoryFieldsNotFilled() {
        if (cbPerson.getSelectionModel().isEmpty()) {
            return true;
        }
        if (tfUsername.getText().equals("")) {
            return true;
        }
        if (pfPassword.getText().equals("")) {
            return true;
        }
        return pfConfirmPassword.getText().equals("");
    }
}
