/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.graph;
import java.util.HashSet;

public class Graph<T> {

	private final HashSet<Node<T>> nodes;
	protected final HashSet<Arc<T>> arcs;

	public Graph(final HashSet<Node<T>> nodes, final HashSet<Arc<T>> arcs){
		this.nodes = nodes;
		this.arcs = arcs;
	}
	
	public HashSet<Node<T>> getNodes(){
		return nodes;
	}
	
	public HashSet<Arc<T>> getArcs(){
		return arcs;
	}
	
	protected HashSet<Node<T>>successors(final Node<T> node){
		final HashSet<Node<T>> successors = new HashSet<>();
		for(final Arc<T> arc : arcs){
			if(arc.isStart(node)){
				successors.add(arc.getEnd());
			}
		}
		return successors;
	}

	public HashSet<Arc<T>> startingAt(final Node<T> node){
		final HashSet<Arc<T>> startingAt = new HashSet<>();
		for(final Arc<T> arc : arcs){
			if(arc.isStart(node)){
				startingAt.add(arc);
			}
		}
		return startingAt;
	}
	
	public HashSet<Arc<T>> endingAt(final Node<T> node){
		final HashSet<Arc<T>> endingAt = new HashSet<>();
		for(final Arc<T> arc : arcs){
			if(arc.isEnd(node)){
				endingAt.add(arc);
			}
		}
		return endingAt;
	}

	private boolean succeeds(final Node<T> n1, final Node<T> n2){
		return successors(n1).contains(n2);
	}

	public int distance(final Node<T> n1, final Node<T> n2)throws IllegalArgumentException{
		if(succeeds(n1,n2)){
			final HashSet<Arc<T>> arcs = startingAt(n1);
			int distance = 0;
			for(final Arc<T> arc : arcs){
				if(arc.isEnd(n2)){
					distance = arc.getWeight();
				}
			}
			return distance;
		}
		else{
			throw new IllegalArgumentException("Nodes not adjacent");
		}
	}
}
