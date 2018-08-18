package space.scown.travellingsalesman.graph;
import java.util.HashSet;

public class Graph<T> {

	protected HashSet<Node<T>> nodes;
	protected HashSet<Arc<T>> arcs;	

	public Graph(HashSet<Node<T>> nodes, HashSet<Arc<T>> arcs){
		this.nodes = nodes;
		this.arcs = arcs;
	}
	
	public HashSet<Node<T>> getNodes(){
		return nodes;
	}
	
	public HashSet<Arc<T>> getArcs(){
		return arcs;
	}
	
	public HashSet<Node<T>>successors(Node<T> node){
		HashSet<Node<T>> successors = new HashSet<Node<T>>();
		for(Arc<T> arc : arcs){
			if(arc.isStart(node)){
				successors.add(arc.getEnd());
			}
		}
		return successors;
	}
	
	public HashSet<Node<T>>predecessors(Node<T> node){
		HashSet<Node<T>> predecessors = new HashSet<Node<T>>();
		for(Arc<T> arc : arcs){
			if(arc.isEnd(node)){
				predecessors.add(arc.getStart());
			}
		}
		return predecessors;
	}
	
	public HashSet<Arc<T>> startingAt(Node<T> node){
		HashSet<Arc<T>> startingAt = new HashSet<Arc<T>>();
		for(Arc<T> arc : arcs){
			if(arc.isStart(node)){
				startingAt.add(arc);
			}
		}
		return startingAt;
	}
	
	public HashSet<Arc<T>> endingAt(Node<T> node){
		HashSet<Arc<T>> endingAt = new HashSet<Arc<T>>();
		for(Arc<T> arc : arcs){
			if(arc.isEnd(node)){
				endingAt.add(arc);
			}
		}
		return endingAt;
	}
	
	public boolean contains(Node<T> node){
		return nodes.contains(node);
	}
	
	public boolean contains(Arc<T> arc){
		return arcs.contains(arc);
	}
	
	public boolean succeeds(Node<T> n1, Node<T>n2){
		return successors(n1).contains(n2);
	}
	
	public boolean precedes(Node<T> n1, Node<T> n2){
		return predecessors(n1).contains(n2);
	}
	
	public boolean adjacent(Node<T> n1, Node<T> n2){
		return succeeds(n1,n2) || precedes(n1,n2);
	}
	
	public int distance(Node<T> n1, Node<T> n2)throws IllegalArgumentException{
		if(succeeds(n1,n2)){
			HashSet<Arc<T>> arcs = startingAt(n1);
			int distance = 0;
			for(Arc<T> arc : arcs){
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
