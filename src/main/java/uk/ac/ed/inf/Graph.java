package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * class generates and stores landmarks, stores and delivery location in graph structure
 */
public class Graph{
    private static final LongLat APPLETON_TOWER = new LongLat(-3.186874, 55.944494);
    private HashMap<LongLat, Node> graphMap = new HashMap<>();
    private ArrayList<Node> nodeList;
    private ArrayList<Node> stores = new ArrayList<>();
    private ArrayList<Node> customers = new ArrayList<>();

    public Graph(ArrayList<Node> nodes){//constructor only to be used for generating test graphs.
        this.nodeList = nodes;
    }

    public Graph(Area area, Orders orders) {
        ArrayList<NoFly> noFlyList = area.noFlyList;
        generateGraph(area, orders);
    }

    public Node graphMapQuery(LongLat longLat){
        return graphMap.get(longLat);
    }

    public void cleanNodes(){
        for (Node node: nodeList){
            node.cleanNode();
        }
    }

    public void generateGraph(Area area, Orders orders){
        this.nodeList = new ArrayList<>();
        this.graphMap = new HashMap<>();
        generateAppletonNode(area);
        generateLandmarkNodes(area);
        ArrayList<OrderDetails> orderDetailsList = orders.getOrdersList();
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
        ArrayList<Item> items = orderDetails.getItems();
        for(Item item: items){
            LongLat itemLongLat = item.getLongLat();
            if(!graphMap.containsKey(itemLongLat)){
                Node storeNode = new Node(itemLongLat);
                this.stores.add(storeNode);
                graphMap.put(itemLongLat, storeNode);
                nodeList.add(storeNode);
            }
        }

        if(!graphMap.containsKey(orderDetails.getDeliverTo())) {
            Node destinationNode = new Node(orderDetails.getDeliverTo());
            this.customers.add(destinationNode);
            this.graphMap.put(orderDetails.getDeliverTo(), destinationNode);
            nodeList.add(destinationNode);
        }
    }
}
