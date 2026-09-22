package ru.daniil4jk.yweather.client;

import ru.daniil4jk.yweather.cli.CliArgs;
import ru.daniil4jk.yweather.model.Forecast;

public interface WeatherClient {
    public Forecast fetch(CliArgs cfg) throws Exception;
}
