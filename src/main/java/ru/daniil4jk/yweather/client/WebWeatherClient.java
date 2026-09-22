package ru.daniil4jk.yweather.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.daniil4jk.yweather.cli.CliArgs;
import ru.daniil4jk.yweather.model.Forecast;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class WebWeatherClient implements WeatherClient {

    private static final String BASE_URL = "https://api.weather.yandex.ru/v2/forecast";

    private final String apiKey;
    private final WebResponseParser responseParser;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public WebWeatherClient(String apiKey, WebResponseParser responseParser, ObjectMapper mapper) {
        this.apiKey = apiKey;
        this.responseParser = responseParser;
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = mapper;
    }

    WebWeatherClient(String apiKey, WebResponseParser responseParser, HttpClient httpClient, ObjectMapper mapper) {
        this.apiKey = apiKey;
        this.responseParser = responseParser;
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    public Forecast fetch(CliArgs cfg) throws Exception {
        String url = BASE_URL
                + "?lat=" + cfg.lat()
                + "&lon=" + cfg.lon()
                + "&lang=ru_RU"
                + "&limit=" + Math.max(1, cfg.days())
                + "&hours=true"
                + "&extra=true";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Yandex-Weather-Key", apiKey)
                .GET()
                .build();

        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            System.err.println("API error " + resp.statusCode() + ": " + resp.body());
            System.exit(1);
        }

        try (var fw = new FileWriter("resp.txt")) {
            fw.append(resp.body());
        }

        return responseParser.parse(mapper.readTree(resp.body()));
    }
}
