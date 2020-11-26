package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    private weighted_graph d;

    @Override
    public void init(weighted_graph g) {
        this.d = g;
    }

    @Override
    public weighted_graph getGraph() {
        return d;
    }

    /**
     * this function creates a new graph that will contain all the copies of the nodes
     * that exist in the ancient graph,
     * in addition it connects all the edges in the new graph as they are in the ancient graph.
     *
     * @return the new copied graph
     */
    @Override
    public weighted_graph copy() {
        if (d != null) {
            weighted_graph ans = new WGraph_DS();

            for (node_info a : this.d.getV()) {
                ans.addNode(a.getKey());
            }

            for (node_info b : this.d.getV()) {

                for (node_info bc : this.d.getV(b.getKey())) {
                    ans.connect(bc.getKey(), b.getKey(), this.d.getEdge(bc.getKey(), b.getKey()));
                }
            }
            return ans;
        }
        return null;
    }

    /** This function briefly goes through the algorithm on all
     * the nodes connected in the graph "and paints" them in black
     * so that if we check whether there are nodes in the graph that are "painted" in white after our coloring
     * we can know whether the graph is linked or not
     * (if there is white node the graph is not connected.)

     *
     * @return ans (boolean) false/true.
     */
    @Override
    public boolean isConnected() {

        if (d.nodeSize() <= 1) {
            return true;
        }

        boolean ans = true;
        Queue<node_info> nextNodes = new LinkedList<>();

        Iterator<node_info> itr = this.d.getV().iterator();
        node_info secNode = itr.next();
        secNode.setInfo("Black");
        nextNodes.add(secNode);

        while (!nextNodes.isEmpty()) {
            node_info current = nextNodes.poll();
            // iterator on current

            for (node_info curNext : this.d.getV(current.getKey())) {
                if (curNext.getInfo().equals("White")) {
                    curNext.setInfo("Black");
                    nextNodes.add(curNext);

                }
            }
        }// check if there is white node the graph is not connected. ans == false
        while (itr.hasNext()) {
            if (itr.next().getInfo().equals("White")) {
                ans = false;
            }
        } //Reset all the nodes to be white

        for (ex1.node_info node_info : this.d.getV()) {
            node_info.setInfo("White");
        }
        return ans;
    }

    /**
     * @param src - start node
     * @param dest - end (target) node
     * @return the sum of the shortest path between src to dest, if there is not return -1(Irrational distance).
     */
    @Override
    public double shortestPathDist ( int src, int dest){
        double shortDist = -1;

        if(d.getNode(src)==null|| d.getNode(dest)==null){
            return shortDist;
        }
        Queue<node_info> q = new LinkedList<>();
        q.add(d.getNode(src));

       if(q.peek()!=null)
        q.peek().setTag(0);
        while (!q.isEmpty()) {
            node_info current = q.poll();
            if (!current.getInfo().equals("Black")) {
                double x = current.getTag();
                for (node_info nex : this.d.getV(current.getKey())) {
                    if (nex.getInfo().equals("White")) {
                        q.add(nex);
                        nex.setInfo("Grey");
                    }
                    if (nex.getTag() > (x + this.d.getEdge(nex.getKey(), current.getKey())) || nex.getTag() == -1) {
                        nex.setTag((x + this.d.getEdge(nex.getKey(), current.getKey())));
                    }
                }
                current.setInfo("Black");
            }
        }
        if(this.d.getNode(src).getTag()!=-1&& this.d.getNode(dest).getTag()!=-1) {
            shortDist = this.d.getNode(dest).getTag();
        }

        for (node_info rem : this.d.getV()) {
            rem.setInfo("White");
            rem.setTag(-1);
        }
        return shortDist;
    }
    /**
     * @param src - start node
     * @param dest - end (target) node
     * @return list of the shortest path if there is not the function will return null
     */
    @Override
    public List<node_info> shortestPath ( int src, int dest){
       // check if they null and if so return null.
        if(this.d.getNode(src)==null|| this.d.getNode(dest)==null){
            return null;
        }
        Queue<node_info> q2 = new LinkedList<>();
        q2.add(this.d.getNode(src));

        if( q2.peek()!=null)
        q2.peek().setTag(0);
        while (!q2.isEmpty()) {
            node_info current2 = q2.poll();
            if (!current2.getInfo().equals("Black")) {
                double x = current2.getTag();
                for (node_info nex : this.d.getV(current2.getKey())) {
                    if (nex.getInfo().equals("White")) {
                        q2.add(nex);
                        nex.setInfo("Grey");
                    }
                    if (nex.getTag() > (x + this.d.getEdge(nex.getKey(), current2.getKey())) || nex.getTag() == -1) {
                        nex.setTag((x + this.d.getEdge(nex.getKey(), current2.getKey())));
                    }
                }
                current2.setInfo("Black");
            }
        }
        Stack<node_info> M = new Stack<>();
        if(this.d.getNode(src).getTag()!=-1&& this.d.getNode(dest).getTag()!=-1) {
            node_info des = this.d.getNode(dest);
            while (true){
                if(des.getTag()==0){
                    M.add(des);
                    break;
                }
                for (node_info sho : this.d.getV(des.getKey())) {
                    double a = this.d.getEdge(des.getKey(), sho.getKey());
                    double b = sho.getTag();
                    double f = des.getTag();
                    if (f == b + a) {
                        M.add(des);
                        des = sho;
                        break;
                    }
                }
            }
        }
        else{
            return null;
        }
        List<node_info> path = new LinkedList<>();
        while (!M.isEmpty()) {
            path.add(M.pop());
        }
        for (node_info rem : this.d.getV()) {
            rem.setInfo("White");
            rem.setTag(-1);
        }
        return path;
    }

    /**
     * This function create a new File and saves a weighted graph into it.
     * @param file - the file name (may include a relative path).
     * @return boolean false/true.
     */
    @Override
    public boolean save(String file) {
        System.out.println("starting Serialize to "+file+"\n");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this.d);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        System.out.println("end of Serialize \n\n");
        return true;
    }

    /**
     * This function loads a file (graph) into a graph.
     * @param file - file name
     * @return boolean false/true.
     */
    @Override
    public boolean load(String file) {
        System.out.println("Deserialize from : "+file);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.d = (weighted_graph)objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
