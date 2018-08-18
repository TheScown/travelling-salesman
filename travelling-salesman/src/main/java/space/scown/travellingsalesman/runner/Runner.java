/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.runner;

import space.scown.travellingsalesman.annealing.SimulatedAnnealing;
import space.scown.travellingsalesman.bestfirst.GreedyBestFirstSearch;
import space.scown.travellingsalesman.genetic.GeneticAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class Runner {
	
	//public static final String prefix = "Batch 1/";

	private static final String[] files =
		{"file12",
		"file17",
		"file21",
		"file26",
		"file42",
		"file48",
		"file58",
		"file175",
		"file180",
		"file535"};

	public static void main(final String[] args) throws IOException{
		final BufferedWriter writer = new BufferedWriter(new FileWriter("times.txt"));
		for(final String filename : files){
			writer.write(filename);
			writer.newLine();
			
			final String[] string = new String[1];
			string[0] = filename.concat("a.txt");
			final long greedyStartTime = System.currentTimeMillis();
			GreedyBestFirstSearch.main(string);
			final long greedyEndTime = System.currentTimeMillis();
			writer.write("Greedy: "+((greedyEndTime - greedyStartTime)/1000));
			writer.newLine();
			
			
			string[0] = filename.concat("b.txt");
			final long annealingStartTime = System.currentTimeMillis();
			SimulatedAnnealing.main(string);
			final long annealingEndTime = System.currentTimeMillis();
			writer.write("Annealing: "+((annealingEndTime - annealingStartTime)/1000));
			writer.newLine();
			
			string[0] = filename.concat("c.txt");
			final long geneticStartTime = System.currentTimeMillis();
			GeneticAlgorithm.main(string);
			final long geneticEndTime = System.currentTimeMillis();
			writer.write("Genetic: "+((geneticEndTime - geneticStartTime)/1000));
			writer.newLine();
			
			writer.newLine();
		}
		writer.close();

	}

}
