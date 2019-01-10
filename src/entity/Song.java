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


/** 
 * Class that represents a song-object
 *
 */
public class Song {
	private String artist;
	private String songName;
	private String trackId;
	private String albumCoverUrl;
	private String originalLyrics;
	private String translatedLyrics;
	private ArrayList<String> gifs;
	private String newTitle;

	/**
	 * Constructs a song-object
	 * @param artist
	 * @param songName
	 * @param trackId
	 * @param albumCoverUrl
	 */
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

	/**
	 * Fetches lyrics from musixMatch-api
	 */
	public void setLyrics() {
		try {
			this.originalLyrics = GetLyrics.getLyricsAsString(songName, artist);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Translates lyrics
	 * @param lang the languange to translate the lyrics to
	 */
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

	/**
	 * Used to validate if we got all hints needed for the song to be playable
	 * @return true if the song is playable, false otherwise
	 */
	public boolean validate() {
		if (originalLyrics != null && gifs.size() > 0) {
			return true;
		}
		return false;
	}

}
