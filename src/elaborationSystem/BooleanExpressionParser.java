package elaborationSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanExpressionParser {

	public static void main(String[] args) {

		String[] a = new String[10];
		a[0] = "if(x == 3 && tested ){";
		a[1] = "else if( (y -3) != 4 || y == 2 && alfa != 9)";
		a[2] = "for(int i = 0; i< 10; i++){";
		a[3] = "if(stack.isempty() | list.size() == 0)";
		a[4] = "if(stack.isempty())";
		a[5] = "for(int i = 0; i< 10 & list.size() == 0; i++){";
		a[6] = "for(String str:lista) {";
		a[7] = "if (isAttributeAccess(node) || (isMethodCall(node) && isForeignGetterSetterCall(node)))";
		a[8] = "if ((stack.isEmpty() || (isMethodCall(node) && isMethodBla(node) & (line.startWith(\"true || false\") | x == 0))))";
		a[9] = "if((methodDeclaration.getMethodName().equals(\"setUp\") || methodDeclaration.getMethodName().equals(\"tearDown\")) && junitImported)";
		
	}

	//estrae gli operatori nell'array result
	public static String[] extractOperands(String string) {
		String expression = extractBooleanExpression(string);
		if(expression.equals("forEach")){
			return new String[]{"forEach"};
		}
		else{
			String[] predicates = checkInnerPredicates(expression);
			return predicates;
		}

		
	}

	private static String[] checkInnerPredicates(String expression) {
		Pattern pattern = Pattern.compile("&&|&|\\|\\||\\|");
		Matcher matcher = pattern.matcher(expression); 
		
		ArrayList<Integer> startIndexList = new ArrayList<Integer>();
		ArrayList<Integer> endIndexList = new ArrayList<Integer>();
		
		while(matcher.find()){
			int ind = matcher.toMatchResult().start();
			String key = matcher.group();
			if(!checkInString(expression, key, ind)){ //se non è una stringa
				startIndexList.add(ind);
				endIndexList.add(matcher.toMatchResult().end());
			}
		}
		
		if(startIndexList.isEmpty()){
			return new String[]{expression};
		}
		ArrayList<String> predicates = new ArrayList<String>();
		int from = 0, curr = -1, first = -1;
		for (int i = 0; i < startIndexList.size(); i++) {
			curr = startIndexList.get(i);
			if(i != 0){
				from = endIndexList.get(i-1);
			}
			
			int curly = 0;
			for(int j = curr; j > from-1; j--){
				if(expression.charAt(j) == '('){
					curly++;
					if(curly == 1){
						first = j;
						break;
					}
				}
				else if(expression.charAt(j) == ')'){
					curly--;
				}
			}
			if(curly > 0){
				predicates.add(expression.substring(first+1, curr));
			}
			else if(curly == 0){
				predicates.add(expression.substring(from, curr));
			}
			else{
				int ind = -1;
				for(int j = curr; j > from; j-- ){
					if(expression.charAt(j) == ')'){
						curly++;
						if(curly == 0){
							ind = j;
							break;
						}
					}
				}
				predicates.add(expression.substring(from, ind));
				
			}
			
			curly = 0;
			int last = expression.length();
			if(i == startIndexList.size()-1){ //se è l'ultimo della lista
				for(int j = curr; j < expression.length(); j++){
					if(expression.charAt(j) == '('){
						curly++;
					}
					else if(expression.charAt(j) == ')'){
						curly--;
						if(curly < 0){
							last = j;
							break;
						}
					}
				}
				curr = endIndexList.get(i);
				
				predicates.add(expression.substring(curr, last));
			}
		}
		String[] condizioni = new String[predicates.size()];
		for (int i = 0; i < condizioni.length; i++) {
			condizioni[i] = predicates.get(i);
		}
		return condizioni;
	}

	//estrae l'espressione booleana dalla riga di codice parsata
	private static String extractBooleanExpression(String string) {
		int indexOpen = string.indexOf('(');
		int indexClosed = -1;
		int curly = 0;
		for (int i = indexOpen; i < string.length(); i++) {
			if(string.charAt(i) == '('){
				curly++;
			}
			else if(string.charAt(i) == ')'){
				curly--;
			}
			if(curly == 0){
				indexClosed = i;
				break;
			}
		}
		
		//condizione for	
		String predicate = string.substring(indexOpen+1,indexClosed);
		if(predicate.contains(";") && !checkIsChar(predicate, ';')){ 
			if(!checkInString(predicate, ";", predicate.indexOf(";"))){
				String[] innerFor= predicate.split(";");
				if(innerFor.length > 2){
					return innerFor[1];
				}
				else{
					return "true"; //fatto per il caso for(;;){}
				}
			}
		}
		if(predicate.contains(":")){
			if(!checkInString(predicate, ":", predicate.indexOf(":")) && !checkIsChar(predicate, ':')){//se non e' condizione di un for
				return "forEach";
			}
		}

			return predicate;
		
	}
	
	private static boolean checkIsChar(String line, char key){
		int index = line.indexOf(key);
		if(index != 0 && index != line.length()-1){
			if(line.charAt(index -1) == '\'' && line.charAt(index+1) == '\''){
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkInString(String line, String key, int beginIndex) {
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

}
