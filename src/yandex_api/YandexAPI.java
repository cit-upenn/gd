package yandex_api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is a class that integrates the Yandex API into our dictionary. 
 * When the user input an English vocab, we send out a request to the Yandex API 
 * and get back the Chn translation of the vocab as a string in JSON format. The translation 
 * is then parsed out and returned.
 * @author Yujie
 *
 */

public class YandexAPI {
	
	private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
	private static final String KEY = "trnsl.1.1.20160422T202513Z.720c3fc3ac965a79.71e018f32c00ddf7a7ab4ac9d26d1e567ea2e420";
	private static final String TRANS_DIR = "en-zh";   // from English to Chinese
	
	/**
	 * Helper for chnTranslation method
	 * generate the request URL according to the passed-in vocab
	 * @param targetWord
	 * @return request URL
	 */
	public static String generateURL(String targetWord) {
		return (API_URL+"?key="+KEY+"&text="+targetWord+"&lang="+TRANS_DIR);
	}

	
	/**
	 * Helper for chnTranslation method
	 * Send http request to the API url and return the response in string.
	 * @param targetURL the target url
	 * @return response in string
	 */
	private static String executeGet(String targetURL) {
		URL url;
		HttpURLConnection connection = null;  
		try {
			//Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
		
			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream (
					connection.getOutputStream ());
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
	
	
	/**
	 * Helper for chnTranslation method
	 * parse out the Chinese translation from the returned response
	 * @param response
	 * @return translation of the vocab
	 */
	public static String parseResponse(String response) {
		int firstIndex = response.indexOf("[\"") + 2;
		int secondIndex = response.indexOf("\"]");
		String translation = response.substring(firstIndex, secondIndex);
		return translation;
	}
	
	
	/**
	 * return the Chn tanslation of the passed-in English vocab
	 * @param vocab
	 * @return a blank str if the vocab is not translatable; ow the Chinese translation
	 */
	public static String chnTranslation (String vocab) {
		String query = generateURL(vocab);
		String request = executeGet(query);
		String translation = parseResponse(request);
		String retVal = translation.matches("[\\w]+")? "":translation;  // if returned translation contains ascii, return a blank string instead
		return retVal;
	}
	
	
	public static void main (String[]args) {
		System.out.println(chnTranslation("apple"));
	}
}
