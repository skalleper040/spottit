package front;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import backend.CreatePlaylistFromSpotify;
import entity.SpotifyPlaylist;


/**
 * Class that retrieves a playlist from spotify and returns it as Json.
 * Needs an accesstoken from user to work
 * @author Oscar
 *
 */
@Path("/playlist")
public class Greetener {

	/**
	 * Gets the requested playlist and returns it as JSON
	 * @param playlistId the spotify playlist to look for
	 * @param accessToken the accesstoken required for spotify
	 * @return the playlist in JSON
	 */
	@GET
	@Produces("text/json")
	public Response getPlaylist(@QueryParam("id") String playlistId, @QueryParam("accessToken") String accessToken) {
		JSONObject json = new JSONObject(); // Creates an object used for response-build
		System.out.println(accessToken);

		CreatePlaylistFromSpotify cpfs = new CreatePlaylistFromSpotify();
		int responseCode = cpfs.checkResponse(playlistId, accessToken);
		
		// If something went wrong when fetching playlist from spotify
		if (responseCode != 200) {
			try {
				json.put("Status", "error");
				json.put("Code", responseCode);
				return Response.status(responseCode)
						.entity(json.toString())
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET")
						.build();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// All good, get the playlist and return it as Json
		String playlistAsJson = cpfs.getPlaylist(playlistId, accessToken);
		return Response.ok() //200
				.entity(playlistAsJson)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.build();
	}
}
