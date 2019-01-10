package backend;

import org.json.JSONException;
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

	public static String getLyricsAsString(String title, String artist) throws JSONException {
		JSONObject track = GetLyrics.getTrackId(title, artist);
		int arraySize = track.optJSONObject("message").optJSONObject("body").optJSONArray("track_list").length();
		if (arraySize > 0) {
			long musixMatchId = track.optJSONObject("message").optJSONObject("body").optJSONArray("track_list").getJSONObject(arraySize-1).getJSONObject("track").getInt("track_id");
			JSONObject jsonLyrics = GetLyrics.getLyricsFromTrackId(musixMatchId);

			// If we found lyrics, save it to the object
			if(jsonLyrics.optJSONObject("message").optJSONObject("header").optInt("status_code") == 200) {	
				String lyrics = jsonLyrics.optJSONObject("message").optJSONObject("body").optJSONObject("lyrics").optString("lyrics_body");

				// Delete the * in the end of the lyrics from musixcmatch
				if(lyrics.contains("*******")) {
					int beginIndex = lyrics.indexOf("*******");
					lyrics = lyrics.substring(0,beginIndex);
				}
				return lyrics;
			}
		}
		return null;
	}
}
