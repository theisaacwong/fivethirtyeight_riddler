package mazeOfCorn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Isaac Wong
 * October 23, 2019
 * 
 * This is my solution to fivethirtyeight's riddler question:
 * https://fivethirtyeight.com/features/can-you-break-the-riddler-bank/
 * 
 * I use breadth first search to find the shortest path to the goal, then print the traversal
 * 
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class MazeOfCorn {

	public Table table;
	public PriorityQueue<Node> myQueue;
	public Node startNode;
	public HashMap<String, String> visited;
	
	public MazeOfCorn(int n) {
		int seed = (int)(Math.random() * 1000);
		//System.out.println("seed: " + seed);
		Random r = new Random();
		r.setSeed(seed);
		
		int[][] myTable = new int[n][n];
		
		for(int i = 0; i < n; i++) {
			for(int k = 0; k < n; k++) {
				myTable[i][k] = (int)(r.nextDouble() * (n-1) + 1);
			}
		}
		int source_x = (int)(r.nextDouble()*n);
		int source_y = (int)(r.nextDouble()*n);
		int goal_x = (int)(r.nextDouble()*n);
		int goal_y = (int)(r.nextDouble()*n);
		
		while(source_x == goal_x && source_y == goal_y) {
			goal_x = (int)(r.nextDouble()*n);
			goal_y = (int)(r.nextDouble()*n);	
		}
		
		myTable[goal_x][goal_y] = 0;
		
		table = new Table(myTable);
		startNode = table.nodeTable[source_x][source_y];
		myQueue = new PriorityQueue<Node>();
		visited = new HashMap<String, String>();
		for(int i = 0; i < table.nrow; i++) {
			for(int k = 0; k < table.ncol; k++) {
				visited.put(table.nodeTable[i][k].name, "unvisited");
			}
		}
	}
	
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
	
	public static void main(String[] args) throws IOException {
		MazeOfCorn mazeOfCorn = new MazeOfCorn();
		mazeOfCorn.printTable();
		mazeOfCorn.BFS();
		
		mazeOfCorn = new MazeOfCorn(10);
		System.out.println(mazeOfCorn.BFS_quiet());
		
		
		// simulate n random boards and find out how many steps it takes to solve
		// 0 means failure
		ArrayList<Integer> research = new ArrayList<Integer>();
		int n_simulations = 1_000_000;
		int board_size = 100;
		for(int i = 0; i < n_simulations; i++) {
			research.add(new MazeOfCorn(board_size).BFS_quiet());
		}
		String OUTPUT_PATH = "C:/Users/iwong/Documents/temp/corn_numbers.txt";
		BufferedWriter output = null;
		File file = new File(OUTPUT_PATH);
		output = new BufferedWriter(new FileWriter(file));
		output.write("integer");
		for(Integer i : research) {output.write("\n" + i);}
		output.close();
		
		
	}
	
	public int BFS_quiet() {
		Node node = this.startNode;
		node.distanceToMe = 0;
		Queue<Node> frontier = new LinkedList<Node>();
		frontier.add(node);
		List<String> explored = new ArrayList<String>();
		while(true) {
			if(frontier.isEmpty()) {
				return 0;
			}
			node = frontier.poll();
			explored.add(node.name);
			node.visited = true;
			List<Node> neighbors = this.getNeighbors(node);
			for(Node child : neighbors) {
				if(explored.contains(child.name)==false && frontier.contains(child)==false) {
					if(child.number == 0) {
						child.previous = node;
						return getSolutionNSteps(child, 0);
					}
					frontier.add(child);
					child.previous = node;
				}
			}
		}
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
						printSolution(child, 0);
						return;
					}
					frontier.add(child);
					child.previous = node;
				}
			}
		}
	}

	public int getSolutionNSteps(Node n, int c) {
		if(n == null) {
			return c;
		}
		return getSolutionNSteps(n.previous, c+1);
	}
	
	public int printSolution(Node n, int c) {
		if(n == null) {
			System.out.println("n steps: " + c);
			return c;
		}
		n.updateDirection();
		System.out.println(n.toString() + n.direction);
		return printSolution(n.previous, c+1);
	}
	
	
	public void printTable() {
		for(int i = 0; i < this.table.nrow; i++) {
			for(int k = 0; k < this.table.ncol; k++) {
				System.out.print(this.table.table[i][k] + " ");
			}
			System.out.println();
		}
	}
	
	public ArrayList<Node> getNeighbors(Node n){
		return this.table.getNeighbors(n);
	}
	
	public Node get(int i, int k) {
		return this.table.nodeTable[i][k];
	}
	
}
