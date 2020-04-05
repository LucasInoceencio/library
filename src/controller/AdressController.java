
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Adress;

public class AdressController implements Initializable {
    
    @FXML
    private TextField tfPublicPlace;

    @FXML
    private TextField tfNumber;

    @FXML
    private TextField tfNeighborhood;

    @FXML
    private TextField tfComplement;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfCity;

    @FXML
    private ComboBox<BrazilianStates> cbState;

    @FXML
    private Button btnCanel;

    @FXML
    private Button btnSave;

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
    
    private static Adress adress;

    public static Adress getAdress() {
        return adress;
    }

    public static void setAdress(Adress adress) {
        AdressController.adress = adress;
    }
    
    
}
