package com.djaktra.app;

import com.djaktra.graph.Dijkstra;
import com.djaktra.graph.WeightedGraph;

/** Punto de entrada inicial de Djaktra. */
public final class DjaktraApplication {

    private DjaktraApplication() {
        // Clase de utilidad; no se instancia.
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph();
        graph.addUndirectedEdge("A", "B", 4);
        graph.addUndirectedEdge("A", "C", 2);
        graph.addUndirectedEdge("B", "C", 1);
        graph.addUndirectedEdge("B", "D", 5);
        graph.addUndirectedEdge("C", "D", 8);
        graph.addUndirectedEdge("C", "E", 10);
        graph.addUndirectedEdge("D", "E", 2);

        Dijkstra.Result result = Dijkstra.shortestPaths(graph, "A");
        String destination = "E";

        System.out.println("Origen: A");
        System.out.println("Destino: " + destination);
        System.out.println("Distancia mínima: " + result.distanceTo(destination));
        System.out.println("Ruta más corta: "
                + String.join(" -> ", result.pathTo(destination)));
    }
}
