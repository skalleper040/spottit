package entity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;

import backend.GetGiphy;
import backend.GetLyrics;
import backend.Translator;


public class Song {
	private String artist;
	private String songName;
	private String trackId;
	private String albumCoverUrl;
	private String originalLyrics;
	private String translatedLyrics;
	private ArrayList<String> gifs;
	
	public Song(String artist, String songName, String trackId, String albumCoverUrl) {
		super();
		this.artist = artist;
		this.songName = songName;
		this.trackId = trackId;
		this.albumCoverUrl = albumCoverUrl;
		setLyrics();
		translateLyrics("da");
		setGiphys();
	}
	
	public Song(SpotifySong spotifySong) {
		this.artist = spotifySong.getArtist();
		this.songName = spotifySong.getSongName();
		this.trackId = spotifySong.getTrackId();
		this.albumCoverUrl = spotifySong.getAlbumCover();	
	}
	
	public void setGiphys() {
		gifs = GetGiphy.getGifs(this.songName);
		
	}
	
	public void setLyrics() {
		JSONObject track = GetLyrics.getTrackId(songName, artist);
		System.out.println(track);
		int arraySize = track.optJSONObject("message").optJSONObject("body").optJSONArray("track_list").length();
		String lyrics = "";
		try {
			long musixMatchId = track.optJSONObject("message").optJSONObject("body").optJSONArray("track_list").getJSONObject(arraySize-1).getJSONObject("track").getInt("track_id");
			System.out.println(musixMatchId);
			JSONObject jsonLyrics = GetLyrics.getLyricsFromTrackId(musixMatchId);
			this.originalLyrics = jsonLyrics.optJSONObject("message").optJSONObject("body").optJSONObject("lyrics").getString("lyrics_body");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void translateLyrics(String lang) {
		this.translatedLyrics = Translator.translateLyrics("auto", lang, originalLyrics);
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public void setAlbumCoverUrl(String albumCoverUrl) {
		this.albumCoverUrl = albumCoverUrl;
	}

	public void setOriginalLyrics(String originalLyrics) {
		this.originalLyrics = originalLyrics;
	}

	public void setTranslatedLyrics(String translatedLyrics) {
		this.translatedLyrics = translatedLyrics;
	}

	public void setGifs(ArrayList<String> gifs) {
		this.gifs = gifs;
	}
	
	
	public boolean validate() {
		if (originalLyrics != null && translatedLyrics != null && gifs != null) {
			return true;
		}
		return false;
	}
	
}
