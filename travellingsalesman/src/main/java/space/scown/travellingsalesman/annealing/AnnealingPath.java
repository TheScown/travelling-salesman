package space.scown.travellingsalesman.annealing;

import space.scown.travellingsalesman.graph.Node;
import space.scown.travellingsalesman.salesman.Parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class AnnealingPath extends space.scown.travellingsalesman.salesman.Path {

	private Parser parser;
	
	public AnnealingPath(String name, Parser parser){
		super();
		this.parser = parser;
	}
	
	private AnnealingPath(AnnealingPath path){
		super();
		this.parser = path.getParser();
		this.path = new ArrayList<Integer>(path.getPath());
		this.length = path.getLength();
	}
	
	public HashSet<AnnealingPath> successors(){
		HashSet<AnnealingPath> successors = new HashSet<AnnealingPath>();
		for(int i = 0;i<size()-1;i++){
			for(int j = i;j<size()-1;j++){
				if(i!=j){
					AnnealingPath newPath = this.swap(i,j);
					successors.add(newPath);
				}
			}
		}
		return successors;
	}
	
	private Parser getParser(){
		return parser;
	}
	
	public AnnealingPath swap(int i,int j){
		AnnealingPath newPath = new AnnealingPath(this);
		ArrayList<Integer> p = new ArrayList<Integer>(newPath.getPath());
		int a = p.get(i);
		p.set(i,p.get(j));
		p.set(j,a);
		if(p.get(0) != p.get(p.size()-1)){
			p.set(size()-1,p.get(0));
		}
		newPath.path = p;
		newPath.length = newPath.recomputeLength();
		return newPath;
	}
	
	private int recomputeLength(){
		int newLength = 0;
		for(int i = 0;i<size()-1;i++){
			Node<Integer> start = new Node<Integer>(path.get(i));
			Node<Integer> end = new Node<Integer>(path.get(i+1));
			newLength = newLength += parser.getGraph().distance(start, end);
		}
		return newLength;
	}
	
	public static AnnealingPath generateRandomPath(Parser parser) {
		AnnealingPath path = new AnnealingPath(parser.getName(),parser);
		HashSet<Node<Integer>> nodes = parser.getGraph().getNodes();
		int size = nodes.size();
		Random randomGen = new Random();
		int start = randomGen.nextInt(size) + 1;
		path.add(start,0);
		while(path.size() < size){
			int next = randomGen.nextInt(size) + 1;
			if(!path.contains(next)){
				Node<Integer> n1 = new Node<Integer>(path.getLast());
				Node<Integer> n2 = new Node<Integer>(next);
				int distance = parser.getGraph().distance(n1, n2);
				path.add(next,distance);
			}
		}
		Node<Integer> n1 = new Node<Integer>(start);
		Node<Integer> n2 = new Node<Integer>(path.getLast());
		path.add(start, parser.getGraph().distance(n1,n2));
		return path;
	}
}
