package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeSet;

public class StatisticsDataOrderer {
	private static HashMap<String, Integer> totalInstructionsNumber = new HashMap<String, Integer>();
	private static Integer numberInstructions = 0;
	private static HashMap<String, Set<String>> evaluatedConditions = new HashMap<String, Set<String>>();
	private static String filesPath = "C:\\Users\\Marco\\Desktop\\files";

	public static void main(String[] args) throws IOException {

		TreeSet<String> treeSet = readAndOrderData();
		writeOrderedData(treeSet);
		writeNumberOfTestedLinesForMethod(treeSet);



		//writeNotCoveredConditions();
		writeCoveredConditions();
		

	}

	private static void writeCoveredConditions() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				filesPath + "\\FilePercorsi.txt"));
		PrintWriter printWriter;
        String numberOfInstructionsFilePath = filesPath + "\\CondizioniCoperte.txt";
        printWriter = new PrintWriter(new FileWriter(
                numberOfInstructionsFilePath));
		String[] arrayLine;
		
		Set<String> valuatedConditions = null;
		String[] methodBlockAndConditionLine;
		String methodAndBlock="";
		String evaluatedConditionsInPath = "";
		
		try {
			String line = br.readLine();
			while (line != null) {
							
				arrayLine = line.split(": ");
				if(!arrayLine[1].contains("-")){	
					methodAndBlock=arrayLine[1];
                    evaluatedConditionsInPath="";
					
				}else{
				    methodBlockAndConditionLine=arrayLine[1].split("-");   
                    methodAndBlock=methodBlockAndConditionLine[0];
                    evaluatedConditionsInPath = methodBlockAndConditionLine[1];
				}
				
				System.out.println(methodAndBlock+".");
						
				if(evaluatedConditionsInPath!=""){
					if(evaluatedConditions.containsValue(methodAndBlock)){
						valuatedConditions= evaluatedConditions.get(methodAndBlock);
						System.err.println(methodAndBlock +" è presente");
						valuatedConditions.add(evaluatedConditionsInPath);

					}else{
						valuatedConditions=new HashSet<String>();
						valuatedConditions.add(evaluatedConditionsInPath);
						
					}
					evaluatedConditions.put(methodAndBlock, valuatedConditions);
				}

				line = br.readLine();
			}

			
			for (Entry<String, Set<String>> entry : evaluatedConditions
					.entrySet()) {
				String valuated="";
				for(String conditionCombination : entry.getValue()){
					valuated+=conditionCombination.substring(0,conditionCombination.length()-1) +";";
				}
				printWriter.println(entry.getKey() + " valutate : "
						+ valuated);
				
				printWriter.flush();

			}

			

		} finally {
			br.close();
			printWriter.close();
		}

	}

	private static void writeNotCoveredConditions() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				filesPath + "\\FilePercorsi.txt"));
		String[] arrayLine;
		String[] evaluatedConditionsInPath;
		try {
			String line = br.readLine();
			while (line != null) {
				//System.err.println(line);
				String methodAndBlock="";
				arrayLine = line.split(": ");
				if(arrayLine[1].contains("-")){
					arrayLine=arrayLine[1].split("-");	
					methodAndBlock=arrayLine[0];
				}else{
					methodAndBlock=arrayLine[1];						
				}
				//arrayLine=arrayLine[1].split(" ");
				evaluatedConditionsInPath = line.split("-");
				if(evaluatedConditionsInPath.length>1){
					//System.err.println(method);
					if(evaluatedConditions.containsValue(methodAndBlock)){
						Set<String> notValuatedConditions= evaluatedConditions.get(methodAndBlock);
						System.err.println(methodAndBlock +" è presente");
						Iterator<String> i = notValuatedConditions.iterator();
						while (i.hasNext()) {
							String conditionCombination = i.next(); 
							if((evaluatedConditionsInPath[1]).substring(0,evaluatedConditionsInPath[1].length()-1).equals(conditionCombination)){	
								i.remove();
								System.out.println("tolgo " +conditionCombination);
							}
						}
						evaluatedConditions.put(methodAndBlock, notValuatedConditions);


					}else{
						if(evaluatedConditionsInPath[1].contains(" ")){
							String[] conditions=evaluatedConditionsInPath[1].split(" ");
							String prova="";
							for(String s : conditions){
								prova+=s;
							}
							System.err.println(prova);
							Set<String> possibleCombinationsCondition=getCombinationsList(conditions.length);

							Iterator<String> i = possibleCombinationsCondition.iterator();
							while (i.hasNext()) {
								String conditionCombination = i.next(); 
								if((evaluatedConditionsInPath[1]).substring(0,evaluatedConditionsInPath[1].length()-1).equals(conditionCombination)){	
									i.remove();

								}
							}
							evaluatedConditions.put(methodAndBlock, possibleCombinationsCondition);
							System.out.println("inserisco "+ methodAndBlock);
						}else{
							//sbagliato
							Set<String> possibleCombinationsCondition=getCombinationsList(evaluatedConditionsInPath[1].length());
							evaluatedConditions.put(methodAndBlock, possibleCombinationsCondition);
						}					

					}
				}

				line = br.readLine();
			}

			PrintWriter printWriter;
			String numberOfInstructionsFilePath = filesPath + "\\CondizioniNonCoperte.txt";
			printWriter = new PrintWriter(new FileWriter(
					numberOfInstructionsFilePath));

			for (Entry<String, Set<String>> entry : evaluatedConditions
					.entrySet()) {
				String notvaluated="";
				for(String conditionCombination : entry.getValue()){
					notvaluated+=conditionCombination +";";
				}
				printWriter.println(entry.getKey() + " non valutate : "
						+ notvaluated);
				printWriter.flush();

			}

			printWriter.close();

		} finally {
			br.close();
		}
	}

	private static Set<String> getCombinationsList(int length) {
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
	private static void writeNumberOfTestedLinesForMethod(
			TreeSet<String> treeSet) throws IOException {
		String[] arrayLine = null;
		String[] numberInstructionsArray;
		PrintWriter printWriter;
		String numberOfInstructionsFilePath = filesPath + "\\NumeroIstruzioniTestatePerMetodo.txt";
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
				if (Integer.parseInt(numberInstructionsArray[1]) > 0) {
					numberInstructions += Integer
							.parseInt(numberInstructionsArray[1]);
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
	private static void writeOrderedData(TreeSet<String> treeSet) throws IOException {
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
	private static TreeSet<String> readAndOrderData() throws FileNotFoundException,
	IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				filesPath + "\\DatiStatistici.txt"));
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
