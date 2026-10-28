package com.djaktra.graph;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraTest {

    @Test
    void findsShortestPath() {
        WeightedGraph graph = new WeightedGraph();
        graph.addUndirectedEdge("A", "B", 4);
        graph.addUndirectedEdge("A", "C", 2);
        graph.addUndirectedEdge("B", "C", 1);
        graph.addUndirectedEdge("B", "D", 5);
        graph.addUndirectedEdge("C", "D", 8);
        graph.addUndirectedEdge("D", "E", 2);

        Dijkstra.Result result = Dijkstra.shortestPaths(graph, "A");

        assertEquals(10, result.distanceTo("E"));
        assertEquals(List.of("A", "C", "B", "D", "E"), result.pathTo("E"));
    }
}
