package tests;

import java.io.IOException;

import org.junit.Test;

public class GlobalTest3 {
	@Test
	public void AllTests() throws IOException{
		GlobalTest gb= new GlobalTest();
		GlobalTest2 gb2= new GlobalTest2();
		
		gb2.BaseConstructTest2();
		gb.BaseConstructTest();
	}

	@Test
	public void GlobalTest3Test() throws IOException{
		AllTests();
	}
	
}
