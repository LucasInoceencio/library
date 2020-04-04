/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import controller.BookController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;

/**
 *
 * @author Lucas
 */
public class BookFX extends Application {

    private static Stage stage;

    public BookFX() {
    }

    public BookFX(Book book) {
        BookController.setBook(book);
    }

    @Override
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/BookView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Livro");
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
        BookFX.stage = stage;
    }

}
