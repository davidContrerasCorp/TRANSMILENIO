package modelo;

/**
 * Representa un paso en un plan de recorrido.
 * Contiene el nombre de la estación y el nombre de la ruta que se toma en esa estación.
 * La última parada del plan tendrá la ruta como null.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class ParadaPlan {
    private String nombreEstacion;
    private String nombreRuta;

    public ParadaPlan(String nombreEstacion, String nombreRuta) {
        this.nombreEstacion = nombreEstacion;
        this.nombreRuta = nombreRuta;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }
}
