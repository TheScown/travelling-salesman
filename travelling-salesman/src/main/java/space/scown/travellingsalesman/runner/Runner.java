/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.runner;

import space.scown.travellingsalesman.genetic.GeneticAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Runner {
	
	//public static final String prefix = "Batch 1/";

	public static final String[] files = 
		{"file12",
		"file17",
		"file21",
		"file26",
		"file42",
		"file48",
		"file58",
		"file175",
		"file180"/*,
		"file535"*/};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("times.txt"));
		for(String filename : files){
			writer.write(filename);
			writer.newLine();
			
			String[] string = new String[1];
			/*string[0] = filename.concat("a.txt");
			long greedyStartTime = System.currentTimeMillis();
			GreedyBestFirstSearch.main(string);
			long greedyEndTime = System.currentTimeMillis();
			writer.write("Greedy: "+((greedyEndTime - greedyStartTime)/1000));
			writer.newLine();*/
			
			
			/*string[0] = filename.concat("b.txt");
			long annealingStartTime = System.currentTimeMillis();
			SimulatedAnnealing.main(string);
			long annealingEndTime = System.currentTimeMillis();
			writer.write("Annealing: "+((annealingEndTime - annealingStartTime)/1000));
			writer.newLine();*/
			
			string[0] = filename.concat("c.txt");
			long geneticStartTime = System.currentTimeMillis();
			GeneticAlgorithm.main(string);
			long geneticEndTime = System.currentTimeMillis();
			writer.write("Genetic: "+((geneticEndTime - geneticStartTime)/1000));
			writer.newLine();
			
			writer.newLine();
		}
		writer.close();

	}

}
