import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author LAUREN SABO
 * @version 1.0
 * @userid lsabo8
 * @GTID 903669960
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start not in the list");
        }

        Set<Vertex<T>> visitedset = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> resultlist = new ArrayList<>();

        queue.add(start);
        visitedset.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> temp = queue.remove();
            resultlist.add(temp);
            for (VertexDistance<T> vertex : graph.getAdjList().get(temp)) {
                if (!visitedset.contains(vertex.getVertex())) {
                    queue.add(vertex.getVertex());
                    visitedset.add(vertex.getVertex());
                }
            }
        }
        return resultlist;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start not in the list");
        }

        Set<Vertex<T>> visitedset = new HashSet<>();
        List<Vertex<T>> resultlist = new ArrayList<>();
        dfs(start, graph, visitedset, resultlist);
        return resultlist;
    }

    /**
     * The helper method to the dfs method above.
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param visitedset the set visited
     * @param resultlist the resulting list
     * @return the resulting list
     */
    private static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph, Set<Vertex<T>> visitedset, List<Vertex<T>> resultlist) {
        visitedset.add(start);
        resultlist.add(start);
        for (VertexDistance<T> single : graph.getAdjList().get(start)) {
            if (!visitedset.contains(single.getVertex())) {
                dfs(single.getVertex(), graph, visitedset, resultlist);
            }

        }
        return resultlist;
    }

    /**
     * Finds the single-source, shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "start not in the list");
        }
        Set<Vertex<T>> visitedset = new HashSet<>();
        Map<Vertex<T>, Integer> dm = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        for (Vertex<T> v : graph.getAdjList().keySet()) {
            if (v.equals(start)) {
                dm.put(v, 0);
            } else {
                dm.put(v, Integer.MAX_VALUE);
            }
        }
        VertexDistance<T> temp = new VertexDistance<>(start, 0);
        pq.add(temp);
        dm.put(start, 0);
        while (!pq.isEmpty() && visitedset.size() < graph.getVertices().size()) {
            VertexDistance<T> yuh = pq.remove();
            if (!visitedset.contains(yuh.getVertex())) {
                visitedset.add(yuh.getVertex());
                dm.put(yuh.getVertex(), yuh.getDistance());
                for (VertexDistance<T> w : graph.getAdjList().get(yuh.getVertex())) {
                    VertexDistance<T> bruh = new VertexDistance<>(w.getVertex(),
                            w.getDistance() + yuh.getDistance());
                    pq.add(bruh);
                }
            }
        }
        return dm;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * You may assume that there will only be one valid MST that can be formed.
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * You should NOT allow self-loops or parallel edges into the MST.
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("the graph is null.");
        }

        DisjointSet<Vertex<T>> disjNew = new DisjointSet<>();
        int vert = graph.getVertices().size();
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> prior = new PriorityQueue<>();

        for (Edge<T> e : graph.getEdges()) {
            prior.add(e);
        }

        while (!(prior.isEmpty()) && mst.size() != 2 * (vert - 1)) {
            Edge<T> edges = prior.remove();
            Vertex<T> u = edges.getU();
            Vertex<T> v = edges.getV();

            if (!(disjNew.find(u).equals(disjNew.find(v)))) {
                disjNew.union(u, v);
                mst.add(new Edge<>(u, v, edges.getWeight()));
                mst.add(new Edge<>(v, u, edges.getWeight()));
            }
        }

        if (mst.size() == (2 * (vert - 1))) {
            return mst;
        } else {
            return null;
        }
    }
}
