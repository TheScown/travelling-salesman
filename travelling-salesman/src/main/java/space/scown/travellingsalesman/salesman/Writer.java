/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.salesman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {
	
	public static void writeToFile(final String name, final int size, final Path path) throws IOException{
		final BufferedWriter writer = new BufferedWriter(new FileWriter("tour"+name+".txt"));
		
		final String line1 = "NAME = "+ name+ ",";
		writer.write(line1);
		writer.newLine();
		
		final String line2 = "TOURSIZE = "+size+",";
		writer.write(line2);
		writer.newLine();
		
		final int length = path.getLength();
		final String line3 = "LENGTH = "+length+",";
		writer.write(line3);
		writer.newLine();
		
		final ArrayList<Integer> cities = path.getPath();
		String line4 = "";
		for(int i = 0;i<cities.size()-2;i++){
			line4+=cities.get(i)+",";
		}
		line4+=cities.get(cities.size()-2);
		writer.write(line4);
		writer.close();
	}
}
