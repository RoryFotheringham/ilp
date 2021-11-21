package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class generates and stores landmarks, stores and delivery location in graph structure
 */
public class Graph{
    HashMap<LongLat, Node> graphMap = new HashMap<>();
    ArrayList<Node> nodeList = new ArrayList<>();
    ArrayList<Node> stores = new ArrayList<>();
    Node destination;

    public Graph(Area area, OrderDetails orderDetails){
        ArrayList<NoFly> noFlyList = area.noFlyList;
        generateGraph(area, orderDetails);
    }

    public void generateGraph(Area area, OrderDetails orderDetails){
        generateNodes(area, orderDetails);
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

    private void generateNodes(Area area, OrderDetails orderDetails){
        ArrayList<Item> items = orderDetails.items;
        ArrayList<LongLat> addedStores = new ArrayList<>();
        for(Item item: items){
            LongLat itemLongLat = item.longLat;
            if(!addedStores.contains(itemLongLat)){
                addedStores.add(itemLongLat);
                Node storeNode = new Node(itemLongLat);
                this.stores.add(storeNode);
                nodeList.add(storeNode);
            }
        }
        ArrayList<Landmark> landmarks = area.landmarks;
        for(Landmark landmark: landmarks){
            nodeList.add(new Node(landmark.longLat));
        }

        Node destinationNode = new Node(orderDetails.deliverTo);
        this.destination = destinationNode;
        nodeList.add(destinationNode);
    }
}

/*
public class Graph {
    public static final double CONFINEMENT_AREA_X1 = -3.192473; //constants outlining the confinement area in long-lat degrees
    public static final double CONFINEMENT_AREA_X2 = -3.184319;
    public static final double CONFINEMENT_AREA_Y1 = 55.942617;
    public static final double CONFINEMENT_AREA_Y2 = 55.946333;
    public static final int[] ANGLES = {90, 210, 300};
    public static final double MOVEMENT_DISTANCE =  0.00015;
    public static final double SHORT_DIAGONAL = 0.0002598;
    public static final double Y_GRID_CHANGE = 0.000375;

    HashMap<Grid, Node> graphMap;
    Area area;

    public Graph(Area area){
        this.graphMap = new HashMap<Grid, Node>();
        generateGraph();
    }

    public void generateGraph(){
        double xDiff = (CONFINEMENT_AREA_X2 - CONFINEMENT_AREA_X1);
        double yDiff = (CONFINEMENT_AREA_Y2 - CONFINEMENT_AREA_Y1);
        double cols =  Math.floor(xDiff/MOVEMENT_DISTANCE) - 2;
        double rows =  Math.floor(yDiff/MOVEMENT_DISTANCE) - 2;
        double xStart = CONFINEMENT_AREA_X1 + MOVEMENT_DISTANCE;
        double yStart = CONFINEMENT_AREA_Y2 + MOVEMENT_DISTANCE;

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                generateNode(new Grid(i, j), (int) rows, (int) cols);
            }
        }
    }

    private void createEdge(Grid grid, int angle, Node node){
        Node nodeToAdd = new Node(grid);
        node.addEdge(new Edge(angle, nodeToAdd));

        if (!this.graphMap.containsKey(grid)) {
            this.graphMap.put(grid, nodeToAdd);
        }
        node.addEdge(new Edge(angle, nodeToAdd));
    }

    private void generateNode(Grid grid, int rows, int cols){
        Node newNode;

        if (!this.graphMap.containsKey(grid)){
            newNode = new Node(grid);
            this.graphMap.put(grid, newNode);
   //         System.out.println(this.graphMap.get(grid));
 //           System.out.println(newNode);
   //         System.out.println(!this.graphMap.containsKey(grid));
 //           System.out.println(!this.graphMap.containsKey(new Grid(grid.i, grid.j)));
        }else{
            newNode = this.graphMap.get(grid);
        }

        if ((grid.i + 1) < rows) {
            createEdge(new Grid(grid.i + 1, grid.j), 90, newNode);
        }
        if ((grid.i - 1) > 0){
            createEdge(new Grid(grid.i - 1, grid.j), 270, newNode);
        }
        if ((grid.j + 1) < cols){
            createEdge(new Grid(grid.i, grid.j + 1), 0, newNode);
        }
        if ((grid.j - 1) > 0){
            createEdge(new Grid(grid.i, grid.j - 1), 180, newNode);
        }
    }


/*
    public HashMap<LongLat, Node> generateGraph(){
        double columns = Math.ceil((CONFINEMENT_AREA_X2 - CONFINEMENT_AREA_X1)/SHORT_DIAGONAL);
        double rows = Math.ceil((CONFINEMENT_AREA_Y2 - CONFINEMENT_AREA_Y1)/Y_GRID_CHANGE);

        double[] centre = {CONFINEMENT_AREA_X1, CONFINEMENT_AREA_Y1};

        LongLat position;
        double distanceY;
        double distanceX;
        int change;

/*
       for (int i = 0; i < rows; i++) {

           change = 1;
           if(i % 2 == 0){ change = -1;}
           distanceX = BigDecimal.valueOf(SHORT_DIAGONAL) * change;
           BigDecimal tempx = c
           centre.set(0, tempx + distanceX);
           distanceY = Y_GRID_CHANGE * i;
           centre[1] += distanceY;

           position = new LongLat(centre[0], centre[1]);

           centre[0] = CONFINEMENT_AREA_X1;
           for (int j = 0; j < columns; j++) {
               distanceX = SHORT_DIAGONAL * j;
               centre[0] += distanceX;
               position = new LongLat(centre[0], centre[1]);

               if (position.isConfined()) {
                   ArrayList<Edge> edges = generateEdges(position);
                   if (this.graphMap.containsKey(position)) {
                        Node node = this.graphMap.get(position);
                        node.setEdges(edges);
                   }else{
                       Node centreNode = new Node(position, edges);
                       this.graphMap.put(position, centreNode);
                   }
               }
           }
       }
        return graphMap;
    }


    private ArrayList<Edge> generateEdges(LongLat longLat){
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            LongLat position = longLat.nextPosition(ANGLES[i]);

            if (!position.isConfined()){
                break;
            }
            if (this.graphMap.containsKey(position)){
                edges.add(new Edge(ANGLES[i], this.graphMap.get(position)));
            }else{
                Node newNode = new Node(position);
                this.graphMap.put(position, newNode);
                edges.add(new Edge(ANGLES[i], newNode));
            }
        }
        return edges;
    }



}
*/