package modelo;

import java.util.ArrayList;
import java.util.List;

public class Ruta {

    private String nombre;
    private List<Estacion> estaciones;

    public Ruta(String nombre) {
        this.nombre = nombre;
        this.estaciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }
}