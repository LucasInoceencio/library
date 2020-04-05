package controller;

import application.PublisherFX;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private TextField tfFirstNumber;

    @FXML
    private TextField tfFirstDdd;

    @FXML
    private TextField tfNeighborhood;

    @FXML
    private TextField tfSecondDdd;

    @FXML
    private TextField tfPublicPlace;

    @FXML
    private TextField tfNumberAdress;

    @FXML
    private TextField tfComplement;

    @FXML
    private TextField tfCity;

    @FXML
    private ComboBox<?> cbState;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfSecondNumber;

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

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancel.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                close();
            }
        });
    }
    
    public void close() {
        PublisherFX.getStage().close();
    }

}
