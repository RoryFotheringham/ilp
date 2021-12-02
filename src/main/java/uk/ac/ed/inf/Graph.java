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
        generateGraph(area, orders);
    }

    public Node graphMapQuery(LongLat longLat){
        return graphMap.get(longLat);
    }

    /**
     * resets the f and g values of each node in the node list
     */
    public void cleanNodes(){
        for (Node node: nodeList){
            node.cleanNode();
        }
    }


    /**
     * generates each node with respect to every store, landmark, customer and appleton tower
     * creates an edge between two nodes if they can be reached with a straight line without intersecting the no fly zone
     * @param area information about the no fly zone and landmarks
     * @param orders information storing the orders made for a specific date
     */
    private void generateGraph(Area area, Orders orders){
        this.nodeList = new ArrayList<>();
        this.graphMap = new HashMap<>();
        // generates every node
        generateAppletonNode(area);
        generateLandmarkNodes(area);
        ArrayList<OrderDetails> orderDetailsList = orders.getOrdersList();
        for (OrderDetails orderDetails: orderDetailsList){
            generateOrderNodes(area, orderDetails);
        }
        generateEdges(area); //generates the edges for every node
    }

    /**
     * iterates through each node object and creates an edge between them if they can be reached in a straight line
     * without intersecting the noflyzone
     * @param area information about the no fly zones and landmarks
     */
    private void generateEdges(Area area){
        for (Node currentNode: this.nodeList){//add the nodes that can reach each other in a straight line as edges
            for(Node otherNode: this.nodeList){
                if(!currentNode.equals(otherNode) && !area.intersectsNoFly(currentNode, otherNode)){
                    double weight = currentNode.distanceTo(otherNode);
                    Edge edge = new Edge(weight, otherNode);
                    currentNode.addEdge(edge);
                }
            }
        }
    }

    /**
     * constructs a node containing the location of each landmark
     * and adds to the graphMap and nodeList
     * @param area contains landmark information
     */
    private void generateLandmarkNodes(Area area){
        ArrayList<Landmark> landmarks = area.landmarks;
        for(Landmark landmark: landmarks){
            Node node = new Node(landmark.getLongLat());
            nodeList.add(node);
            graphMap.put(landmark.getLongLat(), node);
        }
    }

    /**
     * generates a node containing the location of appleton tower
     * @param area
     */
    private void generateAppletonNode(Area area){
        Node appletonNode = new Node(APPLETON_TOWER);
        appletonNode.setName("appleton"); // the name is only relevant for debugging purposes
        graphMap.put(APPLETON_TOWER, appletonNode);
        nodeList.add(appletonNode);
    }

    /**
     * generates a node for every store and customer that are relevant to the orders placed on a specific day
     * @param area contains information on the noflyzone and landmarks
     * @param orderDetails contains information on all of the orders placed on a specific day
     */
    private void generateOrderNodes(Area area, OrderDetails orderDetails){
        ArrayList<Item> items = orderDetails.getItems();
        //generate the nodes for the location of each item
        for(Item item: items){
            LongLat itemLongLat = item.getLongLat();
            if(!graphMap.containsKey(itemLongLat)){
                Node storeNode = new Node(itemLongLat);
                this.stores.add(storeNode);
                graphMap.put(itemLongLat, storeNode);
                nodeList.add(storeNode);
            }
        }
        //generate the nodes for the location of each customer
        if(!graphMap.containsKey(orderDetails.getDeliverTo())) {
            Node destinationNode = new Node(orderDetails.getDeliverTo());
            this.customers.add(destinationNode);
            this.graphMap.put(orderDetails.getDeliverTo(), destinationNode);
            nodeList.add(destinationNode);
        }
    }
}
