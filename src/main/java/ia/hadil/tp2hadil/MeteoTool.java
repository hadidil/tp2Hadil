package ia.hadil.tp2hadil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class MeteoTool {

    private final int seuilPluie = 50;

    @Tool("Indique si de la pluie est prévue pendant un séjour, à partir des coordonnées GPS du lieu. Retourne true s'il faut un parapluie.")
    public boolean isPluiePrevue(
            @P("Latitude du lieu") double latitude,
            @P("Longitude du lieu") double longitude,
            @P("Nombre de jours avant le départ (0 si le départ est aujourd'hui)") int joursAvantDepart,
            @P("Durée du séjour en jours") int dureeSejour) throws Exception {

        int totalJours = joursAvantDepart + dureeSejour;
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude
                + "&daily=precipitation_probability_max&forecast_days=" + totalJours;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requete = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> reponse = client.send(requete, HttpResponse.BodyHandlers.ofString());

        JsonNode probabilites = new ObjectMapper().readTree(reponse.body())
                .path("daily").path("precipitation_probability_max");

        for (int i = joursAvantDepart; i < totalJours && i < probabilites.size(); i++) {
            if (probabilites.get(i).asInt() >= seuilPluie) {
                return true;
            }
        }
        return false;
    }
    @Tool("Donne la latitude et la longitude d'une ville à partir de son nom (en anglais, par exemple 'Rabat,Morocco'). Retourne un tableau [latitude, longitude].")
    public double[] getCoordonnees(@P("Nom de la ville, en anglais") String ville) throws Exception {
        String nom = URLEncoder.encode(ville, StandardCharsets.UTF_8);
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + nom + "&count=1";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requete = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> reponse = client.send(requete, HttpResponse.BodyHandlers.ofString());

        JsonNode resultats = new ObjectMapper().readTree(reponse.body()).path("results");
        if (resultats.isMissingNode() || resultats.isEmpty()) {
            throw new IllegalArgumentException("Ville introuvable : " + ville);
        }
        JsonNode premier = resultats.get(0);
        return new double[]{premier.path("latitude").asDouble(), premier.path("longitude").asDouble()};
    }
}