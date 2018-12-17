package entity;

import java.util.ArrayList;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class SpotifyPlaylist {
	private ArrayList<SpotifySong> songs;
	private String playlistId;
	
	public SpotifyPlaylist(String playlistId) {
		super();
		songs = new ArrayList<SpotifySong>();
		this.playlistId = playlistId;
	}

	public ArrayList<SpotifySong> getSongs() {
		return songs;
	}

	public void addSong(SpotifySong song) {
		songs.add(song);
	}

	public String getPlaylistId() {
		return playlistId;
	}
	
}
