package tests;

import java.io.IOException;

import org.junit.Test;

import sources.BaseConstruct;

public class GlobalTest {

	@Test
	public void BaseConstructTest() throws IOException{
		BaseConstruct bc = new BaseConstruct();
		
		bc.methodIf();
		bc.methodVoid();
		bc.methodLoop();
		bc.methodSwitch();
		bc.methodConditionalOperator();
		bc.methodConditionalOperator(1);
		bc.methodConditionalOperator(0, 0, 0);
		
	}
}
