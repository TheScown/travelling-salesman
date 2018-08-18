package space.scown.travellingsalesman.bestfirst;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Node;
import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Path;
import space.scown.travellingsalesman.salesman.Writer;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

public class GreedyBestFirstSearch {
	
	/**
	 * A greedy best first search algorithm.  From each city,
	 * takes the shortest available route until a tour is found.
	 * Returns the shortest tour in a file.
	 * @param args Input filename
	 */
	public static void main(String[] args){
		Parser parser = null;
		try{
			parser = new Parser(args[0]);
		}
		catch(Exception e){
			System.out.println("Some sort of error occurred.  Check the file was formatted correctly");
			return;
		}
		HashSet<Path> paths = new HashSet<Path>();
		for(Node<Integer> node : parser.getGraph().getNodes()){
			Path path = new Path();
			int i = node.getStored();
			path.add(i,0);
			while(path.size()<parser.getSize()){
				Node<Integer> n = new Node<Integer>(path.getLast());
				HashSet<Arc<Integer>> arcs = parser.getGraph().startingAt(n);
				boolean added = false;
				while(!added){
					Arc<Integer> arc = Collections.min(arcs);		
					if(!path.contains(arc.getEnd().getStored())){
						path.add(arc.getEnd().getStored(),arc.getWeight());
						added = true;
					}
					else{
						arcs.remove(arc);
					}
				}
				
			}
			Node<Integer> n = new Node<Integer>(path.getLast());
			HashSet<Arc<Integer>> arcs = parser.getGraph().startingAt(n);
			for(Arc<Integer> arc : arcs){
				if(arc.isEnd(node)){
					path.add(i, arc.getWeight());
				}
			}
			paths.add(path);
		}
		try{
			Writer.writeToFile(parser.getName(), parser.getSize(), Collections.min(paths));
		}
		catch(IOException e){
			System.out.println("Couldn't write file for some reason.");
		}
	}
}