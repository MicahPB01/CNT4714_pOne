/* Name: Micah Puccio-Ball
 Course: CNT 4714 – Spring 2024
 Assignment title: Project 1 – An Event-driven Enterprise Simulation
 Date: Friday, January 26, 2024
*/

package com.nile.cnt4714_pone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NileApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NileApplication.class.getResource("NileCom.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 466);
        stage.setTitle("Nile Dot Com!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}