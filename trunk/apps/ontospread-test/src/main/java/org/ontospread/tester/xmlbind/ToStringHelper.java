package org.ontospread.tester.xmlbind;

import java.io.StringWriter;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class ToStringHelper {
	
    public static String print(Document doc){			
    	StringWriter writer = new StringWriter();
	    try {
			TransformerFactory.newInstance().newTransformer().transform( new DOMSource(doc),  new StreamResult(writer));
		} catch (Exception e){
		}
		return writer.toString();
    }

}
