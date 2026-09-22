package ru.daniil4jk.yweather.format;

import java.util.Map;

public class RuLocalizer implements Localizer {

    private final Map<String, String> conditions;
    private final Map<String, String> windDirs;
    private final Map<String, String> fields;

    public RuLocalizer() {
        conditions = Map.ofEntries(
                Map.entry("clear", "Ясно"),
                Map.entry("partly-cloudy", "Малооблачно"),
                Map.entry("cloudy", "Облачно с прояснениями"),
                Map.entry("overcast", "Пасмурно"),
                Map.entry("drizzle", "Морось"),
                Map.entry("light-rain", "Небольшой дождь"),
                Map.entry("rain", "Дождь"),
                Map.entry("moderate-rain", "Умеренно сильный дождь"),
                Map.entry("heavy-rain", "Сильный дождь"),
                Map.entry("continuous-heavy-rain", "Длительный сильный дождь"),
                Map.entry("showers", "Ливень"),
                Map.entry("wet-snow", "Дождь со снегом"),
                Map.entry("light-snow", "Небольшой снег"),
                Map.entry("snow", "Снег"),
                Map.entry("snow-showers", "Снегопад"),
                Map.entry("hail", "Град"),
                Map.entry("thunderstorm", "Гроза"),
                Map.entry("thunderstorm-with-rain", "Гроза с дождём"),
                Map.entry("thunderstorm-with-hail", "Гроза с градом")
        );

        windDirs = Map.of(
                "nw", "СЗ",
                "n",  "С",
                "ne", "СВ",
                "e",  "В",
                "se", "ЮВ",
                "s",  "Ю",
                "sw", "ЮЗ",
                "w",  "З"
        );

        fields = Map.ofEntries(
                Map.entry("hour", "Час"),
                Map.entry("temp", "Температура"),
                Map.entry("feels_like", "Ощущается как"),
                Map.entry("condition", "Погодное явление"),
                Map.entry("prec_strength", "Интенсивность осадков"),
                Map.entry("prec_type", "Тип осадков"),
                Map.entry("wind_speed", "Скорость ветра"),
                Map.entry("wind_gust", "Порывы ветра"),
                Map.entry("humidity", "Влажность")
        );
    }

    @Override
    public String mapCondition(String en) {
        return conditions.getOrDefault(en, en);
    }

    @Override
    public String mapWindDir(String dir) {
        return windDirs.getOrDefault(dir, dir);
    }

    @Override
    public String mapFieldName(String field) {
        return fields.getOrDefault(field, field);
    }
}
