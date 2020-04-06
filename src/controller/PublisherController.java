package controller;

import application.PublisherFX;
import dao.AdressDAO;
import dao.PhoneDAO;
import dao.PublisherDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Adress;
import model.Phone;
import model.Publisher;
import model.Validator;

public class PublisherController implements Initializable {

    private static Publisher publisher;

    public static Publisher getPublisher() {
        return publisher;
    }

    public static void setPublisher(Publisher publisher) {
        PublisherController.publisher = publisher;
    }

    @FXML
    private TextField tfCompanyName;

    @FXML
    private TextField tfTradingName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCnpj;

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
        if (allArgumentsWereFilled()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
            alert.showAndWait();
        } else if (cnpjExceededSize()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo CNPJ não pode conter mais do que 14 caracteres.");
            alert.showAndWait();
        } else if (!cnpjIsValid(tfCnpj.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo CNPJ não é válido.");
            alert.showAndWait();
        } else if (dddExceededSize()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo DDD não pode conter mais do que 2 caracteres.");
            alert.showAndWait();
        } else if (numberPhoneExceededSize()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("O campo número não pode conter mais do que 9 caracteres.");
            alert.showAndWait();
        } else if (cepExceededSize()) {
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
                if (allArgumentsWereFilled()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
                    alert.showAndWait();
                } else if (cnpjExceededSize()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo CNPJ não pode conter mais do que 14 caracteres.");
                    alert.showAndWait();
                } else if (!cnpjIsValid(tfCnpj.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo CNPJ não é válido.");
                    alert.showAndWait();
                } else if (dddExceededSize()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo DDD não pode conter mais do que 2 caracteres.");
                    alert.showAndWait();
                } else if (numberPhoneExceededSize()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("O campo número não pode conter mais do que 9 caracteres.");
                    alert.showAndWait();
                } else if (cepExceededSize()) {
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

        if (publisher != null) {
            try {
                initPublisher();
            } catch (SQLException ex) {
                Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }

    }

    private void close() {
        PublisherFX.getStage().close();
    }

    private Publisher newPublisher() {
        return new Publisher(
                tfCompanyName.getText(),
                tfTradingName.getText(),
                tfCnpj.getText(),
                tfEmail.getText()
        );
    }

    private Phone newPhone() {
        return new Phone(
                tfDdd.getText(),
                tfNumber.getText()
        );
    }

    private Adress newAdress() {
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

    private void createEntities() throws SQLException {
        Publisher auxPublisher = newPublisher();
        Phone auxPhone = newPhone();
        Adress auxAdress = newAdress();
        if (publisher != null) {
            auxPublisher.setId(publisher.getId());
            auxPhone.setId(PhoneDAO.retrieveForEntityPerson(publisher.getId(), 4).getId());
            auxAdress.setId(AdressDAO.retrieveForEntityPerson(publisher.getId(), 4).getId());
            PublisherDAO.update(auxPublisher);
            PhoneDAO.update(auxPhone, auxPublisher.getId(), 4);
            AdressDAO.update(auxAdress, auxPublisher.getId(), 4);
        } else {
            PublisherDAO.create(auxPublisher);
            PhoneDAO.create(auxPhone, auxPublisher.getId(), 4);
            AdressDAO.create(auxAdress, auxPublisher.getId(), 4);
        }
    }

    private void initPublisher() throws SQLException {
        Phone auxPhone = PhoneDAO.retrieveForEntityPerson(publisher.getId(), 4);
        Adress auxAdress = AdressDAO.retrieveForEntityPerson(publisher.getId(), 4);
        tfCompanyName.setText(publisher.getCompanyName());
        tfTradingName.setText(publisher.getTradingName());
        tfCnpj.setText(publisher.getCnpj());
        tfEmail.setText(publisher.getEmail());
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

    private boolean mandatoryFieldsPhoneNotFilled() {
        return (tfDdd.getText().equals("") || tfNumber.getText().equals(""));
    }

    private boolean mandatoryFieldsPublisherNotFilled() {
        return (tfCompanyName.getText().equals("") || tfTradingName.getText().equals("")
                || tfCnpj.getText().equals("") || tfEmail.getText().equals(""));
    }

    private boolean mandatoryFieldsAdressNotFilled() {
        return (tfPublicPlace.getText().equals("") || tfCep.getText().equals("")
                || tfCity.getText().equals("") || tfState.getText().equals("")
                || tfNumberAdress.getText().equals(""));
    }

    private boolean dddExceededSize() {
        return (tfDdd.getText().length() > 2);
    }

    private boolean numberPhoneExceededSize() {
        return (tfNumber.getText().length() > 9);
    }

    private boolean cnpjExceededSize() {
        return (tfCnpj.getText().length() > 14);
    }

    private boolean cepExceededSize() {
        return (tfCep.getText().length() > 8);
    }

    private boolean cnpjIsValid(String cnpj) {
        return Validator.isCNPJ(cnpj);
    }

    private boolean allArgumentsWereFilled() {
        return (mandatoryFieldsAdressNotFilled() && mandatoryFieldsPhoneNotFilled() && mandatoryFieldsPublisherNotFilled());
    }
}
