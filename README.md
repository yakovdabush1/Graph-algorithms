# Graph algorithms

### Description
In this project we will focus mainly on the production of weighted graphs (for an explanation click [here](https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)#Weighted_graph)
), Unintentional, and we will perform some actions on them as detailed below.

The project consists of two main parts
* Create the graph
* Implementation of algorithms belonging to the graph

The graph itself has several main functions
```
public node_info getNode(int key)

public boolean hasEdge(int node1, int node2)

public double getEdge(int node1, int node2)
```

Check if node or edge exsits and return them

```
public void addNode(int key)

public void connect(int node1, int node2, double w)
```

Add of node or edge

```
public Collection<node_info> getV()
```

Get all the node in the graph

```
public Collection<node_info> getV(int node_id)
```

Get all nodes that are מeighbors of a given node

```
public node_info removeNode(int key)

public void removeEdge(int node1, int node2)
```

Remove of node or edge

```
public int getMC()
```



### Also, the algorithms implemented on the graph are

```
boolean isConnected ()
```
Checks whether the graph is linked and returns an answer accordingly

```
public double shortestPathDist (int src, int dest)
```
The function receives two codecs and returns the shortest path weight between them

```
public List <node_info> shortestPath (int src, int dest)
```
The function receives two vertices and returns the shortest trajectory between them

```
public boolean save (String file)
```
The function is named after a file into which the graph will be saved

```
public boolean load (String file)
```
The function gets the name of a file from which the graph will load
