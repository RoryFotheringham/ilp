package uk.ac.ed.inf;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PathFindTest {
    ArrayList<Node> testNodes = generateTestNodes();
    Graph testGraph = new Graph(testNodes);

    public static ArrayList<Node> generateTestNodes(){
        ArrayList<Node> testNodes = new ArrayList<>();
        Node node_0 = new Node(new LongLat(1,1));
        Node node_1 = new Node(new LongLat(6,1));
        Node node_2 = new Node(new LongLat(3,3));
        Node node_3 = new Node(new LongLat(5,3));
        Node node_4 = new Node(new LongLat(1,6));
        Node node_5 = new Node(new LongLat(3,6));
        Node node_6 = new Node(new LongLat(1,8));
        Node node_7 = new Node(new LongLat(3,9));
        Node node_8 = new Node(new LongLat(6,9));

        node_0.addEdge(new Edge(5, node_1));
        node_0.addEdge(new Edge(5, node_4));
        node_1.addEdge(new Edge(5, node_0));
        node_1.addEdge(new Edge(8, node_8));
        node_2.addEdge(new Edge(2, node_3));
        node_2.addEdge(new Edge(3, node_5));
        node_3.addEdge(new Edge(2, node_2));
        node_4.addEdge(new Edge(5, node_0));
        node_4.addEdge(new Edge(2, node_5));
        node_4.addEdge(new Edge(2, node_6));
        node_5.addEdge(new Edge(3, node_2));
        node_5.addEdge(new Edge(2, node_4));
        node_5.addEdge(new Edge(3, node_7));
        node_6.addEdge(new Edge(2, node_4));
        node_7.addEdge(new Edge(3, node_5));
        node_7.addEdge(new Edge(3, node_8));
        node_8.addEdge(new Edge(3, node_7));
        node_8.addEdge(new Edge(8, node_1));

        testNodes.add(node_0);
        testNodes.add(node_1);
        testNodes.add(node_2);
        testNodes.add(node_3);
        testNodes.add(node_4);
        testNodes.add(node_5);
        testNodes.add(node_6);
        testNodes.add(node_7);
        testNodes.add(node_8);

        return testNodes;
    }


    @Test
    public void findPathPathListTest() {
        Path path = PathFind.findPath(testGraph, testNodes.get(0), testNodes.get(7), null);
        ArrayList<Node> expected = new ArrayList<>();
        expected.add(testNodes.get(0));
        expected.add(testNodes.get(4));
        expected.add(testNodes.get(5));
        expected.add(testNodes.get(7));
        assertEquals(expected, path.pathList);
    }
    @Test
    public void findPathDistanceTest(){
        Path path = PathFind.findPath(testGraph, testNodes.get(1), testNodes.get(5), null);
        assertEquals(12, path.totalDistance, .01);
    }
    @Test
    public void findPathPreserveStopsTest(){
        ArrayList<Node> stops = new ArrayList<>();
        stops.add(testNodes.get(3));
        stops.add(testNodes.get(2));
        stops.add(testNodes.get(7));
        Path path = PathFind.findPath(testGraph, testNodes.get(3), testNodes.get(7), stops);

        assertEquals(stops, path.stops);
    }
    @Test
    public void findPathSingletonPathTest(){
        Path path = PathFind.findPath(testGraph, testNodes.get(3), testNodes.get(3), null);
        ArrayList<Node> expected = new ArrayList<Node>();
        expected.add(testNodes.get(3));
        assertEquals(expected, path.pathList);
    }
    @Test
    public void findPathSingletonDistanceTest(){
        Path path = PathFind.findPath(testGraph, testNodes.get(3), testNodes.get(3), null);
        assertEquals(0, path.totalDistance, .01);
    }
    @Test
    public void findPathTuplePathTest(){
        Path path = PathFind.findPath(testGraph, testNodes.get(2), testNodes.get(3), null);
        ArrayList<Node> expected = new ArrayList<Node>();
        expected.add(testNodes.get(2));
        expected.add(testNodes.get(3));
        assertEquals(expected, path.pathList);
    }
}

