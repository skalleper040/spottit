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
				String newWord = response.getBody().getArray().getJSONObject(0).getString("word");
				if(newWord.length() < 1) {
					newTitle += word +" ";
				}else {
					newTitle += newWord +" ";
				}
			} catch (Exception e) {
			}	
		}
		
		return newTitle;
	}
}
