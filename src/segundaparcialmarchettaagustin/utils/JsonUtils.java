/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package segundaparcialmarchettaagustin.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import segundaparcialmarchettaagustin.entidades.ReconstruirObjeto;
import segundaparcialmarchettaagustin.entidades.Robot;
import segundaparcialmarchettaagustin.entidades.jsonSerializacion;
import segundaparcialmarchettaagustin.exception.ReconstructorObjetosException;


/**
 *
 * @author agust
 */
public class JsonUtils<T> implements jsonSerializacion<T>, ReconstruirObjeto{
    
      private final Gson gson = new Gson();

    @Override
    public void guardar(List<T> lista, String rutaArchivo) {
        try (Writer writer = new FileWriter(rutaArchivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<T> cargar(String rutaArchivo, Class<T> clase) {
    File file = new File(rutaArchivo);
    if (!file.exists()) return new ArrayList<>();

    try (Reader reader = new FileReader(file)) {
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> datos = gson.fromJson(reader, tipoLista);

        List<T> objetos = new ArrayList<>();
        for (Map<String, Object> d : datos) {
            String tipo = (String) d.get("tipo");

            if (clase == Robot.class) {
                
                Robot r = ReconstruirObjeto.reconstruirRobot(d);
                objetos.add(clase.cast(r)); 
            } else {
                
                objetos.add(gson.fromJson(new Gson().toJson(d), clase));
            }
        }
        return objetos;

    } catch (IOException e) {
        System.out.println("Error al cargar: " + e.getMessage());
        return new ArrayList<>();
    } catch(ReconstructorObjetosException e){
        System.out.println("Error al cargar: " + e.getMessage());
    }
          return null;
    }
}
