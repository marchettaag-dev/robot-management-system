/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.entidades;

/**
 *
 * @author agust
 */
public class Domestico extends Robot{
    
    private int cantidadTareas;

    public Domestico( String nombre, double nivelEnergia, int numeroSerie, int cantidadTareas) {
        super("DOMESTICO", nombre, nivelEnergia, numeroSerie);
        this.cantidadTareas = cantidadTareas;
    }

    public Domestico() {
    }

    public int getCantidadTareas() {
        return this.cantidadTareas;
    }

    public void setCantidadTareas(int cantidadTareas) {
        this.cantidadTareas = cantidadTareas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DOMESTICO |");
        sb.append(super.toString());
        sb.append(" cantidadTareas: ").append(this.cantidadTareas);
        return sb.toString();
    }
    
    @Override
    public String toCSV() {
        return "DOMESTICO;"+ ";" +this.nombre+ ";" +this.nivelEnergia+ ";" +this.numeroSerie+ ";" +this.cantidadTareas;
    }
    
    public static Domestico fromCSV(String linea) {
        String[] p = linea.split(";");
        return new Domestico(
                p[1],     // nombre
                Double.parseDouble(p[2]),     // nivelEnergia
                Integer.parseInt(p[3]), //nivelenergia
                Integer.parseInt(p[4]) //cantidad tareas
                
        );
    }

}
