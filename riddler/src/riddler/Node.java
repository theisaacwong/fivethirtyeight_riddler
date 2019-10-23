package riddler;

public class Node implements Comparable<Node> {

	public String name;
	public int number;
	public Node nextNode;
	public String direction;
	public int distanceToMe;
	public boolean visited;
	public int x;
	public int y;
	public Node previous;
	
	public Node(String s, int n, int i, int k) {
		this.visited = false;
		this.name = s;
		this.distanceToMe = Integer.MAX_VALUE;
		this.distanceToMe = (int)(Math.random() * 100);
		this.x = i; 
		this.y = k;
		this.number = n;
	}

	@Override
	public int compareTo(Node n) {
		if(this.distanceToMe < n.distanceToMe) {
			return -1;
		} else if(this.distanceToMe > n.distanceToMe) {
			return 1;
		} else {
			return 0;
		}
	}

	public String toString() {
		String rval = "";
		rval += "name: " + name + "\t";
		rval += "number: " + number + "\t";
		//if(previous != null)
		//rval += "\t\tprevious: " + previous.name;
		return rval;
	}
	
	public void updateDirection() {
		if(this.previous == null) {
			return;
		}
		if(this.previous.x < this.x) {
			this.direction = "down";
		} else if(this.previous.x > this.x) {
			this.direction = "up"; 
		} else if(this.previous.y > this.y) {
			this.direction = "left";
		} else {
			this.direction = "right"; 
		}
		
	}
	
	
	
	
}
