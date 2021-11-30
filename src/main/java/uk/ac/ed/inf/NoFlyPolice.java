package uk.ac.ed.inf;

import java.util.ArrayList;

public class NoFlyPolice {
    Area area;

    public NoFlyPolice(Area area){
        this.area = area;
    }
    public boolean intersectsNoFly(ArrayList<Node> pathList){
        for(int i = 0; i < pathList.size() - 1; i++){
            Node node_1 = pathList.get(i);
            Node node_2 = pathList.get(i+1);
            if(area.intersectsNoFly(node_1, node_2)){
                return true;
            }
        }
        return false;
    }

    public static boolean validPathList(ArrayList<Node> pathList) {
        boolean valid = true;
        boolean validSequence = false;
        for (int i = 0; i < pathList.size() - 1; i++) {
            Node node = pathList.get(i);
            Node otherNode = pathList.get(i+1);
            if (!nodesShareEdge(node, otherNode)){
                return false;
            }
        }
        return true;
    }

    public static boolean nodesShareEdge(Node node, Node otherNode){
        for(Edge edge : node.getEdges()){
            if (edge.node.equals(otherNode)){
                return true;
            }
        }
        return false;
    }

    public boolean edgesIntersect(ArrayList<Node> nodeList){
        for(Node node : nodeList){
            for(Edge edge : node.getEdges()){
                if(this.area.intersectsNoFly(node, edge.node)){
                    return true;
                }
            }
        }
        return false;
    }
    public ArrayList<Integer> flightPathFollowsAbsPath(Path absolutePath, ArrayList<Move> flightPath){
        absolutePath.popPathList();
        ArrayList<Integer> indices = new ArrayList<Integer>();
        ArrayList<LongLat> hoverStations = new ArrayList<>();
        for(Move move : flightPath){
            if(move.getAngle() == -999){
                hoverStations.add(new LongLat(move.getFromLongitude(), move.getFromLatitude()));
            }
        }
        for(int i = 0; i < absolutePath.getPathList().size(); i++){
            if(!hoverStations.get(i).closeTo(absolutePath.getPathList().get(i).getLongLat())){
                indices.add(i);
            }
        }
        return indices;
    }
}
