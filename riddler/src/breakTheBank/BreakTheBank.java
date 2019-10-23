package breakTheBank;


/**
 * Isaac Wong
 * October 23, 2019
 * 
 * This is my solution to fivethirtyeight's riddler question:
 * https://fivethirtyeight.com/features/can-you-break-the-riddler-bank/
 * 
 * This is a brute force approach which finds all combinations of the coin values up to a certain number
 * It works by filling a hashset with the values of the coins and multiples of the coins
 * It then adds the value of each pairwise sum in the set
 * Iterates until some max iteration value is met
 * 
 * At each iteration, the set is written to a file
 * I then find which numbers aren't in the set with the following R code
 * 
 * breakable_numbers <- read.table("C:/Users/iwong/Documents/temp/fun_numbers.txt", sep="\t", header=TRUE, stringsAsFactors=FALSE)
 * all_numbers <- 1:(nrow(breakable_numbers))
 * unbreakable_numbers <- all_numbers[!all_numbers %in% breakable_numbers$integer]
 * max(unbreakable_numbers)
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class BreakTheBank {

	public static void main(String[] args) throws IOException {
		String s = "C:/Users/iwong/Documents/temp/fun_numbers.txt";
		//foo(s);
		foo_extra(s);
	}
	
	
	public static void foo_extra(String OUTPUT_PATH) throws IOException {
		HashSet<Integer> tokes = new HashSet<>();
		
		//initial seeds
		int small = 1000;
		int big = 100;
		int medium = 500;
		
		//maximum search space
		int max_num = 10_000_000;
		
		//search iterations
		int max_iters = 10_000;
		
		//initial seeding
		System.out.println("seeding");
		for(int i = 0; i < small; i++) {tokes.add(i*19); }
		for(int i = 0; i < medium; i++) {tokes.add(i*101); }
		for(int i = 0; i < big; i++) {tokes.add(i*538); }
		
		//search space expansion
		System.out.println("searching");	// O(n^3)				//this 			cumulative
		for(int i = 0; i < max_iters; i++) { 						//O(m)			O(m)
			ArrayList<Integer> tokes_set = new ArrayList<>(tokes);	//O(a)			O(m a)
			Collections.sort(tokes_set);							//O(n log n)	O(m n log n)
			for(int n = 0; n < tokes_set.size(); n++){				//O(t)			O(						
				for(int m = 0; m < tokes_set.size()/2; m++) {		//O(log n)
					int temp = tokes_set.get(n) + tokes_set.get(m);	//O(1)
					if(temp < max_num) {							//O(1)
						tokes.add(temp);							//O(1)
					} else {
						break;
					}
				}
				int temp = tokes_set.get(n) + tokes_set.get(0);
				if(temp > max_num) {
					break;
				} 
			}
			System.out.println(tokes.size());
			//write tokens
			ArrayList<Integer> rval = new ArrayList<>(tokes);
			Collections.sort(rval);
			BufferedWriter output = null;
			File file = new File(OUTPUT_PATH);
			output = new BufferedWriter(new FileWriter(file));
			output.write("integer");
			for(Integer ii : rval) {output.write("\n" + ii);}
			output.close();
		}
		
		ArrayList<Integer> rval = new ArrayList<>(tokes);
		Collections.sort(rval);
		BufferedWriter output = null;
		File file = new File(OUTPUT_PATH);
		output = new BufferedWriter(new FileWriter(file));
		output.write("integer");
		for(Integer i : rval) {output.write("\n" + i);}
		output.close();
	}
	
	public static void foo(String OUTPUT_PATH) throws IOException {
		HashSet<Integer> tokes = new HashSet<>();
		
		//initial seeds
		int small = 1000;
		int big = 100;
		
		//maximum search space
		int max_num = 10_000_000;
		
		//search iterations
		int max_iters = 10_000;
		
		//initial seeding
		System.out.println("seeding");
		for(int i = 0; i < small; i++) {tokes.add(i*19); }
		for(int i = 0; i < big; i++) {tokes.add(i*538); }
		
		//search space expansion
		System.out.println("searching");	// O(n^3)				//this 			cumulative
		for(int i = 0; i < max_iters; i++) { 						//O(m)			O(m)
			ArrayList<Integer> tokes_set = new ArrayList<>(tokes);	//O(a)			O(m a)
			Collections.sort(tokes_set);							//O(n log n)	O(m n log n)
			for(int n = 0; n < tokes_set.size(); n++){				//O(t)			O(						
				for(int m = 0; m < tokes_set.size()/2; m++) {		//O(log n)
					int temp = tokes_set.get(n) + tokes_set.get(m);	//O(1)
					if(temp < max_num) {							//O(1)
						tokes.add(temp);							//O(1)
					} else {
						break;
					}
				}
				int temp = tokes_set.get(n) + tokes_set.get(0);
				if(temp > max_num) {
					break;
				} 
			}
			System.out.println(tokes.size());
			//write tokens
			ArrayList<Integer> rval = new ArrayList<>(tokes);
			Collections.sort(rval);
			BufferedWriter output = null;
			File file = new File(OUTPUT_PATH);
			output = new BufferedWriter(new FileWriter(file));
			output.write("integer");
			for(Integer ii : rval) {output.write("\n" + ii);}
			output.close();
		}
		
		ArrayList<Integer> rval = new ArrayList<>(tokes);
		Collections.sort(rval);
		BufferedWriter output = null;
		File file = new File(OUTPUT_PATH);
		output = new BufferedWriter(new FileWriter(file));
		output.write("integer");
		for(Integer i : rval) {output.write("\n" + i);}
		output.close();
	}
	
	
	
}
