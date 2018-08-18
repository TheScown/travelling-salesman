package space.scown.travellingsalesman.salesman;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;
import space.scown.travellingsalesman.graph.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class Parser {
	
	private String name;
	private int size;
	private Graph<Integer> graph;
	
	@SuppressWarnings("unchecked")
	public Parser(String filename)throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();
		String data = "";
		while(line != null){
			data+=line;
			line = reader.readLine();
		}
		String[] parts = data.split(",");
		for(int i = 0;i<parts.length;i++){
			parts[i] = parts[i].trim();
		}
		
		String[] nameParts = parts[0].split("=");
		name = nameParts[1].trim();
		
		String[] sizeParts = parts[1].split("=");
		String sizeString = sizeParts[1].trim();
		size = Integer.parseInt(sizeString);
		
		HashSet<Node<Integer>> nodes = new HashSet<Node<Integer>>();
		Node<Integer>[] nodeArray = new Node[size];
		for(int i = 0;i<size;i++){
			nodeArray[i] = new Node<Integer>(i+1);
		}
		for(Node<Integer> node : nodeArray){
			nodes.add(node);
		}
		
		HashSet<Arc<Integer>> arcs = new HashSet<Arc<Integer>>();
		int j = 2;
		for(int k = 0;k<size;k++){
			for(int l = k+1;l<size;l++){
				int weight = Integer.parseInt(parts[j]);
				arcs.add(new Arc<Integer>(nodeArray[k],nodeArray[l],weight));
				arcs.add(new Arc<Integer>(nodeArray[l],nodeArray[k],weight));
				j++;
			}
		}
		
		graph = new Graph<Integer>(nodes,arcs);
	}
	
	public String getName(){
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public Graph<Integer> getGraph(){
		return graph;
	}
}
