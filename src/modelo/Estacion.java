package modelo;

public class Estacion {

    private String nombre;
    private String nivelOcupacion; // "ALTO", "MEDIO", "BAJO"

    public Estacion(String nombre, String nivelOcupacion) {
        this.nombre = nombre;
        this.nivelOcupacion = nivelOcupacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNivelOcupacion() {
        return nivelOcupacion;
    }

    public int calcularTiempoEspera() {
        switch (nivelOcupacion.toUpperCase()) {
            case "ALTO":
                return 10;
            case "MEDIO":
                return 5;
            case "BAJO":
                return 2;
            default:
                return 0;
        }
    }
}