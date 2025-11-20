/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import segundaparcialmarchettaagustin.entidades.CSVEscritor;
import segundaparcialmarchettaagustin.entidades.CSVParseador;

/**
 *
 * @author agust
 */
public class CSVUtils {
    

    public static <T extends CSVEscritor> void guardarListaCSV(String ruta, List<T> lista){
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {

            for (T obj : lista) {
                bw.write(obj.toCSV());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error guardando CSV: " + e.getMessage());
        }
    }
    
    

    public static <T> List<T> cargarListaCSV(String ruta, CSVParseador<T> parser){
        List<T> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                T obj = parser.fromCSV(linea);
                lista.add(obj);
            }

        } catch (IOException e) {
            System.out.println("Error leyendo CSV: " + e.getMessage());
        }

        return lista;
    }
}
