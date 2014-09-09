package elaborationSystem;

import java.util.HashMap;
import java.util.LinkedList;

public class MyTracerClass {
	
	public static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	public static HashMap<String, LinkedList<String>> pathMap = new HashMap<String, LinkedList<String>>();
	public static boolean recordPath = false;
	public static LinkedList<String> blockList = new LinkedList<String>();
	public static String methodPath;
	
	
	public MyTracerClass(){
		countMap = new HashMap<String, Integer>();
	}

	public static void tracer(String objectID, int blockCode, int blockID){
		int n = 1;
		if(countMap.containsKey(objectID+"@"+blockID)){
			n = countMap.get(objectID+"@"+blockID);
			n++;
		}

		System.out.println("Oggetto: " + objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n);
		countMap.put(objectID+"@"+blockID, n);
		System.out.println(countMap.size());
		
		if(recordPath){ //se devo registrare il cammino
			blockList.add(objectID + "@" + blockID);
		}
		
		if(recordPath == false){
			LinkedList<String> newList = (LinkedList<String>) blockList.clone();
			pathMap.put(methodPath, blockList);
		}
		
	}
	
	//inizia a registrare un percorso
	public static void recordPath(String objectID){
		recordPath = true;
		//inizializza i campi
		int executionNumber = countMap.get(objectID+"@0"); //il codice è 0 perchè è l'inizio del metodo
		methodPath = objectID+"-"+ executionNumber;
		blockList.clear();
		
	}
	
	

}
