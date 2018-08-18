package space.scown.travellingsalesman.genetic;

import salesman.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//import java.util.Iterator;

public class GeneticAlgorithm {
	
	private static final int POPULATION_SIZE = 10;
	private static final double MUTATION_CHANCE = 0.1;
	private static final int GENERATIONS = 10;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Parser parser = null;
		try{
			parser = new Parser(args[0]);
		}
		catch(Exception e){
			System.out.println("Some sort of error occurred.  Check the file was formatted correctly");
			return;
		}
		ArrayList<GeneticPath> population = new ArrayList<GeneticPath>();
		for(int i = 0;i<POPULATION_SIZE;i++){
			population.add(GeneticPath.generateRandomPath(parser));
		}
		GeneticPath finalPath = null;
		for(int g = 0;g<GENERATIONS;g++){
			ArrayList<GeneticPath> newPopulation = new ArrayList<GeneticPath>();
			for(int p = 0; p<POPULATION_SIZE;p++){
				GeneticPath p1 = getRandomMember(population);
				GeneticPath p2 = getRandomMember(population);
				/*GeneticPath p1 = Collections.min(population);
				population.remove(p1);
				GeneticPath p2 = Collections.min(population);
				population.add(p1);*/
				GeneticPath child = p1.breed(p2);
				if(Math.random()<MUTATION_CHANCE){
					Random random = new Random();
					int i = random.nextInt(child.size()-1);
					int j = random.nextInt(child.size()-1);
					child = child.swap(i, j);
				}
				newPopulation.add(child);
			}
			assert(newPopulation.size() == population.size());
			population = newPopulation;
			finalPath = Collections.min(population);
		}
		try{
			Writer.writeToFile(parser.getName(),parser.getSize(),finalPath);
		}
		catch(Exception e){
			System.out.println("An error occurred writing the file.");
		}
	}
	
	private static GeneticPath getRandomMember(ArrayList<GeneticPath> population){
		Random random = new Random();
		GeneticPath path = null;
		Collections.sort(population);
		/*int iterations = random.nextInt(population.size()-1)+1;
		Iterator<GeneticPath> it = population.iterator();
		for(int i = 0;i<iterations;i++){
			space.scown.travellingsalesman.path = it.next();
		}*/
		int iSquared = (population.size())*(population.size());
		int randomIndex = random.nextInt(iSquared)+1;
		int i = 0;
		while(path==null){
			int bah = (population.size()-i-1);
			int index = (bah*bah);
			if(index<randomIndex){
				path = population.get(i);
			}
			i++;
		}
		return path;
	}

}
