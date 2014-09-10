package originalFiles;

import java.util.LinkedList;

public class BaseConstruct {

	int x = 0;
	int y = 0;

	public int methodIf() { // + commento
		/*
		 * 
		 * asdasd
		 */
		if (x % 2 == 0 && x == 2) {
			y++;
		} else if (x == 0) {
			y--;
		}
		if (true) {
			y++;
		}
		x--; // is good return
		if (true) {
			x++;
		} else {
			x--;
		}
		;
		;
		return y;
	}
	
	public void methodVoid(){
		
	}

	/**
	 * if
	 * 
	 * @return
	 */
	public int methodLoop() {
		while (x < 0) {
			y++;
		}

		for (int i = 0; i < 10; i++) {
			y++;
		}

		do {
			y--;
		} while (x < 0);

		return y;
	}

	public int methodSwitch() 
	{
		switch (x){
		case 0:{
			y++;
			break;}

		case 1:
			if(x == 0){
				y++;
			}
			y--;
		default:
			break;
		}

		return y;
	}

	public int methodConditionalOperator() {
		x = y > 3 ? 5 : 2;
		return x;
	}

	public int methodConditionalOperator(int x) {
		// questa è una prova
		return x;
	}

	public int methodConditionalOperator(int x, float iffy, int x1) {
		// questa è una prova
		try {
			String a = null;
			a.toString();
		} catch (NullPointerException | ArithmeticException e) {
			System.out.println(e.getClass());
		}
		return x;
	}
	public int methodForEach(int x){ 
	
		LinkedList<String> s= new LinkedList();
	for (String str:s) {
		x++;
	}
	return x;
	}

}
