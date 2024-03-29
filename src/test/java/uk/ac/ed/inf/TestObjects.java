package uk.ac.ed.inf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TestObjects {
    /*
    static final LongLat APPLETON_LONGLAT = new LongLat(-3.186874, 55.944494);
    static final Node APPLETON_NODE = new Node(APPLETON_LONGLAT);
    Graph testGraph;
    Path path_1;
    Path path_2;
    Path path_3;
    Path path_4;
    Path path_small;
    ArrayList<Path> listOfPathsTest142;
    ArrayList<Path> listOfPathsTest124;
    ArrayList<Node> testNodes;
    PathManagement testPathManagement;
    PathManagement pathManagement;
    Path smallPath_1;
    Path smallPath_2;
    Path smallPath_3;
    Path smallPath_4;
    ArrayList<Node> smallNodes;



    public TestObjects() {
        String portDB = "1527";
        String portWeb = "9898";
        String machineName = "localhost";
        Date date = Date.valueOf("2023-01-31"); //placeholder params

        Menus menus = new Menus(machineName, portWeb);
        HashMap<String, Item> itemMap = menus.getItemMap();
        Orders orders = new Orders(machineName, portWeb, portDB, date, itemMap);
        Area area = new Area(machineName, portWeb);
        Graph graph = new Graph(area, orders);

        this.pathManagement = new PathManagement(graph, orders);
        this.testNodes = generateTestNodes();
        this.testGraph = new Graph(testNodes);
        //this.testGraph.graphMap.put(APPLETON_LONGLAT, APPLETON_NODE);
        this.path_1 = generateTestPath_1(testNodes);
        this.path_2 = generateTestPath_2(testNodes);
        this.path_3 = generateTestPath_3(testNodes);
        this.path_4 = generateTestPath_4(testNodes);
        this.path_small = generateTestPath_small();
        this.listOfPathsTest142 = generateListOfPathsTest142();
        this.listOfPathsTest124 = generateListOfPathsTest124();
        this.testPathManagement = generateTestPathManagement(graph, orders);
        this.smallNodes = generateTestNodes_small();
        this.smallPath_1 = generateTestPath_1(smallNodes);
        this.smallPath_2 = generateTestPath_2(smallNodes);
        this.smallPath_3 = generateTestPath_3(smallNodes);
        this.smallPath_4 = generateTestPath_4(smallNodes);
    }

    public PathManagement generateTestPathManagement(Graph graph, Orders orders){
        PathManagement pathManagement = new PathManagement(graph, orders);
        pathManagement.paths = listOfPathsTest124;
        return pathManagement;
    }


    private ArrayList<Path> generateListOfPathsTest124(){
        ArrayList<Path> listOfPathsTest = new ArrayList<Path>();
        listOfPathsTest.add(path_1);
        listOfPathsTest.add(path_2);
        listOfPathsTest.add(path_4);
        return listOfPathsTest;
    }

    private ArrayList<Path> generateListOfPathsTest142(){
        ArrayList<Path> listOfPathsTest = new ArrayList<Path>();
        listOfPathsTest.add(path_1);
        listOfPathsTest.add(path_4);
        listOfPathsTest.add(path_2);
        return listOfPathsTest;
    }

    private ArrayList<Node> generateTestNodes(){

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
        Node appletonNode = APPLETON_NODE;



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
        appletonNode.addEdge(new Edge(1, node_1));


        testNodes.add(node_0);
        testNodes.add(node_1);
        testNodes.add(node_2);
        testNodes.add(node_3);
        testNodes.add(node_4);
        testNodes.add(node_5);
        testNodes.add(node_6);
        testNodes.add(node_7);
        testNodes.add(node_8);
        testNodes.add(appletonNode);



        return testNodes;
    }

    private ArrayList<Node> generateTestNodes_small(){
        double scaleFactor = 0.001;
        ArrayList<Node> testNodes = new ArrayList<>();
        Node node_0 = new Node(new LongLat(1 * scaleFactor,1 * scaleFactor));
        Node node_1 = new Node(new LongLat(6 * scaleFactor,1 * scaleFactor));
        Node node_2 = new Node(new LongLat(3 * scaleFactor,3 * scaleFactor));
        Node node_3 = new Node(new LongLat(5 * scaleFactor,3 * scaleFactor));
        Node node_4 = new Node(new LongLat(1 * scaleFactor,6 * scaleFactor));
        Node node_5 = new Node(new LongLat(3 * scaleFactor,6 * scaleFactor));
        Node node_6 = new Node(new LongLat(1 * scaleFactor,8 * scaleFactor));
        Node node_7 = new Node(new LongLat(3 * scaleFactor,9 * scaleFactor));
        Node node_8 = new Node(new LongLat(6 * scaleFactor,9 * scaleFactor));
        Node appletonNode = APPLETON_NODE;



        node_0.addEdge(new Edge(5 * scaleFactor, node_1));
        node_0.addEdge(new Edge(5 * scaleFactor, node_4));
        node_1.addEdge(new Edge(5 * scaleFactor, node_0));
        node_1.addEdge(new Edge(8 * scaleFactor, node_8));
        node_2.addEdge(new Edge(2 * scaleFactor, node_3));
        node_2.addEdge(new Edge(3 * scaleFactor, node_5));
        node_3.addEdge(new Edge(2 * scaleFactor, node_2));
        node_4.addEdge(new Edge(5 * scaleFactor, node_0));
        node_4.addEdge(new Edge(2 * scaleFactor, node_5));
        node_4.addEdge(new Edge(2 * scaleFactor, node_6));
        node_5.addEdge(new Edge(3 * scaleFactor, node_2));
        node_5.addEdge(new Edge(2 * scaleFactor, node_4));
        node_5.addEdge(new Edge(3 * scaleFactor, node_7));
        node_6.addEdge(new Edge(2 * scaleFactor, node_4));
        node_7.addEdge(new Edge(3 * scaleFactor, node_5));
        node_7.addEdge(new Edge(3 * scaleFactor, node_8));
        node_8.addEdge(new Edge(3 * scaleFactor, node_7));
        node_8.addEdge(new Edge(8 * scaleFactor, node_1));
        appletonNode.addEdge(new Edge(1 * scaleFactor, node_1));


        testNodes.add(node_0);
        testNodes.add(node_1);
        testNodes.add(node_2);
        testNodes.add(node_3);
        testNodes.add(node_4);
        testNodes.add(node_5);
        testNodes.add(node_6);
        testNodes.add(node_7);
        testNodes.add(node_8);
        testNodes.add(appletonNode);



        return testNodes;
    }
    private Path generateTestPath_small(){
        ArrayList<Node> testNodes = new ArrayList<>();
        Node node_0 = new Node(new LongLat(1,1));
        Node node_1 = new Node(new LongLat(1-(0.00015*6),1));

        node_0.addEdge(new Edge(5, node_1));
        testNodes.add(node_0);
        testNodes.add(node_1);

        ArrayList<Node> pathNodes = new ArrayList<>();
        ArrayList<Node> stops = new ArrayList<>();
        pathNodes.add(testNodes.get(0));
        pathNodes.add(testNodes.get(1));
        Path path = new Path(pathNodes, 2);

        stops.add(testNodes.get(0));
        stops.add(testNodes.get(1));
        LinkedList<Node> destination = new LinkedList<>();
        destination.add(testNodes.get(0));
        path.setDestinations(destination);
        LinkedList<String> orderNo = new LinkedList<>();
        orderNo.add("whatever");
        path.setOrderNos(orderNo);
        path.setStops(stops);
        return path;
    }

    private Path generateTestPath_4(ArrayList<Node> testNodes){
        ArrayList<Node> pathNodes_4 = new ArrayList<>();
        ArrayList<Node> stops_4 = new ArrayList<>();
        pathNodes_4.add(testNodes.get(4));
        pathNodes_4.add(testNodes.get(5));
        Path path_4 = new Path(pathNodes_4, 2);

        stops_4.add(testNodes.get(4));
        stops_4.add(testNodes.get(5));
        path_4.setStops(stops_4);

        return path_4;
    }

    private Path generateTestPath_3(ArrayList<Node> testNodes){
        ArrayList<Node> pathNodes_3 = new ArrayList<>();
        ArrayList<Node> stops_3 = new ArrayList<>();
        LinkedList<Node> destination = new LinkedList<>();
        LinkedList<String> orderNo = new LinkedList<>();

        pathNodes_3.add(testNodes.get(5));
        pathNodes_3.add(testNodes.get(2));
        Path path_3 = new Path(pathNodes_3, 3);

        stops_3.add(testNodes.get(5));
        stops_3.add(testNodes.get(2));
        path_3.setStops(stops_3);

        destination.add(testNodes.get(2));
        path_3.setDestinations(destination);

        orderNo.add("whatever");
        path_3.setOrderNos(orderNo);

        return path_3;
    }

    private Path generateTestPath_2(ArrayList<Node> testNodes){
        ArrayList<Node> pathNodes_2 = new ArrayList<>();
        ArrayList<Node> stops_2 = new ArrayList<>();
        LinkedList<Node> destination = new LinkedList<>();
        LinkedList<String> orderNo = new LinkedList<>();

        pathNodes_2.add(testNodes.get(2));
        pathNodes_2.add(testNodes.get(3));
        Path path_2 = new Path(pathNodes_2, 2);

        stops_2.add(testNodes.get(2));
        stops_2.add(testNodes.get(3));
        path_2.setStops(stops_2);

        destination.add(testNodes.get(3));
        path_2.setDestinations(destination);

        orderNo.add("whatever");
        path_2.setOrderNos(orderNo);
        return path_2;
    }

    private Path generateTestPath_1(ArrayList<Node> testNodes){
        ArrayList<Node> pathNodes_1 = new ArrayList<>();
        ArrayList<Node> stops_1 = new ArrayList<>();
        LinkedList<Node> destination = new LinkedList<>();
        LinkedList<String> orderNo = new LinkedList<>();

        pathNodes_1.add(testNodes.get(1));
        pathNodes_1.add(testNodes.get(0));
        pathNodes_1.add(testNodes.get(4));
        Path path_1 = new Path(pathNodes_1, 10);

        stops_1.add(testNodes.get(1));
        stops_1.add(testNodes.get(4));
        path_1.setStops(stops_1);

        destination.add(testNodes.get(4));
        path_1.setDestinations(destination);


        orderNo.add("whatever");
        path_1.setOrderNos(orderNo);
        return path_1;
    }




     */
}
