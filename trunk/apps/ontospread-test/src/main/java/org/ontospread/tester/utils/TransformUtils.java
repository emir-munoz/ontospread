package org.ontospread.tester.utils;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class TransformUtils {
    public static String print(Document doc, String xslFile){			
    	StringWriter writer = new StringWriter();
	    try {
			TransformerFactory factory = TransformerFactory.newInstance();
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = contextClassLoader.getResourceAsStream(xslFile);
			Transformer newTransformer = factory.newTransformer(new StreamSource(in));
			newTransformer.transform( new DOMSource(doc),  new StreamResult(writer));
		} catch (Exception e){

		}
		return writer.toString();
    }
}
