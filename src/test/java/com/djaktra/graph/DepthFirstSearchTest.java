package com.djaktra.graph;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DepthFirstSearchTest {

    @Test
    void traversesGraphAndFindsPath() {
        WeightedGraph graph = new WeightedGraph();
        graph.addUndirectedEdge("A", "B", 1);
        graph.addUndirectedEdge("A", "C", 1);
        graph.addUndirectedEdge("B", "D", 1);
        graph.addUndirectedEdge("C", "E", 1);

        assertEquals(List.of("A", "B", "D", "C", "E"),
                DepthFirstSearch.traverse(graph, "A"));
        assertEquals(List.of("A", "C", "E"),
                DepthFirstSearch.findPath(graph, "A", "E"));
    }
}
