/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package segundaparcialmarchettaagustin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author agust
 */
public class SegundaParcialMarchettaAgustin extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/view.fxml"));
        
        Scene scene = new Scene(loader.load());
        
        stage.setScene(scene);
        
        stage.setTitle("Gestion del Laboratorio de Robots");
        
        //stage.setOnCloseRequest(e -> cont);
        
        stage.show();
        
    }

 
    public static void main(String[] args) {
        Application.launch(args);
    }
}