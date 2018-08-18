package space.scown.travellingsalesman.path;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {
	
	public static void writeToFile(String name, int size, Path path) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("tour"+name+".txt"));
		
		String line1 = "NAME = "+ name+ ",";
		writer.write(line1);
		
		String line2 = "TOURSIZE = "+size+",";
		writer.write(line2);
		
		int length = path.getLength();
		String line3 = "LENGTH = "+length+",";
		writer.write(line3);
		
		ArrayList<Integer> cities = path.getPath();
		String line4 = "";
		for(int i = 0;i<cities.size()-1;i++){
			line4+=cities.get(i)+",";
		}
		line4+=cities.get(cities.size()-1);
		writer.write(line4);
		writer.close();
	}
}
