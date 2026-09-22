package ru.daniil4jk.yweather.cli;

public record CliArgs(
        boolean userFormat,
        String lat,
        String lon,
        int days,
        HourFilter hourFilter,
        String[] fields
) {
}
