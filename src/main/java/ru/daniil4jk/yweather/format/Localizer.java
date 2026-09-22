package ru.daniil4jk.yweather.format;

public interface Localizer {
    String mapCondition(String paramName);
    String mapWindDir(String state);
    String mapFieldName(String field);
}
