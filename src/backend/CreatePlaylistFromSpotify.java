package backend;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import entity.SpotifyPlaylist;
import entity.SpotifySong;

public class CreatePlaylistFromSpotify {
	private static JSONObject object = null;
	private String accessToken = "";
	private SpotifyPlaylist playlist;
	
	public String getPlaylist(String playlistId, String accessToken) {
		this.accessToken = accessToken;
		try {
			object = readJSONfromURL("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks").getBody().getObject();
			playlist = new SpotifyPlaylist(playlistId);
			int playlistSize = object.getJSONArray("items").length();

			for(int i = 0; i < playlistSize; i++) {
				JSONObject track = object.getJSONArray("items").getJSONObject(i).getJSONObject("track");
				String artist = track.getJSONArray("artists").getJSONObject(0).getString("name");
				String songName = track.getString("name");
				String trackId = track.getString("uri");
				String albumCoverUrl = track.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
				playlist.addSong(new SpotifySong(artist, songName, trackId, albumCoverUrl));
			}
		} catch (Exception e) {
			System.out.println("Error when fetching playlist");
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(playlist);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	public int checkResponse (String playlistId, String accessToken) {
		this.accessToken = accessToken;
		return readJSONfromURL("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks").getStatus();
	}
	
	public HttpResponse<JsonNode> readJSONfromURL(String url) {		
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(url)
					.header("Authorization", "Bearer "+accessToken)
					.header("Accept", "application/json").asJson();
		} catch (Exception e) {
		}
		return response;
	}
}
