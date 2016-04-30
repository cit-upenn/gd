package yandex_api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YandexAPI {
	private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
	private static final String KEY = "trnsl.1.1.20160422T202513Z.720c3fc3ac965a79.71e018f32c00ddf7a7ab4ac9d26d1e567ea2e420";
	private static final String TRANS_DIR = "en-zh";
	
	public static String generateURL(String targetWord) {
		return (API_URL+"?key="+KEY+"&text="+targetWord+"&lang="+TRANS_DIR);
	}

	/**
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
	
	public static String parseResponse(String response) {
		int firstIndex = response.indexOf("[\"") + 2;
		int secondIndex = response.indexOf("\"]");
		String translation = response.substring(firstIndex, secondIndex);
		return translation;
	}
	
	public static String chnTranslation (String vocab) {
		String query = generateURL(vocab);
		String request = executeGet(query);
		String translation = parseResponse(request);
		String retVal = translation.matches("[\\w]+")? "":translation;
		return retVal;
	}
	
	public static void main (String[]args) {
		System.out.println(chnTranslation("apple"));
	}
}
