/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.genetic;

import space.scown.travellingsalesman.graph.Node;
import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class GeneticPath extends Path {
	
	private Parser parser;
	
	public GeneticPath(Parser parser){
		super();
		this.parser = parser;
	}
	
	private GeneticPath(GeneticPath path){
		super();
		this.parser = path.getParser();
		this.path = new ArrayList<Integer>(path.getPath());
		this.length = path.getLength();
	}
	
	private void setPath(ArrayList<Integer> path){
		this.path = path;
		length = recomputeLength();
	}
	
	public Parser getParser(){
		return parser;
	}

	public static GeneticPath generateRandomPath(Parser parser) {
		GeneticPath path = new GeneticPath(parser);
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
	
	public GeneticPath breed(GeneticPath p2){
		ArrayList<Integer> path1 = new ArrayList<Integer>(path);
		ArrayList<Integer> path2 = new ArrayList<Integer>(p2.getPath());
		Random random = new Random();
		path1.remove(path1.size()-1);
		path2.remove(path2.size()-1);
		int breedLength = random.nextInt(path1.size());
		ArrayList<Integer> newPath1 = new ArrayList<Integer>();
		ArrayList<Integer> newPath2 = new ArrayList<Integer>();
		for(int i = 0;i<breedLength;i++){
			newPath1.add(i,path1.get(i));
			newPath2.add(i,path2.get(i));
		}
		for(int i = breedLength;i<path1.size();i++){
			newPath1.add(i,path2.get(i));
			newPath2.add(i,path1.get(i));
		}
		newPath1 = fix(newPath1);
		newPath2 = fix(newPath2);
		GeneticPath child1 = new GeneticPath(parser);
		child1.setPath(newPath1);
		GeneticPath child2 = new GeneticPath(parser);
		child2.setPath(newPath2);
		HashSet<GeneticPath> paths = new HashSet<GeneticPath>();
		paths.add(child1);
		paths.add(child2);
		return Collections.min(paths);
	}
	
	private ArrayList<Integer> fix(ArrayList<Integer> p){
		ArrayList<Integer> missing = new ArrayList<Integer>();
		ArrayList<Integer> doubled = new ArrayList<Integer>();
		for(int i = 1;i<=p.size();i++){
			int count = 0;
			for(int j = 0;j<p.size();j++){
				if(p.get(j) == i){
					count ++;
				}
			}
			if(count == 0){
				missing.add(i);
			}
			else if (count == 2) {
				doubled.add(i);
			}
		}
		for(int i = 0;i<missing.size();i++){
			p.set(p.indexOf(doubled.get(i)),missing.get(i));
		}
		p.add(p.get(0));
		return new ArrayList<Integer>(p);
	}
	
	public GeneticPath swap(int i,int j){
		GeneticPath newPath = new GeneticPath(this);
		ArrayList<Integer> p = newPath.getPath();
		int a = p.get(i);
		p.set(i,p.get(j));
		p.set(j,a);
		if(p.get(0) != p.get(p.size()-1)){
			p.set(size()-1,p.get(0));
		}
		newPath.setPath(p);
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
}
