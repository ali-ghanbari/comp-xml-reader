package org.mudebug.reader;

import java.io.*;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MutationDataReader {
	private final MutationDataVisitor visitor;
	private final Document document;

	public MutationDataReader(final File xmlFile,
                              final MutationDataVisitor visitor) {
		Document document = null;
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final InputStream is = new GZIPInputStream(new FileInputStream(xmlFile));
			document = builder.parse(is);
		} catch (Exception e) {
			e.getStackTrace();
			System.exit(-1);
		}
		this.document = document;
		this.visitor = visitor;
	}
	
	public void start() {
	    startXML();
	}

	private void startXML() {
		final NodeList nodeList = document.getDocumentElement().getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			final Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				
				final String status = element.getAttributes().getNamedItem("status").getNodeValue();
				
				final String sourceFile = element.getElementsByTagName("sourceFile")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue();
				final String mutatedClass = element.getElementsByTagName("mutatedClass")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue();
				final String mutatedMethod = element.getElementsByTagName("mutatedMethod")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue();
				final String methodDescription = element.getElementsByTagName("methodDescription")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue();
				final int lineNumber = Integer.parseInt(element.getElementsByTagName("lineNumber")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue());
				final int index = Integer.parseInt(element.getElementsByTagName("index")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue());
				final double susp = Double.parseDouble(element.getElementsByTagName("suspValue")
                        .item(0)
                        .getChildNodes()
                        .item(0)
                        .getNodeValue());
				final String mutator = element.getElementsByTagName("mutator")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue();
				final String mutationDescription = element.getElementsByTagName("description")
						.item(0)
						.getChildNodes()
						.item(0)
						.getNodeValue();
				
				Node item0; 
				
				item0 = element.getElementsByTagName("killingTests")
						.item(0)
						.getChildNodes()
						.item(0);
				final String[] killingTests;
				if(item0 == null) {
					killingTests = new String[0];
				} else {
					final String temp = item0.getNodeValue().trim();
					if(temp.isEmpty()) {
						killingTests = new String[0];
					} else {
						killingTests = temp.split(",(\\s)*");
					}
				}
				
				item0 = element.getElementsByTagName("coveringTests")
						.item(0)
						.getChildNodes() 
						.item(0);
				final String[] coveringTests;
				if(item0 == null) {
					coveringTests = new String[0]; 
				} else {
					final String temp = item0.getNodeValue().trim();
					if(temp.isEmpty()) {
						coveringTests = new String[0];
					} else {
						coveringTests = temp.split(",(\\s)*"); 
					}
				}
				
				visitor.visitMutation(status,
						sourceFile,
						mutatedClass,
						mutatedMethod,
						methodDescription,
						lineNumber,
						index,
						susp,
						mutator,
						mutationDescription,
						killingTests,
						coveringTests);
			}
		}
	}
}
