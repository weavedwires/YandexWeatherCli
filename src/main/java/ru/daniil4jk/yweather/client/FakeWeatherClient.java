package ru.daniil4jk.yweather.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.daniil4jk.yweather.cli.CliArgs;
import ru.daniil4jk.yweather.model.Forecast;

import java.io.BufferedReader;
import java.io.FileReader;

public final class FakeWeatherClient implements WeatherClient {
    private final WebResponseParser responseParser;
    private final ObjectMapper mapper;

    public FakeWeatherClient(WebResponseParser responseParser, ObjectMapper mapper) {
        this.responseParser = responseParser;
        this.mapper = mapper;
    }

    @Override
    public Forecast fetch(CliArgs cfg) throws Exception {
        try (var r = new BufferedReader(new FileReader("resp.txt"))) {
            return responseParser.parse(mapper.readTree(r.readLine()));
        }
    }
}
