package org.ontospread.gui.controller;

import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.player.SpreadPlayer;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.state.OntoSpreadState;

import junit.framework.TestCase;

public class ApplicationContextLocatorTest extends TestCase {

	public void testCreateProcess(){
		OntoSpreadProcess process = (OntoSpreadProcess) ApplicationContextLocator.getApplicationContext().getBean("process");
		assertNotNull(process);
		
	}
	public void testCreateExecProcess(){
		OntoSpreadProcess process = (OntoSpreadProcess) ApplicationContextLocator.getApplicationContext().getBean("process");
		OntoSpreadState state = (OntoSpreadState) ApplicationContextLocator.getApplicationContext().getBean("ontoSpreadState");
		assertNotNull(process);		
		assertNotNull(state);
		assertEquals(2, state.getInitialConcepts().length);
	}
	public void testPlayer() throws ConceptNotFoundException{
		SpreadPlayer player = (SpreadPlayer) ApplicationContextLocator.getApplicationContext().getBean("player");
		assertNotNull(player);
		while(player.hasNext()){
			player.next();
		}
	}
}
