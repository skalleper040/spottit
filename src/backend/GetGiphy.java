package backend;

import java.util.ArrayList;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetGiphy {

	public static ArrayList<String> getGifs(String title) {
		String url = "http://api.giphy.com/v1/gifs/search";
		
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(url)
					.header("Accept", "application/json")
					.queryString("limit", "5")
					.queryString("api_key", "fMy0JVBAZxlxU02WJjO475CMg8FGTyMy")
					.queryString("q", title).asJson();
		} catch (Exception e) {
			System.out.println(e);
		}

		ArrayList<String> urls = new ArrayList<String>();
		try {
			for(int i = 0; i < response.getBody().getObject().getJSONArray("data").length(); i++) {
				urls.add(response.getBody().getObject().getJSONArray("data").getJSONObject(i).getJSONObject("images").getJSONObject("fixed_width").getString("url"));
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return urls;
	}
		
}
