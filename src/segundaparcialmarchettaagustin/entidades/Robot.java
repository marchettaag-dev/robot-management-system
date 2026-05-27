/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.entidades;

import java.util.Objects;

/**
 *
 * @author agust
 */
public abstract class Robot implements CSVEscritor{
    
    protected String tipo;
    protected String nombre;
    protected double nivelEnergia;
    protected int numeroSerie;

    public Robot(String tipo, String nombre, double nivelEnergia, int numeroSerie) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.nivelEnergia = nivelEnergia;
        this.numeroSerie = numeroSerie;
    }

    public Robot() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getNivelEnergia() {
        return this.nivelEnergia;
    }

    public void setNivelEnergia(double nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public int getNumeroSerie() {
        return this.numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.numeroSerie;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Robot otro= (Robot) obj;
        return Objects.equals(this.numeroSerie, otro.numeroSerie);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" nombre: ").append(this.nombre);
        sb.append(", nivelEnergia: ").append(this.nivelEnergia);
        sb.append(", numeroSerie: ").append(this.numeroSerie);
        return sb.toString();
    }
    
    
     public static Robot fromCSV(String line) {
        String[] p = line.split(";");
        String tipo = p[0];

        switch (tipo) {
            case "DOMESTICO":       
                return Domestico.fromCSV(line);
            case "INDUSTRIAL":       
                return Industrial.fromCSV(line);
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        }
     }
    
}
