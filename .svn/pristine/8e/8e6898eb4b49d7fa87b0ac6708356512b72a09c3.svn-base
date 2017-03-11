
/**
 * Takes in a text file of cities and their distances
 * from other cities in a specific format.
 * Creates data structures that finds the shortest path
 * using a binomial queue, adjacency list, and the
 * Djikstra algorithm. Prints out the shortest paths.
 * 
 * @author lancefernando
 *
 */
public class Dijkstra {
	
	public static void main(String[] args){
		Graph g = new Graph(args[0]);
		g.createTables();
		g.printAdjList();
		g.runDjikstra();
		g.printShortestPaths();
	}
}
