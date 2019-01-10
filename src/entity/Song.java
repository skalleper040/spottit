package entity;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;

import backend.DataMuse;
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
	private String newTitle;

	public Song(String artist, String songName, String trackId, String albumCoverUrl) {
		super();
		this.artist = artist;
		this.songName = songName;
		this.trackId = trackId;
		this.albumCoverUrl = albumCoverUrl;
		setLyrics();
		translateLyrics("da");
		setGiphys();
		setNewTitle();
	}

	public Song(SpotifySong spotifySong) {
		this.artist = spotifySong.getArtist();
		this.songName = spotifySong.getSongName();
		this.trackId = spotifySong.getTrackId();
		this.albumCoverUrl = spotifySong.getAlbumCover();	
	}

	public void setNewTitle() {
		this.newTitle = DataMuse.getNewTitle(songName);
	}

	public void setGiphys() {
		gifs = GetGiphy.getGifs(this.songName);

	}

	public void setLyrics() {

		// Search for the song on musixmatch
		JSONObject track = GetLyrics.getTrackId(songName, artist);
		int status_code = track.optJSONObject("message").optJSONObject("header").optInt("status_code");
		int arraySize = track.optJSONObject("message").optJSONObject("body").optJSONArray("track_list").length();
		// Check if we got a valid response, if so, try to get the list of lyric versions
		if(status_code == 200 && arraySize > 0) {
			System.out.println(track);
			
			String lyrics = "";
			try {

				// Get the latest version of lyrics
				long musixMatchId = track.optJSONObject("message").optJSONObject("body").optJSONArray("track_list").getJSONObject(arraySize-1).getJSONObject("track").getInt("track_id");
				JSONObject jsonLyrics = GetLyrics.getLyricsFromTrackId(musixMatchId);
				status_code = jsonLyrics.optJSONObject("message").optJSONObject("header").optInt("status_code");

				// If we found lyrics, save it to the object
				if(status_code == 200) {	
					lyrics = jsonLyrics.optJSONObject("message").optJSONObject("body").optJSONObject("lyrics").optString("lyrics_body");
					
					// Delete the * in the end of the lyrics from musixcmatch
					if(lyrics.contains("*******")) {
						int beginIndex = lyrics.indexOf("*******");
						lyrics = lyrics.substring(0,beginIndex);
					}
					this.originalLyrics = lyrics;
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void translateLyrics(String lang) {
		if(originalLyrics != "") {
			this.translatedLyrics = Translator.translateLyrics("auto", lang, originalLyrics);
		}

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
		if (originalLyrics != null && gifs.size() > 0) {
			return true;
		}
		return false;
	}

}
