package controller;

import application.AuthorFX;
import dao.AuthorDAO;
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
import model.Author;

public class AuthorController implements Initializable {

    @FXML
    private TextField tfName;

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
        if (verifyArguments()) {
            createAuthor();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao preencher dados.");
            alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
            alert.showAndWait();
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
                if (verifyArguments()) {
                    createAuthor();
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao preencher dados.");
                    alert.setContentText("Existem dados obrigatórios que não foram preenchidos.");
                    alert.showAndWait();
                }
            }
        });
    }

    public void close() {
        AuthorFX.getStage().close();
    }

    public void createAuthor() {
        Author author = new Author(tfName.getText());
        try {
            AuthorDAO.create(author);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Erro ao cadastrar autor.");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public boolean verifyArguments() {
        return !tfName.getText().equals("");
    }

}
