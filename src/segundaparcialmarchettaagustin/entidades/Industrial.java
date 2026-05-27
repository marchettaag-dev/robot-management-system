/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.entidades;

/**
 *
 * @author agust
 */
public class Industrial extends Robot{
    
    private int capacidadCarga;

    public Industrial( String nombre, double nivelEnergia, int numeroSerie, int capacidadCarga) {
        super("INDUSTRIAL", nombre, nivelEnergia, numeroSerie);
        this.capacidadCarga = capacidadCarga;
    }

    public Industrial() {
    }

    public int getCapacidadCarga() {
        return this.capacidadCarga;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("INDUSTRIAL |");
        sb.append(super.toString());
        sb.append(" capacidadCarga: ").append(this.capacidadCarga);
        return sb.toString();
    }
    
    @Override
    public String toCSV() {
        return "INDUSTRIAL;"+ ";" +this.nombre+ ";" +this.nivelEnergia+ ";" +this.numeroSerie+ ";" +this.capacidadCarga;
    }
    
    public static Industrial fromCSV(String linea) {
        String[] p = linea.split(";");
        return new Industrial(
                p[1],     // nombre
                Double.parseDouble(p[2]),     // nivelEnergia
                Integer.parseInt(p[3]), //nivelenergia
                Integer.parseInt(p[4]) //capacidad carga
                
        );
    }
}
