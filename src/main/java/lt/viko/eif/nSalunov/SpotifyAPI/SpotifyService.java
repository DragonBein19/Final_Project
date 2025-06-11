package lt.viko.eif.nSalunov.SpotifyAPI;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for interacting with the Spotify API.
 * Provides methods to authenticate and fetch data such as top tracks of an artist.
 */

@Service
public class SpotifyService {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token", request, JsonNode.class);

        return response.getBody().get("access_token").asText();
    }

    public List<String> getTopTracks(String artistName) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getSpotifyAccessToken()); // метод, который получает токен
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Поиск исполнителя
        String searchUrl = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(artistName, StandardCharsets.UTF_8)
                + "&type=artist&limit=1";
        ResponseEntity<Map> searchResponse = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, Map.class);

        List<Map<String, Object>> artists = (List<Map<String, Object>>)
                ((Map<String, Object>) searchResponse.getBody().get("artists")).get("items");

        if (artists.isEmpty()) return List.of();

        String artistId = (String) artists.get(0).get("id");

        // Получаем топ треки
        String topTracksUrl = "https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=US";
        ResponseEntity<Map> topTracksResponse = restTemplate.exchange(topTracksUrl, HttpMethod.GET, entity, Map.class);

        List<Map<String, Object>> tracks = (List<Map<String, Object>>) topTracksResponse.getBody().get("tracks");

        List<String> allTracks = tracks.stream()
                .map(track -> (String) track.get("name"))
                .collect(Collectors.toList());

        // Перемешиваем и возвращаем 5 случайных
        Collections.shuffle(allTracks);
        return allTracks.stream().limit(5).toList();
    }

    private String getSpotifyAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                request,
                Map.class
        );

        return (String) response.getBody().get("access_token");
    }

}
