package space.scown.travellingsalesman.graph;


public class Arc<T> implements Comparable<Arc<T>>{
		
		private Node<T> start;
		private Node<T> end;
		int weight;
		
		public Arc(Node<T> start, Node<T> end, int weight){
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		
		public Node<T> getStart(){
			return start;
		}
		
		public Node<T> getEnd() {
			return end;
		}
		
		public int getWeight() {
			return weight;
		}
		
		public boolean isStart(Node<T> n){
			return start.equals(n);
		}
		
		public boolean isEnd(Node<T> n){
			return end.equals(n);
		}
		
		@SuppressWarnings("unchecked")
		public boolean equals(Object o){
			if(getClass().equals(o.getClass())){
				Arc<T> a = (Arc<T>) o;
				return (start.equals(a.getStart())&&end.equals(a.getEnd())&&weight==a.getWeight());
			}
			return false;
		}
		
		public int hashCode() {
			return start.hashCode() + end.hashCode() + weight;
		}
		
		public String toString(){
			return "("+start.toString()+","+end.toString()+","+weight+")";
		}
		
		public int compareTo(Arc<T> o){
			return weight - o.getWeight();
		}


}
