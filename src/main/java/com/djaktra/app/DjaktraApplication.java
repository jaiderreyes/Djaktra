package com.djaktra.app;

import com.djaktra.graph.BreadthFirstSearch;
import com.djaktra.graph.Dijkstra;
import com.djaktra.graph.DepthFirstSearch;
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

        System.out.println();
        System.out.println("Recorrido DFS desde A: "
                + String.join(" -> ", DepthFirstSearch.traverse(graph, "A")));
        System.out.println("Ruta encontrada por DFS de A hasta E: "
                + String.join(" -> ",
                        DepthFirstSearch.findPath(graph, "A", "E")));

        System.out.println();
        System.out.println("Recorrido BFS desde A: "
                + String.join(" -> ", BreadthFirstSearch.traverse(graph, "A")));
        System.out.println("Ruta con menos aristas por BFS de A hasta E: "
                + String.join(" -> ",
                        BreadthFirstSearch.findPath(graph, "A", "E")));
    }
}
