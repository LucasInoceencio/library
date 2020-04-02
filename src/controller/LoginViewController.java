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

public class LoginViewController implements Initializable {

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

    @FXML
    void exit(ActionEvent event) {
        clearView();
        close();
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

        pfPassword.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                try {
                    logar();
                } catch (SQLException ex) {
                    errorLogin(ex);
                }
            }
        });
    }

    public boolean verifyLogin() throws SQLException {
        //return tfUser.getText().equals("admin") && pfPassword.getText().equals("admin20");
        return User.logar(tfUser.getText(), pfPassword.getText());
    }

    public void clearView() {
        tfUser.clear();
        pfPassword.clear();
    }

    public void close() {
        LoginFX.getStage().close();
    }

    public void logar() throws SQLException {
        if (verifyLogin()) {
            MainFX mainFX = new MainFX();
            close();
            try {
                mainFX.start(new Stage());
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

    public void errorLogin(Exception ex) {
        Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText("Erro ao realizar o login.");
        alert.setContentText(ex.getMessage());
        alert.show();
    }

}
