package org.ontospread.tester.xmlbind;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class ReportBatteryXMLBind {
	
	private static Logger logger = Logger.getLogger(ReportBatteryXMLBind.class);
	
	public static final String PACKAGE_NAME = ReportBatteryXMLBind.class.getPackage().getName();
	
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	
	public ReportBatteryXMLBind() {
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(PACKAGE_NAME);
			this.unmarshaller = jc.createUnmarshaller();
			this.marshaller = jc.createMarshaller();
			//            unmarshaller.setEventHandler(eventHandler);
			//            marshaller.setEventHandler(eventHandler);
		} catch(JAXBException e) {
			//			
		}
	}

	public ReportBattery restoreTestReportBattery(Node node) throws JAXBException {
		try {
			return (ReportBattery) unmarshaller.unmarshal(node);
		} catch (JAXBException je) {
			throw je;
		}
	}
	
	
	public Document serializeTestReportBattery(ReportBattery testReportBattery) throws JAXBException, DocumentBuilderException {		
		try {
			Document doc = DocumentBuilderHelper.getEmptyDocument();
			marshaller.marshal(testReportBattery, doc);
			return doc;
		} catch (JAXBException je) {
			throw je;
		} catch (DocumentBuilderException e) {
			throw e;
		}        
	}

	/**
	 * Singleton field
	 */
	private static ReportBatteryXMLBind instance;
	
	/**
	 * Singleton method
	 * 
	 * @return The singleton instance of this class 
	 * @throws XmlBindException
	 */
	public static ReportBatteryXMLBind getInstance() {
		if (instance == null) {
			instance = new ReportBatteryXMLBind();
		}
		return instance;
	}	
}
