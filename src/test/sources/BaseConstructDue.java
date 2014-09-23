package sources; import elaborationSystem.MyTracerClass;

public class BaseConstructDue {

	
	


	
	int y = 0;
	int x = 0;

	public void methodIf2()

	{ MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",-1,0); MyTracerClass.recordPath("BaseConstructDue methodIf2,void,;");
		if (true) {boolean[] ilMioArrayDiBooleani0 ={true};  MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",0,1,ilMioArrayDiBooleani0);

		}

		if (true) {boolean[] ilMioArrayDiBooleani1 ={true};  MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",0,2,ilMioArrayDiBooleani1);
		} else { MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",1,3);
		}
		if (false) {boolean[] ilMioArrayDiBooleani2 ={false};  MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",0,4,ilMioArrayDiBooleani2);
		}
		while (y > 0) { boolean[] ilMioArrayDiBooleani3 ={y > 0};  MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",2,5,ilMioArrayDiBooleani3);
			int x = 0;
		}
		if (y == 0) {boolean[] ilMioArrayDiBooleani4 ={y == 0};  MyTracerClass.tracer("BaseConstructDue methodIf2,void,;",0,6,ilMioArrayDiBooleani4);
			int x = 0;
		}

		x = y > 3 ? 5 : 2;
	 MyTracerClass.endRecordPath("BaseConstructDue methodIf2,void,;");}

	


	public void methodTry() { MyTracerClass.tracer("BaseConstructDue methodTry,void,;",-1,0); MyTracerClass.recordPath("BaseConstructDue methodTry,void,;");
		int c = 3;
		try { MyTracerClass.tracer("BaseConstructDue methodTry,void,;",11,1);
			while (c > 0) { boolean[] ilMioArrayDiBooleani5 ={c > 0};  MyTracerClass.tracer("BaseConstructDue methodTry,void,;",2,2,ilMioArrayDiBooleani5);
				c--;
			}

		} catch (Exception e) { MyTracerClass.tracer("BaseConstructDue methodTry,void,;",7,3);

		} finally {
			c = 4;
			if (c != 4) {boolean[] ilMioArrayDiBooleani6 ={c != 4};  MyTracerClass.tracer("BaseConstructDue methodTry,void,;",0,4,ilMioArrayDiBooleani6);
				c = 0;
			} else { MyTracerClass.tracer("BaseConstructDue methodTry,void,;",1,5);
				c = 3;
			}
		}
	 MyTracerClass.endRecordPath("BaseConstructDue methodTry,void,;");}

	public void methodSynchronized() { MyTracerClass.tracer("BaseConstructDue methodSynchronized,void,;",-1,0); MyTracerClass.recordPath("BaseConstructDue methodSynchronized,void,;");
		int z = 2;
		if (z == 2 || false) {boolean[] ilMioArrayDiBooleani7 ={z == 2 , false};  MyTracerClass.tracer("BaseConstructDue methodSynchronized,void,;",0,1,ilMioArrayDiBooleani7);
			synchronized (this) {
				z++;

			}
		}
	 MyTracerClass.endRecordPath("BaseConstructDue methodSynchronized,void,;");}

}
