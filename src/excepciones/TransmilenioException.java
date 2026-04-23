package excepciones;

/**
 * Clase base centralizada para todas las excepciones del sistema Transmilenio.
 * Contiene mensajes de error predefinidos como constantes estáticas.
 * 
 * @author Juan Gaitan, Oscar Lasso, David Contreras, Cristian Moreno
 * @version 1.0
 * @since 22 - 04 - 2026
 */
public class TransmilenioException extends Exception {

    private static final long serialVersionUID = 1L;

    // Mensajes de error como constantes estáticas
    public static final String ESTACION_NO_ENCONTRADA = "La estación especificada no se encuentra en el sistema.";
    public static final String RUTA_NO_ENCONTRADA = "La ruta especificada no existe en el sistema.";
    public static final String RUTA_INVALIDA = "La ruta no es válida para el trayecto solicitado.";
    public static final String ERROR_PERSISTENCIA_ARCHIVO = "Ocurrió un error al procesar el archivo de persistencia.";
    public static final String ARCHIVO_VACIO = "El archivo de importación está vacío o tiene un formato incorrecto.";
    public static final String NO_HAY_RUTA_DISPONIBLE = "No se encontró un plan de recorrido viable entre las estaciones dadas.";

    /**
     * Constructor que recibe el mensaje de error.
     * @param message El mensaje descriptivo del error (usar constantes de esta clase).
     */
    public TransmilenioException(String message) {
        super(message);
    }
}
