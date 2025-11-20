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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import segundaparcialmarchettaagustin.entidades.Domestico;
import segundaparcialmarchettaagustin.entidades.Industrial;
import segundaparcialmarchettaagustin.entidades.Robot;
import segundaparcialmarchettaagustin.exception.CamposVaciosException;
import segundaparcialmarchettaagustin.exception.NumeroSerieNegativoException;
import segundaparcialmarchettaagustin.exception.RobotEncontradoException;
import segundaparcialmarchettaagustin.exception.ValoresFueraDelLimiteException;

/**
 * FXML Controller class
 *
 * @author agust
 */
public class FormularioController implements Initializable {

  
    @FXML
    private Button btnConfirmar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private TextField txfNombre;
    
    @FXML
    private TextField txfNivelEnergia;
    
    @FXML
    private TextField txfNumeroSerie;
    
    @FXML
    private RadioButton rbDomestico;

    @FXML
    private RadioButton rbIndustrial;

    @FXML
    private TextField txtDatoEspecifico;
    
    @FXML
    private ToggleGroup grupoRobot;
    
    @FXML
    private void actualizarLabel() {
        if (rbDomestico.isSelected()) {
            this.lblActividad.setText("Cantidad de Tareas: ");
        } else if (rbIndustrial.isSelected()) {
           this.lblActividad.setText("Capacidad de Carga Maxima (kg): ");
        }
}
    
    @FXML
    private Label lblActividad;

    
    private Robot robot;
    
    private boolean confirmado;
    
    private List<Robot> listaRobots;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.grupoRobot.selectToggle(this.rbDomestico); 
        this.actualizarLabel();

    }    
    
    
    public Robot getRobot(){
        
        return this.confirmado ? this.robot : null;
    }
    

    
    public void setRobot(Robot r){
        this.robot = r;
        String datoEspecifico = null;
        if(r !=null){
            
            this.txfNombre.setText(String.valueOf(r.getNombre()));
            this.txfNivelEnergia.setText(String.valueOf(r.getNivelEnergia()));
            this.txfNumeroSerie.setText(String.valueOf(r.getNumeroSerie()));
            this.txfNumeroSerie.setDisable(true);
            
            if(r instanceof Domestico d){
                 datoEspecifico = String.valueOf(d.getCantidadTareas());
                 
                 this.grupoRobot.selectToggle(this.rbDomestico); 
            } else if (r instanceof Industrial i){
                datoEspecifico = String.valueOf(i.getCapacidadCarga());
                
                this.grupoRobot.selectToggle(this.rbIndustrial); 
            }
            this.rbDomestico.setDisable(true);
            this.rbIndustrial.setDisable(true);
            this.txtDatoEspecifico.setText(String.valueOf(datoEspecifico));
        }
    }
    
    
    @FXML
    private void confirmar() throws CamposVaciosException, RobotEncontradoException, ValoresFueraDelLimiteException, NumeroSerieNegativoException{
        try{
         // Validación de campos vacíos
        if (this.validadCamposVacios()) {
            throw new CamposVaciosException("Debe completar todos los campos.");
        }
        
        // Validacion de valores dentro del rango
        if(this.validarRango()){
            throw new ValoresFueraDelLimiteException("Ingrese valores entre el 0 y el 100 en el nivel de energia");
        }
        
        // Validacion de numero de serie permitido
        if(this.validarNumeroSerie()){
            throw new NumeroSerieNegativoException("El numero de serie no puede ser negativo");
        }
        
        String nombre = this.txfNombre.getText();
        int numeroSerie = Integer.parseInt(this.txfNumeroSerie.getText());
        double nivelEnergia = Double.parseDouble(this.txfNivelEnergia.getText());
        
        String dato = txtDatoEspecifico.getText();

        if (this.robot == null) {
            if (rbDomestico.isSelected()) {
                int cantidadTareas = Integer.parseInt(dato);
                
                this.robot = new Domestico(nombre, nivelEnergia, numeroSerie, cantidadTareas);
                
            } else if (rbIndustrial.isSelected()) {
                int capacidadMaxima = Integer.parseInt(dato);
                
                  this.robot = new Industrial(nombre, nivelEnergia, numeroSerie, capacidadMaxima);
                
            }
            
             //buscamos si ya esta en la lista
        if(this.estaAgregado(this.robot)){
            throw new RobotEncontradoException("Ese objeto ya se encuentra agregado en el sistema");
        }
            
            
        } else {
            cargarDatosComunes(this.robot, nombre, nivelEnergia, numeroSerie);

            if (this.robot instanceof Domestico) {
                ((Domestico) robot).setCantidadTareas(Integer.parseInt(dato));
            } else if (this.robot instanceof Industrial) {
                ((Industrial) robot).setCapacidadCarga(Integer.parseInt(dato));
            } 
    }
        
        // 4) Validación de duplicado ANTES de confirmar y cerrar
        if (this.robot == null) { // solo al agregar
            if (estaAgregado(robot)) {
                throw new RobotEncontradoException("Ese objeto ya se encuentra agregado en el sistema");
            }
        } else { 
            // Si estás modificando, y cambiaste algún campo que lo hace duplicado contra otro,
            // podés validar evitando compararte a vos mismo:
            for (Robot r : this.listaRobots) {
                if (r != robot && r.equals(robot)) {
                    throw new RobotEncontradoException("Los datos modificados duplican otro registro existente");
                }
            }
        }
                
        confirmado = true;
        this.cerrarVentana();
        
    } catch (CamposVaciosException e){
        this.mostrarAlerta("Campos incompletos", e.getMessage());
    } catch (NumberFormatException e) {
        mostrarAlerta("Error de formato", "Ingrese solo números en los campos numéricos.");
    } catch(RobotEncontradoException e){
         mostrarAlerta("Error de objeto", e.getMessage());
         confirmado = false;
         if (this.robot == null) {
        }
        return;
    } catch(ValoresFueraDelLimiteException e){
        mostrarAlerta("Error de objeto", e.getMessage());
    } catch(NumeroSerieNegativoException e){
        mostrarAlerta("Error de objeto", e.getMessage());
    }
    }
    
    @FXML
    private void cancelar(){
        this.cerrarVentana();
    }
    
    
    private void cerrarVentana(){
        
        Stage stage = (Stage)btnCancelar.getScene().getWindow();
        
        stage.close();
    }
    
    
    private void mostrarAlerta(String titulo, String mensaje) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}
    
    private void cargarDatosComunes(Robot r, String nombre, double nivelEnergia, int numeroSerie) {
    r.setNombre(nombre);
    r.setNivelEnergia(nivelEnergia);
    r.setNumeroSerie(numeroSerie);
}
    
    private boolean estaAgregado(Robot r){
        for( Robot vehiculo : this.listaRobots){
            if(vehiculo.equals(r)){
                return true;
            }
        }
        return false;
    }
    
    public void setListaRobots(List<Robot> listaRobots) {
    this.listaRobots = listaRobots;
    }
    
    private boolean validarRango(){
        double nivelEnergiaIngresado = Double.parseDouble(this.txfNivelEnergia.getText());
        if(nivelEnergiaIngresado<0 || nivelEnergiaIngresado>100){
            return true;
        }
        return false;
    }
    
    private boolean validadCamposVacios(){
        if(this.txfNombre.getText().isEmpty() || this.txfNumeroSerie.getText().isEmpty() || this.txfNivelEnergia.getText().isEmpty()
            || txtDatoEspecifico.getText().isEmpty()){
            return true;
        }
        return false;
    }
    
    private boolean validarNumeroSerie(){
        int numeroSerieIngresado = Integer.parseInt(this.txfNumeroSerie.getText());
        if(numeroSerieIngresado<0){
            return true;
        }
        return false;
    }
}
