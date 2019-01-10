package front;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		return Response.status(403)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.entity("Status 403")
				.build();
	}
}
