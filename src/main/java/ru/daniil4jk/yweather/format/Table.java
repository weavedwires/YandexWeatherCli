package ru.daniil4jk.yweather.format;

import ru.daniil4jk.yweather.cli.CliArgs;
import ru.daniil4jk.yweather.model.DayForecast;
import ru.daniil4jk.yweather.model.Forecast;
import ru.daniil4jk.yweather.model.HourForecast;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Table {
    private final String[][] table;
    private final boolean userFormat;

    public Table(Forecast forecast, Localizer localizer, CliArgs args) { //todo вынести в отдельный класс
        String[] fields = args.fields();
        userFormat = args.userFormat();

        int hoursInForecast = (int) forecast.days()
                .stream()
                .flatMap((Function<DayForecast, Stream<?>>) dayForecast -> dayForecast.hours().stream())
                .count();
        int rows = hoursInForecast + 1;
        int params = fields.length + 1;

        table = new String[rows][params];

        table[0][0] = localizer.map("date");
        table[0][1] = localizer.map("hour");
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i];
            table[0][i + 1] = localizer.map(fieldName);
        }

        int row = 1;
        for (DayForecast day : forecast.days()) {
            for (HourForecast hour : day.hours()) {
                table[row][0] = day.date();
                for (int fieldNumber = 0; fieldNumber < fields.length; fieldNumber++) {
                    String value = hour.getFieldValue(fields[fieldNumber]);
                    String localValue = localizer.map(value);
                    table[row][fieldNumber + 1] = localValue;
                }
                row++;
            }
        }
    }

    public String draw() {
        return userFormat ? drawBeauty() : drawSimple();
    }

    public String drawSimple() {
        return Arrays.stream(table)
                .map(s -> String.join("|", s))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static final String COL_DIVIDER = " | ";

    public String drawBeauty() {
        int[] maxLengthOfField = getMaxLengthOfFields();
        int strLength = calcStrLength(maxLengthOfField);

        StringBuilder graph = new StringBuilder();

        {
            for (int col = 0; col < table[0].length; col++) {
                String value = table[0][col];
                graph.append(value).append(" ".repeat(maxLengthOfField[col] - value.length())).append(COL_DIVIDER);
            }
            graph.append(System.lineSeparator()).append("-".repeat(strLength)).append(System.lineSeparator());
        }

        for (int row = 1; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                String value = table[row][col];
                graph.append(value).append(" ".repeat(maxLengthOfField[col] - value.length())).append(COL_DIVIDER);
            }
            graph.append(System.lineSeparator());
        }

        return graph.toString();
    }

    private static int calcStrLength(int[] maxLengthOfField) {
        int strLength = 0;
        for (int i : maxLengthOfField) {
            strLength += (i + COL_DIVIDER.length());
        }
        strLength -= 1; //пагинация
        return strLength;
    }

    private int[] getMaxLengthOfFields() {
        int[] maxLengthOfField = new int[table[0].length];
        for (String[] strings : table) {
            for (int col = 0; col < table[0].length; col++) {
                String currStr = strings[col];
                int prevNum = maxLengthOfField[col];
                int newNum = currStr.length();
                maxLengthOfField[col] = Math.max(prevNum, newNum);
            }
        }
        return maxLengthOfField;
    }
}
