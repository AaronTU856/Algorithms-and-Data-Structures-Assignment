/*Student Name: Aaron Baggot
 * Student Number: C22716399
 */

import java.io.*;
import java.util.Queue;
import java.util.LinkedList;


enum C {White, Grey, Black};

class GraphMatrix 
{
    // V = number of vertices
    // E = number of edges
    // adj[ ][ ] is the adjacency matrix
    private int V, E;
    private int[][] adj;

    // Traversing graph to mark vertices already visited
    private C[] colour;
    private int time;
    
    // Storing the traversal tree and distance from starting vertex
    private int[] parent, d, f ;
   
   
    // Default constructor
    public GraphMatrix(String graphFile)  throws IOException
    {
        int u, v;
        int e, wgt;
       
		FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);	          
        
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
		    
		V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);

        // Create adjacency matrix, initialised to 0's        
        adj = new int[V+1][V+1];        
        colour = new C[V+1];
        parent = new int[V+1];
        d = new int[V+1];
        f = new int[V+1];
        
        // Read the edges
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            // Add edge to adjacency matrix
            adj[u][v] = wgt; // Assuming undirected graph
        }	       
    }

    // Convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
	
    // Method to display the graph representation
    public void display() {
        int u,v;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + v + "] = ");
            for(u=1; u<=V; ++u) 
                System.out.print("  " + adj[u][v]);
        }    
        System.out.println("");
    }


    // Method to initialise Depth First Traversal of Graph
    // Assuming graph is connected
    public void DF( int s) 
    {     
        int v;
        for(v=1; v<=V; ++v) {
            colour[v] = C.White;
            parent[v] = 0;        
        }
        
        System.out.print("\nDepth First Graph Traversal\n");
        System.out.println("Starting with Vertex " + toChar(s));
        
        time = 0;
        dfVisit(s);                      
        
        System.out.print("\n\n");
    }


    // Recursive Depth First Traversal for adjacency matrix
    private void dfVisit( int v)
    {
        int u;
        ++time;
        d[v] = time;
        colour[v] = C.Grey;
        
        System.out.print("\n  DF just visited vertex " + toChar(v) + " along edge " + 
            toChar(parent[v]) + "--" + toChar(v) );
        
        // Process all the vertices u connected to vertex v
        for(u = 1; u <= V; ++u) {
            if(adj[v][u] != 0) { // there is an edge
                if(colour[u] == C.White) {
                    parent[u] = v;
                    dfVisit(u);
                }
            }
        }
        
        colour[v] = C.Black;
        ++time;
        f[v] = time;
    }
    
    public void BF(int s) {
        Queue<Integer> q = new LinkedList<>();
        int u, v;
        
        for(v = 1; v <= V; ++v) {
            colour[v] = C.White;
            parent[v] = 0;
        }
        
        System.out.print("\nBreadth First Graph Traversal\n");
        System.out.println("Starting with Vertex " + toChar(s));
        
        q.add(s);
        colour[s] = C.Grey;
        
        while(!q.isEmpty()) {
            u = q.remove();
            System.out.print("\nBF just visited vertex " + toChar(u) + " along edge " + 
                toChar(parent[u]) + "--" + toChar(u) );
            
            for(v = 1; v <= V; ++v) {
                if(adj[u][v] != 0) { // there is an edge
                    if(colour[v] == C.White) {
                        colour[v] = C.Grey;
                        parent[v] = u;
                        q.add(v);
                    }
                }
            }
            
            colour[u] = C.Black;
        }
        
        System.out.print("\n\n");
    }

    public static void main(String[] args) throws IOException
    {
        int s = 4;
        String fname = "wGraph1.txt";               

        GraphMatrix g = new GraphMatrix(fname);
       
        g.display();
        
        g.DF(s);

        g.BF(s);
        
    }

}
