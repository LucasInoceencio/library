
package application;

import controller.ProfileController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public class ProfileFX extends Application {
    
    private static Stage stage;

    public ProfileFX() {
    }

    public ProfileFX(User user) {
        ProfileController.setUser(user);
    }
    
    @Override
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ProfileView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Meu Perfil");
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
        ProfileFX.stage = stage;
    }
}
