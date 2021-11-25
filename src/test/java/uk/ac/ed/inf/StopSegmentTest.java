package uk.ac.ed.inf;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class StopSegmentTest {
    ArrayList<Node> testNodes = PathFindTest.generateTestNodes();
    Graph testGraph = new Graph(testNodes);
    StopSegment testSegment_1 = generateTestStopSegment_1();
    ArrayList<Node> testList_1 = generateTestPathList_1();
    ArrayList<Node> testList_2 = generateTestPathList_2();


    public ArrayList<Node> generateTestPathList_2(){
        ArrayList<Node> testList_2 = new ArrayList<>();
        testList_2.add(testNodes.get(4));
        testList_2.add(testNodes.get(0));
        testList_2.add(testNodes.get(4));
        testList_2.add(testNodes.get(5));
        testList_2.add(testNodes.get(7));
        return testList_2;
    }

    public ArrayList<Node> generateTestPathList_1(){
        ArrayList<Node> testList_1 = new ArrayList<>();
        testList_1.add(testNodes.get(0));
        testList_1.add(testNodes.get(4));
        testList_1.add(testNodes.get(5));
        testList_1.add(testNodes.get(7));
        return testList_1;
    }


    public StopSegment generateTestStopSegment_1(){
        StopSegment stopSegment= new StopSegment();
        stopSegment.addStore(testNodes.get(0));
        stopSegment.addStore(testNodes.get(5));
        stopSegment.setDestination(testNodes.get(7));
        return stopSegment;
    }

    @Test
    public void bestPathTest(){
        Path actual = generateTestStopSegment_1().bestPath(testGraph);
        assertEquals(testList_1, actual.pathList);
    }
}
