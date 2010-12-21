package org.ontospread.tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.ontospread.tester.to.StateDAOPairTO;
import org.ontospread.tester.utils.OntoSpreadStateXMLSerializer;
import org.ontospread.tester.utils.TransformUtils;
import org.ontospread.tester.xmlbind.DocumentBuilderException;
import org.ontospread.tester.xmlbind.FormatType;
import org.ontospread.tester.xmlbind.FormatTypeList;
import org.ontospread.tester.xmlbind.FunctionType;
import org.ontospread.tester.xmlbind.OntoSpreadConfigReport;
import org.ontospread.tester.xmlbind.ReportBattery;
import org.ontospread.tester.xmlbind.ReportBatteryXMLBind;
import org.ontospread.tester.xmlbind.StatTOListType;
import org.ontospread.tester.xmlbind.StatTOType;
import org.ontospread.tester.xmlbind.Test;
import org.ontospread.tester.xmlbind.TestInfoReport;
import org.ontospread.tester.xmlbind.TestReportType;
import org.ontospread.to.StatTO;
import org.ontospread.utils.DOMToString;
import org.w3c.dom.Document;

public class OntoSpreadTestReporter {

	public static final String GENERATE_TEX_TABLE_XSL = "generate-tex-table.xsl";
	public static final String GENERATE_HTML_XSL = "generate-html.xsl";
	protected static Logger logger = Logger.getLogger(OntoSpreadTestReporter.class);
	private static final String DEFAULT_STRING = "DEFAULT";
	private static final int DEFAULT_VALUE = 0;
	private ReportBatteryXMLBind reporter = new ReportBatteryXMLBind();
	
	public void reportIteration(Test test, StateDAOPairTO stateDAOPair, RestrictionManagerHelper manager, int iteration) throws JAXBException, DocumentBuilderException, IOException {	
		TestInfoReport testInfoReport = new TestInfoReport();
		TestReportType report = new TestReportType();
		ReportBattery reportBattery = new ReportBattery();
		
		testInfoReport.setId(test.getId()+"_iter_"+iteration);		
		testInfoReport.setResources(test.getResources());
		OntoSpreadConfigReport value = new OntoSpreadConfigReport();
		value.setActivation(manager.getSpreadMinAct()!=null?manager.getSpreadMinAct().getMinActivationValue():DEFAULT_VALUE);
		value.setContext(manager.getSpreadContext()!=null?manager.getSpreadContext().getContext():DEFAULT_STRING);
		value.setRetries(manager.getSpreadContext()!=null?manager.getSpreadContext().getCurrentRetries():DEFAULT_VALUE);
		value.setMaxConcepts(manager.getSpreadMaxConcepts()!=null?manager.getSpreadMaxConcepts().getMaxConceptsSpreaded():0);
		value.setMinConcepts(manager.getSpreadMinConcepts()!=null?manager.getSpreadMinConcepts().getMinConceptsSpreaded():0);
		value.setTime(manager.getSpreadTime()!=null?manager.getSpreadTime().getMaxTime():0);
		value.setPrizePaths(test.getOntoSpreadConfig().isPrizePaths());
		value.setRelations(test.getOntoSpreadConfig().getRelations());
		value.setFunction(manager.getFunctionType()!=null?manager.getFunctionType():FunctionType.H_1);
		testInfoReport.setOntoSpreadConfig(value);
		
		report.setOntoSpreadStateXML(OntoSpreadStateXMLSerializer.asXML(stateDAOPair.getOntoSpreadState()));
		
		StatTOListType stats = new StatTOListType();
		StatTO[] ontoStats = stateDAOPair.getOntoDAO().getStats();
		for (StatTO statTO : ontoStats) {
			StatTOType statTOType = new StatTOType();
			statTOType.setType(statTO.getType());
			statTOType.setValue(statTO.getValue());
			stats.getStatTOs().add(statTOType);
		}
		report.setOntologyStats(stats);
		
		report.getTestReports().add(testInfoReport);
		
		reportBattery.getReports().add(report);
		
		Document doc = reporter.serializeTestReportBattery(reportBattery);
		writeFile(new File(
				test.getOutputDirectory(),test.getId()+"_iter_"+iteration+".xml"),DOMToString.printXML(doc));
		//FIXME: Extract polimorfismo
		String xslFile = null;
		FormatTypeList formats = test.getFormats();
		for (FormatType format : formats.getFormats()) {
			xslFile = null;
			if(format.compareTo(FormatType.TEX) == 0){
				xslFile = GENERATE_TEX_TABLE_XSL;
			}else if(format.compareTo(FormatType.HTML)==0){
				//
				xslFile = GENERATE_HTML_XSL;
			}
			if(xslFile != null){
				String dir = createDir(test.getOutputDirectory(),format.toString());
				writeFile(new File(dir,test.getId()+"_iter_"+iteration+"."+format.toString().toLowerCase()),
						TransformUtils.print(doc, xslFile));
			}
		}
	
	}
	
	public void writeFile(File outputFile, String content) throws FileNotFoundException{
		logger.info("Writing report to "+outputFile.getAbsolutePath());		
		PrintWriter pw = new PrintWriter(outputFile);
		pw.println(content);
		pw.close();
	}
	
	public String createDir(String path, String name){
		File f = new File(path,name);
		if(!f.exists()){
			f.mkdir();
		}
		return f.getAbsolutePath();		
	}
		
}
