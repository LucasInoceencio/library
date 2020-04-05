package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Phone;

public class PhoneController implements Initializable {

    @FXML
    private TextField tfDdd;

    @FXML
    private TextField tdNumber;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSavel;

    @FXML
    void actionCancel(ActionEvent event) {

    }

    @FXML
    void actionSave(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private static Phone phone;

    public static Phone getPhone() {
        return phone;
    }

    public static void setPhone(Phone phone) {
        PhoneController.phone = phone;
    }

}
