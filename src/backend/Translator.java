package backend;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


/**
 * Class that translates a text to another language
 *
 */
public class Translator {

/**
 * Translates lyrics to another language
 * @param sl the orignal lanugage
 * @param tl langague to translate to
 * @param lyrics the text to translate
 * @return translated text
 */
	public static String translateLyrics(String sl, String tl, String lyrics) {
		String url = "https://translate.googleapis.com/translate_a/single";
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(url)
					.header("Accept", "application/json")
					.queryString("client", "gtx")
					.queryString("sl", "en")
					.queryString("tl", "da")
					.queryString("dt", "t")
					.queryString("q", lyrics).asJson();
		} catch (Exception e) {
			System.out.println(e);
		}
		String translatedLyrics = "";
		
		try {
			for(int i = 0; i <response.getBody().getArray().getJSONArray(0).length();i++ ) {	
				translatedLyrics += response.getBody().getArray().optJSONArray(0).optJSONArray(i).get(0);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return translatedLyrics;
	}
}