package udecaldas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Archivo {
    private String ruta;
    private Estudiantes estudiantes;

    public Archivo(String ruta) {
        this.ruta = ruta;
    }

    public Archivo(String ruta, Estudiantes estudiantes) {
        this.ruta = ruta;
        this.estudiantes = estudiantes;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public boolean buscarArchivo(String ruta) {
        File Ruta = new File(ruta);

        if (Ruta.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean archivoAceptado(String archivo) {
        File Ruta = new File(archivo);

        String nombreArchivo = Ruta.getName();

        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
        if (buscarArchivo(archivo)) {
            if (extension.equals("csv")) {
                return true;
            } else {
                return false;
            }
        }
        System.out.println("La ruta no existe por tanto no se busco la extension: " + false);
        return false;
    }

    public String crearNuevoArchivo(String ruta) {
        File file = new java.io.File(ruta);
        // file.mkdirs(); // wrong!
        file.getParentFile().mkdirs(); // correct!

        String rutaCarpeta = ruta;
        String nombreArchivo = "Estudiantes.json";

        File carpeta = new File(rutaCarpeta);

        File archivoJson = new File(carpeta, nombreArchivo);

        try {
            // Verificar si la carpeta existe
            if (buscarArchivo(rutaCarpeta) == false) {
                String fallo = "Ruta no encontrada";
                return fallo;
            }

            // Crear el archivo JSON vacío
            if (archivoJson.createNewFile()) {
                System.out.println("Archivo JSON creado con éxito en: " + archivoJson.getAbsolutePath());
            } else {
                System.out.println("El archivo JSON ya existe en: " + archivoJson.getAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-------";
    }

    public void pasaInformacion(String ruta, String rutaCsv) {
        String archivoCsv = rutaCsv;
        List<Estudiantes> listaEstudiantes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCsv))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                System.out.println("datos: " + linea);
                if (datos.length == 3) {
                    String id = datos[0];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    Estudiantes estudiante = new Estudiantes(id, nombre, apellido);
                    listaEstudiantes.add(estudiante);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.err.println("El archivo CSV no pudo encontrarse: " + fnfe.getMessage());
        } catch (AccessDeniedException ade) {
            ade.printStackTrace();
            System.err.println("Acceso denegado al archivo de salida: " + ade.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        // Construir manualmente el JSON en el orden deseado
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"estudiantes\":[");

        for (Estudiantes estudiante : listaEstudiantes) {
            jsonBuilder.append("{\"id\":\"").append(estudiante.getId()).append("\",")
                    .append("\"nombre\":\"").append(estudiante.getNombre()).append("\",")
                    .append("\"apellido\":\"").append(estudiante.getApellido()).append("\"},");
        }

        // Eliminar la coma final si hay elementos en la lista
        if (!listaEstudiantes.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }

        jsonBuilder.append("]}");

        // Convertir el objeto JSON a una cadena JSON
        String json = jsonBuilder.toString();

        try (FileWriter fileWriter = new FileWriter(ruta)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }

}
