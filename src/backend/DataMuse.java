package backend;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class DataMuse {

	public static String getNewTitle(String oldTitle) {
		String[] words = oldTitle.split("\\W+");
		String newTitle = "";
		HttpResponse<JsonNode> response = null;
		
		for(String word: words) {
			String url = "https://api.datamuse.com/words?ml="+word;
			System.out.println(url);
			try {
				response = Unirest.get(url)
						.header("Accept", "application/json").asJson();
				System.out.println(response.getBody().getArray().length());
				if(response.getBody().getArray().length() == 0) {
					newTitle += word +" ";
				}else {
					String newWord = response.getBody().getArray().optJSONObject(0).optString("word");
					newTitle += newWord +" ";
				}
				
				
			} catch (Exception e) {
			}	
		}
		
		return newTitle;
	}
}
