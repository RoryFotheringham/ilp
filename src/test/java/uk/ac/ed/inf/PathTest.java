package uk.ac.ed.inf;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class PathTest {
    TestObjects to = new TestObjects();
    ArrayList<Node> testNodes = to.testNodes;
    Graph testGraph = new Graph(testNodes);
    Path path_1 = to.path_1;
    Path path_2 = to.path_2;
    Path path_3 = to.path_3;
    Path path_4 = to.path_4;


    @Test
    public void concatPathTest(){
        ArrayList<Node> expectedList = new ArrayList<>();
        expectedList.add(testNodes.get(1));
        expectedList.add(testNodes.get(0));
        expectedList.add(testNodes.get(4));
        expectedList.add(testNodes.get(5));
        expectedList.add(testNodes.get(2));
        expectedList.add(testNodes.get(3));
        Path expectedPath = new Path(expectedList, 17);
        Path actualPath = path_1.concatPaths(testGraph, path_2);

        assertEquals(expectedPath.pathList, actualPath.pathList);
    }
    @Test
    public void concatPathsDistanceTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_2);
        assertEquals(17, actualPath.totalDistance, .01);
    }
    @Test
    public void concatPathsAdjacentTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_3);
        ArrayList<Node> expectedList = new ArrayList<>();
        expectedList.add(testNodes.get(1));
        expectedList.add(testNodes.get(0));
        expectedList.add(testNodes.get(4));
        expectedList.add(testNodes.get(5));
        expectedList.add(testNodes.get(2));
        assertEquals(expectedList, actualPath.pathList);
    }

    @Test
    public void concatPathsAdjacentDistanceTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_3);
        assertEquals(15, actualPath.totalDistance, .01);
    }

    @Test
    public void concatPathsOverlappingTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_4);
        ArrayList<Node> expectedList = new ArrayList<>();
        expectedList.add(testNodes.get(1));
        expectedList.add(testNodes.get(0));
        expectedList.add(testNodes.get(4));
        expectedList.add(testNodes.get(5));
        assertEquals(expectedList, actualPath.pathList);
    }

    @Test
    public void concatPathsPreserveStopsTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_2);
        ArrayList<Node> expected = new ArrayList<>();
        expected.addAll(path_1.stops);
        expected.addAll(path_2.stops);
        assertEquals(expected, actualPath.stops);
    }

    @Test
    public void concatPathsPreserveStopsOverlappingTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_4);
        ArrayList<Node> expected = new ArrayList<>();
        expected.add(testNodes.get(1));
        expected.add(testNodes.get(4));
        expected.add(testNodes.get(5));
        assertEquals(expected, actualPath.stops);
    }

    @Test
    public void concatPathsOverlappingDistanceTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_4);
        assertEquals(12, actualPath.totalDistance, .01);
    }
}
