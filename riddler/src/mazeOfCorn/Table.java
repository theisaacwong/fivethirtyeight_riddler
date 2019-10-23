package mazeOfCorn;

import java.util.ArrayList;

public class Table {

	public int[][] table;
	public int nrow;
	public int ncol;
	public Node[][] nodeTable;
	
	
	public Table(int[][] n) {
		table = n;
		nrow = n.length;
		ncol = n[0].length;
		nodeTable = new Node[nrow][ncol];
		for(int i = 0; i < nrow; i++) {
			for(int k = 0; k < ncol; k++) {
				nodeTable[i][k] = new Node(i+"_"+k, table[i][k], i, k);
			}
		}
	}
	
	public String toString() {
		String rval = "";
		for(int i = 0; i < nrow; i++) {
			for(int k = 0; k < ncol; k++) {
				rval += table[i][k] + " ";
			}
			rval += "\n";
		}
		return rval;
	}
	
	public ArrayList<Node> getNeighbors(Node n) {
	    ArrayList<Node> neighbors = new ArrayList<Node>();
	    for (int x = -1; x <= 1; x++) {
	        for (int y = -1; y <= 1; y++) {
	            if (x == 0 && y == 0) {
	                continue; // You are not neighbor to yourself
	            }
	            if (Math.abs(x) + Math.abs(y) > 1) {
	                continue;
	            }
	            if (isOnMap(n.x + x*n.number, n.y + y*n.number)) {
	            	neighbors.add(nodeTable[n.x+x*n.number][n.y+y*n.number]);
	            }
	        }
	    }
	    return neighbors;
	}

	public boolean isOnMap(int x, int y) {
	    return x >= 0 && y >= 0 && x < ncol && y < nrow;
	}
	
	
	
	
	
	
	

	
	
	
}
