package org.ontospread.tester.xmlbind;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import org.w3c.dom.Document;

public class TestBatteryXMLBindTest extends TestCase {

	public void testLoadDocument(){
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-schema-1.xml");
		assertNotNull(is);
	}

	public void testLoadDocumentAsDOM() throws DocumentBuilderException{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-schema-1.xml");
		Document doc = DocumentBuilderHelper.getDocumentFromInputStream(is);
		assertNotNull(doc);
	}
	
	public void testCreateTestBattery() throws DocumentBuilderException, JAXBException{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-schema-1.xml");
		Document doc = DocumentBuilderHelper.getDocumentFromInputStream(is);
		TestBatteryXMLBind bind = new TestBatteryXMLBind();
		TestBattery testBattery = bind.restoreTestBattery(doc);
		assertNotNull(testBattery);
		doc = bind.serializeTestBattery(testBattery);
		assertNotNull(doc);
		//System.out.println(ToStringHelper.print(doc));
	}

}
