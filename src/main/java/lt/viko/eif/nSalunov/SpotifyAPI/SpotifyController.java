package lt.viko.eif.nSalunov.SpotifyAPI;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // разрешает доступ с любого домена, настрой при необходимости
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
