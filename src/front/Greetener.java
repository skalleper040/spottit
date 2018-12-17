package front;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import backend.CreatePlaylistFromSpotify;
import entity.SpotifyPlaylist;

@Path("/playlist")
public class Greetener {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPlaylist(@QueryParam("id") String playlistId) {
		CreatePlaylistFromSpotify playlist = new CreatePlaylistFromSpotify();
		String playlistAsJson = playlist.getPlaylist(playlistId);
		return playlistAsJson;
	}
}
