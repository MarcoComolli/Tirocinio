package tests;

import java.io.IOException;

import org.junit.Test;

import sources.BaseConstruct;
import sources.BaseConstructDue;

public class GlobalTest2 {
	
	@Test
	public void BaseConstructTest2() throws IOException{
		BaseConstructDue bc2 = new BaseConstructDue();
     	bc2.methodIf2();
		BaseConstruct bc = new BaseConstruct();
     	bc.methodLoop();
     	bc2.methodTry();
     	bc2.methodSynchronized();
     	bc.methodIf();
		
	}

}
