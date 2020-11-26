package ex1;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private HashMap<Integer,HashMap<Integer,Double>> Ni;
    private HashMap<Integer,node_info> graphMap;

    private int EdgeCount;
    private int MC;

    public WGraph_DS(){
        this.graphMap = new HashMap<Integer, node_info>();
        this.Ni = new HashMap<Integer,HashMap<Integer,Double>>();
    }


    /**
     * this function check if there is Node with the same key in the Graph and than return this Node
     * @param key - the node_id
     * @return if this key exist in the graph return this Node else return null
     */
    @Override
    public node_info getNode(int key) {
        if(graphMap.containsKey(key)){
            return graphMap.get(key);
        }
        return null;
    }

    /**
     * this function check if there is an edge between the accepted nodes.
     * @param node1
     * @param node2
     * @return boolean false/true.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(Ni.get(node1).containsKey(node2)&&Ni.get(node2).containsKey(node1)){
            return true;
        }
        return false;
    }

    /**
     *  this function get the edge between the accepted nodes.
     * @param node1
     * @param node2
     * @return double edg - the value of the edge between the accepted nodes.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
            double edg = Ni.get(node1).get(node2).doubleValue();
            return edg;
        }
        return -1;
    }

    /**
     * This function adds a node to the graph
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!graphMap.containsKey(key)){
            MC++;

            graphMap.put(key, new NodeInfo(key));
            Ni.put(key,new HashMap<Integer, Double>());
        }
    }

    /**
     * this function connect between 2 of the accepted node with accepted value (w).
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(graphMap.containsKey(node1) && graphMap.containsKey(node2) && node1 != node2) {

            MC++;

            if (!Ni.containsKey(node2)) {
                Ni.put(node2, new HashMap<Integer, Double>());
            }
            if (!Ni.containsKey(node1)) {
                Ni.put(node1, new HashMap<Integer, Double>());
            }

            if (!hasEdge(node1, node2)) {
                Ni.get(node1).put(node2, w);
                Ni.get(node2).put(node1, w);
                EdgeCount++;
            }
            else{
                Ni.get(node1).replace(node2,w);
                Ni.get(node2).replace(node1,w);
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return graphMap.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if(graphMap.containsKey(node_id)) {
            Collection<node_info> neighbors = new ArrayList<>();

            if (Ni.get(node_id).keySet() != null) {
                Iterator<Integer> itr = Ni.get(node_id).keySet().iterator();

                while (itr.hasNext()) {
                    neighbors.add(graphMap.get(itr.next()));
                }

                return neighbors;
            }
        }
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        if(graphMap.containsKey(key)){

            MC++;

            node_info removedNode = this.getNode(key);
            if(Ni.containsKey(key)) {
                Iterator<Integer> iterator = Ni.get(key).keySet().iterator();
                while (iterator.hasNext()) {
                    Ni.get(iterator.next()).remove(key);

                    EdgeCount--;
                }
                Ni.remove(key);
            }

            graphMap.remove(key);

            return removedNode;
        }
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){

            EdgeCount--;
            MC++;

            Ni.get(node1).remove(node2);
            Ni.get(node2).remove(node1);
        }
    }

    @Override
    public int nodeSize() {
        return graphMap.keySet().size();
    }

    @Override
    public int edgeSize() {
        return this.EdgeCount;
    }

    @Override
    public int getMC() { return this.MC; }

    private class NodeInfo implements node_info, Serializable{
        private int key;
        private String Info;
        private double Tag;

        public NodeInfo(int key){
            this.key = key;
            this.Info = "White";
            this.Tag = -1;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.Info;
        }

        @Override
        public void setInfo(String s) {
            this.Info = s;
        }

        @Override
        public double getTag() {
            return this.Tag;
        }

        @Override
        public void setTag(double b) {
            this.Tag=b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Double.compare(nodeInfo.Tag, Tag) == 0 &&
                    Info.equals(nodeInfo.Info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, Info, Tag);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (  getClass() != o.getClass() || o == null) {
            return false;
        }

        WGraph_DS wGraph_ds = (WGraph_DS) o;

        return EdgeCount == wGraph_ds.EdgeCount &&
                graphMap.keySet().size() == wGraph_ds.graphMap.keySet().size() && graphMap.equals(wGraph_ds.graphMap)&&
                MC == wGraph_ds.MC && Ni.equals(wGraph_ds.Ni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graphMap, Ni, EdgeCount, graphMap.keySet().size(), MC);
    }
}