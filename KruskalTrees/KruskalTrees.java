/*  Student Name: Aaron Baggot
    Student Number: C22716399
    Kruskal's Minimum Spanning Tree Algorithm
    Union-find implemented using disjoint set trees without compression */

import java.io.*;    
import java.util.Scanner; 

// Class to represent an edge in the graph
class Edge 
{

    public int u, v, wgt;

    public Edge() 
    {
        u = 0;
        v = 0;
        wgt = 0;
    }

    public Edge(int x, int y, int w) 
    { 
        u = x;
        v = y;
        wgt = w;
    }
    
    // Method to display the edge
    public void show() 
    {
        System.out.println("Edge " + toChar(u) + "--" + wgt + "--" + toChar(v) + "\n") ;
    }
    
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
}

class Heap 
{
    private int[] h;     // Array to store heap indices
    int N, Nmax;         // Variables to store current heap size and maximum heap size
    Edge[] edge;         // Array to store edges

    // Constructor to initialize the heap
    public Heap(int _N, Edge[] _edge) 
    {
        int i;
        Nmax = N = _N;
        h = new int[N + 1]; // Initialize heap array
        edge = _edge;       // Store the edges array

        // Fill heap array with indices of edge[] array
        for (i = 0; i <= N; ++i)
        {
            h[i] = i;
        }

        // Convert h[] into a heap from the bottom up
        for (i = N / 2; i > 0; --i) 
        {
            siftDown(i); // Call siftDown to maintain heap property
        }
    }

    // Method to perform siftDown operation
    private void siftDown(int k) 
    {
        int e, j;

        e = h[k];
        while (k <= N / 2) 
        {
            // Determine the index of the child node with minimum weight
            j = 2 * k;

            if ((j < N) && (edge[h[j]].wgt > edge[h[j + 1]].wgt)) 
            {
                j++;
            }

            // If the weight of the child node is greater than or equal to the weight of the current node, break
            if (edge[h[j]].wgt >= edge[e].wgt) 
            {
                break;
            }

            // Swap the current node with the child node with minimum weight
            h[k] = h[j];
            k = j;
        }
        h[k] = e;
    }

    // Method to remove and return the minimum element from the heap
    public int remove() 
    {
        h[0] = h[1];
        h[1] = h[N--];
        siftDown(1);
        return h[0];
    }
}

/****************************************************
*
*       UnionFind partition to support union-find operations
*       Implemented simply using Discrete Set Trees
*
*****************************************************/

// Class to support union-find operations
class UnionFindSets
{
    private int[] treeParent;
    private int N;
    
    public UnionFindSets(int V)
    {
        N = V;
        treeParent = new int[V + 1];
        
        //Initially each element is its own parent
        for(int i = 0;i <=V; i++) 
        {
            treeParent[i] = i;
        }
    } // end union find data

    public int findSet(int vertex)
    {   
        // Traverse through the parent pointers until finding the root node
        while (vertex != treeParent[vertex]) 
        { // Start while
            vertex = treeParent[vertex];
        } // End while

        return vertex;
    } 

    public void union(int set1, int set2)
    {     
        // Joins two subsets into single set
        treeParent[findSet(set2)] = findSet(treeParent[set1]);
        System.out.print("Union: (" + toChar(set1) + "," + toChar(set2) + "): ");
    }
    
    public void showTrees()
    {
        int i;
        for(i=1; i<=N; ++i)
        {
            System.out.print(toChar(i) + "->" + toChar(treeParent[i]) + "  " );
        }
        System.out.print("\n");
    }
    
    public void showSets()
    {
        int u, root;
        int[] shown = new int[N + 1];
        for (u=1; u<=N; ++u)
        {   
            root = findSet(u);

            if(shown[root] != 1) 
            {
                showSet(root);
                shown[root] = 1;
            }            
        }   
        System.out.print("\n");
    }

    private void showSet(int root)
    {
        int v;
        System.out.print("Set{");
        for(v=1; v<=N; ++v)
        {
            if(findSet(v) == root)
            {
                System.out.print(toChar(v) + " ");
            }
        }
        System.out.print("}  ");
    
    }
    
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
}
// Class to represent the graph
class Graph 
{ 
    private int V, E;
    private Edge[] edge;
    private Edge[] mst;        

    public Graph(String graphFile) throws IOException
    {
        int u, v;
        int w, e;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create edge array
        edge = new Edge[E + 1];   
        
       // read the edges
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            w = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + w + ")--" + toChar(v));                         
             
            // create Edge object
            edge[e] = new Edge(u, v, w);  
        }
    }


    /**********************************************************
    *
    *       Kruskal's minimum spanning tree algorithm
    *
    **********************************************************/

    public Edge[] MST_Kruskal() 
    {
        int ei, i = 0, j = 0;
        Edge e;
        int uSet, vSet;
        UnionFindSets partition;
        
        // Create edge array to store MST
        // Initially it has no edges.
        mst = new Edge[V-1];

        // Priority queue for indices of array of edges
        Heap h = new Heap(E, edge);

        // Create partition of singleton sets for the vertices
        partition = new UnionFindSets(V);
        System.out.println("\nKruskal's sets: ");

        // Initial set
        partition.showSets();

        // While size(T) < n-1
        for (i = 0; i < E; i++) 
        { // start for

            // (u,v,wgt) := h.removeMin()
            ei = h.remove();
            uSet = edge[ei].u;
            vSet = edge[ei].v;

            // If uSet and vSet are not in the same set, do the following:
            if (partition.findSet(uSet) != partition.findSet(vSet)) 
            { // Start if
                // Union operation to combine sets
                partition.union(uSet, vSet);

                mst[j++] = edge[ei];
                partition.showSets();
            } // End if

            if (j == V - 1) 
            { // Start if
                break;
            } // End if
        } // End for

        return mst;
    }


    // Convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }

    public void showMST()
    {
        System.out.println("\nMinimum spanning tree build from following edges:\n");
        for(int e = 0; e < V-1; ++e) 
        {
            mst[e].show(); 
        }

        System.out.println();    
    }

} // End of Graph class
        
    // Test code
class KruskalTrees 
{
    public static void main(String[] args) throws IOException
    {
        String fname;

        //String fname = "wGraph1.txt";
        
        try{
        //fname = Console.ReadLine();


        Scanner input = new Scanner(System.in);
        System.out.print("\nInput name of file with graph definition: ");
        fname = input.nextLine();
        
        Graph g = new Graph(fname);

        g.MST_Kruskal();
        g.showMST();
        }
        catch (Exception e) 
        {
            System.out.println("Please enter a valid file name with extension .txt ");
        }
    }

}    


