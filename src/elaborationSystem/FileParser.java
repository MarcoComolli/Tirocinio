package elaborationSystem; 

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.internal.matchers.SubstringMatcher;

public class FileParser {
	
	public static final int CODE_IF = 0;
	public static final int CODE_ELSE = 1;
	public static final int CODE_WHILE = 2;
	public static final int CODE_DO = 3;
	public static final int CODE_FOR = 4;
	public static final int CODE_SWITCH = 5;
	public static final int CODE_CONDITIONAL = 6;
	public static final int CODE_CATCH = 7;
	public static final int CODE_CASE = 8;
	public static final int CODE_PACKAGE = 9;


	private final String READ_URI;
	private String writeUri;
	public Stack<Character> stack;
	public Stack<Stack<Character>> stackOfStack;
	private boolean processNextLine = false;
	private boolean processNextMethodLine = false;
	private boolean processSwitchCase = false;
	private boolean processNextCase = false;
	private boolean whileAfterDo = false;
	
	
	
	private SortedSet<Map.Entry<String, Integer>> currentMethodMapSorted;
	private Iterator<Entry<String, Integer>> iterator;
	private int curlyOpened = 0;
	private int curlyCountMethodEnd = 0;
	private int currentCode = -1;
	private int currentLine = 1;
	
	private String className;
	private String currentMethod ;
	private boolean currentMethodReturnVoid = false;
	private int currentBlockID = 0;
	private int nextMethodLine;
	private Entry<String,Integer> iteratorEntry;
	private int currentInstructionCount = 0;
	private boolean processFirstCase;
	private int arrayConditionsNumber=0;
	
	public FileParser(String readURI, TreeMap<String, Integer> methodMap, String writeURI) {
		this.READ_URI = readURI;
		System.out.println("READURI " + readURI);
		className = extractClassFromPathString(readURI);
		File f = new File(readURI);
		String packageName = extractPackageFromFile(f);
		SortedMap<String, Integer> currentMethodMap = getByPreffix(methodMap, packageName+"/" + className + " ");
//		System.out.println("|||||| MAPPA NON SORTED ||||||");
//		for (Map.Entry entry : currentMethodMap.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
		currentMethodMapSorted = entriesSortedByValues(currentMethodMap);
//		System.out.println("|||||| MAPPA SORTED ||||||");
//		for (Map.Entry entry : currentMethodMapSorted) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
	
	
		
		iterator = currentMethodMapSorted.iterator();
		if(iterator.hasNext()){
			iteratorEntry = iterator.next();
			nextMethodLine = iteratorEntry.getValue();
			currentMethod = iteratorEntry.getKey();
			if(checkReturnType(currentMethod).equals("void")){
				currentMethodReturnVoid = true;
			}
			else{
				currentMethodReturnVoid = false;
			}
			
			System.out.println("COSTRUTTORE - prossimo metodo " + currentMethod + " at line " + nextMethodLine);
		}
		
		writeUri = writeURI;//"C:/Users/Marco/Desktop/FileParsato"+fileindex+".txt";
		//fileindex++;
	}
	
	public void a(){
		FileReader fr = null;
		FileWriter fw = null;
		BufferedReader buffRead = null;
		BufferedWriter buffWrite = null;
		try {
			fr = new FileReader(READ_URI);
			fw = new FileWriter(writeUri);
			buffRead = new BufferedReader(fr);
			buffWrite = new BufferedWriter(fw);
			

			String line = buffRead.readLine();
			
			
            while(line != null){
            	line = CommentsRemover.removeComments(line);
            	String newLine = parseString(line);
    			buffWrite.write(newLine);
    			buffWrite.newLine();
    			buffWrite.flush();
	
    			line = buffRead.readLine();
    			currentLine++; 
    			if(currentLine == nextMethodLine && line != null){
    				currentMethod = iteratorEntry.getKey();
    				if(checkReturnType(currentMethod).equals("void")){
    					currentMethodReturnVoid = true;
    				}
    				else{
    					currentMethodReturnVoid = false;
    				}
    				line = insertMethodTracer(currentMethod, line);
    				if(processNextMethodLine){
    					nextMethodLine++;
    					processNextMethodLine = false;
    				}else{
    					if(iterator.hasNext()){
        					iteratorEntry = iterator.next();
        					nextMethodLine = iteratorEntry.getValue();
        					
        				}
    				}
    			}
            }  

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				
				buffRead.close();
				fr.close();
				buffWrite.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	
	private String insertMethodTracer(String currentMethod, String line) {
		int index = checkCurlyOpen(line);
		if(index != -1){
			if(currentMethodReturnVoid){
				curlyCountMethodEnd++;
			}
			currentBlockID = 0;
			return line.substring(0, index+1) + 
					" MyTracerClass.tracer(\""+currentMethod+"\",-1,"+currentBlockID+");" + 
					" MyTracerClass.recordPath(\""+currentMethod+"\");" +
					line.substring(index+1, line.length());
		}
		else if(line.contains(";")){
			processNextMethodLine = false;
			return line;
		}
		else{
			processNextMethodLine = true;
			return line;
		}
	}

	private int checkCurlyOpen(String line) {
		if(line.contains("{")){
			Pattern pattern = Pattern.compile("\\{");
			Matcher matcher = pattern.matcher(line); 
			int count = 0;
			
			int ind;
			while (matcher.find())
				count++;
						
			if(count == 1){ //una sola parentesi {
				if(!checkInString(line, "{", line.indexOf('{')) && !line.contains("'{'")){
					return line.indexOf("{");
				}
			}
			else{ //sicuramente maggiore di 1
				pattern = Pattern.compile("\'\\{\'"); //Pattern string you want to be matched
				matcher = pattern.matcher(line); 
				int sub = 0;
				while (matcher.find())
					sub++;
				count = count - sub;
				if(count != 0){
					if((ind = searchCurlyOpen(line)) != -1){
						return ind;
					}
				}
			}
			
		}
			return -1;
		
	}
	
	private int searchCurlyOpen(String line){
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '{'){
				indexList.add(i);
			}
		}
		int ind = 0;
		for (int i = 0; i < indexList.size(); i++) {
			ind = indexList.get(i);
			if(ind != 0 && ind != line.length() -1){ //charater
				if(line.charAt(ind-1) == '\'' && line.charAt(ind+1) == '\''){
					indexList.remove(i);
				}
			}
			if(checkInString(line, "{", ind)){
				indexList.remove(i);
			}
		}
		
		if(indexList.isEmpty()) {
			return -1;
		}
		else{
			return indexList.get(0);
		}
	}
	
	private String checkReturnType(String method){
		String[] splitted = method.split(",");
		if(splitted != null && splitted.length != 0){
			return splitted[1];
		}
		return null;
	}

	public int checkConstruct(String line) {
		int code = -1;
		
		if(line.contains("if")){
			if(checkKeyword("if", line) && !checkInString(line, "if", line.indexOf("if"))){
				code = CODE_IF;
			}
		}
		if(line.contains("else")){
			if(checkKeyword("else", line) && !checkInString(line, "else", line.indexOf("else"))){
				code = CODE_ELSE;
			}
		}

		if(line.contains("while")){
			if(checkKeyword("while", line) && !checkInString(line, "while", line.indexOf("while"))){
				code = CODE_WHILE;
			}
		}
		
		if(line.contains("do")){
			if(checkKeyword("do", line) && !checkInString(line, "do", line.indexOf("do"))){
				code = CODE_DO;
			}
		}
		if(line.contains("for")){
			if(checkKeyword("for", line) && !checkInString(line, "for", line.indexOf("for"))){
				code = CODE_FOR;
			}
			
		}
		if(line.contains("switch")){
			if(checkKeyword("switch", line) && !checkInString(line, "switch", line.indexOf("switch"))){
				code = CODE_SWITCH;
			}
		}
		
		if(line.contains("?")){
			if(checkKeyword("?", line) && !checkInString(line, "?", line.indexOf("?"))){
				code = CODE_CONDITIONAL;
			}
		}
		if(line.contains("catch")){
			if(checkKeyword("catch", line) && !checkInString(line, "catch", line.indexOf("catch"))){
				code = CODE_CATCH;
			}
		}
		if((line.contains("case") || line.contains("default:")) && !line.contains(").append") && !line.contains("case\"")){
			if(processSwitchCase){
				code = CODE_CASE;
			}

		}
		if(line.contains("package ")){
			if(checkKeyword("package", line) && !checkInString(line, "package", line.indexOf("package"))){
				code = CODE_PACKAGE;
			}
		}
		return code;
		
	}
	
	
	private boolean checkKeyword(String key, String line) {
		int index = line.indexOf(key);
		int keylength = key.length();
		if(index > 0 && ((int)line.charAt(index-1) >= 60 && (int)line.charAt(index-1) <= 122)){
			return false;
		}
		if(index != (line.length() - keylength) && ((int)line.charAt(index+keylength) >= 60 && (int)line.charAt(index+keylength) <= 122) ){
			return false;
		}
		return true;
	}

	public String parseString(String line) {
		int constructCode;
		String newLine = line;
		if(currentMethodReturnVoid){
			newLine = checkEndOfMethod(line);
		}
		else{
			newLine = checkReturns(line);
		}
		if(processSwitchCase){
			line = processCurly(line);
			newLine = line;
		}
		if(processNextLine){
			constructCode = currentCode;
			processNextLine = false;
		}
		else{
			constructCode = checkConstruct(line);
		}
		if(constructCode != -1){
			switch (constructCode) {
			case CODE_IF:
				newLine = processIf(line);
				break;
			case CODE_ELSE:
				newLine = processElse(line);
				break;
			case CODE_DO:
				newLine = processDo(line);
				break;
			case CODE_WHILE:
				newLine = processWhile(line);
				break;
			case CODE_FOR:
				newLine = processFor(line);
				break;
			case CODE_SWITCH:
				newLine = processSwitch(line);
				break;
			case CODE_CONDITIONAL:
				newLine = processConditional(line);
				break;
			case CODE_CATCH:
				newLine = processCatch(line);
				break;
			case CODE_CASE:
				newLine = processCase(line);
				break;
			case CODE_PACKAGE:
				newLine = addImport(line);
				break;
			default:
				break;
			}
			return newLine;
		}
		else{
			if(curlyOpened != 0){
				return closeCurly(line);
			}
			return newLine;
		}



	}

	
	//cerca se c'è un return e aggiunge la riga
	private String checkReturns(String line) {
		if(line.contains("return ") || line.contains("return;")){
			int ind = line.indexOf("return");
			if(!checkInString(line, "return ", ind) && !checkInString(line, "return;", ind)){
				return line.substring(0,ind) + " MyTracerClass.endRecordPath(\""+currentMethod+"\");" + line.substring(ind);
			}
		}
		return line;
	}

	//esegue il conteggio delle parentesi graffe e se è arrivato alla fine aggiunge la fine metodo
	private String checkEndOfMethod(String line) {
		line = checkReturns(line);
		if(curlyCountMethodEnd != 0){ //fai il tutto quando il count non è 0
			for (int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == '{'){ //se ho trovato una {
					if(!checkInString(line, "{", i)){ //se non è in una stringa
						curlyCountMethodEnd++;
					}
					
				}
				else if(line.charAt(i) == '}'){ //se ho trovato una }
					if(!checkInString(line, "}", i)){ //se non è in una stringa
						curlyCountMethodEnd--;
						if(curlyCountMethodEnd == 1){
							curlyCountMethodEnd = 0;
							return line.substring(0,i) + " MyTracerClass.endRecordPath(\""+currentMethod+"\");" + line.substring(i);
						}
					}
				}
			}
			
		}
		return line;
	}

	private String addImport(String line) {
		return line.concat(" import elaborationSystem.MyTracerClass;");
	}

	private String processCurly(String line) {
		for (int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '{'){
				stack.push('a');
			}
			if(line.charAt(i) == '}'){
				if(!stack.empty()){
					stack.pop();
				}
				else{
					processSwitchCase = false;
					if(stackOfStack != null && !stackOfStack.isEmpty()){
						processSwitchCase = true;
						stack = stackOfStack.pop();
					}
					return line.substring(0, i+1) + "}" + line.substring(i+1, line.length());
				}
			}
		}
		return line;
		
	}
	
	

	private String closeCurly(String line) {
		String newLine = "----ERRORE----";
		String curlies = "";
		for (int i = 0; i < curlyOpened; i++) {
			curlies = curlies.concat("}");
		}
		for (int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == ';'){
				newLine = line.substring(0, i+1) + curlies + line.substring(i+1, line.length());
				curlyOpened = 0;
				break;
			}
		}
		return newLine;
	}

	private String processCase(String line) {
		printStack();
		currentBlockID++;
		String newLine = null;
		int keywordIndex = 0;
		String tracerCatch = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_CASE+","+currentBlockID+");";
		if(line.contains(":")){
			keywordIndex = line.indexOf("case");
			if(keywordIndex == -1){
				keywordIndex = line.indexOf("default");
			}
			if(processFirstCase == false){
				line = line.substring(0, keywordIndex) + "}" + line.substring(keywordIndex, line.length());
			}
			for (int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == ':'){
					newLine = line.substring(0, i+1) + "{" +  tracerCatch + line.substring(i+1, line.length());
				}
			}
			if(processFirstCase){
				processFirstCase = false;
			}
			return newLine;
		}
		return line;
	}
	
	private String processCatch(String line) {
		currentBlockID++;
		String newLine = null;
		String tracerCatch = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_CATCH+","+currentBlockID+");";
		if(line.contains("{")){
			for (int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == '{'){
					newLine = line.substring(0, i+1) + tracerCatch + line.substring(i+1, line.length());
				}
			}
			return newLine;
		}
		return line;
	}
	
	private String processConditional(String line) {
		currentBlockID++;
		return line;
	}

	private String processSwitch(String line) {
		System.out.println("PRocess switch");
		processSwitchCase = true;
		processFirstCase = true;
		if(stack == null || stack.isEmpty()){
			stack = new Stack<Character>();
		}
		else{
			if(stackOfStack == null){
				stackOfStack = new Stack<Stack<Character>>();
			}
			stackOfStack.push(stack);
			stack = new Stack<Character>();
		}
		return line;
	}

	private String processFor(String line) {
		currentBlockID++;
		String newLine = null;
		String tracerFor = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_FOR+","+currentBlockID+");";
		int index = checkCurlyOpen(line);
		if(index != -1){
			String booleanArrayString = getBooleanArrayString(line);
			newLine = line.substring(0, index+1) + booleanArrayString+" "+tracerFor + line.substring(index+1, line.length());
			
			return newLine;
		}
		else{
			currentCode = CODE_FOR;
			processNextLine = true;
		}
		return line;
	}

	private String processWhile(String line) {
		currentBlockID++;
		String newLine = null;
		System.err.println("line: "+line);
		String tracerWhile = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_WHILE+","+currentBlockID+");";
		int index = checkCurlyOpen(line);
		if(whileAfterDo == false){
			//int index = checkCurlyOpen(line);
			if(index != -1){
				System.err.println("line1: "+line);
				newLine = line.substring(0, index+1) + tracerWhile + line.substring(index+1, line.length());			
				String booleanArrayString = getBooleanArrayString(line);		
				return booleanArrayString+"\n"+newLine;
			}
			else{
				System.err.println("line2: "+line);
				currentCode = CODE_WHILE;
				processNextLine = true;
			}
		}
		else{
			System.err.println("line3: "+line);
			whileAfterDo = false;
			if(line.contains("while")){
				newLine = line.substring(0, index+1) + tracerWhile + line.substring(index+1, line.length());				
				String booleanArrayString = getBooleanArrayString(line);			
			return booleanArrayString+"\n"+newLine;
			}
			
		}
		return line;
	}

	private String processDo(String line) {
		currentBlockID++;
		String newLine = null;
		whileAfterDo = true;
		String tracerDo = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_DO+","+currentBlockID+");";
		int index = checkCurlyOpen(line);
		if(index != -1){
			newLine = line.substring(0, index+1) + tracerDo + line.substring(index+1, line.length());
			return newLine;
		}
		else{
			currentCode = CODE_DO;
			processNextLine = true;
		}
		return line;
	}
	

	private String processElse(String line) {
		currentBlockID++;
		String newLine = null;
		String tracerElse = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_ELSE+","+currentBlockID+");";
		int index = checkCurlyOpen(line);
		if(index != -1){
			if(line.contains("if")){
				String booleanArrayString = getBooleanArrayString(line);
				newLine = line.substring(0, index+1) +booleanArrayString+" "+ tracerElse + line.substring(index+1, line.length());
				
				return newLine;
				
			}else{
		
			newLine = line.substring(0, index+1) + tracerElse + line.substring(index+1, line.length());
			
			return newLine;}
		}
		else{
			currentCode = CODE_ELSE;
			processNextLine = true;
		}
		return line;
	}

	private String processIf(String line) {
		currentBlockID++;
		String newLine = null;
		String tracerIf = " MyTracerClass.tracer(\""+currentMethod+"\","+CODE_IF+","+currentBlockID+");";
		int index = checkCurlyOpen(line);
		if(index != -1){
			newLine = line.substring(0, index+1) + tracerIf + line.substring(index+1, line.length());
			String booleanArrayString = getBooleanArrayString(line);
			return booleanArrayString +"\n"+newLine ;
		}
		else{
			currentCode = CODE_IF;
			processNextLine = true;
		}
		
		return line;
		
	}
	
	private int findInstructions(String line){
		int count = 0;
		if(line.contains(";")){
			for (int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == ';'){
					if(!checkInString(line, ";", i)){
						count++;
					}
				}
			}
		}
		return count;
	}
	
	//TODO
	//metodo per test cancellare finito il tutto
	private void printStack(){
		System.out.print("STACK: ");
		Enumeration<Character> e = stack.elements();
		while(e.hasMoreElements()){
			System.out.print(e.nextElement() + " ");
		}
		System.out.println();
	}
	
	public String extractClassFromPathString(String path){
		int i = path.lastIndexOf('\\');
		int j = path.lastIndexOf('.');
		return path.substring(i+1,j);
	}
	
	private SortedMap<String, Integer> getByPreffix(NavigableMap<String, Integer> myMap, String preffix ) {
	    return myMap.subMap( preffix, preffix + Character.MAX_VALUE );
	}
	
	private <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(new Comparator<Map.Entry<K,V>>() {
	    	
	            @Override 
	            public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                return e1.getValue().compareTo(e2.getValue());
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
	public String extractPackageFromFile(File f){
		String path = f.getParent();
		String temp;
		if(path != null){
			int i = path.lastIndexOf('\\');
			temp = path.substring(0,i);
			i = temp.lastIndexOf('\\');
			return path.substring(i+1).replace("\\", "/");
		}
		else{
			return null;
		}
	}
	
	private String extractFileFromPath(String path){
		int index = path.lastIndexOf('\\');
		path.substring(index);
		return path.substring(index+1);
	}
	
	/**
	 * Check if a specified key with start at beginIndex is in a string or not
	 * @param line
	 * @param key
	 * @param beginIndex
	 * @return true if the key is in a string false otherwise
	 */
	private boolean checkInString(String line, String key, int beginIndex) {
		if(beginIndex >= line.length()){
			return false;
		}
		boolean firstApex = false;
		for (int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '"'){
				if(i != 0 && line.charAt(i-1) != '\\'){
					if(firstApex){
						if(i < beginIndex){
							firstApex = false;
						}
						else{
							return true;
						}
					}
					else{
						if(i < beginIndex){
							firstApex = true;
						}
					}
				}	
			}

		}
		return false;
	}
	
	private String getBooleanArrayString(String line) {
		String[] operands=BooleanExpressionParser.extractOperands(line).split("\\r?\\n");
		//boolean[] boolArr = new boolArr[array.length]{cond1,stack.isEmpty()};

		String conditions = "";
		for(int i=0;i<operands.length;i++){
			conditions+=operands[i]+",";	
		}
		String booleanArrayString="boolean[] ilMioArrayDiBooleani"+arrayConditionsNumber+" ={"+conditions.substring(0, conditions.length()-1)+"};";
		arrayConditionsNumber++;
		return booleanArrayString;
	}
	
}
