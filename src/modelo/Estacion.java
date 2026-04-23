package modelo;

import java.io.Serializable;

/**
 * Representa una estación del sistema Transmilenio.
 * Implementa Serializable para permitir el guardado de la información del sistema.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class Estacion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Niveles de ocupación posibles para una estación.
     */
    public enum NivelOcupacion {
        ALTO, MEDIO, BAJO
    }

    private String nombre;
    private NivelOcupacion ocupacion;

    /**
     * Crea una nueva estación.
     * @param nombre El nombre de la estación.
     * @param ocupacion El nivel inicial de ocupación.
     */
    public Estacion(String nombre, NivelOcupacion ocupacion) {
        this.nombre = nombre;
        this.ocupacion = ocupacion;
    }

    public String getNombre() {
        return nombre;
    }

    public NivelOcupacion getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(NivelOcupacion ocupacion) {
        this.ocupacion = ocupacion;
    }

    /**
     * Calcula el tiempo de espera basado en el nivel de ocupación.
     * @return Tiempo en minutos.
     */
    public int calcularTiempoEspera() {
        switch (ocupacion) {
            case ALTO:
                return 10;
            case MEDIO:
                return 5;
            case BAJO:
                return 2;
            default:
                return 0;
        }
    }
}