/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package segundaparcialmarchettaagustin.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import segundaparcialmarchettaagustin.entidades.Robot;

/**
 * FXML Controller class
 *
 * @author agust
 */
public class RobotsenergiaController implements Initializable {

    @FXML
    private ListView<String> listViewRobots;

    public void setRobots(List<Robot> robots) {
        listViewRobots.getItems().clear();
        for (Robot r : robots) {
            listViewRobots.getItems().add(r.getNombre() + " - Energía: " + r.getNivelEnergia());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

