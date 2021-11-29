package uk.ac.ed.inf;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * class generates and stores landmarks, stores and delivery location in graph structure
 */
public class Graph{
    private static final LongLat APPLETON_TOWER = new LongLat(-3.186874, 55.944494);
    HashMap<LongLat, Node> graphMap = new HashMap<>();
    ArrayList<Node> nodeList;
    ArrayList<Node> stores = new ArrayList<>();
    ArrayList<Node> customers = new ArrayList<>();
    ArrayList<Node> unreachableNodes = new ArrayList<>();

    public Graph(ArrayList<Node> nodes){//constructor only to be used for generating test graphs.
        this.nodeList = nodes;
    }

    public Graph(Area area, Orders orders) {
        ArrayList<NoFly> noFlyList = area.noFlyList;
        boolean connected = false;
        generateGraph(area, orders);
        connected = isConnected();
        while(!connected){
            Node node = nodeList.get(0);//todo choose a better node
            generateBridgeNode(area, node);
            connected = isConnected();
        }
    }

    public Node graphMapQuery(LongLat longLat){
        return graphMap.get(longLat);
    }

    public void cleanNodes(){
        for (Node node: nodeList){
            node.cleanNode();
        }
    }

    private void generateBridgeNode(Area area, Node unreachable) {
        ArrayList<Double> distances = new ArrayList<>();
        for(int i = 0; i < nodeList.size();){
            Node node = nodeList.get(i);
            double distance = node.distanceTo(unreachable);
            distances.add(distance);
        }
        //todo make this function actually generate a bridge node
        //todo perhaps we don't need the closest node - maybe it's better to work with the landmarks.
    }

    public Node unreachableNode(){
        for(Node node: nodeList){
            if(node.getEdges().isEmpty()){
                return node;
            }
        }
        return null;
    }

    public boolean isConnected(){
        //todo check whether graph is a connected graph.
        // need to use DFS.
        // right now we assume that graph is connected
        return true;
    }


    public void generateGraph(Area area, Orders orders){
        this.nodeList = new ArrayList<>();
        this.graphMap = new HashMap<>();
        generateAppletonNode(area);
        generateLandmarkNodes(area);
        ArrayList<OrderDetails> orderDetailsList = orders.ordersList;
        for (OrderDetails orderDetails: orderDetailsList){
            generateOrderNodes(area, orderDetails);
        }
        generateEdges(area);
    }

    public void generateEdges(Area area){
        for (Node currentNode: this.nodeList){//add the nodes that can reach each other in a straight line as edges
            for(Node otherNode: this.nodeList){
                if(currentNode != otherNode && !area.intersectsNoFly(currentNode, otherNode)){
                    double weight = currentNode.distanceTo(otherNode);
                    Edge edge = new Edge(weight, otherNode);
                    currentNode.addEdge(edge);
                }
            }
        }
    }

    public void generateLandmarkNodes(Area area){
        ArrayList<Landmark> landmarks = area.landmarks;
        for(Landmark landmark: landmarks){
            Node node = new Node(landmark.longLat);
            nodeList.add(node);
            graphMap.put(landmark.longLat, node);
        }
    }

    private void generateAppletonNode(Area area){
        Node appletonNode = new Node(APPLETON_TOWER);
        appletonNode.setName("appleton");
        graphMap.put(APPLETON_TOWER, appletonNode);
        nodeList.add(appletonNode);
    }

    private void generateOrderNodes(Area area, OrderDetails orderDetails){
        ArrayList<Item> items = orderDetails.items;
        for(Item item: items){
            LongLat itemLongLat = item.longLat;
            if(!graphMap.containsKey(itemLongLat)){
                Node storeNode = new Node(itemLongLat);
                this.stores.add(storeNode);
                graphMap.put(itemLongLat, storeNode);
                nodeList.add(storeNode);
            }
        }

        if(!graphMap.containsKey(orderDetails.deliverTo)) {
            Node destinationNode = new Node(orderDetails.deliverTo);
            this.customers.add(destinationNode);
            this.graphMap.put(orderDetails.deliverTo, destinationNode);
            nodeList.add(destinationNode);
        }
    }
}
