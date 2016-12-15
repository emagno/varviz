package cmu.varviz.io.graphviz;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import cmu.varviz.io.xml.XMLReader;
import cmu.varviz.io.xml.XMLWriter;
import cmu.varviz.trace.Trace;
import cmu.vaviz.io.testutils.TraceFactory;
import de.fosd.typechef.featureexpr.FeatureExprFactory;

public class GrapVizExportTest {

	@Test
	public void testXMLexportImportDotExport() throws ParserConfigurationException, TransformerException, IOException, SAXException {
		FeatureExprFactory.setDefault(FeatureExprFactory.bdd());
		Trace trace = TraceFactory.createTrace();
		XMLWriter writer = new XMLWriter(trace);
		String content = writer.write();
		XMLReader reader = new XMLReader();
		
		Trace traceRead = reader.readXML(content);
		GrapVizExport exporter = new GrapVizExport("graph", traceRead);
		exporter.write();
	}

}