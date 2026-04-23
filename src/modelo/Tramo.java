package modelo;

import java.io.Serializable;

/**
 * Representa un segmento o tramo entre dos estaciones en una troncal.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class Tramo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Estacion origen;
    private Estacion destino;
    private double distancia; // en metros

    /**
     * Crea un nuevo tramo.
     * @param origen Estación de inicio.
     * @param destino Estación de fin.
     * @param distancia Distancia en metros entre las estaciones.
     */
    public Tramo(Estacion origen, Estacion destino, double distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    public Estacion getOrigen() {
        return origen;
    }

    public Estacion getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }
}
