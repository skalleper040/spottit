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

	@GET
	@Produces("text/json")
	public Response getSongInfo(@QueryParam("artist") String artist, @QueryParam("title") String title, @QueryParam("trackUri") String trackUri, @QueryParam("coverUri") String coverUri) {

		System.out.println("getting a song");
		Song song = new Song(artist, title, trackUri, coverUri);

		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		if (song.validate()) {

			String jsonInString = "";
			try {
				jsonInString = mapper.writeValueAsString(song);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			return Response.ok()
					.entity(jsonInString)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.build();
		}
		
		// Create an error message and return it if the song isnt available.
		JSONObject json = new JSONObject();
		try {

			json.put("Status", "error");
			json.put("Code", 404);
			json.put("Message", "Song not found or not all hints for song available");
		} catch (JSONException e) {
		}
		
		return Response.status(404)
				.entity(json.toString())
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}
}
