package entity;

public class SpotifySong {
	String artist;
	String songName;
	String trackId;
	String albumCoverUrl;
	
	public SpotifySong(String artist, String songName, String trackId, String albumCoverUrl) {
		super();
		this.artist = artist;
		this.songName = songName;
		this.trackId = trackId;
		this.albumCoverUrl = albumCoverUrl;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	
	public String getAlbumCover() {
		return this.albumCoverUrl;
	}
	
}
