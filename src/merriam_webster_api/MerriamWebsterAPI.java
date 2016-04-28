package merriam_webster_api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.*;

public class MerriamWebsterAPI {

	private static final String API_URL = "http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/";
	private static final String KEY = "a635aced-28cf-441c-98f4-27b890201eb7";
	
	public static String getThesaurusHtml(String word) {
		TransformerFactory factory = TransformerFactory.newInstance();
		String stylesheetPathname = "style.xsl";
		Source stylesheetSource = new StreamSource(new File(stylesheetPathname).getAbsoluteFile());
		String html = "";
		try {
			Transformer transformer = factory.newTransformer(stylesheetSource);
			Source inputSource = new StreamSource(getFile(word).getAbsoluteFile());
			StringWriter writer = new StringWriter();
			transformer.transform(inputSource, new StreamResult(writer));
			html = writer.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}
	
	private static File getFile(String word) {
		String url = API_URL + word + "?key=" + KEY;
		String response = excutePost(url, "");
		File xmlFile = new File("thesaurus.xml");
		try {
			FileWriter fw = new FileWriter(xmlFile);
			fw.write(response);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlFile;
	}
	
	private static String excutePost(String targetURL, String urlParameters)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
}
