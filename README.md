# Djaktra

Implementación educativa del algoritmo de Dijkstra en Java 17, construida como
proyecto Maven compatible con Apache NetBeans.

## Características

- Grafo ponderado mediante listas de adyacencia.
- Grafos dirigidos y no dirigidos.
- Cola de prioridad con `PriorityQueue`.
- Cálculo de la distancia mínima.
- Reconstrucción de la ruta más corta.
- Prueba automática con JUnit 5.

## Ejecutar en NetBeans

1. Abrir el proyecto Maven desde **File > Open Project**.
2. Ejecutar `DjaktraApplication.java`.

## Ejecutar con Maven

```bash
mvn test
mvn exec:java
```

El ejemplo incluido calcula la ruta más corta desde `A` hasta `E`.
