package controller;

import application.ProfileFX;
import dao.UserDAO;
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
import jdbc.DBConfig;
import model.Cryptography;
import model.User;

public class ProfileController implements Initializable {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ProfileController.user = user;
    }

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfUser;

    @FXML
    private TextField tfName;

    @FXML
    private PasswordField pfCurrentPassword;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfConfirmNewPassword;

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
        try {
            alterPassword();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Erro ao comunicar com o banco de dados.");
            alert.setContentText(ex.getMessage());
            alert.show();
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
                try {
                    alterPassword();
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                    alert.setContentText(ex.getMessage());
                    alert.show();
                }
            }
        });

        User auxUser = DBConfig.userLogged;
        tfId.setText(String.valueOf(auxUser.getId()));
        tfUser.setText(auxUser.getUsername());
        if (auxUser.getPerson() == null) {
            tfName.setText("");
        } else {
            auxUser.getPerson().getName();
        }
    }

    private void alterPassword() throws SQLException {
        if (mandatoryFieldsNotFilled()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Erro ao preencher campos.");
            alert.setContentText("Existem campos obrigatórios que não foram preenchidos.");
            alert.show();
        } else if (!verifyNewPassword()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Verifique a nova senha.");
            alert.setContentText("O campo nova senha não é igual ao campo confirmar nova senha.");
            alert.show();
        } else if (!logar()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Verifique a senha atual.");
            alert.setContentText("A senha atual não confere com a senha cadastrada.");
            alert.show();
        } else {
            User auxUser;
            String hash = new Cryptography().createHash(pfNewPassword.getText());
            if (hash != null) {
                try {
                    auxUser = DBConfig.userLogged;
                    auxUser.setPassword(hash);
                    UserDAO.update(auxUser);
                    close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
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

    private boolean verifyNewPassword() {
        return (pfNewPassword.getText().equals(pfConfirmNewPassword.getText()));
    }

    private boolean mandatoryFieldsNotFilled() {
        return (pfCurrentPassword.getText().equals("") || pfNewPassword.getText().equals("")
                || pfConfirmNewPassword.getText().equals(""));
    }

    private boolean logar() throws SQLException {
        String hash = new Cryptography().createHash(pfCurrentPassword.getText());
        if (hash != null) {
            return User.logar(tfUser.getText(), hash);
        }
        return false;
    }

    private void close() {
        ProfileFX.getStage().close();
    }
}
