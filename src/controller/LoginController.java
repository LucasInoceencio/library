package controller;

import application.LoginFX;
import application.MainFX;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.User;

public class LoginController implements Initializable {

    @FXML
    private TextField tfUser;
    
    @FXML
    private Button btnEnter;
    
    @FXML
    private PasswordField pfPassword;

    @FXML
    void enter(ActionEvent event) {
        try {
            logar();
        } catch (SQLException ex) {
            errorLogin(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEnter.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                try {
                    logar();
                } catch (SQLException ex) {
                    errorLogin(ex);
                }
            }
        });

        tfUser.setOnKeyPressed((KeyEvent e) -> {
            if (!pfPassword.getText().isEmpty() && e.getCode() == KeyCode.ENTER) {
                try {
                    logar();
                } catch (SQLException ex) {
                    errorLogin(ex);
                }
            }
        });

        pfPassword.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    logar();
                } catch (SQLException ex) {
                    errorLogin(ex);
                }
            }
        });
    }

    private boolean verifyLogin() throws SQLException {
        return User.logar(tfUser.getText(), pfPassword.getText());
    }

    private void close() {
        LoginFX.getStage().close();
    }

    private void logar() throws SQLException {
        if (verifyLogin()) {
            MainFX main = new MainFX();
            close();
            try {
                main.start(new Stage());
            } catch (Exception ex) {
                errorLogin(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Erro ao realizar o login.");
            alert.setContentText("Usuário ou senha incorreto!");
            alert.show();
        }
    }

    private void errorLogin(Exception ex) {
        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText("Erro ao realizar o login.");
        alert.setContentText(ex.getMessage());
        alert.show();
    }

}
