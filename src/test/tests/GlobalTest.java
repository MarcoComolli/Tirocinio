package tests;

import java.io.IOException;

import org.junit.Test;

import sources.BaseConstruct;
import sources.BaseConstructDue;

public class GlobalTest {

	@Test
	public void BaseConstructTest() throws IOException{
		BaseConstruct bc = new BaseConstruct();
		BaseConstructDue bc2 = new BaseConstructDue();
		bc.methodIf();
		bc.methodVoid();
		bc.methodLoop();
		bc.methodSwitch();
		bc2.methodSynchronized();
		bc.methodConditionalOperator();
		bc.methodConditionalOperator(1);
		bc.methodConditionalOperator(0, 0, 0);
		bc.methodIf();
		
	}
}
