package ru.daniil4jk.yweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.daniil4jk.yweather.cli.CliArgParser;
import ru.daniil4jk.yweather.client.FakeWeatherClient;
import ru.daniil4jk.yweather.client.WebResponseParser;
import ru.daniil4jk.yweather.client.WebWeatherClient;
import ru.daniil4jk.yweather.config.ApiKeyConfig;
import ru.daniil4jk.yweather.config.PlaceLoader;
import ru.daniil4jk.yweather.config.PlacesConfig;
import ru.daniil4jk.yweather.format.*;

public class Main {

    public static void main(String[] args) throws Exception {
        var objectMapper = new ObjectMapper();
        String places = new PlacesConfig().read();
        var placeLoader = new PlaceLoader(objectMapper, places);

        var cfg = new CliArgParser(placeLoader).parse(args);
        var forecastParser = new WebResponseParser();
        String apiKey = new ApiKeyConfig().read();
        var weatherClient = new WebWeatherClient(apiKey, forecastParser, objectMapper);
        var forecast = weatherClient.fetch(cfg);

        Localizer localizer = new RuLocalizer();
        var table = new Table(forecast, localizer, cfg.fields());
        System.out.println(table.drawSimple());
    }
}
