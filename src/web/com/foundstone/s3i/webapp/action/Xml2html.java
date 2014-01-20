package com.foundstone.s3i.webapp.action;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Xml2html {
	public static String spew(String xslPath, String xmlPath) {
		try {

			return pullFeed(xslPath, xmlPath);

		} catch (TransformerConfigurationException e) {

		} catch (TransformerException e) {

		}

		try {
			return pullFeed(xslPath,
					"http://localhost:8989/HacmeBooks/data/info.xml");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "Problem with DataFeed";
	}

	/**
	 * @param xsl
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	private static String pullFeed(String xsl, String xmlPath)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = tFactory.newTransformer(new StreamSource(xsl));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamSource streamSource = new StreamSource(xmlPath);

		transformer.transform(streamSource, new StreamResult(baos));
		return baos.toString();
	}

	/**
	 * @param s
	 */
	private static void messWithS(List l) {
		// TODO Auto-generated method stub
		String x = "b";
		l.add(x);

	}

	public static void main(String[] args) {
		System.out.println(spew(
				"http://localhost:8989/HacmeBooks/xsl/info.xsl",
				"http://www.foundstone.com/home/info.xml"));
	}
}