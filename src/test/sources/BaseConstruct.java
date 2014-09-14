package sources; import java.io.IOException;

import elaborationSystem.MyTracerClass;

public class BaseConstruct {

	int x = 0;
	int y = 0;

	public int methodIf() throws IOException { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf,int,.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodIf,int,."); 
		



		if (x % 2 == 0 && x == 2) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf,int,.",0,1);
			y++;
		} else if (x == 0) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf,int,.",1,2);
			y--;
		}
		if (true) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf,int,.",0,3);
			y++;
		}
		x--; 
		if (true) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf,int,.",0,4);
			x++;
		} else { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodIf,int,.",1,5);
			x--;
		}
		;
		;
		 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodIf,int,.");return y;
	}
	
	public void methodVoid() throws IOException{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodVoid,void,.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodVoid,void,.");
		
	 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodVoid,void,.");}

	




	public int methodLoop() throws IOException { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop,int,.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodLoop,int,.");
		while (x < 0) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop,int,.",2,1);
			y++;
		}

		for (int i = 0; i < 10; i++) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop,int,.",4,2);
			y++;
		}

		do { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodLoop,int,.",3,3);
			y--;
		} while (x < 0);

		 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodLoop,int,.");return y;
	}

	public int methodSwitch() throws IOException 
	{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch,int,.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodSwitch,int,.");
		switch (x){
		case 0:{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch,int,.",8,1);{
			y++;
			break;}

		}case 1:{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch,int,.",8,2);
			if(x == 0){ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch,int,.",0,3);
				y++;
			}
			y--;
		}default:{ MyTracerClass.tracer("src/originalFiles/BaseConstruct methodSwitch,int,.",8,4);
			break;
		}}

		 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodSwitch,int,.");return y;
	}

	public int methodConditionalOperator() throws IOException { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator,int,.",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodConditionalOperator,int,.");
		x = y > 3 ? 5 : 2;
		 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodConditionalOperator,int,.");return x;
	}

	public int methodConditionalOperator(int x) throws IOException { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int ",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int ");
		
		 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int ");return x;
	}

	public int methodConditionalOperator(int x, float iffy, int x1) throws IOException { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int float int ",-1,0); MyTracerClass.recordPath("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int float int ");
		
		try {
			String a = null;
			a.toString();
		} catch (NullPointerException | ArithmeticException e) { MyTracerClass.tracer("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int float int ",7,1);
			
		}
		 MyTracerClass.endRecordPath("src/originalFiles/BaseConstruct methodConditionalOperator,int,.int float int ");return x;
	}

}
