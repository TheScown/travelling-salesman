/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.annealing;

import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Writer;

import java.io.IOException;
import java.util.Random;

//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Random;
//import java.util.Iterator;
//import java.util.Collections;

class SimulatedAnnealing {

	/**
	 * A simulated space.scown.travellingsalesman.annealing algorithm
	 * 
	 * @param args Input filename
	 */
	public static void main(String[] args) {
		Parser parser;
		try{
			parser = new Parser(args[0]);
		}
		catch(Exception e){
			System.out.println("Some sort of error occurred.  Check the file was formatted correctly");
			return;
		}
		int[] temperatures = new int[200];
		for(int i = 0;i<200;i++){
			temperatures[i] = 199-20*i;
			if(i>9){
				temperatures[i] = 1;
			}
			if(i==199){
				temperatures[i]=0;
			}
		}
		AnnealingPath current = AnnealingPath.generateRandomPath(parser);
		for(int t = 0;t<200;t++){
			if(temperatures[t] == 0){
				try {
					Writer.writeToFile(parser.getName(), parser.getSize(), current);
					return;
				} catch (IOException e) {
					System.out.println("Error writing file.");
				}
			}
			else{
				Random numberGenerator = new Random();
				//HashSet<AnnealingPath> successors = current.successors();
				int successors = (parser.getSize())*parser.getSize();
				int randomSuccessor = numberGenerator.nextInt(successors);
				int i = (randomSuccessor-randomSuccessor%parser.getSize())/parser.getSize();
				int j = randomSuccessor%parser.getSize();
				AnnealingPath newPath = current.swap(i,j);
				/*int randomSuccessor = numberGenerator.nextInt(successors.size()-1) + 1;
				//AnnealingPath newPath = Collections.min(successors);
				/AnnealingPath newPath = null;
				Iterator<AnnealingPath> it = successors.iterator();
				for(int i = 0;i<randomSuccessor;i++){
					newPath = it.next();
				}*/
				int deltaE = current.getLength() - newPath.getLength();
				//System.out.println(deltaE);
				int temp = temperatures[t];
				double d = (double) Math.abs(deltaE)/(double)temp;
				//System.out.println(d);
				if(deltaE > 0){
					current = newPath;
				}
				else if(Math.random()<Math.exp(-d)){
					//System.out.println(Math.exp(-d));
					current = newPath;
				}
			}
		}
		
	}

	

	

}
