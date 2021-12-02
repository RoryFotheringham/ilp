package uk.ac.ed.inf;

/**
 * an edge object that is stored in a node
 */
public class Edge {
    private double weight = 0; // represents the euclidean distance between two nodes
    private Node node; // the node that is connected by this edge

    /**
     * creates an edge pointing to another node
     * @param weight the weight of the edge
     * @param node the node that is being pointed to
     */
    public Edge(double weight, Node node){
        this.weight = weight;
        this.node = node;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
