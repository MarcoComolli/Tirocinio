package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class StatisticsDataOrderer {
	private static HashMap<String, Integer> totalInstructionsNumber = new HashMap<String, Integer>();
	private static Integer numberInstructions = 0;
	private static HashMap<String, Set<String>> evaluatedConditions = new HashMap<String, Set<String>>();
	private static  TreeMap<String, Integer> pathNumberMap = new TreeMap<String, Integer>();
	private static String filesPath = "C:\\Users\\Marco\\Desktop\\files";

	public static void main(String[] args) throws IOException {

		TreeSet<String> treeSet = readAndOrderData();
		writeOrderedData(treeSet);
		writeNumberOfLinesForMethod(treeSet);
		writeCoveredConditions();
		writePathsLength();
	}



	public static void writeBlockFrequency() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(filesPath + "\\Blocks.txt"));
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = filesPath + "\\BlocksFrequency.txt";
		int a = 0;
		printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));
		
		int[] numberOfBlocks = new int[13];
		for (int i = 0; i < numberOfBlocks.length; i++) {
			numberOfBlocks[i] = 0;
		}
		String line = br.readLine();
		while (line != null) {  
			String code = line.split("#")[1];
			switch (Integer.parseInt(code)) {
			case -1:
				numberOfBlocks[0]++;
				break;
			case 0:
				numberOfBlocks[1]++;
				break;
			case 1:
				numberOfBlocks[2]++;
				break;
			case 2:
				numberOfBlocks[3]++;
				break;
			case 3:
				numberOfBlocks[4]++;
				break;
			case 4:
				numberOfBlocks[5]++;
				break;
			case 5:
				numberOfBlocks[6]++;
				break;
			case 7:
				numberOfBlocks[7]++;
				break;
			case 8:
				numberOfBlocks[8]++;
				break;
			case 10:
				numberOfBlocks[9]++;
				break;
			case 11:
				numberOfBlocks[10]++;
				break;
			case 12:
				numberOfBlocks[11]++;
				break;
			case 13:
				numberOfBlocks[12]++;
				break;
			}
			
			line = br.readLine();
		}
		
		
		printWriter.println("Number of METHOD block: " + numberOfBlocks[0]);
		printWriter.println("Number of IF block: " + numberOfBlocks[1]);
		printWriter.println("Number of ELSE block: " + numberOfBlocks[2]);
		printWriter.println("Number of WHILE block: " + numberOfBlocks[3]);
		printWriter.println("Number of DO-WHILE block: " + numberOfBlocks[4]);
		printWriter.println("Number of FOR block: " + numberOfBlocks[5]);
		printWriter.println("Number of SWITCH block: " + numberOfBlocks[6]);
		printWriter.println("Number of CATCH block: " + numberOfBlocks[7]);
		printWriter.println("Number of CASE block: " + numberOfBlocks[8]);
		printWriter.println("Number of ELSE IF block: " + numberOfBlocks[9]);
		printWriter.println("Number of TRY block: " + numberOfBlocks[10]);
		printWriter.println("Number of FINALLY block: " + numberOfBlocks[11]);
		printWriter.println("Number of SYNCHRONIZED block: " + numberOfBlocks[12]);
		
		printWriter.flush();
		

		br.close();
		printWriter.close();

	}
	
	public static void writePathsLength() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filesPath + "\\FilePercorsi.txt"));
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = filesPath + "\\LunghezzaCammini.txt";
		printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));

		int pathSize = 0;
		String pathName = "";
		try {
			String line = br.readLine();
			while (line != null) {      
				if(line.startsWith("§")){
					pathName = line.substring(1);
				}
				if(pathNumberMap.containsKey(pathName)){
					pathSize = pathNumberMap.get(pathName);
					pathSize++;
				}
				else{
					pathSize = 0;
				}
				pathNumberMap.put(pathName, pathSize);
				line = br.readLine();
			}

			double totalLength=0;
			double totalNumberOfPaths=0;
			totalNumberOfPaths=pathNumberMap.size();
			int maxPathLength=0;
			String maxPathKey = null;
			double maxCoveragePath = 0;
			int minPathLength=0;
			String minPathKey = null ;
			double minCoveragePath = 0;
			double coverage;

			TreeSet<String> minLengthPathsSet= new TreeSet<String>();
			TreeSet<String> maxLengthPathsSet= new TreeSet<String>();


			for (Entry<String, Integer> entry : pathNumberMap.entrySet()) {

				totalLength+=entry.getValue();
			}
			
			if(!pathNumberMap.isEmpty()){
				maxPathLength=minPathLength=pathNumberMap.get(pathNumberMap.firstKey());
				maxPathKey=minPathKey=pathNumberMap.firstKey();
				maxCoveragePath=minCoveragePath=(pathNumberMap.get(pathNumberMap.firstKey())*100)/totalLength;
			}
			
			for (Entry<String, Integer> entry : pathNumberMap.entrySet()) {
				coverage=(entry.getValue()*100)/totalLength;
				printWriter.println(entry.getKey() + " size : " + entry.getValue() + " covers "+
						coverage+"% of total paths");
				if(entry.getValue() == maxPathLength){
					maxLengthPathsSet.add(entry.getKey());
				}else if(entry.getValue()>maxPathLength){
					maxLengthPathsSet.clear();
					maxPathLength=entry.getValue();
					maxPathKey=entry.getKey();
					maxCoveragePath=coverage;
					maxLengthPathsSet.add(entry.getKey());

				}
				if(entry.getValue()==minPathLength){
					minLengthPathsSet.add(entry.getKey());

				}else if(entry.getValue()<minPathLength){
					minLengthPathsSet.clear();
					minPathLength=entry.getValue();
					minPathKey=entry.getKey();
					minCoveragePath=coverage;
					minLengthPathsSet.add(entry.getKey());
				}
			}
			String shortestPathsKeys="";
			String longestPathsKeys="";

			for(String i:minLengthPathsSet){
				shortestPathsKeys+=i+",";
			}

			for(String i:maxLengthPathsSet){
				longestPathsKeys+=i+",";
			}
			System.out.println(shortestPathsKeys +" length: "+minPathLength +" covers " + minCoveragePath);

			printWriter.println("Total number of paths: " + totalNumberOfPaths);
			printWriter.println("Total Paths length: " +totalLength);
			printWriter.println("Average Paths length: " +totalLength/totalNumberOfPaths);
			printWriter.println("Longest Path: " +maxPathKey +" length: "+maxPathLength +" covers " + maxCoveragePath +"%");
			printWriter.println("Shortest Path: " +minPathKey +" length: "+minPathLength +" covers " + minCoveragePath +"%");
			printWriter.println("Longest Paths: " +longestPathsKeys.substring(0, longestPathsKeys.length()-1) +" length: "+maxPathLength +" covers " + maxCoveragePath +"%");
			printWriter.println("Shortest Paths: "+ shortestPathsKeys.substring(0, shortestPathsKeys.length()-1) +" length: "+minPathLength +" covers " + minCoveragePath +"%");

		} finally {
			printWriter.flush();
			br.close();
			printWriter.close();
		}
	}



	public static void writeCoveredConditions() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filesPath + "\\FilePercorsi.txt"));
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = filesPath + "\\CondizioniCoperte.txt";
		printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));
		String[] arrayLine;

		Set<String> valuatedConditions = null;
		String[] methodBlockAndConditionLine;
		String methodAndBlock="";
		String evaluatedConditionsInPath = "";

		try {
			String line = br.readLine();
			while (line != null) {		
				if(!line.startsWith("§")){
					if(!line.contains("-")){	
						methodAndBlock=line;
						evaluatedConditionsInPath="";

					}else{
						methodBlockAndConditionLine=line.split("-");   
						methodAndBlock=methodBlockAndConditionLine[0];
						evaluatedConditionsInPath = methodBlockAndConditionLine[1];
					}


					if(evaluatedConditionsInPath!=""){ 		//se ci sono le condizioni
						if(evaluatedConditions.containsKey(methodAndBlock)){
							valuatedConditions = evaluatedConditions.get(methodAndBlock);
							valuatedConditions.add(evaluatedConditionsInPath);

						}else{
							valuatedConditions=new HashSet<String>();
							valuatedConditions.add(evaluatedConditionsInPath);

						}
						evaluatedConditions.put(methodAndBlock, valuatedConditions);
					}
				}

				line = br.readLine();
			}


			for (Entry<String, Set<String>> entry : evaluatedConditions.entrySet()) {
				String valuated="";
				for(String conditionCombination : entry.getValue()){
					valuated+=conditionCombination.substring(0,conditionCombination.length()-1) +";";
				}
				printWriter.println(entry.getKey() + " valutate : " + valuated);

				printWriter.flush();

			}



		} finally {
			br.close();
			printWriter.close();
		}

	}

	public static Set<String> getCombinationsList(int length) {
		Set<String> lista = new HashSet<String>();
		String booleana="";
		//final int n = 2;
		for (int i = 0; i < Math.pow(2, length); i++) {
			String bin = Integer.toBinaryString(i);
			while (bin.length() < length)
				bin = "0" + bin;

			for(int j=0;j<bin.length();j++){
				if(bin.charAt(j)=='0'){
					booleana+="false ";
				}else{
					booleana+="true ";
				}
			}

			booleana=booleana.substring(0,booleana.length()-1);
			lista.add(booleana);
			booleana="";

		}
		return lista;
	}

	/**
	 * Scrive file contenente strighe del tipo: metodo-numero di istruzioni
	 * testate in totale all'interno del metodo.
	 * 
	 * @param treeSet
	 * @throws IOException
	 */
	public static void writeNumberOfLinesForMethod(TreeSet<String> treeSet) throws IOException {
		String[] arrayLine = null;
		String[] numberInstructionsArray;
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = filesPath + "\\NumeroIstruzioniPerMetodo.txt";
		printWriter = new PrintWriter(new FileWriter(
				numberOfInstructionsFilePath));

		for (String line : treeSet) {
			arrayLine = line.split("#c");
			numberInstructionsArray = line.split("#i");

			//System.out.println(numberInstructionsArray[1]
			//	+ " numero istruzioni");
			if (totalInstructionsNumber.containsKey(arrayLine[0])) {
				numberInstructions = totalInstructionsNumber.get(arrayLine[0]);
				//System.out.println(numberInstructions + " " + arrayLine[0]);
				if (Integer.parseInt(numberInstructionsArray[1].trim()) > 0) {
					numberInstructions += Integer.parseInt(numberInstructionsArray[1].trim());
				}
			}
			totalInstructionsNumber.put(arrayLine[0], numberInstructions);
			numberInstructions = 0;

		}
		for (Entry<String, Integer> entry : totalInstructionsNumber.entrySet()) {
			printWriter.println(entry.getKey() + " numero : " + entry.getValue());
			printWriter.flush();
		}
		printWriter.close();
	}

	/**
	 * Inserisce le linee ordinate in un nuovo file.
	 * @param treeSet
	 * @throws IOException
	 */
	public static void writeOrderedData(TreeSet<String> treeSet) throws IOException {
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = filesPath + "\\DatiStatisticiOrdinati.txt";
		printWriter = new PrintWriter(new FileWriter(
				numberOfInstructionsFilePath));

		for (String line : treeSet) {
			printWriter.println(line);

			printWriter.flush();
		}

		printWriter.close();
	}

	/**
	 * Legge il file e ordina le stringhe inserendole in un TreeSet.
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static TreeSet<String> readAndOrderData() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader( filesPath + "\\DatiStatistici.txt"));
		TreeSet<String> treeSet = new TreeSet<String>();
		try {
			String line = br.readLine();
			while (line != null) {
				//System.err.println(line);
				treeSet.add(line);
				line = br.readLine();
			}

		} finally {
			br.close();
		}
		return treeSet;
	}

	public static void setFilesPath(String path){
		filesPath = path;
	}
}
