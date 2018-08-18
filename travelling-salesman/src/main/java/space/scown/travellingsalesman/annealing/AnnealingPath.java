/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.annealing;

import space.scown.travellingsalesman.graph.Node;
import space.scown.travellingsalesman.salesman.Parser;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class AnnealingPath extends space.scown.travellingsalesman.salesman.Path {

	private final Parser parser;
	
	private AnnealingPath(final Parser parser){
		super();
		this.parser = parser;
	}
	
	private AnnealingPath(final AnnealingPath path){
		super();
		this.parser = path.getParser();
		this.path = new ArrayList<>(path.getPath());
		this.length = path.getLength();
	}

	private Parser getParser(){
		return parser;
	}
	
	public AnnealingPath swap(final int i, final int j){
		final AnnealingPath newPath = new AnnealingPath(this);
		final ArrayList<Integer> p = new ArrayList<>(newPath.getPath());
		final int a = p.get(i);
		p.set(i,p.get(j));
		p.set(j,a);
		if(!p.get(0).equals(p.get(p.size() - 1))){
			p.set(size()-1,p.get(0));
		}
		newPath.path = p;
		newPath.length = newPath.recomputeLength();
		return newPath;
	}
	
	private int recomputeLength(){
		int newLength = 0;
		for(int i = 0;i<size()-1;i++){
			final Node<Integer> start = new Node<>(path.get(i));
			final Node<Integer> end = new Node<>(path.get(i + 1));
			newLength = parser.getGraph().distance(start, end);
		}
		return newLength;
	}
	
	public static AnnealingPath generateRandomPath(final Parser parser) {
		final AnnealingPath path = new AnnealingPath(parser);
		final Set<Node<Integer>> nodes = parser.getGraph().getNodes();
		final int size = nodes.size();
		final Random randomGen = new Random();
		final int start = randomGen.nextInt(size) + 1;
		path.add(start,0);
		while(path.size() < size){
			final int next = randomGen.nextInt(size) + 1;
			if(!path.contains(next)){
				final Node<Integer> n1 = new Node<>(path.getLast());
				final Node<Integer> n2 = new Node<>(next);
				final int distance = parser.getGraph().distance(n1, n2);
				path.add(next,distance);
			}
		}
		final Node<Integer> n1 = new Node<>(start);
		final Node<Integer> n2 = new Node<>(path.getLast());
		path.add(start, parser.getGraph().distance(n1,n2));
		return path;
	}
}
