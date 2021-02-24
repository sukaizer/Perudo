package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class MainStage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../ressources/fxmlFiles/Start.fxml"));
        Scene scene = new Scene(root);
        scene.setRoot(root);

        primaryStage.setTitle("Perudo");
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        Image icon = new Image(new FileInputStream("src/ressources/files/icon.jpeg"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}

