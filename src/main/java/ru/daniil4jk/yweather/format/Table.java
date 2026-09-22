package ru.daniil4jk.yweather.format;

import ru.daniil4jk.yweather.model.DayForecast;
import ru.daniil4jk.yweather.model.Forecast;
import ru.daniil4jk.yweather.model.HourForecast;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Table {
    private final String[][] table;

    public Table(Forecast forecast, Localizer localizer, String[] fields) { //todo вынести в отдельный класс
        int hoursInForecast = (int) forecast.days()
                .stream()
                .flatMap((Function<DayForecast, Stream<?>>) dayForecast -> dayForecast.hours().stream())
                .count();
        int rows = hoursInForecast + 1;
        int params = fields.length;

        table = new String[rows][params];

        table[0][0] = localizer.map("date");
        for (int i = 1; i < params; i++) {
            String fieldName = fields[i];
            table[0][i] = localizer.map(fieldName);
        }

        int tableYPos = 1;
        for (DayForecast day : forecast.days()) {
            for (HourForecast hour : day.hours()) {
                table[tableYPos][0] = day.date();
                for (int fieldNumber = 1; fieldNumber < fields.length; fieldNumber++) {
                    String value = hour.getFieldValue(fields[fieldNumber]);
                    String localValue = localizer.map(value);
                    table[tableYPos][fieldNumber] = localValue;
                }
                tableYPos++;
            }
        }
    }

    public String drawSimple() {
        return Arrays.stream(table)
                .map(s -> String.join("|", s))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String drawBeauty() {
return null;
    }
}
