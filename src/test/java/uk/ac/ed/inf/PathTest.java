package uk.ac.ed.inf;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class PathTest {
    ArrayList<Node> testNodes = PathFindTest.generateTestNodes();
    Graph testGraph = new Graph(testNodes);
    Path path_1 = generateTestPath_1();
    Path path_2 = generateTestPath_2();
    Path path_3 = generateTestPath_3();
    Path path_4 = generateTestPath_4();

    public Path generateTestPath_4(){
        ArrayList<Node> pathNodes_4 = new ArrayList<>();
        ArrayList<Node> stops_4 = new ArrayList<>();
        pathNodes_4.add(testNodes.get(4));
        pathNodes_4.add(testNodes.get(5));
        Path path_4 = new Path(pathNodes_4, 2);

        stops_4.add(testNodes.get(4));
        stops_4.add(testNodes.get(5));
        path_4.addStops(stops_4);

        return path_4;
    }

    public Path generateTestPath_3(){
        ArrayList<Node> pathNodes_3 = new ArrayList<>();
        ArrayList<Node> stops_3 = new ArrayList<>();
        pathNodes_3.add(testNodes.get(5));
        pathNodes_3.add(testNodes.get(2));
        Path path_3 = new Path(pathNodes_3, 3);

        stops_3.add(testNodes.get(5));
        stops_3.add(testNodes.get(2));
        path_3.addStops(stops_3);

        return path_3;
    }

    public Path generateTestPath_1(){
        ArrayList<Node> pathNodes_1 = new ArrayList<>();
        ArrayList<Node> stops_1 = new ArrayList<>();
        pathNodes_1.add(testNodes.get(1));
        pathNodes_1.add(testNodes.get(0));
        pathNodes_1.add(testNodes.get(4));
        Path path_1 = new Path(pathNodes_1, 10);

        stops_1.add(testNodes.get(1));
        stops_1.add(testNodes.get(4));
        path_1.addStops(stops_1);

        return path_1;
    }

    public Path generateTestPath_2(){
        ArrayList<Node> pathNodes_2 = new ArrayList<>();
        ArrayList<Node> stops_2 = new ArrayList<>();
        pathNodes_2.add(testNodes.get(2));
        pathNodes_2.add(testNodes.get(3));
        Path path_2 = new Path(pathNodes_2, 2);

        stops_2.add(testNodes.get(2));
        stops_2.add(testNodes.get(3));
        path_2.addStops(stops_2);

        return path_2;
    }

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
