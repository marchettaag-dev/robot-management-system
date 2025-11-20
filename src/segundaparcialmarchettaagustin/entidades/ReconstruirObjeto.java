/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.entidades;

import java.util.Map;
import segundaparcialmarchettaagustin.exception.ReconstructorObjetosException;

/**
 *
 * @author agust
 */
public interface ReconstruirObjeto {
    
    static Robot reconstruirRobot(Map<String, Object> d) throws ReconstructorObjetosException {
        String tipo = (String) d.get("tipo");
        String nombre = (String) d.get("nombre");
        double nivelEnergia = (Double) d.get("nivelEnergia");
        Double numeroSerieRaw = (Double) d.get("numeroSerie");
        int numeroSerie = numeroSerieRaw.intValue();
        

        switch (tipo) {
            case "DOMESTICO":
                int cantidadTareas = ((Double) d.get("cantidadTareas")).intValue();
                return new Domestico (nombre, nivelEnergia,  numeroSerie, cantidadTareas);

            case "INDUSTRIAL":
                int capacidadCarga = ((Double) d.get("capacidadCarga")).intValue();
                return new Industrial(nombre, nivelEnergia,  numeroSerie, capacidadCarga);

            default:
                throw new ReconstructorObjetosException("Tipo de objeto desconocido: " + tipo);
        }
    }

}