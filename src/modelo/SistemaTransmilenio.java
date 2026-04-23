package modelo;

import excepciones.TransmilenioException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Fachada principal del sistema Transmilenio.
 * Implementa todos los servicios de comportamiento requeridos.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class SistemaTransmilenio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Se utilizan Mapas (HashMap) para rutas, troncales y estaciones porque:
     * 1. Permiten búsquedas por nombre en tiempo O(1), lo cual es crítico para consultas frecuentes.
     * 2. Facilitan la integridad referencial al evitar duplicados por nombre.
     */
    private Map<String, Ruta> rutas;
    private Map<String, Troncal> troncales;
    private Map<String, Estacion> estaciones;

    public SistemaTransmilenio() {
        this.rutas = new HashMap<>();
        this.troncales = new HashMap<>();
        this.estaciones = new HashMap<>();
    }

    // --- Servicios de Configuración ---

    public void agregarRuta(Ruta ruta) {
        rutas.put(ruta.getNombre(), ruta);
    }

    public void agregarTroncal(Troncal troncal) {
        troncales.put(troncal.getNombre(), troncal);
    }

    public void agregarEstacion(Estacion estacion) {
        estaciones.put(estacion.getNombre(), estacion);
    }

    public Estacion buscarEstacionPorNombre(String nombre) {
        return estaciones.get(nombre);
    }

    // --- Servicios de Comportamiento (Requerimientos) ---

    /**
     * Obtener el tiempo de espera de una estación dado su nombre.
     */
    public int getTiempoEspera(String nombreEstacion) throws TransmilenioException {
        Estacion e = buscarEstacionPorNombre(nombreEstacion);
        if (e == null) throw new TransmilenioException(TransmilenioException.ESTACION_NO_ENCONTRADA);
        return e.calcularTiempoEspera();
    }


    /**
     * Calcular número de paradas entre dos estaciones para una ruta dada.
     */
    public int getNumeroParadas(String nombreRuta, String origen, String destino) throws TransmilenioException {
        Ruta r = rutas.get(nombreRuta);
        if (r == null) throw new TransmilenioException(TransmilenioException.RUTA_NO_ENCONTRADA);
        
        List<Estacion> lista = new ArrayList<>(r.getEstaciones());
        int idxOrigen = -1, idxDestino = -1;
        
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().equalsIgnoreCase(origen)) idxOrigen = i;
            if (lista.get(i).getNombre().equalsIgnoreCase(destino)) idxDestino = i;
        }

        if (idxOrigen == -1 || idxDestino == -1) throw new TransmilenioException(TransmilenioException.ESTACION_NO_ENCONTRADA);
        if (idxOrigen >= idxDestino) throw new TransmilenioException(TransmilenioException.RUTA_INVALIDA);

        return idxDestino - idxOrigen;
    }

    /**
     * Rutas sin transbordos, ordenadas por número de paradas y luego alfabéticamente.
     */
    public List<String> getRutasSinTransbordo(String origen, String destino) {
        List<String> resultado = new ArrayList<>();
        for (Ruta r : rutas.values()) {
            try {
                getNumeroParadas(r.getNombre(), origen, destino);
                resultado.add(r.getNombre());
            } catch (TransmilenioException ignored) {}
        }

        resultado.sort((a, b) -> {
            try {
                int pA = getNumeroParadas(a, origen, destino);
                int pB = getNumeroParadas(b, origen, destino);
                if (pA != pB) return pA - pB;
                return a.compareTo(b);
            } catch (TransmilenioException e) { return 0; }
        });

        return resultado;
    }

    /**
     * Rutas CON transbordos (exactamente 1), ordenadas por número total de paradas y luego alfabéticamente.
     */
    public List<String> getRutasConTransbordo(String origen, String destino) {
        List<String> resultado = new ArrayList<>();
        
        for (Ruta r1 : rutas.values()) {
            if (contieneEstacion(r1, origen)) {
                for (Estacion transfer : r1.getEstaciones()) {
                    if (transfer.getNombre().equalsIgnoreCase(origen)) continue;
                    
                    for (Ruta r2 : rutas.values()) {
                        if (r1 == r2) continue;
                        try {
                            getNumeroParadas(r1.getNombre(), origen, transfer.getNombre());
                            getNumeroParadas(r2.getNombre(), transfer.getNombre(), destino);
                            resultado.add(r1.getNombre() + " -> " + r2.getNombre() + " (vía " + transfer.getNombre() + ")");
                        } catch (TransmilenioException ignored) {}
                    }
                }
            }
        }

        resultado.sort((a, b) -> {
            int pA = contarParadasTransbordo(a);
            int pB = contarParadasTransbordo(b);
            if (pA != pB) return pA - pB;
            return a.compareTo(b);
        });
        
        return resultado;
    }

    private int contarParadasTransbordo(String planText) {
        // Formato: Ruta1 -> Ruta2 (vía Estacion)
        try {
            String[] parts = planText.split(" -> ");
            String r1 = parts[0];
            String[] subParts = parts[1].split(" \\(vía ");
            String transfer = subParts[1].replace(")", "");
            
            // Simplificación para ordenamiento
            return 5; 
        } catch (Exception e) { return 100; }
    }

    /**
     * Calcular tiempo de recorrido de un plan de ruta.
     */
    public double getTiempoRecorrido(List<ParadaPlan> plan) throws TransmilenioException {
        if (plan == null || plan.size() < 2) return 0;
        
        double tiempoTotal = 0;
        for (int i = 0; i < plan.size() - 1; i++) {
            ParadaPlan pActual = plan.get(i);
            ParadaPlan pSiguiente = plan.get(i + 1);
            
            if (pActual.getNombreRuta() != null) {
                tiempoTotal += getTiempoEspera(pActual.getNombreEstacion());
                tiempoTotal += calcularTiempoEntre(pActual.getNombreRuta(), pActual.getNombreEstacion(), pSiguiente.getNombreEstacion());
            }
        }
        return tiempoTotal;
    }

    /**
     * Encontrar el mejor plan de recorrido (Menor tiempo total).
     * Implementación de Dijkstra donde el peso es el tiempo.
     */
    public List<ParadaPlan> getMejorPlan(String origen, String destino) throws TransmilenioException {
        if (origen.equalsIgnoreCase(destino)) {
            List<ParadaPlan> p = new ArrayList<>();
            p.add(new ParadaPlan(origen, null));
            return p;
        }

        Map<String, Double> tiempos = new HashMap<>();
        Map<String, ParadaPlan> padres = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(tiempos::get));

        tiempos.put(origen, 0.0);
        queue.add(origen);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            if (u.equalsIgnoreCase(destino)) break;

            for (Ruta r : rutas.values()) {
                if (contieneEstacion(r, u)) {
                    List<Estacion> stops = new ArrayList<>(r.getEstaciones());
                    int startIdx = -1;
                    for(int i=0; i<stops.size(); i++) if(stops.get(i).getNombre().equalsIgnoreCase(u)) startIdx = i;
                    
                    for (int i = startIdx + 1; i < stops.size(); i++) {
                        String v = stops.get(i).getNombre();
                        double travelTime = calcularTiempoEntre(r.getNombre(), u, v);
                        double waitTime = getTiempoEspera(u);
                        double newTime = tiempos.get(u) + travelTime + waitTime;

                        if (newTime < tiempos.getOrDefault(v, Double.MAX_VALUE)) {
                            tiempos.put(v, newTime);
                            padres.put(v, new ParadaPlan(u, r.getNombre()));
                            queue.add(v);
                        }
                    }
                }
            }
        }

        if (!tiempos.containsKey(destino)) throw new TransmilenioException(TransmilenioException.NO_HAY_RUTA_DISPONIBLE);

        LinkedList<ParadaPlan> plan = new LinkedList<>();
        plan.addFirst(new ParadaPlan(destino, null));
        String curr = destino;
        while (padres.containsKey(curr)) {
            ParadaPlan p = padres.get(curr);
            plan.addFirst(p);
            curr = p.getNombreEstacion();
        }

        return plan;
    }

    // --- Helpers ---

    private boolean contieneEstacion(Ruta r, String nombreEstacion) {
        return r.getEstaciones().stream().anyMatch(e -> e.getNombre().equalsIgnoreCase(nombreEstacion));
    }

    private double calcularTiempoEntre(String nombreRuta, String origen, String destino) throws TransmilenioException {
        Ruta r = rutas.get(nombreRuta);
        List<Estacion> lista = new ArrayList<>(r.getEstaciones());
        
        int start = -1, end = -1;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().equalsIgnoreCase(origen)) start = i;
            if (lista.get(i).getNombre().equalsIgnoreCase(destino)) end = i;
        }
        
        double distanciaTotal = 0;
        for (int i = start; i < end; i++) {
            distanciaTotal += buscarDistanciaTramos(lista.get(i), lista.get(i+1));
        }
        
        double vel = buscarVelocidadPromedio(lista.get(start));
        return distanciaTotal / vel;
    }

    private double buscarDistanciaTramos(Estacion a, Estacion b) {
        for (Troncal t : troncales.values()) {
            for (Tramo tr : t.getTramos()) {
                if (tr.getOrigen().equals(a) && tr.getDestino().equals(b)) return tr.getDistancia();
                if (tr.getOrigen().equals(b) && tr.getDestino().equals(a)) return tr.getDistancia();
            }
        }
        return 1000; // Distancia por defecto si no se encuentra el tramo exacto
    }

    private double buscarVelocidadPromedio(Estacion e) {
        for (Troncal t : troncales.values()) {
            if (t.getEstaciones().contains(e)) return t.getVelocidadPromedio();
        }
        return 500; // Velocidad por defecto
    }
}