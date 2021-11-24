package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/View.fxml")));
            //____________remove barra topo_____________
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
