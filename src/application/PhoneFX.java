
package application;

import controller.PhoneController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Phone;

public class PhoneFX extends Application {
    
    private static Stage stage;
    
    public PhoneFX() {
    }
    
    public PhoneFX(Phone phone) {
        PhoneController.setPhone(phone);
    }
    
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/PhoneView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Telefone");
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
        PhoneFX.stage = stage;
    }
    
}
