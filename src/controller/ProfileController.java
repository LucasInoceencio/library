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

        try {
            User user = UserDAO.retrieve(DBConfig.idUserLogged);
            tfId.setText(String.valueOf(user.getId()));
            tfUser.setText(user.getUsername());
            if (user.getPerson() == null) {
                tfName.setText("");
            } else {
                user.getPerson().getName();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void alterPassword() throws SQLException {
        if (verifyFields()) {
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
            User user;
            try {
                user = UserDAO.retrieve(DBConfig.idUserLogged);
                user.setPassword(pfNewPassword.getText());
                UserDAO.update(user);
                close();
            } catch (SQLException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText("Erro ao comunicar com o banco de dados.");
                alert.setContentText(ex.getMessage());
                alert.show();
            }
        }
    }

    private boolean verifyNewPassword() {
        return (pfNewPassword.getText().equals(pfConfirmNewPassword.getText()));
    }
    
    private boolean verifyFields() {
        return (pfCurrentPassword.getText().equals("") || pfNewPassword.getText().equals("") 
                || pfConfirmNewPassword.getText().equals(""));
    }

    private boolean logar() throws SQLException {
        return User.logar(tfUser.getText(), pfCurrentPassword.getText());
    }

    private void close() {
        ProfileFX.getStage().close();
    }
}
