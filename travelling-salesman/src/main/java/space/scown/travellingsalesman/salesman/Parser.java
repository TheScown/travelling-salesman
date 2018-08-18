/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.salesman;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;
import space.scown.travellingsalesman.graph.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

public class Parser {
	
	private final String name;
	private final int size;
	private final Graph<Integer> graph;
	
	@SuppressWarnings("unchecked")
	public Parser(final String filename) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();
		String data = "";
		while(line != null){
			data+=line;
			line = reader.readLine();
		}
		final String[] parts = data.split(",");
		for(int i = 0;i<parts.length;i++){
			parts[i] = parts[i].trim();
		}
		
		final String[] nameParts = parts[0].split("=");
		name = nameParts[1].trim();
		
		final String[] sizeParts = parts[1].split("=");
		final String sizeString = sizeParts[1].trim();
		size = Integer.parseInt(sizeString);
		
		final HashSet<Node<Integer>> nodes = new HashSet<>();
		final Node<Integer>[] nodeArray = new Node[size];
		for(int i = 0;i<size;i++){
			nodeArray[i] = new Node<>(i + 1);
		}
        Collections.addAll(nodes, nodeArray);
		
		final HashSet<Arc<Integer>> arcs = new HashSet<>();
		int j = 2;
		for(int k = 0;k<size;k++){
			for(int l = k+1;l<size;l++){
				final int weight = Integer.parseInt(parts[j]);
				arcs.add(new Arc<>(nodeArray[k], nodeArray[l], weight));
				arcs.add(new Arc<>(nodeArray[l], nodeArray[k], weight));
				j++;
			}
		}
		
		graph = new Graph<>(nodes, arcs);
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
