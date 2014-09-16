package sources; import elaborationSystem.MyTracerClass;

public class BaseConstructDue {

	
	


	
	int y = 0;
	int x = 0;

	public void methodIf()

	{ MyTracerClass.tracer("aseConstructDue methodIf,void,;",-1,0); MyTracerClass.recordPath("aseConstructDue methodIf,void,;");
		if (true) {boolean[] ilMioArrayDiBooleani0 ={true};  MyTracerClass.tracer("aseConstructDue methodIf,void,;",0,1,ilMioArrayDiBooleani0);

		}

		if (true) {boolean[] ilMioArrayDiBooleani1 ={true};  MyTracerClass.tracer("aseConstructDue methodIf,void,;",0,2,ilMioArrayDiBooleani1);
		} else { MyTracerClass.tracer("aseConstructDue methodIf,void,;",1,3);
		}
		if (false) {boolean[] ilMioArrayDiBooleani2 ={false};  MyTracerClass.tracer("aseConstructDue methodIf,void,;",0,4,ilMioArrayDiBooleani2);
		}
		while (y > 0) { boolean[] ilMioArrayDiBooleani3 ={y > 0};  MyTracerClass.tracer("aseConstructDue methodIf,void,;",2,5,ilMioArrayDiBooleani3);
			int x = 0;
		}
		if (y == 0) {boolean[] ilMioArrayDiBooleani4 ={y == 0};  MyTracerClass.tracer("aseConstructDue methodIf,void,;",0,6,ilMioArrayDiBooleani4);
			int x = 0;
		}

		x = y > 3 ? 5 : 2;
	 MyTracerClass.endRecordPath("aseConstructDue methodIf,void,;");}
	


}
