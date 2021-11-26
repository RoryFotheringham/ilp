package uk.ac.ed.inf;
import org.junit.Test;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PathManagementTest {
    TestObjects to = new TestObjects();

    @Test
    public void absolutePathStartAppletonTest(){
        PathManagement pm = to.pathManagement;
        Path absolutePath = pm.absolutePath;
        Node actual = absolutePath.pathList.get(0);
        Node expected = to.APPLETON_NODE;
        assertEquals(expected, actual);
    }
    @Test
    public void absolutePathFinishAppletonTest(){
        PathManagement pm = to.pathManagement;
        Path absolutePath = pm.absolutePath;
        Node actual = absolutePath.pathList.get(absolutePath.pathList.size() -1);
        Node expected = to.APPLETON_NODE;
        assertEquals(expected, actual);
    }

    @Test
    public void absolutePathReachesAllStopsInOrder(){
        PathManagement pm = to.pathManagement;
        Path absolutePath = pm.absolutePath;
        ArrayList<Node> pathList = absolutePath.pathList;
        ArrayList<Node> stopsArr = absolutePath.stops;
        LinkedList<Node> stopsLL = new LinkedList<>();
        stopsLL.addAll(stopsArr);
        boolean flag = true;

        for(Node node: pathList){
            if(pathList.isEmpty()){
                flag = false;
            }
            if(node.equals(stopsLL.getFirst())){
                stopsLL.removeFirst();
            }
        }
        if(!stopsLL.isEmpty()){
            flag = false;
        }
        assertTrue(flag);
    }

}
