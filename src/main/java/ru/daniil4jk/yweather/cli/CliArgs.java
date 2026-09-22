package ru.daniil4jk.yweather.cli;

import java.util.List;

public record CliArgs(
        String lat,
        String lon,
        int days,
        HourFilter hourFilter,
        String[] fields
) {
}
