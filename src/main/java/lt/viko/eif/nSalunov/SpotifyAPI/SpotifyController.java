package lt.viko.eif.nSalunov.SpotifyAPI;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Spotify-related endpoints.
 * 
 * Provides access to Spotify data such as the top tracks of a given artist.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/top-tracks")
    public List<String> getTopTracks(@RequestParam String artist) {
        return spotifyService.getTopTracks(artist);
    }
}
