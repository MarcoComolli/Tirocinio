package elaborationSystem;

public class BooleanExpressionParser {

	public static void main(String[] args) {
		
		String[] a = new String[7];
		a[0] = "if(x == 3 && tested ){";
		a[1] = "else if( (y -3) != 4 || y == 2 && alfa != 9)";
		a[2] = "for(int i = 0; i< 10; i++){";
		a[3] = "if(stack.isempty() | list.size() == 0)";
		a[4] = "if(stack.isempty())";
		a[5] = "for(int i = 0; i< 10 & list.size() == 0; i++){";
		a[6] = "for(String str:lista) {";
		
		for (int i = 0; i < a.length; i++) {
			System.out.println(extractOperands(a[i]));
			System.out.println();
		}
	}

	//estrae gli operatori nell'array result
	public static String extractOperands(String string) {
		String expression = extractBooleanExpression(string);
		System.out.println("Espressione: " + expression);
		String[] result = expression.split("&&|&|\\|\\||\\|");
		String res = "";
		System.out.println("operatori: " + result.length);
		for (int i = 0; i < result.length; i++) {
			//res += (i+1) + " " + result[i] + "\n" ;
			res+=result[i]+"\n";
		}
		return res;
	}

	//estrae l'espressione booleana dalla riga di codice parsata
	private static String extractBooleanExpression(String string) {
		System.out.println(string);
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
		if(curly != 0){
			System.out.println("E' su pi� righe " + curly);
		}
		System.out.println("INDICE: " +  indexClosed);
		//condizione for	
		if(string.substring(indexOpen+1,indexClosed).contains(";")){ //TODO il ; pu� essere contenuto in una stringa di una condizione
			String[] innerFor=string.substring(indexOpen+1,indexClosed).split(";");
			if(innerFor.length > 2){
				return innerFor[1];
			}
			else{
				return "true"; //fatto per il caso for(;;){}
			}
		
		}else if(string.substring(indexOpen+1,indexClosed).contains(":")){ //TODO il : pu� essere contenuto in una stringa di una condizione
			//se non e' condizione di un for
			return "forEach";
		}else{
			return string.substring(indexOpen+1,indexClosed);
		}
	}

}
