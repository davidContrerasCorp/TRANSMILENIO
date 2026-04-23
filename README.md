# 🚌 Proyecto TRANSMILENIO

## 🙋‍♂️ Introducción
Este proyecto es un sistema de gestión y simulación computacional para la red de transporte **Transmilenio**, desarrollado para modelar colecciones y persistencia en Java. El sistema permite consultar tiempos de espera por estación, buscar rutas directas o con transbordos, planificar el recorrido más rápido entre dos puntos y manejar la importación/exportación de la configuración de la red.

**Desarrolladores:** * Juan Diego Gaitán
* Oscar Lasso
* David Contreras
* Cristian Moreno

## 🏗️ Diseño
El sistema emplea un diseño estructural fundamentado en la Programación Orientada a Objetos, utilizando el patrón **Fachada** a través de la clase central `SistemaTransmilenio`. 

* **Lógica:** El dominio divide el problema en entidades claras: `Estacion` (que gestiona sus niveles de ocupación para calcular tiempos de espera), `Ruta` (secuencia de paradas), y `Troncal` (secuencia física de `Tramo`s). 
* Para las consultas de enrutamiento, la lógica recorre las conexiones entre estaciones implementando el algoritmo de **Dijkstra**, calculando el peso de cada ruta mediante la suma del tiempo de espera y el tiempo de viaje (distancia del tramo sobre la velocidad promedio de la troncal). 
* Se maneja la lectura y escritura de estado mediante un `GestorPersistencia` independiente.

## 🧠 Justificación de Colecciones
La selección de los contenedores se realizó considerando que las operaciones de consulta en el sistema son de alta frecuencia y requieren optimización estructural:

* **`HashMap` (SistemaTransmilenio):** Se emplearon mapas para el almacenamiento general de `rutas`, `troncales` y `estaciones`. Al usar el nombre como llave (String), se garantiza una complejidad de búsqueda de **O(1)**. Esto es crítico para el rendimiento cuando se consulta iterativamente información de una estación o ruta, y a su vez protege la integridad referencial al no permitir nombres duplicados.
* **`LinkedHashSet` (Ruta y Troncal):** Para almacenar las estaciones que componen una ruta o una troncal, se eligió este conjunto sobre una lista tradicional. El `Set` evita automáticamente que se registre por error la misma estación dos veces, mientras que su cualidad `Linked` **mantiene el orden secuencial estricto de inserción**, lo cual refleja de manera exacta el recorrido lineal físico de los buses.
* **`ArrayList` (Troncal):** Los segmentos físicos (`Tramo`) se guardan en listas porque posibilitan un acceso posicional rápido por índice.
* **`TreeSet` (SistemaTransmilenio):** Utilizado de forma auxiliar al devolver los nombres de las rutas, ya que el requerimiento exige listados ordenados alfabéticamente de forma natural.
* **`PriorityQueue` (SistemaTransmilenio):** Esencial en el diseño algorítmico del recorrido más rápido (Dijkstra), permite extraer siempre la estación con el menor tiempo acumulado de viaje en tiempo logarítmico, optimizando la búsqueda del plan ideal.
