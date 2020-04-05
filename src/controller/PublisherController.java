package controller;

import application.PublisherFX;
import dao.PublisherDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Adress;
import model.Phone;
import model.Publisher;

public class PublisherController implements Initializable {

    @FXML
    private TextField tfCompanyame;

    @FXML
    private TextField tfTradingName;

    @FXML
    private TextField tfCnpj;

    @FXML
    private TextField tfEmail;

    @FXML
    private TableView<Phone> tvPhones;

    @FXML
    private TableColumn<Phone, String> tcPhoneId;

    @FXML
    private TableColumn<Phone, String> tcPhoneDdd;

    @FXML
    private TableColumn<Phone, String> tcPhoneNumber;

    @FXML
    private TableView<Adress> tvAdresses;

    @FXML
    private TableColumn<Adress, String> tcAdressId;

    @FXML
    private TableColumn<Adress, String> tcAdressPublicPlace;

    @FXML
    private TableColumn<Adress, String> tcAdressNumber;

    @FXML
    private TableColumn<Adress, String> tcAdressNeighborhood;

    @FXML
    private TableColumn<Adress, String> tcAdressComplement;

    @FXML
    private TableColumn<Adress, String> tcAdressCep;

    @FXML
    private TableColumn<Adress, String> tcAdressCity;

    @FXML
    private TableColumn<Adress, String> tcAdressState;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEditPhone;

    @FXML
    private Button btnAddPhone;

    @FXML
    private Button btnDeletePhone;

    @FXML
    private Button btnEditAdress;

    @FXML
    private Button btnAddAdress;

    @FXML
    private Button btnDeleteAdress;

    @FXML
    void actionAddAdress(ActionEvent event) {

    }

    @FXML
    void actionAddPhone(ActionEvent event) {

    }

    @FXML
    void actionCancel(ActionEvent event) {

    }

    @FXML
    void actionDeleteAdress(ActionEvent event) {

    }

    @FXML
    void actionDeletePhone(ActionEvent event) {

    }

    @FXML
    void actionEditAdress(ActionEvent event) {

    }

    @FXML
    void actionEditPhone(ActionEvent event) {

    }

    @FXML
    void actionSave(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                close();
            }
        });
    }

    private static Publisher publisher;

    public static Publisher getPublisher() {
        return publisher;
    }

    public static void setPublisher(Publisher publisher) {
        PublisherController.publisher = publisher;
    }

    public void close() {
        PublisherFX.getStage().close();
    }

    public boolean verifyGeneralArguments() {
        return !(tfCompanyame.getText().equals("") || tfTradingName.getText().equals("")
                || tfCnpj.getText().equals("") || tfEmail.getText().equals(""));
    }

    public void createPublisher() {
        Publisher newPublisher = new Publisher(
                tfCompanyame.getText(),
                tfTradingName.getText(),
                tfCnpj.getText(),
                tfEmail.getText()
        );
        if (tvPhones.getItems() != null) {
            tvPhones.getItems().forEach(phone -> {
                Phone aux = new Phone(
                        tcPhoneDdd.getText(),
                        tcPhoneNumber.getText()
                );
                newPublisher.addPhone(aux);
            });
        }
        if (tvAdresses.getItems() != null) {
            tvAdresses.getItems().forEach(adress -> {
                Adress aux = new Adress(
                        tcAdressPublicPlace.getText(),
                        Integer.parseInt(tcAdressNumber.getText()),
                        tcAdressNeighborhood.getText(),
                        tcAdressComplement.getText(),
                        tcAdressCep.getText(),
                        tcAdressCity.getText(),
                        tcAdressState.getText()
                );
                newPublisher.addAdress(adress);
            });
        }
        if (publisher != null) {
            try {
                PublisherDAO.update(newPublisher);
                newPublisher.setId(publisher.getId());
                close();
            } catch (SQLException ex) {
                Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                alertDAO.setTitle("Erro");
                alertDAO.setHeaderText("Erro ao atualizar a editora.");
                alertDAO.setContentText(ex.getMessage());
                alertDAO.showAndWait();
            }
        } else {
            try {
                PublisherDAO.create(newPublisher);
            } catch (SQLException ex) {
                Logger.getLogger(PublisherController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alertDAO = new Alert(Alert.AlertType.ERROR);
                alertDAO.setTitle("Erro");
                alertDAO.setHeaderText("Erro ao criar a editora.");
                alertDAO.setContentText(ex.getMessage());
                alertDAO.showAndWait();
            }

        }
    }

}
