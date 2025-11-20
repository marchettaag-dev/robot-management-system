/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package segundaparcialmarchettaagustin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segundaparcialmarchettaagustin.entidades.Robot;
import segundaparcialmarchettaagustin.utils.CSVUtils;
import segundaparcialmarchettaagustin.utils.JsonUtils;

/**
 * FXML Controller class
 *
 * @author agust
 */
public class ViewController implements Initializable {
    
    static private String archivo = "ArchivoRobots";
    
    @FXML
    private ListView<Robot> listviewObjetos;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnEliminar;
    
    // aca tambien es donde se inicializan las listas de objetos que vamos a usar
    
    ArrayList<Robot> listaRobots;
    private CSVUtils CSVUtils;
    private JsonUtils JsonUtils;
    
    
    // private ServicioCSv<SerializableCSV> servicio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.listaRobots = new ArrayList<>();
        
        this.CSVUtils = new CSVUtils();
        this.JsonUtils = new JsonUtils();

        
        // cargar lista desde el archivo
       this.listaRobots = new ArrayList<>(CSVUtils.cargarListaCSV("vehiculos.csv", linea -> Robot.fromCSV(linea)));
       
       // Cargar
       this.listaRobots = (ArrayList) JsonUtils.cargar(this.archivo, Robot.class);

    if (this.listaRobots == null) {
        this.listaRobots = new ArrayList<>();
    }

    // mostrar en pantalla
    this.actualizarListView();
    }    
    
    // aca debemos de referenciar los metodos de los elementos que pusimos
    
    @FXML
    private void agregar(ActionEvent a){
        
        this.abrirFormulario(null);
        
    }
    
    
    private void abrirFormulario(Robot robotExistente){
        
        try{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/formulario.fxml"));
            
            Scene scene = new Scene(loader.load());
            
            FormularioController controller = loader.getController();
            controller.setListaRobots(listaRobots); 
          
            controller.setRobot(robotExistente);
            
            Stage stage = new Stage();
            
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.setScene(scene);
            
            stage.showAndWait();
            
            Robot resultado = controller.getRobot();
            
            if(resultado != null){
                
                if(robotExistente == null){ //esto quiere decir q es un alta
                    
                    if(!this.listaRobots.contains(resultado)){
                        this.listaRobots.add(resultado);
                    }
                }
                
                //GUARDAR ARCHIVO JSON
                this.JsonUtils.guardar(this.listaRobots, this.archivo);
                
                this.actualizarListView();
                
                //GUARDAR ARCHIVO CSV
                this.CSVUtils.guardarListaCSV("robotEnergia.csv", this.listaRobots);
                
                
            }
                    
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void actualizarListView(){
        this.listviewObjetos.getItems().clear();
        this.listviewObjetos.getItems().addAll(this.listaRobots);
    }
     
    
    @FXML
    private void modificar(ActionEvent a){
        Robot seleccionado = this.listviewObjetos.getSelectionModel().getSelectedItem();
        if(seleccionado!= null){
            this.abrirFormulario(seleccionado);
        }
    }
    
    @FXML
    private void eliminar(ActionEvent a){
        
        Robot seleccionado = this.listviewObjetos.getSelectionModel().getSelectedItem();
        if(seleccionado!= null){
            Optional<ButtonType> resultado = this.mostrarAlerta("Desea Eliminar el Objeto?", "Esta a punto de eliminar el objeto: ");
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                this.listaRobots.remove(seleccionado);
                this.actualizarListView();
                
                //guardar csv
                this.CSVUtils.guardarListaCSV("robotEnergia.csv", this.listaRobots);
                
                //guardar json
                this.JsonUtils.guardar(this.listaRobots, this.archivo);
            }
        }
    }
    
    @FXML
private void mostrarRobotsBajaEnergia() {
    // Filtrar robots con energía baja
    List<Robot> robotsBajaEnergia = listaRobots.stream()
            .filter(r -> r.getNivelEnergia() < 20) // umbral configurable
            .toList();

    // Mostrar en una nueva ventana
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/robotsenergia.fxml"));
        Parent root = loader.load();

        // Pasar la lista al nuevo controlador
        RobotsenergiaController controller = loader.getController();
        controller.setRobots(robotsBajaEnergia);

        Stage stage = new Stage();
        stage.setTitle("Robots con energía baja");
        stage.setScene(new Scene(root));
        stage.show();

        // Guardar en CSV
         this.CSVUtils.guardarListaCSV("robotEnergia.csv", this.listaRobots);

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
    private Optional mostrarAlerta(String titulo, String mensaje) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    Optional<ButtonType> resultado = alert.showAndWait();
    return resultado;
}
 
}
