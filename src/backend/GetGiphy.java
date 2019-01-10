package backend;


import java.util.ArrayList;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetGiphy {
	
	public static String getGif(String searchWord) {
		HttpResponse<JsonNode> response = null;
		String giphyUrl = "http://api.giphy.com/v1/gifs/translate";
		try {
			response = Unirest.get(giphyUrl)
					.header("Accept", "application/json")
					
					.queryString("api_key", "fMy0JVBAZxlxU02WJjO475CMg8FGTyMy")
					.queryString("s", searchWord).asJson();
		} catch (Exception e) {
			System.out.println(e);
		}

		String url = "";
		try {
				url = (response.getBody().getObject().getJSONObject("data").getJSONObject("images").getJSONObject("fixed_width").getString("url"));	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}

	public static ArrayList<String> getGifs(String title) {
		String[] words = title.split(" ");
		ArrayList<String> urls = new ArrayList<String>();
		
		for (String word:words) {
			urls.add(getGif(word));
		}
		
		return urls;
	}

}
