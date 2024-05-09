/*Student Name: Aaron Baggot
 * Student Number: C22716399
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class Heap {
    private int[] a;      // heap array
    private int[] hPos;   // hPos[h[k]] == k
    private int[] dist;    // dist[v] = priority of v
    private int N;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array
    public Heap(int maxSize, int[] _dist, int[] _hPos)  {
        N = 0;
        a = new int[maxSize + 1];
        dist = _dist;
        hPos = _hPos;
    }

    public boolean isEmpty()  {
        return N == 0;
    }

    public void siftUp(int k)  {
        int v = a[k];
        // code yourself
        // must use hPos[] and dist[] arrays
    }

    public void siftDown(int k)  {
        int v, j;
        v = a[k];  
        // code yourself 
        // must use hPos[] and dist[] arrays
    }

    public void insert(int x)  {
        a[++N] = x;
        siftUp(N);
    }

    public int remove()  {   
        int v = a[1];
        hPos[v] = 0; // v is no longer in heap
        a[N+1] = 0;  // put null node into empty spot
        a[1] = a[N--];
        siftDown(1);
        return v;
    }
}

class Graph {
    class Node {
        public int vert;
        public int wgt;
        public Node next;
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private int[] mst;
    
    // used for traversing graph
    private boolean[] visited;
    private int id;
    
    
    // default constructor
    public Graph(String graphFile)  throws IOException {
        int u, v;
        int e, wgt;
        Node t;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z       
        adj = new Node[V+1];        
        for(v = 1; v <= V; ++v)
            adj[v] = z;               
        
       // read the edges
        for(e = 1; e <= E; ++e) {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            Node newNode = new Node();
            newNode.vert = v;
            newNode.wgt = wgt;
            newNode.next = adj[u];
            adj[u] = newNode;
        }
        reader.close();
    }
   
    // convert vertex into char for pretty printing
    private char toChar(int u) {  
        return (char)(u + 64);
    }
    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");    
        }
        System.out.println("");
    }

    // Depth First Search (DFS) using recursion
    public void DFS(int start) {
        visited = new boolean[V + 1];
        System.out.println("\nDepth First Search:");
        dfVisit(start);
        System.out.println();
    }

    private void dfVisit(int v) {
        visited[v] = true;
        System.out.print(toChar(v) + " ");

        Node u = adj[v];
        while (u != z) {
            if (!visited[u.vert]) {
                dfVisit(u.vert);
            }
            u = u.next;
        }
    }

    // Breadth First Search (BFS) using a queue
    public void BFS(int start) {
        visited = new boolean[V + 1];
        Queue<Integer> queue = new LinkedList<>();
        System.out.println("\nBreadth First Search:");

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print(toChar(v) + " ");

            Node u = adj[v];
            while (u != z) {
                if (!visited[u.vert]) {
                    visited[u.vert] = true;
                    queue.add(u.vert);
                }
                u = u.next;
            }
        }
        System.out.println();
    }

    public void MST_Prim(int s) {
        // Implementation of Prim's algorithm
    }

    public void showMST() {
        // Implementation of showing Minimum Spanning Tree
    }

    public void SPT_Dijkstra(int s) {
        // Implementation of Dijkstra's algorithm for Shortest Path Tree
    }
}

public class GraphLists {
    public static void main(String[] args) throws IOException {
        String fname = "wGraph1.txt";               

        Graph g = new Graph(fname);
        g.display();

        int startVertex = 1; // Change this to the desired start vertex

        g.DFS(startVertex);
        g.BFS(startVertex);
    }
}