package ru.daniil4jk.yweather.format;

import ru.daniil4jk.yweather.model.DayForecast;
import ru.daniil4jk.yweather.model.Forecast;
import ru.daniil4jk.yweather.model.HourForecast;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Table {
    private final String[][] table;

    public Table(Forecast forecast, Localizer localMapper, String[] fields) { //вынести в отдельный класс
        System.out.println(forecast);

        int hoursInForecast = (int) forecast.days()
                .stream()
                .flatMap((Function<DayForecast, Stream<?>>) dayForecast -> dayForecast.hours().stream())
                .count();
        int rows = hoursInForecast + 1;
        int params = fields.length;

        table = new String[rows][params];

        for (int i = 0; i < params; i++) {
            String fieldName = fields[i];
            table[0][i] = localMapper.mapFieldName(fieldName);
        }

        int tableYPos = 1;
        for (DayForecast day : forecast.days()) {
            for (HourForecast hour : day.hours()) {
                table[tableYPos][0] = day.date();
                for (int fieldNumber = 1; fieldNumber < fields.length; fieldNumber++) {
                    String condition = hour.getFieldValue(fields[fieldNumber]);
                    String localizedCondition = localMapper.mapCondition(condition);
                    table[tableYPos][fieldNumber] = localizedCondition;
                }
                tableYPos++;
            }
        }
    }

    public String drawSimple() {
        return Arrays.stream(table)
                .map(s -> Arrays.stream(s)
                        .collect(Collectors.joining("|"))
                )
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String drawBeauty() {
        return null;
    }
}
