package persistencia;

import excepciones.TransmilenioException;
import modelo.Estacion;
import modelo.Ruta;
import modelo.SistemaTransmilenio;
import modelo.Troncal;

import java.io.*;
import java.util.List;

/**
 * Encargado del manejo de archivos y persistencia del sistema.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class GestorPersistencia {

    /**
     * 1. Importar una nueva ruta desde un archivo de texto plano.
     */
    public Ruta importarRuta(String path, SistemaTransmilenio sistema) throws TransmilenioException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String nombreRuta = br.readLine();
            if (nombreRuta == null) throw new TransmilenioException(TransmilenioException.ARCHIVO_VACIO);
            
            Ruta ruta = new Ruta(nombreRuta);
            String linea;
            while ((linea = br.readLine()) != null) {
                String nombreEstacion = linea.trim();
                if (!nombreEstacion.isEmpty()) {
                    Estacion e = sistema.buscarEstacionPorNombre(nombreEstacion);
                    if (e == null) {
                        e = new Estacion(nombreEstacion, Estacion.NivelOcupacion.MEDIO);
                        sistema.agregarEstacion(e);
                    }
                    ruta.agregarEstacion(e);
                }
            }
            return ruta;
        } catch (IOException e) {
            throw new TransmilenioException(TransmilenioException.ERROR_PERSISTENCIA_ARCHIVO);
        }
    }

    /**
     * 2. Exportar a un archivo de texto las rutas sin transbordos entre dos estaciones.
     */
    public void exportarRutasSinTransbordo(String origen, String destino, String path, SistemaTransmilenio sistema) 
            throws TransmilenioException {
        List<String> rutas = sistema.getRutasSinTransbordo(origen, destino);
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println("Rutas directas de " + origen + " a " + destino + ":");
            for (String r : rutas) {
                pw.println(r);
            }
        } catch (IOException e) {
            throw new TransmilenioException(TransmilenioException.ERROR_PERSISTENCIA_ARCHIVO);
        }
    }

    /**
     * 3. Salvar la información actual de una troncal mediante serialización de objetos.
     */
    public void salvarTroncal(Troncal troncal, String path) throws TransmilenioException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(troncal);
        } catch (IOException e) {
            throw new TransmilenioException(TransmilenioException.ERROR_PERSISTENCIA_ARCHIVO);
        }
    }

    /**
     * Cargar una troncal serializada.
     */
    public Troncal cargarTroncal(String path) throws TransmilenioException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (Troncal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new TransmilenioException(TransmilenioException.ERROR_PERSISTENCIA_ARCHIVO);
        }
    }
}
