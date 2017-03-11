For your final project, you will implement Dijksra's algorithm for finding the shortest path between a source vertex and all other vertices in the graph.  This project will combine Graphs, Dijkstra's Algorithm, Binomial Queues, Hash Tables and Lists (whew!).  


##**Nodes with Labels**

For this assignment, each vertex in the graph will be labeled with an arbitrary string. How can we run Dijkstra's algorithm when the vertices are not integers? First, we will assign an integer to each vertex string (the easiest way to do this is to assign the first string we see to 0, the second string we see to 1, etc). We can then store the vertex strings in an array, where the index of the array is the number that we assign to the vertex string stored at that index. For instance, if the datafile contains the vertex names "San Francisco", "Los Angeles",  and "New York", we can assign 0 to San Francisco (storing the string “San Francisco” at index 0 of our array), assign 1 to Los Angles (storing the string “Los Angles” at the index 1 of our array) and we can assign 2 to New York (storing the string “New York” at index 2 of our array).   Now,  given any number n, it is easy to find the appropriate string assigned to that number (by looking at index n in our array).
How can we get an index given a string? One method would be to search through our entire array looking for the appropriate string. A better solution (and the solution that you are required to use for this assignment) is to create a hash table to store the vertex string / vertex number combinations. You can use the vertex string as the key to enter the vertex number into the hash table (so each hash table entry will have a vertex number data value, and a string key value). You can use any hashing strategy that you wish, though you might find that open hashing is slightly easier to implement. Note that you are not allowed to use any built-in hash table functionality in Java, you will need to write the hash table yourself!


##**Implementing the Graph**
One you have created your array of vertex names and your hash table to look up vertex numbers, you are ready to build the graph. Your graph need not contain any information about which vertex number is assigned to which node string – that information can be kept entirely in you node string array and your hash table. You will find the hash table quite useful when creating the graph, however!


##**Dijkstra's Algorithm**
Once you have your graph, you are ready to run Dijkstra's algorithm, using Binomial Queues. Start with the specified initial vertex. Create an instance of the Dijksra table. All "cost" entries in the Dijkstra table (other than the intial vertex) should be infinity (you can use Integer.MAX_VALUE) and all "path" entries should be -1. Add all of the vertices to the BinomialQueue (with priority of Integer.MAX_VALUE, except for the initial vertex, with priority 0). While the priority queue is not empty, you will need to remove the element with the smallest cost, then update the costs of all of its neighbors – also updating the costs in the priority queue! Thus your priority queue will need some way of finding elements in the queue quickly. To do this, you should maintain an array of pointers into your Binomial Queue.   Once you have created the table, you will need to print out the cost of the shortest path from the intial vertex to all other vertices, as well as the path itself.  See the section on output format to get the formatting correct.

So, while your algorithm is running, you will have the following data structures:
	•	Graph (Adjacency List)
	•	List used to associate vertex numbers with vertex names
	•	Hash table used to associate vertex names with vertex numbers
	•	Dijkstra Table
	•	Binomial Queue (including an array of pointers into the queue for easy updating)

Running The Program
Simply run in command line with Test.txt as an argument

