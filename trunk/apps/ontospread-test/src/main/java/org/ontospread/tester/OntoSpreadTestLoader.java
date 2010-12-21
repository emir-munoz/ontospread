package org.ontospread.tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.ontospread.tester.xmlbind.DocumentBuilderException;
import org.ontospread.tester.xmlbind.DocumentBuilderHelper;
import org.ontospread.tester.xmlbind.Test;
import org.ontospread.tester.xmlbind.TestBattery;
import org.ontospread.tester.xmlbind.TestBatteryXMLBind;
import org.w3c.dom.Document;

public class OntoSpreadTestLoader {
	TestBatteryXMLBind bind = new TestBatteryXMLBind();
	private TestBattery testBattery;
	private int test = 0;
	
	public OntoSpreadTestLoader(String testFile, boolean classpath) throws DocumentBuilderException, JAXBException, FileNotFoundException{
		this.loadTestFiles(testFile, classpath);
	}
	
	private void loadTestFiles(String testFile, boolean classpath) throws DocumentBuilderException, JAXBException, FileNotFoundException{
		InputStream is = null;
		if(classpath){
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(testFile);
		}else{
			is = new FileInputStream(new File(testFile));
		}
		Document doc = DocumentBuilderHelper.getDocumentFromInputStream(is);		
		this.testBattery = bind.restoreTestBattery(doc);
		test = 0;
	}
	
	
	public Test next(){
		return (test<=testBattery.getTests().size()-1)?testBattery.getTests().get(test++):null;
	}
}
