package sources; import elaborationSystem.MyTracerClass;

import java.util.LinkedList;

public class BaseConstruct {

	int x = 0;
	int y = 0;

	public int methodIf() { MyTracerClass.tracer("BaseConstruct methodIf,int,;",-1,0); MyTracerClass.recordPath("BaseConstruct methodIf,int,;"); 
		



		if (x % 2 == 0 && x == 2) {boolean[] ilMioArrayDiBooleani0 ={x % 2 == 0 , x == 2};  MyTracerClass.tracer("BaseConstruct methodIf,int,;",0,1,ilMioArrayDiBooleani0);
			y++;
		} else if (x == 0) {boolean[] ilMioArrayDiBooleani1 ={x == 0};  MyTracerClass.tracer("BaseConstruct methodIf,int,;",10,2,ilMioArrayDiBooleani1);
			y--;
		}
		if (true) {boolean[] ilMioArrayDiBooleani2 ={true};  MyTracerClass.tracer("BaseConstruct methodIf,int,;",0,3,ilMioArrayDiBooleani2);
			y++;
		}
		x--; 
		if (true) {boolean[] ilMioArrayDiBooleani3 ={true};  MyTracerClass.tracer("BaseConstruct methodIf,int,;",0,4,ilMioArrayDiBooleani3);
			x++;
		} else { MyTracerClass.tracer("BaseConstruct methodIf,int,;",1,5);
			x--;
		}
		;
		;
		 MyTracerClass.endRecordPath("BaseConstruct methodIf,int,;");return y;
	}

	public void methodVoid() { MyTracerClass.tracer("BaseConstruct methodVoid,void,;",-1,0); MyTracerClass.recordPath("BaseConstruct methodVoid,void,;");

	 MyTracerClass.endRecordPath("BaseConstruct methodVoid,void,;");}

	




	public int methodLoop() { MyTracerClass.tracer("BaseConstruct methodLoop,int,;",-1,0); MyTracerClass.recordPath("BaseConstruct methodLoop,int,;");
		while (x < 0) { boolean[] ilMioArrayDiBooleani4 ={x < 0};  MyTracerClass.tracer("BaseConstruct methodLoop,int,;",2,1,ilMioArrayDiBooleani4);
			y++;
		}

		for (int i = 0; i < 10; i++) {boolean[] ilMioArrayDiBooleani5 ={ i < 10};  MyTracerClass.tracer("BaseConstruct methodLoop,int,;",4,2,ilMioArrayDiBooleani5);
			y++;
		}

		do { MyTracerClass.tracer("BaseConstruct methodLoop,int,;",3,3);
			y--;
boolean[] ilMioArrayDiBooleani6 ={x < 0};  MyTracerClass.tracer("BaseConstruct methodLoop,int,;",2,4);		} while (x < 0);

		 MyTracerClass.endRecordPath("BaseConstruct methodLoop,int,;");return y;
	}

	public int methodSwitch() { MyTracerClass.tracer("BaseConstruct methodSwitch,int,;",-1,0); MyTracerClass.recordPath("BaseConstruct methodSwitch,int,;");
		switch (x) {
		case 0:{ MyTracerClass.tracer("BaseConstruct methodSwitch,int,;",8,1); {
			y++;
			break;
		}

		}case 1:{ MyTracerClass.tracer("BaseConstruct methodSwitch,int,;",8,2);
			if (x == 0) {boolean[] ilMioArrayDiBooleani7 ={x == 0};  MyTracerClass.tracer("BaseConstruct methodSwitch,int,;",0,3,ilMioArrayDiBooleani7);
				y++;
			}
			y--;
		}default:{ MyTracerClass.tracer("BaseConstruct methodSwitch,int,;",8,4);
			break;
		}}

		 MyTracerClass.endRecordPath("BaseConstruct methodSwitch,int,;");return y;
	}

	public int methodConditionalOperator() { MyTracerClass.tracer("BaseConstruct methodConditionalOperator,int,;",-1,0); MyTracerClass.recordPath("BaseConstruct methodConditionalOperator,int,;");
		x = y > 3 ? 5 : 2;
		 MyTracerClass.endRecordPath("BaseConstruct methodConditionalOperator,int,;");return x;
	}

	public int methodConditionalOperator(int x) { MyTracerClass.tracer("BaseConstruct methodConditionalOperator,int,;int ",-1,0); MyTracerClass.recordPath("BaseConstruct methodConditionalOperator,int,;int ");
		
		 MyTracerClass.endRecordPath("BaseConstruct methodConditionalOperator,int,;int ");return x;
	}

	public int methodConditionalOperator(int x, float iffy, int x1) { MyTracerClass.tracer("BaseConstruct methodConditionalOperator,int,;int float int ",-1,0); MyTracerClass.recordPath("BaseConstruct methodConditionalOperator,int,;int float int ");
		
		try { MyTracerClass.tracer("BaseConstruct methodConditionalOperator,int,;int float int ",11,1);
			String a = null;
			a.toString();
		} catch (NullPointerException | ArithmeticException e) { MyTracerClass.tracer("BaseConstruct methodConditionalOperator,int,;int float int ",7,2);
			System.out.println(e.getClass());
		}
		 MyTracerClass.endRecordPath("BaseConstruct methodConditionalOperator,int,;int float int ");return x;
	}

	public int methodForEach(int x) { MyTracerClass.tracer("BaseConstruct methodForEach,int,;int ",-1,0); MyTracerClass.recordPath("BaseConstruct methodForEach,int,;int ");

		LinkedList<String> s = new LinkedList();
		for (String str : s) { MyTracerClass.tracer("BaseConstruct methodForEach,int,;int ",4,1);
			x++;
		}
		 MyTracerClass.endRecordPath("BaseConstruct methodForEach,int,;int ");return x;
	}

}
