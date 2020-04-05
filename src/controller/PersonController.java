package controller;

import application.PersonFX;
import dao.AdressDAO;
import dao.PersonDAO;
import dao.PhoneDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Adress;
import model.Person;
import model.Phone;

public class PersonController implements Initializable {

    private static Person person;

    public static Person getPerson() {
        return person;
    }

    public static void setPerson(Person person) {
        PersonController.person = person;
    }

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfDdd;

    @FXML
    private TextField tfNumber;

    @FXML
    private TextField tfPublicPlace;

    @FXML
    private TextField tfNumberAdress;

    @FXML
    private TextField tfComplement;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfNeighborhood;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfState;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    void actionCancel(ActionEvent event) {
        close();
    }

    @FXML
    void actionSave(ActionEvent event) {
        if (verifyAllArgumentsFielded()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
            alert.showAndWait();
        } else if (verifyLengthCnpj()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo CPF não pode conter mais do que 11 caracteres.");
            alert.showAndWait();
        } else if (verifyLengthDdd()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo DDD não pode conter mais do que 2 caracteres.");
            alert.showAndWait();
        } else if (verifyLengthNumberPhone()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo número não pode conter mais do que 9 caracteres.");
            alert.showAndWait();
        } else if (verifyLengthCep()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo CEP não pode conter mais do que 8 caracteres.");
            alert.showAndWait();
        } else {
            try {
                createEntities();
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                close();
            }
        });

        btnSave.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                if (verifyAllArgumentsFielded()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
                    alert.showAndWait();
                } else if (verifyLengthCnpj()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo CPF não pode conter mais do que 11 caracteres.");
                    alert.showAndWait();
                } else if (verifyLengthDdd()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo DDD não pode conter mais do que 2 caracteres.");
                    alert.showAndWait();
                } else if (verifyLengthNumberPhone()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo número não pode conter mais do que 9 caracteres.");
                    alert.showAndWait();
                } else if (verifyLengthCep()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo CEP não pode conter mais do que 8 caracteres.");
                    alert.showAndWait();
                } else {
                    try {
                        createEntities();
                        Stage stage = (Stage) btnSave.getScene().getWindow();
                        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                        stage.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        });

        if (person != null) {
            try {
                initPerson();
            } catch (SQLException ex) {
                Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    public Person newPerson() {
        return new Person(
                tfName.getText(),
                tfCpf.getText(),
                tfEmail.getText()
        );
    }

    public Phone newPhone() {
        return new Phone(
                tfDdd.getText(),
                tfNumber.getText()
        );
    }

    public Adress newAdress() {
        return new Adress(
                tfPublicPlace.getText(),
                tfNumberAdress == null ? 0 : Integer.parseInt(tfNumberAdress.getText()),
                tfNeighborhood.getText(),
                tfComplement.getText(),
                tfCep.getText(),
                tfCity.getText(),
                tfState.getText()
        );
    }

    public void createEntities() throws SQLException {
        Person auxPerson = newPerson();
        Phone auxPhone = newPhone();
        Adress auxAdress = newAdress();
        if (person != null) {
            auxPerson.setId(person.getId());
            auxPhone.setId(PhoneDAO.retrieveForEntityPerson(person.getId(), 3).getId());
            auxAdress.setId(AdressDAO.retrieveForEntityPerson(person.getId(), 3).getId());
            PersonDAO.update(auxPerson);
            PhoneDAO.update(auxPhone, auxPerson.getId(), 3);
            AdressDAO.update(auxAdress, auxPerson.getId(), 3);
        } else {
            PersonDAO.create(auxPerson);
            PhoneDAO.create(auxPhone, auxPerson.getId(), 3);
            AdressDAO.create(auxAdress, auxPerson.getId(), 3);
        }
    }

    public void initPerson() throws SQLException {
        Phone auxPhone = PhoneDAO.retrieveForEntityPerson(person.getId(), 3);
        Adress auxAdress = AdressDAO.retrieveForEntityPerson(person.getId(), 3);
        tfName.setText(person.getName());
        tfCpf.setText(person.getCpf());
        tfEmail.setText(person.getEmail());
        tfDdd.setText(auxPhone.getDdd());
        tfNumber.setText(auxPhone.getNumber());
        tfPublicPlace.setText(auxAdress.getPublicPlace());
        if (auxAdress.getNumber() > 0) {
            tfNumberAdress.setText(String.valueOf(auxAdress.getNumber()));
        }
        tfComplement.setText(auxAdress.getComplement());
        tfCep.setText(auxAdress.getCep());
        tfNeighborhood.setText(auxAdress.getNeighborhood());
        tfCity.setText(auxAdress.getCity());
        tfState.setText(auxAdress.getState());
    }

    public boolean verifyArgumentsPhone() {
        return (tfDdd.getText().equals("") || tfNumber.getText().equals(""));
    }

    public boolean verifyArgumentsPublisher() {
        return (tfName.getText().equals("") || tfCpf.getText().equals("")
                || tfEmail.getText().equals(""));
    }

    public boolean verifyArgumentsAdress() {
        return (tfPublicPlace.getText().equals("") || tfCep.getText().equals("")
                || tfCity.getText().equals("") || tfState.getText().equals("")
                || tfNumberAdress.getText().equals(""));
    }

    public boolean verifyLengthDdd() {
        return (tfDdd.getText().length() > 2);
    }

    public boolean verifyLengthNumberPhone() {
        return (tfNumber.getText().length() > 9);
    }

    public boolean verifyLengthCnpj() {
        return (tfCpf.getText().length() > 11);
    }

    public boolean verifyLengthCep() {
        return (tfCep.getText().length() > 8);
    }

    public boolean verifyAllArgumentsFielded() {
        return (verifyArgumentsAdress() && verifyArgumentsPhone() && verifyArgumentsPublisher());
    }

    public void close() {
        PersonFX.getStage().close();
    }
}
