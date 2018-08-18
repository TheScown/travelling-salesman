package space.scown.travellingsalesman.minedge;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;
import space.scown.travellingsalesman.graph.Node;

import java.util.HashSet;
import java.util.Iterator;

public class MinGraph<T> extends Graph<T> {

	public MinGraph(HashSet<Node<T>> nodes, HashSet<Arc<T>> arcs) {
		super(nodes,arcs);
	}
	
	public void addArc(Arc<T> arc) {
		arcs.add(arc);
	}
	
	public Node<T> getNextCity(Node<T> city) {
		HashSet<Node<T>> nextNode = successors(city);
		Iterator<Node<T>> it = nextNode.iterator();
		return it.next();
	}
	
	public Arc<T> getNextLeg(Node<T> city) {
		HashSet<Arc<T>> nextLeg = startingAt(city);
		Iterator<Arc<T>>it = nextLeg.iterator();
		return it.next();
	}
}
