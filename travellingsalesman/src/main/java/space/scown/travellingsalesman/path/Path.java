package space.scown.travellingsalesman.path;
import java.util.ArrayList;

public class Path implements Comparable<Path>{
	
	private int length;
	private ArrayList<Integer> path;
	
	public Path(String name, int size){
		length = 0;
		path = new ArrayList<Integer>();
	}
	
	public void add(int city, int lengthIncrease){
		path.add(city);
		length+=lengthIncrease;
	}
	
	public int getLength(){
		return length;
	}
	
	public ArrayList<Integer> getPath(){
		return path;
	}
	
	public boolean contains(Integer i){
		return path.contains(i);
	}
	
	public boolean equals(Object o){
		if (o instanceof Path){
			Path p = (Path) o;
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
		for(int i: path){
			s+=i+",";
		}
		s+=length+")";
		return s;
	}
	
	
	
	public int hashCode(){
		return path.hashCode()+length;
	}

	public int compareTo(Path o) {
		return length - o.getLength();
	}
}
