/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.graph;

public class Node<T>{
		
	private T stored;
		
	public Node(T object){
		stored = object;
	}
		
	public T getStored(){
		return stored;
	}
		
	@SuppressWarnings("unchecked")
	public boolean equals(Object o){
		if(o.getClass().equals(this.getClass())){
			Node<T> n = (Node<T>) o;
			return stored.equals(n.getStored());
		}
		return false;
	}
		
	public int hashCode(){
		return stored.hashCode();
	}
		
	public String toString(){
		return stored.toString();
	}
}