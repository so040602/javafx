package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameProjectMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlfile = Utility.FXML_PATH + "GameProject.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlfile));

        Parent container = fxmlLoader.load();
        Scene scene = new Scene(container);

        String myStyle = getClass().getResource(Utility.CSS_PATH + "CartCss.css").toString();
        scene.getStylesheets().add(myStyle);

        stage.setTitle("게임 리스트");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
