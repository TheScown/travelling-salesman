/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.salesman;
import java.util.ArrayList;

public class Path implements Comparable<Path>{
	
	protected int length;
	protected ArrayList<Integer> path;
	
	public Path(){
		length = 0;
		path = new ArrayList<Integer>();
	}
	
	public void add(final int city, final int lengthIncrease){
		path.add(city);
		length+=lengthIncrease;
	}
	
	public int getLength(){
		return length;
	}
	
	public ArrayList<Integer> getPath(){
		return path;
	}
	
	public boolean contains(final Integer i){
		return path.contains(i);
	}
	
	public boolean equals(final Object o){
		if (o instanceof Path){
			final Path p = (Path) o;
			return path.equals(p.getPath())&&length==p.getLength();
		}
		return false;
	}
	
	public int size(){
		return path.size();
	}
	
	public int getLast(){
		return path.get(path.size()-1);
	}
	
	public String toString(){
		String s = "(";
		for(final int i: path){
			s+=i+",";
		}
		s+=length+")";
		return s;
	}
	
	
	
	public int hashCode(){
		return path.hashCode()+length;
	}

	public int compareTo(final Path o) {
		return length - o.getLength();
	}
}
