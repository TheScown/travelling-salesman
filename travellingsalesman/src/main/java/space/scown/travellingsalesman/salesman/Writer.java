package space.scown.travellingsalesman.salesman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {
	
	public static void writeToFile(String name, int size, Path path) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("tour"+name+".txt"));
		
		String line1 = "NAME = "+ name+ ",";
		writer.write(line1);
		writer.newLine();
		
		String line2 = "TOURSIZE = "+size+",";
		writer.write(line2);
		writer.newLine();
		
		int length = path.getLength();
		String line3 = "LENGTH = "+length+",";
		writer.write(line3);
		writer.newLine();
		
		ArrayList<Integer> cities = path.getPath();
		String line4 = "";
		for(int i = 0;i<cities.size()-2;i++){
			line4+=cities.get(i)+",";
		}
		line4+=cities.get(cities.size()-2);
		writer.write(line4);
		writer.close();
	}
}