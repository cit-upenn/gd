package merriam_webster_api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * This class integrated Merriam-Webster API. It sends request to the API and get response
 * about the thesaurus of the word. It then converts the response string (XML) to HTML format 
 * using xslt file. 
 * @author Qingxiao Dong
 *
 */
public class MerriamWebsterAPI {

	private static final String API_URL = "http://www.dictionaryapi.com/api/v1/references/thesaurus/xml/";
	private static final String KEY = "a635aced-28cf-441c-98f4-27b890201eb7";
	
	/**
	 * Send http request to API server and get response. It converts the response from XML format
	 * to HTML format for UI to use.
	 * @param word the word searched
	 * @return response string in HTML format
	 */
	public static String getThesaurusHtml(String word) {
		TransformerFactory factory = TransformerFactory.newInstance();
		String stylesheetPathname = "style.xsl";
		Source stylesheetSource = new StreamSource(new File(stylesheetPathname).getAbsoluteFile());
		String html = "";
		try {
			Transformer transformer = factory.newTransformer(stylesheetSource);
			String url = API_URL + word + "?key=" + KEY;
			String response = excutePost(url, "");
			if (response == null) {
				return "<html><body><h3>&nbsp;&nbsp;No response. Please verify your internet connection.</h3></body></html>";
			}
			File sourceFile = toFile(response, "thesaurus.xml");
			Source inputSource = new StreamSource(sourceFile.getAbsoluteFile());
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
	
	/**
	 * Writes the string to a file and returns the file.
	 * @param strToWrite contents that is going to be written in the file
	 * @param filename the filename of the file
	 * @return the file that contains the string
	 */
	private static File toFile(String strToWrite, String filename) {
		File xmlFile = new File(filename);
		try {
			FileWriter fw = new FileWriter(xmlFile);
			fw.write(strToWrite);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlFile;
	}
	
	/**
	 * Send http POST request to the API url and return the response in string.
	 * @param targetURL the target url
	 * @param urlParameters url parameters
	 * @return response in string (XML format)
	 */
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
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
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
	    
	    } catch (java.net.UnknownHostException e) { 
	      return null;
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
