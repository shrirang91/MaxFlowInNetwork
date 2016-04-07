package v2;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MaxFlow
{
    private int[] base;
    private Queue<Integer> Q;
    private int Vertex;
    private boolean[] traversed;
    private Set<Pair> minCut;
    private ArrayList<Integer> accessible;
    private ArrayList<Integer> inaccessible;
 
    public MaxFlow (int Vertex)
    {
        this.Vertex = Vertex;
        this.Q = new LinkedList<Integer>();
        base = new int[Vertex + 1];
        traversed = new boolean[Vertex + 1];
        minCut = new HashSet<Pair>();
        accessible = new ArrayList<Integer>();
        inaccessible = new ArrayList<Integer>();
    }
 
    public boolean flow (int s, int goal, int graph[][])
    {
        boolean augumentedPath = false;
        int d, element;
        for (int vertex = 1; vertex <= Vertex; vertex++)
        {
            base[vertex] = -1;
            traversed[vertex] = false;
        }
        Q.add(s);
        base[s] = -1;
        traversed[s] = true;
 
        while (!Q.isEmpty())
        {
            element = Q.remove();
            d = 1;
            while (d <= Vertex)
            {
                if (graph[element][d] > 0 &&  !traversed[d])
                {
                    base[d] = element;
                    Q.add(d);
                    traversed[d] = true;
                }
                d++;
            }
        }
 
        if (traversed[goal])
        {
            augumentedPath = true;
        }
        return augumentedPath;
    }
 
    public void displayMinCut (){
        Iterator<Pair> iterator = minCut.iterator();
        while (iterator.hasNext())
        {
            Pair pair = iterator.next();
            System.out.println(pair.s + "-" + pair.d);
        }
    }
    
    int  maxFlowFunction (int graph[][], int source, int d)
    {
        int u, v;
        int maxFlow = 0;
        int pathFlow;
        int[][] backtracked = new int[Vertex + 1][Vertex + 1];
 
        for (int sourceVertex = 1; sourceVertex <= Vertex; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= Vertex; destinationVertex++)
            {
                backtracked[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }
        }
 
        //maximum flow ??
        while (flow(source, d, backtracked))
        {
            pathFlow = Integer.MAX_VALUE;
            for (v = d; v != source; v = base[v])
            {
                u = base[v];
                pathFlow = Math.min(pathFlow,backtracked[u][v]);
            }
            for (v = d; v != source; v = base[v])
            {
                u = base[v];
                backtracked[u][v] -= pathFlow;
                backtracked[v][u] += pathFlow;
            }
            maxFlow += pathFlow;	
        }
 
        //calculate the cut 		
        for (int vertex = 1; vertex <= Vertex; vertex++)
        {
            if (flow(source, vertex, backtracked))
            {
                accessible.add(vertex);
            }
            else
            {
                inaccessible.add(vertex);
            }
        }
        for (int i = 0; i < accessible.size(); i++)
        {
            for (int j = 0; j < inaccessible.size(); j++)
            {
                if (graph[accessible.get(i)][inaccessible.get(j)] > 0)
                {
                    minCut.add(new Pair(accessible.get(i), inaccessible.get(j)));
                }
            }
        }
        return maxFlow;
    }
 
    public static void main (String[] args) throws Exception{
        int[][] graph;
        int vertex;
        int source;
        int sink;
        int maxFlow;
 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of nodes");
        vertex = scanner.nextInt();
        graph = new int[vertex + 1][vertex + 1];
 
        System.out.println("Enter the graph matrix");
        for (int sourceVertex = 1; sourceVertex <= vertex; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= vertex ; destinationVertex++)
            {
                graph[sourceVertex][destinationVertex] = scanner.nextInt();
            }
        }
        System.out.println("Enter the source of the graph");
        source= scanner.nextInt();
 
        System.out.println("Enter the sink of the graph");
        sink = scanner.nextInt();
 
        MaxFlow max = new MaxFlow(vertex);
        maxFlow = max.maxFlowFunction(graph, source, sink);
 
        System.out.println("The Max Flow is " + maxFlow);
        System.out.println("The Cut Set is ");
        max.displayMinCut();
        scanner.close();
    }
}
 
