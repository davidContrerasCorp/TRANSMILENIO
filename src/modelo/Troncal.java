package modelo;

import java.util.ArrayList;
import java.util.List;

public class Troncal {

    private String nombre;
    private double velocidadPromedio;
    private List<Estacion> estaciones;

    public Troncal(String nombre, double velocidadPromedio) {
        this.nombre = nombre;
        this.velocidadPromedio = velocidadPromedio;
        this.estaciones = new ArrayList<>();
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }

    public String getNombre() {
        return nombre;
    }

    public double getVelocidadPromedio() {
        return velocidadPromedio;
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }
}