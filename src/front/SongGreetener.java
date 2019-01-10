package front;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Song;

@Path("/song")
public class SongGreetener {

	private String retrieveSong(Song song) {
		String jsonInString = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		if (song.validate()) {
			try {
				jsonInString = mapper.writeValueAsString(song);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return jsonInString;
	}

	@GET
	@Produces("text/json")
	public Response getSongInfo(@QueryParam("artist") String artist, @QueryParam("title") String title, @QueryParam("trackUri") String trackUri, @QueryParam("coverUri") String coverUri) {
		JSONObject json = new JSONObject(); // Creates an object used for response-build

		if (artist.length() < 1 || title.length() < 1) { // Check that we got both artist and title to look for
			try {
				json.put("Status", "error");
				json.put("Code", 400);
				json.put("Message", "Bad request. Please provide correct artist and title");
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(json.toString())
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
						.build();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// All good, create a song
		Song song = new Song(artist, title, trackUri, coverUri);
		String entity = null;

		if (song.validate()) { // Check that the song got all info it needs to be playable
			entity = retrieveSong(song);
		}

		if (entity == null) { // If something went wrong with the hints
			try {
				json.put("Status", "error");
				json.put("Code", 404);
				json.put("Message", "Song not found or not all hints for song available");
				return Response.status(Response.Status.NOT_FOUND)
						.entity(json.toString())
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
						.build();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return Response.ok()
				.entity(entity)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}
}
