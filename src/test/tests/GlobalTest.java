package tests;

import org.junit.Test;

import sources.BaseConstruct;

public class GlobalTest {

	@Test
	public void BaseConstructTest(){
		BaseConstruct bc = new BaseConstruct();
		
		bc.methodIf();
		bc.methodLoop();
		bc.methodSwitch();
		bc.methodConditionalOperator();
		bc.methodConditionalOperator(1);
		bc.methodConditionalOperator(0, 0, 0);
		
	}
}
