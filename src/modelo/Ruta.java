package modelo;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Representa una ruta del sistema que para en estaciones específicas.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    
    /**
     * Se usa LinkedHashSet porque:
     * 1. Las paradas de una ruta tienen un orden secuencial estricto.
     * 2. Evita que una misma estación se registre dos veces por error.
     */
    private Set<Estacion> estaciones;

    public Ruta(String nombre) {
        this.nombre = nombre;
        this.estaciones = new LinkedHashSet<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }

    public Set<Estacion> getEstaciones() {
        return estaciones;
    }
}