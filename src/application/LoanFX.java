
package application;

import controller.LoanController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Loan;

public class LoanFX extends Application {
    
    private static Stage stage;
    
    public LoanFX() {
    }
    
    public LoanFX(Loan loan) {
        LoanController.setLoan(loan);
    }
    
    @Override
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/LoanView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Empréstimo");
            stage.setScene(scene);
            stage.show();
            setStage(stage);
        } catch (IOException ex) {
            Logger.getLogger(LoginFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getStage() {
        return stage;
    }
    
    public static void setStage(Stage stage) {
        LoanFX.stage = stage;
    }
    
}
