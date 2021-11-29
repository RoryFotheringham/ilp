package uk.ac.ed.inf;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FlightPathTest {
    TestObjects to = new TestObjects();
    /*
    @Test
    public void findBestAngleTest(){
        FlightPath fp = new FlightPath(to.testGraph, to.path_1);
        LongLat currentPos = new LongLat(2, 0);
        LongLat destinationPos = new LongLat(6, 0);
        ArrayList<Integer> subFlightPath = new ArrayList<>();
        LongLat expected = new LongLat(2.00015, 0);
        LongLat actual = fp.findBestAngle(currentPos, destinationPos, subFlightPath);
        assertEquals(expected, actual);
    }
    @Test
    public void findBestAngleTestPath() {
        FlightPath fp = new FlightPath(to.testGraph, to.path_1);
        LongLat currentPos = new LongLat(2, 0);
        LongLat destinationPos = new LongLat(6, 0);
        ArrayList<Integer> subFlightPath = new ArrayList<>();
        fp.findBestAngle(currentPos, destinationPos, subFlightPath);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, subFlightPath);
    }
    @Test
    public void findBestAngleTestAtStop() {
        FlightPath fp = new FlightPath(to.testGraph, to.path_1);
        LongLat currentPos = new LongLat(6 - 0.00015, 1);
        LongLat destinationPos = new LongLat(6, 1);
        ArrayList<Integer> subFlightPath = new ArrayList<>();
        fp.findBestAngle(currentPos, destinationPos, subFlightPath);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(-999);
        assertEquals(expected, subFlightPath);
    }

    @Test
    public void generateSubFlightPathTest(){
        Path path = to.path_small;
        FlightPath fp = new FlightPath(to.testGraph, path);
        ArrayList<Integer> actual = fp.generateSubFlightPath(path);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(180);
        expected.add(180);
        expected.add(180);
        expected.add(180);
        expected.add(180);
        expected.add(180);
        expected.add(-999);
        assertEquals(expected, actual);
    }
*/
}
