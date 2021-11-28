package uk.ac.ed.inf;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

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

        assertEquals(expectedPath.getPathList(), actualPath.getPathList());
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
        assertEquals(expectedList, actualPath.getPathList());
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
        assertEquals(expectedList, actualPath.getPathList());
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
    public void concatPathsDestinations(){
        LinkedList<Node> destination = new LinkedList<Node>();
        destination.add(testNodes.get(3));
        path_2.setDestinations(destination);
        Path actualPath = path_3.concatPaths(testGraph, path_2);

        LinkedList<Node> expected = destination;
        assertEquals(expected, actualPath.destinations);
    }

    @Test
    public void concatPathsTwoDestinations(){
        LinkedList<Node> destination1 = new LinkedList<Node>();
        destination1.add(testNodes.get(4));
        LinkedList<Node> destination2 = new LinkedList<Node>();
        destination2.add(testNodes.get(3));
        LinkedList<Node> expected = new LinkedList<Node>();
        expected.add(testNodes.get(4));
        expected.add(testNodes.get(3));

        path_1.setDestinations(destination1);
        path_2.setDestinations(destination2);
        Path actualPath = path_1.concatPaths(testGraph, path_2);
        assertEquals(expected, actualPath.destinations);
    }

    @Test
    public void concatPathsOverlappingDistanceTest(){
        Path actualPath = path_1.concatPaths(testGraph, path_4);
        assertEquals(12, actualPath.totalDistance, .01);
    }

    @Test
    public void popSubPathPathListTest(){
        LinkedList<Node> destination = new LinkedList<Node>();
        destination.add(testNodes.get(4));
        path_1.setDestinations(destination);
        Path fullPath = path_1.concatPaths(testGraph, path_3);

        Path actual = fullPath.popSubPath();
        assertEquals(path_1.getPathList(), actual.getPathList());
    }
    @Test
    public void popSubPathDestinationTest(){
        LinkedList<Node> destination = new LinkedList<Node>();
        destination.add(testNodes.get(4));
        path_1.setDestinations(destination);
        Path fullPath = path_1.concatPaths(testGraph, path_3);

        Path actual = fullPath.popSubPath();
        assertEquals(path_1.getDestinations(), actual.getDestinations());
    }

    @Test
    public void popSubPathStopsTest(){
        LinkedList<Node> destination = new LinkedList<Node>();
        destination.add(testNodes.get(4));
        path_1.setDestinations(destination);
        Path fullPath = path_1.concatPaths(testGraph, path_3);

        Path actual = fullPath.popSubPath();
        assertEquals(path_1.stops, actual.stops);
    }

    @Test
    public void popSubPathRemainingPath(){
        LinkedList<Node> destination = new LinkedList<Node>();
        destination.add(testNodes.get(4));
        path_1.setDestinations(destination);
        Path fullPath = path_1.concatPaths(testGraph, path_3);
        fullPath.popSubPath();
        Path actual = fullPath;
        ArrayList<Node> expected = path_3.getPathList();
        expected.add(0, path_1.getDestinations().get(0));

        assertEquals(expected, actual.getPathList());

    }

}


