# Djaktra

Implementación educativa de Dijkstra, búsqueda en profundidad (DFS) y búsqueda
por anchura (BFS) en Java 17, construida como proyecto Maven compatible con
Apache NetBeans.

## Objetivo del proyecto

El proyecto muestra tres maneras de recorrer o buscar rutas en un grafo:

- **Dijkstra:** encuentra la ruta de menor costo teniendo en cuenta los pesos.
- **DFS:** recorre una rama hasta el final antes de regresar y probar otra.
- **BFS:** recorre el grafo por niveles y encuentra una ruta con el menor número
  de aristas cuando todas tienen la misma importancia.

Los algoritmos comparten el mismo grafo para facilitar su comparación.

## Estructura del código

```text
src/
├── main/java/com/djaktra/
│   ├── app/
│   │   └── DjaktraApplication.java
│   └── graph/
│       ├── WeightedGraph.java
│       ├── Dijkstra.java
│       ├── DepthFirstSearch.java
│       └── BreadthFirstSearch.java
└── test/java/com/djaktra/graph/
    ├── DijkstraTest.java
    ├── DepthFirstSearchTest.java
    └── BreadthFirstSearchTest.java
```

### `WeightedGraph.java`

Representa el grafo mediante una **lista de adyacencia**. Un `Map` relaciona
el nombre de cada vértice con las aristas que salen de él.

```java
private final Map<String, List<Edge>> adjacency = new HashMap<>();
```

Cada arista guarda el destino y su peso:

```java
public record Edge(String destination, int weight) { }
```

Métodos principales:

- `addVertex`: agrega un vértice si todavía no existe.
- `addEdge`: crea una arista dirigida y rechaza pesos negativos.
- `addUndirectedEdge`: crea la conexión en las dos direcciones.
- `neighborsOf`: devuelve los vecinos de un vértice.
- `contains`: comprueba si un vértice pertenece al grafo.

### `Dijkstra.java`

Calcula la distancia mínima desde un origen hasta los demás vértices. Utiliza:

- Un `Map<String, Integer>` para guardar la mejor distancia conocida.
- Un `Map<String, String>` para recordar el vértice anterior y reconstruir la
  ruta.
- Una `PriorityQueue` para procesar primero el vértice con menor distancia.

La parte central es la **relajación de aristas**. Si pasar por el vértice actual
produce una distancia menor, se actualizan la distancia y el predecesor:

```java
int candidateDistance = current.distance() + edge.weight();

if (candidateDistance < destinationDistance) {
    distances.put(edge.destination(), candidateDistance);
    previous.put(edge.destination(), current.vertex());
}
```

El resultado permite consultar:

- `distanceTo(destination)`: costo mínimo total.
- `pathTo(destination)`: vértices que forman la ruta más corta.
- `isReachable(destination)`: indica si existe una ruta.

> Dijkstra no admite aristas con pesos negativos.

### `DepthFirstSearch.java` — búsqueda por profundidad

DFS usa recursividad. Marca el vértice actual como visitado y continúa con el
primer vecino no visitado. Solo regresa cuando ya no puede avanzar.

```java
visited.add(vertex);
traversal.add(vertex);

for (WeightedGraph.Edge edge : graph.neighborsOf(vertex)) {
    if (!visited.contains(edge.destination())) {
        visit(graph, edge.destination(), visited, traversal);
    }
}
```

Métodos disponibles:

- `traverse`: devuelve el orden del recorrido en profundidad.
- `findPath`: busca una ruta utilizando DFS y retroceso (*backtracking*).

DFS encuentra una ruta si existe, pero esa ruta no tiene que ser la más corta.

### `BreadthFirstSearch.java` — búsqueda por anchura

BFS utiliza una cola `ArrayDeque`. Primero visita el origen, después todos sus
vecinos, luego los vecinos de esos vecinos y así sucesivamente.

```java
Queue<String> pending = new ArrayDeque<>();
visited.add(source);
pending.add(source);

while (!pending.isEmpty()) {
    String current = pending.remove();
    // Se agregan a la cola los vecinos que aún no fueron visitados.
}
```

Métodos disponibles:

- `traverse`: devuelve el recorrido por niveles.
- `findPath`: reconstruye una ruta con el menor número de aristas.

BFS minimiza la cantidad de aristas, pero no considera sus pesos. Para obtener
el menor costo en un grafo ponderado debe utilizarse Dijkstra.

### `DjaktraApplication.java`

Es el punto de entrada. Construye un grafo de ejemplo, ejecuta los tres
algoritmos e imprime los resultados. Las conexiones se crean así:

```java
graph.addUndirectedEdge("A", "B", 4);
graph.addUndirectedEdge("A", "C", 2);
```

Después se puede ejecutar cada algoritmo:

```java
Dijkstra.Result result = Dijkstra.shortestPaths(graph, "A");
List<String> dfs = DepthFirstSearch.traverse(graph, "A");
List<String> bfs = BreadthFirstSearch.traverse(graph, "A");
```

## Comparación de algoritmos

| Algoritmo | Estructura principal | Resultado | Complejidad |
|---|---|---|---|
| Dijkstra | Cola de prioridad | Ruta de menor costo | `O((V + E) log V)` |
| DFS | Recursividad y conjunto de visitados | Recorrido profundo o una ruta | `O(V + E)` |
| BFS | Cola y conjunto de visitados | Recorrido por niveles o ruta con menos aristas | `O(V + E)` |

`V` representa el número de vértices y `E` el número de aristas.

## Pruebas automáticas

Las clases de prueba utilizan JUnit 5:

- `DijkstraTest` comprueba la distancia y la ruta de menor costo.
- `DepthFirstSearchTest` comprueba el recorrido profundo y la ruta encontrada.
- `BreadthFirstSearchTest` comprueba el recorrido por niveles y la ruta con
  menos aristas.

## Ejecutar en NetBeans

1. Abrir el proyecto Maven desde **File > Open Project**.
2. Ejecutar `DjaktraApplication.java`.

## Ejecutar con Maven

```bash
mvn test
mvn exec:java
```

El ejemplo incluido calcula la ruta más corta con Dijkstra y realiza recorridos
por profundidad y por anchura desde `A`.

## Salida esperada

```text
Origen: A
Destino: E
Distancia mínima: 10
Ruta más corta: A -> C -> B -> D -> E

Recorrido DFS desde A: A -> B -> C -> D -> E
Ruta encontrada por DFS de A hasta E: A -> B -> C -> D -> E

Recorrido BFS desde A: A -> B -> C -> D -> E
Ruta con menos aristas por BFS de A hasta E: A -> C -> E
```
