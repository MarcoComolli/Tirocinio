package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class MyTracerClass {

	private static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> instructionsCountMap = new HashMap<String, Integer>();
	private static HashMap<String, LinkedList<String>> pathMap = new HashMap<String, LinkedList<String>>();
	private static boolean recordPath = false;
	private static LinkedList<String> blockList = new LinkedList<String>();
	private static String currentObjectIDPath;
	private static int currentexecutionNumberPath;
	private static boolean firstTime=true;
	private static int instructionsNumber;



	public MyTracerClass(){
		countMap = new HashMap<String, Integer>();
		instructionsCountMap= new HashMap<String, Integer>();
	}

	public static void tracer(String objectID, int blockCode, int blockID) {
		try {
			insertIstructionsNumber();
		

		if(instructionsCountMap.containsKey(objectID+"@"+blockID)){
			instructionsNumber=instructionsCountMap.get(objectID+"@"+blockID);
		}

		int n = 1;
		if(countMap.containsKey(objectID+"@"+blockID)){
			n = countMap.get(objectID+"@"+blockID);
			n++;
		}

		System.out.println("Oggetto: " + objectID + " code: " + blockCode + "@" + blockID + " numero di volte: " + n
				+" numero istruzioni nel blocco: " +instructionsNumber);
		

	    writeStatisticsData(objectID, blockCode, blockID, n);
	    
	    
							
		
		instructionsNumber=-1;
		
		countMap.put(objectID+"@"+blockID, n);
		System.out.println(countMap.size());

		if(recordPath){ //se devo registrare il cammino
			blockList.add(objectID + "@" + blockID);
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeStatisticsData(String objectID, int blockCode,
			int blockID, int n) throws IOException {
		PrintWriter printWriter;
		String numberOfInstructionsFilePath="C:/Users/Jacopo/Desktop/DatiStatistici.txt";
		
			if(firstTime){
				printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));
				firstTime=false;
			}else{
				printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath,true));
			}
			
				//printWriter.println(objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
				//		+" numero istruzioni nel blocco: " +instructionsNumber);
			printWriter.println(objectID +" #c" + blockCode + " @" + blockID + " #v " + n
			+" #i" +instructionsNumber);
				printWriter.flush();

			
			printWriter.close();
	}

	private static void insertIstructionsNumber() throws FileNotFoundException,
	IOException {
		if(firstTime){
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/Jacopo/Desktop/NumeroIstruzioni.txt"));
			try {
				String line = br.readLine();
				while (line != null) {
					System.err.println(line);
					String []lineArray=line.split("#");
					instructionsCountMap.put(lineArray[0],Integer.parseInt(lineArray[1]));
					line = br.readLine();
				}

			} finally {
				br.close();
			}
			//firstTime=false;
		}
	}

	public static void tracer(String objectID, int blockCode, int blockID, boolean[] ilMioArrayDiBooleani){
		try {
			insertIstructionsNumber();
		

		if(instructionsCountMap.containsKey(objectID+"@"+blockID)){
			instructionsNumber=instructionsCountMap.get(objectID+"@"+blockID);
		}

		int n = 1;
		if(countMap.containsKey(objectID+"@"+blockID)){
			n = countMap.get(objectID+"@"+blockID);
			n++;
		}

		System.out.println("Oggetto: " + objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
				+" numero istruzioni nel blocco: " +instructionsNumber);
		
		
		writeStatisticsData(objectID, blockCode, blockID, n);
		
		writePathsFile(objectID, blockID, ilMioArrayDiBooleani);
			
			
		
		instructionsNumber=-1;
		
		countMap.put(objectID+"@"+blockID, n);
		System.out.println(countMap.size());

		if(recordPath){ //se devo registrare il cammino
			blockList.add(objectID + "@" + blockID);
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writePathsFile(String objectID, int blockID,
			boolean[] ilMioArrayDiBooleani) throws IOException {
		String booleanString="";
		for (int i=0; i<ilMioArrayDiBooleani.length; i++){
			booleanString+=ilMioArrayDiBooleani[i] +" ";
		}
		PrintWriter printWriter;
		String pathsFilePath="C:/Users/Jacopo/Desktop/FilePercorsi.txt";
		
			if(firstTime){
				printWriter = new PrintWriter(new FileWriter(pathsFilePath));
				firstTime=false;
			}else{
				printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
			}
			
				//printWriter.println(objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
				//		+" numero istruzioni nel blocco: " +instructionsNumber);
			printWriter.println("percorso "+currentexecutionNumberPath+": " + objectID +" @" + blockID + "-"+booleanString);
				printWriter.flush();

			
			printWriter.close();
	}

	//inizia a registrare un percorso
	public static void recordPath(String objectID){
		if(recordPath == false){

			recordPath = true;
			//inizializza i campi
			currentexecutionNumberPath = countMap.get(objectID+"@0"); //il codice è 0 perchè è l'inizio del metodo
			currentObjectIDPath = objectID;
			blockList.clear();
			System.out.println("Inizio a registrare per " + objectID + "-" + currentexecutionNumberPath);
		}
	}

	public static void endRecordPath(String objectID){
		if(objectID == currentObjectIDPath){ //se l'ordine di fermarsi arriva dalla fine del metodo giusto
			recordPath = false;
			LinkedList<String> newList = (LinkedList<String>) blockList.clone();
			pathMap.put(objectID+"-"+ currentexecutionNumberPath, blockList);
			System.out.println("Finisco di registrare per " + objectID +"-"+ currentexecutionNumberPath);
			for (String s : newList) {
				System.out.print(s +  " >>> ");
			}
			System.out.println();
		}

	}



}
