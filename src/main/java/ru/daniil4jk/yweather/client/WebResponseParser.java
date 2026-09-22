package ru.daniil4jk.yweather.client;

import com.fasterxml.jackson.databind.JsonNode;
import ru.daniil4jk.yweather.model.DayForecast;
import ru.daniil4jk.yweather.model.Forecast;
import ru.daniil4jk.yweather.model.HourForecast;

import java.util.ArrayList;
import java.util.List;

public final class WebResponseParser {

    public Forecast parse(JsonNode root) {
        JsonNode forecasts = root.get("forecasts");
        List<DayForecast> days = new ArrayList<>();
        if (forecasts != null && forecasts.isArray()) {
            for (JsonNode day : forecasts) {
                days.add(parseDay(day));
            }
        }
        return new Forecast(days);
    }

    private static DayForecast parseDay(JsonNode node) {
        String date = node.get("date").asText();
        JsonNode hoursArr = node.get("hours");
        List<HourForecast> hours = new ArrayList<>();
        if (hoursArr != null && hoursArr.isArray()) {
            for (JsonNode h : hoursArr) {
                hours.add(parseHour(h));
            }
        }
        return new DayForecast(date, hours);
    }

    private static HourForecast parseHour(JsonNode node) {
        return new HourForecast(
                node.get("hour").asInt(),
                node.get("temp").asDouble(),
                node.get("feels_like").asDouble(),
                node.has("condition") ? node.get("condition").asText() : "",
                node.has("prec_strength") ? node.get("prec_strength").asDouble() : 0,
                node.has("prec_type") ? node.get("prec_type").asInt() : 0,
                node.has("wind_speed") ? node.get("wind_speed").asDouble() : 0,
                node.has("wind_gust") ? node.get("wind_gust").asDouble() : 0,
                node.has("humidity") ? node.get("humidity").asDouble() : 0,
                node.has("wind_dir") ? node.get("wind_dir").asText() : ""
        );
    }
}
