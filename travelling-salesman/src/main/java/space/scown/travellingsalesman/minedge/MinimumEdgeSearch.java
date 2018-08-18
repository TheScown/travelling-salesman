/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.minedge;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;
import space.scown.travellingsalesman.graph.Node;
import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Path;
import space.scown.travellingsalesman.salesman.Writer;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class MinimumEdgeSearch {

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
		
		Graph<Integer> graph = parser.getGraph();
		HashSet<Arc<Integer>> arcs = graph.getArcs();
		MinGraph<Integer> minGraph = new MinGraph<Integer>(graph.getNodes(), new HashSet<Arc<Integer>>());
		while(arcs.size() != 0) {
			Arc<Integer> min = Collections.min(arcs);
			arcs.remove(min);
			
			Node<Integer> minStart = min.getStart();
			Node<Integer> minEnd = min.getEnd();
			
			arcs.remove(new Arc<Integer>(minEnd,minStart,min.getWeight()));
			
			Iterator<Arc<Integer>> it = arcs.iterator();
			while(it.hasNext()){
				Arc<Integer> a = it.next();
				if(a.getStart().equals(minStart) || a.getEnd().equals(minEnd)) {
					it.remove();
				}
			}
			
			
			if((minGraph.startingAt(minStart).size() == 0) && (minGraph.endingAt(minEnd).size() == 0)) {
				minGraph.addArc(min);
			}
		}
		
		Path path = new Path();
		Node<Integer> startNode = new Node<Integer>(1);
		Arc<Integer> leg = minGraph.getNextLeg(startNode);
		path.add(startNode.getStored(),leg.getWeight());
		
		Node<Integer> pathNode = minGraph.getNextCity(startNode);
		leg = minGraph.getNextLeg(pathNode);
		
		while(!(leg.getStart().equals(startNode))) {
			path.add(pathNode.getStored(), leg.getWeight());
			pathNode = minGraph.getNextCity(pathNode);
			leg = minGraph.getNextLeg(pathNode);
		}
		
		try {
			Writer.writeToFile(parser.getName(), parser.getSize(), path);
		} 
		catch (IOException e) {
			System.out.println("Couldn't write file for some reason.");
		}
	}

}
