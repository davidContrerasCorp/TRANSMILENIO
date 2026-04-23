package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa una troncal del sistema (ej. Caracas, Calle 80).
 * Las troncales son lineales y contienen estaciones y los tramos que las conectan.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class Troncal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private double velocidadPromedio; // metros/minuto
    
    /**
     * Se utiliza Set (LinkedHashSet) para las estaciones porque:
     * 1. No permite estaciones duplicadas por nombre.
     * 2. Mantiene el orden de inserción, lo cual es vital para una troncal lineal.
     */
    private Set<Estacion> estaciones;
    
    /**
     * Se utiliza List (ArrayList) para los tramos porque:
     * 1. Representan una secuencia física de segmentos.
     * 2. Permiten acceso posicional eficiente.
     */
    private List<Tramo> tramos;

    public Troncal(String nombre, double velocidadPromedio) {
        this.nombre = nombre;
        this.velocidadPromedio = velocidadPromedio;
        this.estaciones = new LinkedHashSet<>();
        this.tramos = new ArrayList<>();
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }

    /**
     * Agrega un tramo a la troncal.
     * @param tramo El tramo a agregar.
     */
    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
        // Asegurar que las estaciones del tramo estén en la lista de la troncal
        estaciones.add(tramo.getOrigen());
        estaciones.add(tramo.getDestino());
    }

    public String getNombre() {
        return nombre;
    }

    public double getVelocidadPromedio() {
        return velocidadPromedio;
    }

    public Set<Estacion> getEstaciones() {
        return estaciones;
    }

    public List<Tramo> getTramos() {
        return tramos;
    }
}