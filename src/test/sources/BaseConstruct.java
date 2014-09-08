package sources;

import elaborationSystem.MyTracerClass;

public class BaseConstruct {

	int x = 0;
	int y = 0;

	public int methodIf() { MyTracerClass.tracer("originalFiles/BaseConstruct methodIf.",-1,0); 
		



	if (x % 2 == 0 && x == 2) { MyTracerClass.tracer("originalFiles/BaseConstruct methodIf.",0,1);
			y++;
		} else if (x == 0) { MyTracerClass.tracer("originalFiles/BaseConstruct methodIf.",1,2);
			y--;
		}
		if (true) { MyTracerClass.tracer("originalFiles/BaseConstruct methodIf.",0,3);
			y++;
		}
		x--; 
		if (true) { MyTracerClass.tracer("originalFiles/BaseConstruct methodIf.",0,4);
			x++;
		} else { MyTracerClass.tracer("originalFiles/BaseConstruct methodIf.",1,5);
			x--;
		}
		;
		;
		return y;
	}

	




public int methodLoop() { MyTracerClass.tracer("originalFiles/BaseConstruct methodLoop.",-1,0);
		while (x < 0) { MyTracerClass.tracer("originalFiles/BaseConstruct methodLoop.",2,1);
			y++;
		}

		for (int i = 0; i < 10; i++) { MyTracerClass.tracer("originalFiles/BaseConstruct methodLoop.",4,2);
			y++;
		}

		do { MyTracerClass.tracer("originalFiles/BaseConstruct methodLoop.",3,3);
			y--;
		} while (x < 0);

		return y;
	}

	public int methodSwitch() 
	{ MyTracerClass.tracer("originalFiles/BaseConstruct methodSwitch.",-1,0);
		switch (x){
		case 0:{ MyTracerClass.tracer("originalFiles/BaseConstruct methodSwitch.",8,2);{
			y++;
			break;}

		}case 1:{ MyTracerClass.tracer("originalFiles/BaseConstruct methodSwitch.",8,3);
			if(x == 0){ MyTracerClass.tracer("originalFiles/BaseConstruct methodSwitch.",0,4);
				y++;
			}
			y--;
		}default:{ MyTracerClass.tracer("originalFiles/BaseConstruct methodSwitch.",8,5);
			break;
		}}

		return y;
	}

	public int methodConditionalOperator() { MyTracerClass.tracer("originalFiles/BaseConstruct methodConditionalOperator.",-1,0);
		x = y > 3 ? 5 : 2;
		return x;
	}

	public int methodConditionalOperator(int x) { MyTracerClass.tracer("originalFiles/BaseConstruct methodConditionalOperator.int ",-1,0);
		
		return x;
	}

	public int methodConditionalOperator(int x, float iffy, int x1) { MyTracerClass.tracer("originalFiles/BaseConstruct methodConditionalOperator.int float int ",-1,0);
		
		try {
			String a = null;
			a.toString();
		} catch (NullPointerException | ArithmeticException e) { MyTracerClass.tracer("originalFiles/BaseConstruct methodConditionalOperator.int float int ",7,1);
			System.out.println(e.getClass());
		}
		return x;
	}

}
