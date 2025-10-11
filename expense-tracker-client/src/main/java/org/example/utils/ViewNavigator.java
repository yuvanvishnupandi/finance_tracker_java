package org.example.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ViewNavigator is a utility class responsible for managing the navigation
 * between different scenes within the same primary stage (window) of a JavaFX application.
 * It provides methods to set the main stage and switch between different views (scenes).
 */
public class ViewNavigator {
    private static Stage mainStage;

    public static void setMainStage(Stage stage){
        mainStage = stage;
    }

    public static void switchViews(Scene scene){
        if(mainStage != null){
            mainStage.setScene(scene);
            mainStage.show();
        }
    }
}
