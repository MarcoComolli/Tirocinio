package sources; import elaborationSystem.MyTracerClass;

public class BaseConstruct {

	int x = 0;
	int y = 0;

	public int methodIf() { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodIf."); 
		



		if (x % 2 == 0 && x == 2) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf.",0,1);
			y++;
		} else if (x == 0) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf.",1,2);
			y--;
		}
		if (true) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf.",0,3);
			y++;
		}
		x--; 
		if (true) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf.",0,4);
			x++;
		} else { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf.",1,5);
			x--;
		}
		;
		;
		return y;
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodIf.");}

	




	public int methodLoop() { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodLoop.");
		while (x < 0) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop.",2,1);
			y++;
		}

		for (int i = 0; i < 10; i++) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop.",4,2);
			y++;
		}

		do { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop.",3,3);
			y--;
		} while (x < 0);

		return y;
	}

	public int methodSwitch() 
	{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch.",2,1); MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodSwitch.");
		switch (x){
		case 0:{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch.",8,2);{
			y++;
			break;}

		}case 1:{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch.",8,3);
			if(x == 0){ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch.",0,4);
				y++;
			}
			y--;
		}default:{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch.",8,5);
			break;
		}}

		return y;
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodSwitch.");}

	public int methodConditionalOperator() { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodConditionalOperator.");
		x = y > 3 ? 5 : 2;
		return x;
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodConditionalOperator.");}

	public int methodConditionalOperator(int x) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator.int ",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodConditionalOperator.int ");
		
		return x;
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodConditionalOperator.int ");}

	public int methodConditionalOperator(int x, float iffy, int x1) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator.int float int ",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodConditionalOperator.int float int ");
		
		try {
			String a = null;
			a.toString();
		} catch (NullPointerException | ArithmeticException e) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator.int float int ",7,1);
			System.out.println(e.getClass());
		}
		return x;
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodConditionalOperator.int float int ");}

}
