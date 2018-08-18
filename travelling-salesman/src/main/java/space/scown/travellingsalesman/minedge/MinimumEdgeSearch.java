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

class MinimumEdgeSearch {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Parser parser;
		try{
			parser = new Parser(args[0]);
		}
		catch(final Exception e){
			System.out.println("Some sort of error occurred.  Check the file was formatted correctly");
			return;
		}
		
		final Graph<Integer> graph = parser.getGraph();
		final HashSet<Arc<Integer>> arcs = graph.getArcs();
		final MinGraph<Integer> minGraph = new MinGraph<>(graph.getNodes(), new HashSet<>());
		while(!arcs.isEmpty()) {
			final Arc<Integer> min = Collections.min(arcs);
			arcs.remove(min);
			
			final Node<Integer> minStart = min.getStart();
			final Node<Integer> minEnd = min.getEnd();
			
			arcs.remove(new Arc<>(minEnd, minStart, min.getWeight()));

			arcs.removeIf(a -> a.getStart().equals(minStart) || a.getEnd().equals(minEnd));
			
			
			if((minGraph.startingAt(minStart).isEmpty()) && (minGraph.endingAt(minEnd).isEmpty())) {
				minGraph.addArc(min);
			}
		}
		
		final Path path = new Path();
		final Node<Integer> startNode = new Node<>(1);
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
		catch (final IOException e) {
			System.out.println("Couldn't write file for some reason.");
		}
	}

}
