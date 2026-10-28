package com.djaktra.graph;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BreadthFirstSearchTest {

    @Test
    void traversesByLevelsAndFindsPathWithFewestEdges() {
        WeightedGraph graph = new WeightedGraph();
        graph.addUndirectedEdge("A", "B", 1);
        graph.addUndirectedEdge("A", "C", 1);
        graph.addUndirectedEdge("B", "D", 1);
        graph.addUndirectedEdge("C", "E", 1);
        graph.addUndirectedEdge("D", "E", 1);

        assertEquals(List.of("A", "B", "C", "D", "E"),
                BreadthFirstSearch.traverse(graph, "A"));
        assertEquals(List.of("A", "C", "E"),
                BreadthFirstSearch.findPath(graph, "A", "E"));
    }
}
