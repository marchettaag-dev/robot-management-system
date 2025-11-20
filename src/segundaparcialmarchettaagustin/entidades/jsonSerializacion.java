/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.entidades;

import java.util.List;

/**
 *
 * @author agust
 */
public interface jsonSerializacion<T> {
    void guardar(List<T> lista, String rutaArchivo);
    List<T> cargar(String rutaArchivo, Class<T> clase);
}
