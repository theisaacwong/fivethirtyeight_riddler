package riddler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//https://fivethirtyeight.com/features/can-you-break-the-riddler-bank/

import java.util.PriorityQueue;
import java.util.Queue;

public class MazeOfCorn {

	public Table table;
	public PriorityQueue<Node> myQueue;
	public Node startNode;
	public HashMap<String, String> visited;
	
	public MazeOfCorn() {
		
		int[][] myTable = {
				{6, 2, 1, 3, 6, 1, 7, 7, 4, 3},
				{2, 3, 4, 5, 7, 8, 1, 5, 2, 3},
				{1, 6, 1, 2, 5, 1, 6, 3, 6, 2},
				{5, 3, 5, 5, 1, 6, 7, 3, 7, 3},
				{1, 2, 6, 4, 1, 3, 3, 5, 5, 5}, 
				{2, 4, 6, 6, 6, 2, 1, 3, 8, 8}, 
				{2, 4, 0, 2, 3, 6, 5, 2, 4, 6}, 
				{3, 1, 7, 6, 2, 3, 1, 5, 7, 7}, 
				{6, 1, 3, 6, 4, 5, 4, 2, 2, 7},
				{6, 7, 5, 7, 6, 2, 4, 1, 9, 1}};
		
		table = new Table(myTable);
		startNode = table.nodeTable[9][0];
		myQueue = new PriorityQueue<Node>();
		visited = new HashMap<String, String>();
		for(int i = 0; i < table.nrow; i++) {
			for(int k = 0; k < table.ncol; k++) {
				visited.put(table.nodeTable[i][k].name, "unvisited");
			}
		}
	}
	
	public static void main(String[] args) {
		MazeOfCorn mazeOfCorn = new MazeOfCorn();
		System.out.println(mazeOfCorn.table.toString());
		
		for(int i = 0; i < mazeOfCorn.table.nrow; i++) {
			for(int k = 0; k < mazeOfCorn.table.ncol; k++) {
				mazeOfCorn.myQueue.add(mazeOfCorn.table.nodeTable[i][k]);
			}
		}
		
		while(mazeOfCorn.myQueue.size() > 0) {
			System.out.println(mazeOfCorn.myQueue.poll().toString());
		}
		
		System.out.println("neighbors");
		ArrayList<Node> neighbors = mazeOfCorn.table.getNeighbors(mazeOfCorn.table.nodeTable[9][0]);
		System.out.println(neighbors.size());
		for(Node node: neighbors) {
			System.out.println(node.toString());
		}
		
		mazeOfCorn.BFS();
		
	}
	
	
	
	public void BFS() {
		Node node = this.startNode;
		node.distanceToMe = 0;
		Queue<Node> frontier = new LinkedList<Node>();
		frontier.add(node);
		List<String> explored = new ArrayList<String>();
		while(true) {
			if(frontier.isEmpty()) {
				System.out.println("failure");
				return;
			}
			node = frontier.poll();
			explored.add(node.name);
			node.visited = true;
			List<Node> neighbors = this.getNeighbors(node);
			for(Node child : neighbors) {
				if(explored.contains(child.name)==false && frontier.contains(child)==false) {
					if(child.number == 0) {
						System.out.println("solution");
						child.previous = node;
						printSolution(child);
						return;
					}
					frontier.add(child);
					child.previous = node;
				}
			}
		}
	}
	
	public void printSolution(Node n) {
		if(n == null) {
			return;
		}
		n.updateDirection();
		System.out.println(n.toString() + n.direction);
		printSolution(n.previous);
	}
	
	
	
	
	public ArrayList<Node> getNeighbors(Node n){
		return this.table.getNeighbors(n);
	}
	
	public Node get(int i, int k) {
		return this.table.nodeTable[i][k];
	}
	
}
