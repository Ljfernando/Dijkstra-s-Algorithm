/**
 * Custom node class for the Hash table which
 * contains the key (City), value (vertexNum), and
 * a pointer to the next node if collision occurs.
 * 
 * @author lancefernando
 *
 */
public class HashNode {
	String key;
	int value;
	HashNode next = null;
	
	HashNode(String key, int value, HashNode next) {
		this.key = key;
		this.value = value;
		this.next = next;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public void setNext(HashNode n){
		this.next = n;
	}
	
	public HashNode getNext(){
		return next;
	}

}
