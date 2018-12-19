package front;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import backend.CreatePlaylistFromSpotify;
import entity.SpotifyPlaylist;

@Path("/playlist")
public class Greetener {

	@GET
	@Produces("text/json")
	
	public Response getPlaylist(@QueryParam("id") String playlistId, @QueryParam("accessToken") String accessToken) {
		System.out.println(accessToken);
		String playlistAsJson = new CreatePlaylistFromSpotify().getPlaylist(playlistId, accessToken);
		
		
		return Response.ok() //200
				.entity(playlistAsJson)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}
}