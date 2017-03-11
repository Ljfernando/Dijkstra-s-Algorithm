import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class containing data structures needed to store values
 * from a file and run Dijkstra's algorithm to find the
 * shortest paths. 
 * 
 * Adjacency list adjList stores AdjNodes where the index
 * is the starting vertex and each neighbor is a connecting city.
 * 
 * Hash Table HT allows access to the vertex number given the
 * city String or the other way around. 
 * 
 * BinomialQueue BQ allows access to the smallest unknown vertex
 * 
 * File is the given file to read.
 * 
 * 2D Array djikstra contains the shortest distances and path from
 * the starting vertex to each.
 * 
 * @author lancefernando
 *
 */
public class Graph {
	
	private AdjNode[] adjList;
	private HashTable HT;
	private BinomialQueue BQ;
	private String file;
	
	private int[][] djikstra;
	
	Graph(String file){
		this.file = file;
		HT = new HashTable();
	}
	private class AdjNode{
		int vertex;
		int distance;
		AdjNode next;
		
		AdjNode(int vertex, int distance, AdjNode next){
			this.vertex = vertex;
			this.distance = distance;
			this.next = next;
		
		}

		public int getVertex() {
			return vertex;
		}

		public void setVertex(int vertex) {
			this.vertex = vertex;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public AdjNode getNext() {
			return next;
		}

		public void setNext(AdjNode next) {
			this.next = next;
		}
		
		@Override
		public String toString(){
			return "Vertex=" + vertex + " Distance=" + distance;
		}
		
		
	}
	
	/**
	 * Takes two city strings, accesses their vertex numbers using
	 * the hashtable, and creates an AdjNode at the starting
	 * vertex to the ending vertex with the given distance.
	 * 
	 * @param from
	 * 		Starting city
	 * @param to
	 * 		Ending city
	 * @param distance
	 * 		Distance between two cities
	 */
	public void addToAdjList(String from, String to, int distance){
		int start = HT.find(from);
		int end = HT.find(to);
		
		adjList[start] = new AdjNode(end, distance, adjList[start]);
		
	}
	
	/**
	 * Initializes the djikstra array given the
	 * size. Each index of the array is first initialized
	 * with the cost as INFINITY and the path as -1. The 
	 * first index however begins with a cost of 0.
	 * 
	 * @param size
	 * 		Amount of vertices in graph.
	 */
	public void createDjikstra(int size){
		djikstra = new int[size][2];
		djikstra[0][0] = 0;
		djikstra[0][1] = -1;
		
		for(int i = 1; i < size; i++){
			djikstra[i][0] = Integer.MAX_VALUE;
			djikstra[i][1] = -1;
		}
	}
	
	/**
	 * Reads the given file two count the amount of cities (vertices) there are. 
	 * The end of cities is marked with a "." in the file. 
	 * The AdjacencyList and BinomialQueue are initialized given the size and
	 * the Djikstra table is made. 
	 * 
	 * All nodes in BQ have vertex value with cost of INFINITY
	 * besides the 0 vertex with cost of 0.
	 * 
	 * The file is read from the beginning again to hash
	 * each city into the hash table and assign them a vertex number. 
	 * 
	 * The rest of the file containing connected cities and their paths
	 * is read. This information is added to the adjacency list .
	 */
	public void createTables(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String city;
			int vertex = 0;
			
			while(!(city = br.readLine()).equals(".")){
				vertex++;
			}
			
			br.close();
			BQ = new BinomialQueue(vertex);
			adjList = new AdjNode[vertex];
			createDjikstra(vertex);
			BQ.insertElem(0, 0);
			for(int i = 1; i < vertex; i ++)
				BQ.insertElem(i, Integer.MAX_VALUE);
			

			
			br = new BufferedReader(new FileReader(file));
			vertex = 0;
			while(!(city = br.readLine()).equals(".")){
				
				HT.insert(vertex++, city);
				
			}
			String start;
			while((start = br.readLine()) != null){
				String end = br.readLine();
				Integer cost = Integer.parseInt(br.readLine());
				addToAdjList(start, end, cost);
			}
			br.close();


			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file while creating tables");
		} catch (IOException e) {
			System.out.println("Input/Output error found while creating tables");
		}
		
	}
	
	/**
	 * Prints the adjacency list by using the HashTable to
	 * access the city names given their vertex.
	 * 
	 * Printed in format " 'Starting City' : 'Neighbor City' 'Distance' ,
	 */
	public void printAdjList(){
		System.out.println("Original Graph");
		for(int i = 0; i < adjList.length; i ++){
			System.out.print(HT.getCity(i) + ": " );
			
			for(AdjNode m = adjList[i]; m != null; m = m.getNext()){
				if (m.getNext() == null)
					System.out.print(HT.getCity(m.getVertex()) + " " + m.getDistance());
				else
					System.out.print(HT.getCity(m.getVertex()) + " " + m.getDistance() +  ", ");
			}
			System.out.println();
		}
			
	}
	
	/**
	 * Runs the dijkstra algorithm. While the BQ is not empty,
	 * it will the smallest unknown vertex and update the distances
	 * of each neighbor at the vertex of the adjacency list.
	 * 
	 */
	public void runDjikstra(){
		while(!BQ.empty()){
			int smallest = BQ.removeSmallest();
			for(AdjNode edge = adjList[smallest]; edge != null; edge = edge.getNext()){
				if(djikstra[edge.getVertex()][0] > edge.getDistance() + djikstra[smallest][0]){
					BQ.decreaseKey(edge.getVertex(), edge.getDistance() + djikstra[smallest][0]);
					djikstra[edge.getVertex()][0] = edge.getDistance() + djikstra[smallest][0];
					djikstra[edge.getVertex()][1] = smallest;
				}
			}
		}
	}

	/**
	 * Prints the shortest path using the djikstra table and
	 * helper method PrintPath().
	 * Printed in format " 'Ending City' 'Distance' : 'Path to starting city'

	 */
	public void printShortestPaths(){
		System.out.println("\nShortests Paths \n");

		for(int i = 0; i < djikstra.length; i++){
			System.out.print(HT.getCity(i) + " " + djikstra[i][0] + ": ");
			PrintPath(i);
		}
		
	}

	/**
	 * Prints the path of the given vertex recursively by called
	 * its helper method PrintPathHelper;
	 * @param endvertex
	 */
    private void PrintPath(int endvertex) 
    {

    	PrintPathHelper(endvertex);
    	System.out.println();

    }
    
    /**
     * Prints the path until it reaches the starting point.
     * @param endvertex
     * 		Given vertex of ending city
     */
    private void PrintPathHelper(int endvertex){
    	
    	//Base case when it reaches the starting vertex
    	if(djikstra[endvertex][1] == -1)
    		
    		System.out.print(HT.getCity(endvertex) + " ");
    	
    	else{
    		
    		PrintPathHelper(djikstra[endvertex][1]);
    		System.out.print(HT.getCity(endvertex) + " ");
    	}
    }
	
	


}
