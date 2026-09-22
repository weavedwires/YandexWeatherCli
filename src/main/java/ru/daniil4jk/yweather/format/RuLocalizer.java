package ru.daniil4jk.yweather.format;

import java.util.Map;

public final class RuLocalizer implements Localizer {

    private final Map<String, String> translations;

    public RuLocalizer() {
        translations = Map.ofEntries(
                // Погодные условия
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
                Map.entry("thunderstorm-with-hail", "Гроза с градом"),
                Map.entry("none", "нет"),

                // Направления ветра
                Map.entry("nw", "СЗ"),
                Map.entry("n",  "С"),
                Map.entry("ne", "СВ"),
                Map.entry("e",  "В"),
                Map.entry("se", "ЮВ"),
                Map.entry("s",  "Ю"),
                Map.entry("sw", "ЮЗ"),
                Map.entry("w",  "З"),

                // Поля
                Map.entry("date", "Дата"),
                Map.entry("value", "Час"),
                Map.entry("temp", "Температура"),
                Map.entry("feels_like", "Ощущается как"),
                Map.entry("condition", "Облачность"),
                Map.entry("prec_strength", "Интенсивность осадков"),
                Map.entry("prec_type", "Тип осадков"),
                Map.entry("wind_speed", "Скорость ветра"),
                Map.entry("wind_gust", "Порывы ветра"),
                Map.entry("wind_dir", "Направление ветра"),
                Map.entry("humidity", "Влажность")
        );
    }


    @Override
    public String map(String param) {
        return translations.getOrDefault(param, param);
    }
}
