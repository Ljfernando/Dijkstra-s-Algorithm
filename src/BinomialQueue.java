/**
 * This class creates a Binomial Queue where each vertex
 * added to the queue can easily be found at their index
 * of the array vertexList. The queue is used to find the
 * closest unknown vertex of a graph that uses Djikstra's
 * shortest path algorithm. The node with the lowest priority
 * at the top of the binomial queue will is removed and its
 * children merge back into the queue in the correct order by
 * degree.
 * 
 * @author lancefernando
 *
 */
public class BinomialQueue {
	int size;
	QNode head;
	QNode[] vertexList;
	
	BinomialQueue(int size){
		this.size = size;
		vertexList = new QNode[size];
		head = null;
		
	}
	
	/**
	 * Custom node class for each node
	 * in the Binomial Queue. The priority
	 * is the distance and value is the 
	 * vertex.
	 * @author lancefernando
	 *
	 */
	private class QNode{
		private QNode parent;
		private QNode leftChild;
		private QNode rightSib;
		private int priority;
		private int degree;
		private int value;
		private QNode(QNode parent, QNode leftChild, QNode rightSib, int priority, int degree, int value) {
			this.parent = parent;
			this.leftChild = leftChild;
			this.rightSib = rightSib;
			this.priority = priority;
			this.degree = degree;
			this.value = value;
		}
		public QNode getParent() {
			return parent;
		}
		public void setParent(QNode parent) {
			this.parent = parent;
		}
		public QNode getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(QNode leftChild) {
			this.leftChild = leftChild;
		}
		public QNode getRightSib() {
			return rightSib;
		}
		public void setRightSib(QNode rightSib) {
			this.rightSib = rightSib;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public int getDegree() {
			return degree;
		}
		public void setDegree(int degree) {
			this.degree = degree;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return "Value = " + value + ", Priority = " + priority;
		}
		
	}
	
	/**
	 * Checks if binomial queue is empty.
	 * 
	 * @return
	 * True if binomial queue is empty.
	 * False otherwise.
	 */
	public boolean empty() {
		if (head == null)
			return true;
		else
			return false;
	}
	
	/**
	 * Removes the node with the lowest priority by
	 * traversing through the head and its right siblings.
	 * The method first traverses to find the minimum priority value.
	 * It then traverses again to isolate the specific node with
	 * the minimum value, with pointers to the prev node. The min
	 * node is removed from the list while its children, if any, are
	 * then merged back into the binomial queue.
	 * @return
	 */
	public int removeSmallest(){

		if(empty())
			return 0;
		
		else if(head.getRightSib() == null){
			int min = head.getValue();

			head = reverseList(head.getLeftChild());
			
			return min;
		}
		
		else
		{
			
			int min = Integer.MAX_VALUE;

			QNode prev = null;
			QNode curr = head;
			
			//First traversal to find minimum value;
			for(curr = head; curr != null; curr = curr.getRightSib()){

				if(curr.getPriority() < min)
					min = curr.getPriority();
			}

			//Checks if the head has the smallest priority and removes it.
			//Head's children are merged back in and a new head is set.
			if(head.getPriority() == min){
				curr = head;
				head = head.getRightSib();
				head = mergeLists(reverseList(curr.getLeftChild()), head);
				combineQueues(head);
				curr.setLeftChild(null);
				return curr.getValue();
			}
			
			//Second traversal to isolate min node
			for(curr = head; curr.getPriority() != min; curr = curr.getRightSib()){

				prev = curr;
			}

			prev.setRightSib(curr.getRightSib());

			curr.setRightSib(null);

			//Checking if min node has children
			if(curr.getLeftChild() == null){
				return curr.getValue();

			}
			
			//Merging min node's children
			QNode child = reverseList(curr.getLeftChild());
			head = mergeLists(child , head);
			combineQueues(head);

			curr.setLeftChild(null);

			return curr.getValue();
		}

	}
	
	/**
	 * This method decreases the priority of a given
	 * vertex if the new priority is less than the current
	 * priority. The vertexList array is used to easily find
	 * the node given the vertex number. Helper method adjustHeap()
	 * is called to ensure the queue remains in the heap form.
	 * 
	 * @param elem
	 * 		Given vertex to check.
	 * @param new_priority
	 * 		New priority(distance) to update.
	 */
	public void decreaseKey(int elem, int new_priority){
		QNode node = vertexList[elem];
		if(node.getPriority() > new_priority){
			node.setPriority(new_priority);

			adjustHeap(elem);
		}
		
	}
	
	/**
	 * Maintains the binomial queue in heap form by recursively
	 * checking if the node at the given elem vertex should
	 * be moved up in its tree. The node moves up if the parent's
	 * priority is greater. The parent's priority, and value change
	 * while everything else remains the same. The nodes also swich
	 * places within the vertexList array.
	 * 
	 * @param elem
	 * 		Given elem(vertex) to check.
	 */
	public void adjustHeap(int elem){
		QNode curr =vertexList[elem];
		if(curr.getParent() == null){
			return;
		}
		if(curr.getParent().getPriority() > curr.getPriority()){
			QNode parent = curr.getParent();
			int tempPriority = parent.getPriority();
			int tempValue = parent.getValue();
			
			parent.setPriority(curr.getPriority());
			parent.setValue(curr.getValue());
			
			curr.setPriority(tempPriority);
			curr.setValue(tempValue);
			
			
			vertexList[parent.getValue()] = parent;
			vertexList[curr.getValue()] = curr;
			adjustHeap(elem);
		}
	}
	
	/**
	 * Prints the queue in as a preorder traversal.
	 * Calls print() helper function to work recursively.
	 */
	public void printQueue(){

		print(head, 0);

	}
	
	/**
	 * Recursively prints the binomial queue with indentations
	 * to create visual aspect of trees.
	 * 
	 * @param node
	 * 		Given node to look at.
	 * @param indent
	 * 		The amount of indentations printed before the given node.
	 */
	public static void print(QNode node, int indent) {
		if (node != null) {
			for (int i = 0; i < indent; i++)
				System.out.print("     ");
			System.out.println(node);
			print(node.getLeftChild(), indent + 1);
			print(node.getRightSib(), indent);
		}
	}

	
	/**
	 * Takes the head of a given linked and reverses it.
	 * Does this by maintaining prev, curr, and next
	 * pointers.
	 * 
	 * @param node
	 * 		Given node to start at.
	 * @return
	 * 		Head of the reversed list
	 */
	public QNode reverseList(QNode node){
		if(node == null)
			return node;
		QNode curr = node;
		node.setParent(null);
		if(node.getRightSib() != null){
			
			QNode prev = null;
			QNode next = null;
			while(curr != null){
				curr.setParent(null);
				
				next = curr.getRightSib();
				
				curr.setRightSib(prev);
				
				prev = curr;

				curr = next;
				
			}
			return prev;
			
		}
		else 
			return node;
	}
	
	/**
	 * Creates a new QNode and adds it to the Binomial queue.
	 * The pointers are set to null and the degree is 0.
	 * The single node is then merged to the current queue and
	 * the queue is adjusted to accommodate the new insert. VertexList
	 * at the given vertex index is set to the new node.
	 * 
	 * @param elem
	 * 		Vertex of new node
	 * @param priority
	 * 		Priority (distance) of new node.
	 */
	public void insertElem(int elem, int priority){

		QNode newNode = new QNode(null, null, null, priority, 0, elem);
		
		if(head == null)
			head = newNode;
		
		else{
			
			head = mergeLists(newNode, head);
			
			combineQueues(head);
		}
		vertexList[elem] = newNode;
	}

	/**
	 * Takes in two sorted lists and recursively sorts them
	 * by degree. 
	 * 
	 * @param node1
	 * 		First list
	 * @param node2
	 * 		Second list
	 * @return
	 * 		Head of merged list
	 */
	public QNode mergeLists(QNode node1, QNode node2){

		if (node1 == null){
			return node2;
		}
		else if(node2 == null){
			return node1;
		}
		else if(node1.getDegree() <= node2.getDegree()){
			node1.setRightSib(mergeLists(node1.getRightSib(), node2));
			return node1;
		}
		else{
			node2.setRightSib(mergeLists(node1, node2.getRightSib()));
			return node2;
		}
	}

	/**
	 * Traverses through a Binomial queue and merges
	 * the trees with the same degree. It will first check
	 * if only two trees are in the queue. Otherwise, it checks
	 * if 3 trees have the same degree or the two trees that are
	 * being looked at don't have the same degree and moves onto
	 * the next two nodes. When two trees have the same degree,
	 * the one with the lower priority becomes the parents while
	 * the other is the child whose right sib becomes the right sib
	 * of the new parent. The degree of the parent is increased by one.
	 * 
	 * @param node
	 * 		Node to start from.
	 */
	public void combineQueues(QNode node){
		
		QNode prev = null;
		QNode curr = node;
		QNode parent;
		QNode child;
		while(curr.getRightSib() != null){
			
			if(curr.getRightSib().getRightSib() == null){
				if(curr.getDegree() == curr.getRightSib().getDegree()){

					if(curr.getPriority() < curr.getRightSib().getPriority()){
						parent = curr;
						child = curr.getRightSib();
					}
					else{
						parent = curr.getRightSib();
						child = curr;
					}
					if(prev == null)
						head = parent;
					parent.setRightSib(null);
					child.setParent(parent);
					child.setRightSib(parent.getLeftChild());
					parent.setLeftChild(child);
					parent.setDegree(parent.getDegree() + 1);
						
				}
				break;
			}
			if(curr.getDegree() == curr.getRightSib().getRightSib().getDegree() || curr.getDegree() != curr.getRightSib().getDegree()){
				prev = curr;
				curr = curr.getRightSib();
				break;
			}

			if(curr.getDegree() == curr.getRightSib().getDegree()){
				if(curr.getPriority() < curr.getRightSib().getPriority()){
					parent = curr;
					child = curr.getRightSib();

					parent.setRightSib(curr.getRightSib().getRightSib());

				}
				else{
					parent = curr.getRightSib();
					child = curr;
				}
				if(prev == null)
					head = parent;

				child.setRightSib(parent.getLeftChild());
				child.setParent(parent);
				parent.setLeftChild(child);
				parent.setDegree(parent.getDegree() + 1);	
				
				curr = parent;

			}
		}
	}
}
