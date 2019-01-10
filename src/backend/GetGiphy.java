package backend;


import java.util.ArrayList;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetGiphy {
	
	public static String getGif(String searchWord) {
		HttpResponse<JsonNode> response = null;
		String giphyUrl = "http://api.giphy.com/v1/gifs/random";
		try {
			response = Unirest.get(giphyUrl)
					.header("Accept", "application/json")
					.queryString("limit", "1")
					.queryString("api_key", "fMy0JVBAZxlxU02WJjO475CMg8FGTyMy")
					.queryString("q", searchWord).asJson();
		} catch (Exception e) {
			System.out.println(e);
		}

		String url = "";
		try {
			for(int i = 0; i < response.getBody().getObject().getJSONArray("data").length(); i++) {
				url = (response.getBody().getObject().getJSONArray("data").getJSONObject(i).getJSONObject("images").getJSONObject("fixed_width").getString("url"));
				
			}
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
