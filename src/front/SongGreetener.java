package front;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Song;

@Path("/song")
public class SongGreetener {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSongInfo(@QueryParam("artist") String artist, @QueryParam("title") String title, @QueryParam("trackUri") String trackUri, @QueryParam("coverUri") String coverUri) {
		//Call spotify mby. Use trackUri for SpotifyPlay
		System.out.println("getting a song");
		Song song = new Song(artist, title, trackUri, coverUri);
		//Call Musixmatch
		
		//Call Giphy
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(song);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonInString;

	}
}
