package udecaldas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner Ruta = new Scanner(System.in);
        System.out.println("Ingrese la ruta: ");
        String directorio = Ruta.nextLine();

        Scanner RutaCsv = new Scanner(System.in);
        System.out.println("Ingrese la ruta del archivo csv");
        String rutaAchivo = RutaCsv.nextLine();

        Scanner RutaJson = new Scanner(System.in);
        System.out.println("Ingrese la ruta del archivo json");
        String rutaNuevoAchivo = RutaJson.nextLine();

        Archivo archivo = new Archivo(directorio);

        archivo.buscarArchivo(directorio);

        archivo.archivoAceptado(directorio);
        archivo.crearNuevoArchivo(directorio);
        archivo.pasaInformacion(rutaNuevoAchivo, rutaAchivo);
    }
}