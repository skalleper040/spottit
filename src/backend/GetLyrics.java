package backend;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetLyrics {
	private static String apikey = "0610864a81d53cfcd0e29b0116393091";
	
	
	public static JSONObject getTrackId(String title, String artist) {
		String url = "https://api.musixmatch.com/ws/1.1/track.search?format=json&callback=callback";
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(url)
					.header("Accept", "application/json")
					.queryString("q_track", title)
					.queryString("q_artist", artist)
					.queryString("quorum_factor", "1")
					.queryString("apikey", apikey).asJson();
		} catch (Exception e) {
		}
		return response.getBody().getObject();
	}
	
	public static JSONObject getLyricsFromTrackId(long trackId) {
		String url = "https://api.musixmatch.com/ws/1.1/track.lyrics.get?format=json&callback=callback";
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(url)
					.header("Accept", "application/json")
					.queryString("track_id", trackId)
					.queryString("apikey", apikey).asJson();
		} catch (Exception e) {
		}
		return response.getBody().getObject();
	}
}
