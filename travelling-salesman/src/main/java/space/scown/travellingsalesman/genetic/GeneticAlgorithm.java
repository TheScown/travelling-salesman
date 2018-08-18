/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.genetic;

import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//import java.util.Iterator;

public class GeneticAlgorithm {
	
	private static final int POPULATION_SIZE = 10;
	private static final double MUTATION_CHANCE = 0.1;
	private static final int GENERATIONS = 10;

	public static void main(final String[] args) throws IOException {
		final Parser parser = new Parser(args[0]);

		List<GeneticPath> population = new ArrayList<>();
		for(int i = 0;i<POPULATION_SIZE;i++){
			population.add(GeneticPath.generateRandomPath(parser));
		}
		GeneticPath finalPath = null;
		for(int g = 0;g<GENERATIONS;g++){
			final List<GeneticPath> newPopulation = new ArrayList<>();
			for(int p = 0; p<POPULATION_SIZE;p++){
				final GeneticPath p1 = getRandomMember(population);
				final GeneticPath p2 = getRandomMember(population);
				/*GeneticPath p1 = Collections.min(population);
				population.remove(p1);
				GeneticPath p2 = Collections.min(population);
				population.add(p1);*/
				GeneticPath child = p1.breed(p2);
				if(Math.random()<MUTATION_CHANCE){
					final Random random = new Random();
					final int i = random.nextInt(child.size()-1);
					final int j = random.nextInt(child.size()-1);
					child = child.swap(i, j);
				}
				newPopulation.add(child);
			}
			assert(newPopulation.size() == population.size());
			population = newPopulation;
			finalPath = Collections.min(population);
		}

        Writer.writeToFile(parser.getName(),parser.getSize(),finalPath);
	}
	
	private static GeneticPath getRandomMember(final List<GeneticPath> population){
		final Random random = new Random();
		GeneticPath path = null;
		Collections.sort(population);
		/*int iterations = random.nextInt(population.size()-1)+1;
		Iterator<GeneticPath> it = population.iterator();
		for(int i = 0;i<iterations;i++){
			space.scown.travellingsalesman.path = it.next();
		}*/
		final int iSquared = (population.size())*(population.size());
		final int randomIndex = random.nextInt(iSquared)+1;
		int i = 0;
		while(path==null){
			final int bah = (population.size()-i-1);
			final int index = (bah*bah);
			if(index<randomIndex){
				path = population.get(i);
			}
			i++;
		}
		return path;
	}

}
