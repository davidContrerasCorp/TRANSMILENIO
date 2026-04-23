package modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaTransmilenio {

    private List<Ruta> rutas;
    private List<Troncal> troncales;
    private List<Estacion> estaciones;

    public SistemaTransmilenio() {
        rutas = new ArrayList<>();
        troncales = new ArrayList<>();
        estaciones = new ArrayList<>();
    }

    public void agregarRuta(Ruta ruta) {
        rutas.add(ruta);
    }

    public void agregarTroncal(Troncal troncal) {
        troncales.add(troncal);
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }

    public Estacion buscarEstacionPorNombre(String nombre) {
        for (Estacion e : estaciones) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }
}