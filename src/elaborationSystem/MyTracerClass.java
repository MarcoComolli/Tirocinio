package elaborationSystem;

import java.util.HashMap;
import java.util.LinkedList;

public class MyTracerClass {
	
	private static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	private static HashMap<String, LinkedList<String>> pathMap = new HashMap<String, LinkedList<String>>();
	private static boolean recordPath = false;
	private static LinkedList<String> blockList = new LinkedList<String>();
	private static String currentObjectIDPath;
	private static int currentexecutionNumberPath;
	
	
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
		
		
			
		
	}
	
	//inizia a registrare un percorso
	public static void recordPath(String objectID){
		if(recordPath == false){
			System.out.println("Inizio a registrare per " + objectID);
			recordPath = true;
			//inizializza i campi
			currentexecutionNumberPath = countMap.get(objectID+"@0"); //il codice è 0 perchè è l'inizio del metodo
			currentObjectIDPath = objectID;
			blockList.clear();
		}
	}
	
	public static void endRecordPath(String objectID){
		if(objectID == currentObjectIDPath){ //se l'ordine di fermarsi arriva dalla fine del metodo giusto
			System.out.println("Finisco di registrare per " + objectID);
			recordPath = false;
			LinkedList<String> newList = (LinkedList<String>) blockList.clone();
			pathMap.put(objectID+"-"+ currentexecutionNumberPath, blockList);
		}
	
	}
	
	

}
