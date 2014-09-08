package elaborationSystem;

import java.util.HashMap;

public class MyTracerClass {
	
	public static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	
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
		System.out.println(countMap);
		
	}
	

}
