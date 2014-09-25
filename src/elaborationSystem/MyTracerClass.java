package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MyTracerClass {

	private static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> instructionsCountMap = new HashMap<String, Integer>();
	private static HashMap<String, LinkedList<String>> pathMap = new HashMap<String, LinkedList<String>>();
	private static boolean recordPath = false;
	private static LinkedList<String> blockList = new LinkedList<String>();
	private static String currentObjectIDPath;
	private static int currentexecutionNumberPath = -1;
	private static boolean firstTime = true, firstTimeTest = true, firstTimeBlock = true, firstTimeStat = true;
	private static String filesPath = "C:\\Users\\Marco\\Desktop\\files";
	private static int coveredBlocksTest = 0, totalCoveredBlocksTest = 0, newUniqueCoveredBloksTest = 0;
	private static boolean recordTest = false;
	private static int blockCount = 0;
	private static TreeSet<String> testedBlockSet = new TreeSet<String>();;
	static int c = 0;
	private static int pathNumber = 0, totalPathSize = 0;




	public static void tracer(String objectID, int blockCode, int blockID) {
		try {
			

			int n = 1;
			if(countMap.containsKey(objectID+"@"+blockID+"#"+blockCode)){
				n = countMap.get(objectID+"@"+blockID+"#"+blockCode);
				n++;
			}


			if(recordPath){
				writePathsFile(objectID, blockID);
				totalPathSize++;
				blockList.add(objectID + "@" + blockID);
			}


			countMap.put(objectID+"@"+blockID+"#"+blockCode, n);


			if(recordTest){
				coveredBlocksTest++;
				if(n == 1){
					newUniqueCoveredBloksTest++;
				}
			testedBlockSet.add(objectID+"@"+blockID);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void tracer(String objectID, int blockCode, int blockID, boolean[] ilMioArrayDiBooleani){
		try {
			

			int n = 1;
			if(countMap.containsKey(objectID+"@"+blockID+"#"+blockCode)){
				n = countMap.get(objectID+"@"+blockID+"#"+blockCode);
				n++;
			}
			
			
			if(recordPath){
				writePathsFile(objectID, blockID, ilMioArrayDiBooleani);
				totalPathSize++;
				blockList.add(objectID + "@" + blockID);
			}

			countMap.put(objectID+"@"+blockID+"#"+blockCode, n);

			if(recordTest){
				coveredBlocksTest++;
				if(n == 1){
					newUniqueCoveredBloksTest++;
				}
			testedBlockSet.add(objectID+"@"+blockID);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeStatisticsData(String objectID, int blockCode, int blockID, int n) throws IOException {
		PrintWriter printWriter;
		String numberOfInstructionsFilePath= filesPath + "\\DatiStatistici.txt";


		if(firstTimeStat){
			printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));
			firstTimeStat=false;
		}else{
			printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath,true));
		}

		
		Integer instructions = instructionsCountMap.get(objectID+"@"+blockID);
		if(instructions == null){
			c++;
			System.out.println(c + " E' nullo " + objectID+"@"+blockID);
			instructions = -1;
		}
		printWriter.println(objectID +" #c" + blockCode + " @" + blockID + " #v " + n +" #i " +instructions);
		printWriter.flush();


		printWriter.close();
	}

	private static void insertIstructionsNumber() throws FileNotFoundException, IOException {

		BufferedReader br = new BufferedReader(new FileReader(filesPath + "\\NumeroIstruzioni.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String []lineArray=line.split("#");
				instructionsCountMap.put(lineArray[0],Integer.parseInt(lineArray[1]));
				line = br.readLine();
			}

		} finally {
			br.close();
		}

	}

	

	private static void writePathsFile(String objectID, int blockID, boolean[] ilMioArrayDiBooleani) throws IOException {
		String booleanString="";
		for (int i=0; i<ilMioArrayDiBooleani.length; i++){
			booleanString+=ilMioArrayDiBooleani[i] +" ";
		}
		PrintWriter printWriter;
		String pathsFilePath=filesPath + "\\FilePercorsi.txt";

		if(firstTime){
			printWriter = new PrintWriter(new FileWriter(pathsFilePath));
			firstTime=false;
		}else{
			printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
		}

		//printWriter.println(objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
		//		+" numero istruzioni nel blocco: " +instructionsNumber);
		printWriter.println(currentObjectIDPath+" * "+currentexecutionNumberPath+": " + objectID +" @" + blockID + "-"+booleanString);
		printWriter.flush();


		printWriter.close();
	}

	private static void writePathsFile(String objectID, int blockID) throws IOException {


		PrintWriter printWriter;
		String pathsFilePath= filesPath + "\\FilePercorsi.txt";

		if(firstTime){
			printWriter = new PrintWriter(new FileWriter(pathsFilePath));
			firstTime=false;
		}else{
			printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
		}

		//printWriter.println(objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
		//		+" numero istruzioni nel blocco: " +instructionsNumber);
		printWriter.println(currentObjectIDPath+" * "+currentexecutionNumberPath+": " + objectID +" @" + blockID );
		printWriter.flush();


		printWriter.close();
	}

	//inizia a registrare un percorso
	public static void recordPath(String objectID){
		if(recordPath == false){
			recordPath = true;
			//inizializza i campi
			currentexecutionNumberPath = countMap.get(objectID+"@0#-1"); //il codice è 0 perchè è l'inizio del metodo
			currentObjectIDPath = objectID;
			blockList.clear();
			pathNumber ++;
		}
	}

	public static void endRecordPath(String objectID){
		if(objectID == currentObjectIDPath){ //se l'ordine di fermarsi arriva dalla fine del metodo giusto
			recordPath = false;
			LinkedList<String> newList = (LinkedList<String>) blockList.clone();
			pathMap.put(objectID+"*"+ currentexecutionNumberPath, newList);
		}

	}

	public static void setFilesPath(String path){
		filesPath = path;
	}

	public static void startRecordTestCoverage() {
		if(recordTest == false){
			recordTest = true;
		}
		coveredBlocksTest = 0;
		newUniqueCoveredBloksTest = 0;
		testedBlockSet.clear();
	}
	
	public static void endRecordTestCoverage(String fullname, float timeSec, int testNumber, int failCount){
		try{
			PrintWriter printWriter;
			String pathsFilePath= filesPath + "\\TestCoverage.txt";
			totalCoveredBlocksTest += coveredBlocksTest;
			if(firstTimeTest){
				printWriter = new PrintWriter(new FileWriter(pathsFilePath));
				firstTimeTest=false;
			}else{
				printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
			}

			printWriter.println("Test " + fullname + " #c " + coveredBlocksTest + " #n " + testNumber + " #f " + failCount + " #t " + timeSec +
					" #ub " + newUniqueCoveredBloksTest + " #ubp " + (double)newUniqueCoveredBloksTest/blockCount*100 +
					" #b " + testedBlockSet.size() + " bp " +  (double)testedBlockSet.size()/blockCount*100);
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void endOfTests(){
		try{
			
			int testedBlockCount = 0, uncoveredBlocks = 0;
			String objectID, blockCode, blockID;
			for(Entry<String,Integer> entry : countMap.entrySet()){
				String key = entry.getKey();
				objectID = key.substring(0, key.indexOf("@"));
				blockCode = key.substring(key.indexOf("#")+1);
				blockID = key.substring(key.indexOf("@") +1, key.indexOf("#"));
				
				insertIstructionsNumber();
				writeStatisticsData(objectID, Integer.parseInt(blockCode), Integer.parseInt(blockID), entry.getValue());
				
				if(entry.getValue() != 0){
					testedBlockCount++;
				}
				else{
					uncoveredBlocks++;
				}
			}
			
			PrintWriter printWriter;
			String pathsFilePath= filesPath + "\\GlobalData.txt";
			printWriter = new PrintWriter(new FileWriter(pathsFilePath));

			printWriter.println("Total block code: " +  blockCount);
			printWriter.println("Total block code tested (cumulative): " + totalCoveredBlocksTest);
			printWriter.println("Total block code tested: " + testedBlockCount);
			printWriter.println("Uncovered block: " + uncoveredBlocks);
			printWriter.println("% test coverage: " + (double)testedBlockCount/(double)blockCount*100);
			printWriter.println("% test uncovered: " + (double)uncoveredBlocks/(double)blockCount*100);
			printWriter.println("Total number of path: " + pathNumber);
			printWriter.println("Total number of path-block covered: " + totalPathSize);
			printWriter.println("Average path size: " + (double)totalPathSize/pathNumber);
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		for (Map.Entry<String, LinkedList<String>> entry : pathMap.entrySet()) {
//			System.out.print(entry.getKey() + " ");
//			for (String s : entry.getValue()) {
//				System.out.print(s + " ");
//			}
//			System.out.println();
//		}
	}
	
	//se è necessario un file con l'elenco dei blocchi basta scommentare il codice qua sotto
	public static void addBlock(String blockName, int blockID, int blockCode){

		try{
			PrintWriter printWriter;
			String pathsFilePath= filesPath + "\\Blocks.txt";
			
			if(firstTimeBlock){
				printWriter = new PrintWriter(new FileWriter(pathsFilePath));
				firstTimeBlock=false;
			}else{
				printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
			}
			blockCount++;
			countMap.put(blockName+"@"+blockID+"#"+blockCode, 0);

			printWriter.println(blockName+"@"+blockID+"#"+blockCode);
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
