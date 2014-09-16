package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class StatisticsDataOrderer {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Jacopo/Desktop/DatiStatistici.txt"));
		TreeSet<String> treeSet= new TreeSet<String>();
		try {
			String line = br.readLine();
			while (line != null) {
				System.err.println(line);
				treeSet.add(line);
				//instructionsCountMap.put(lineArray[0],Integer.parseInt(lineArray[1]));
				line = br.readLine();
			}

		} finally {
			br.close();
		}
		
		PrintWriter printWriter;
		String numberOfInstructionsFilePath="C:/Users/Jacopo/Desktop/DatiStatisticiOrdinati.txt";
		
			
				printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));
			
			for(String line: treeSet){
				printWriter.println(line);
						
				printWriter.flush();
			}
			
			printWriter.close();
	}

}
