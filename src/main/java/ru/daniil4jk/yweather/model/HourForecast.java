package ru.daniil4jk.yweather.model;

public record HourForecast(
    int hour,
    double temp,
    double feelsLike,
    String condition,
    double precStrength,
    int precType,
    double windSpeed,
    double windGust,
    double humidity,
    String windDir
) {

    public static String[] supportedFields() {
        return new String[] {
                "hour",
                "temp",
                "feels_like",
                "condition",
                "prec_type",
                "prec_strength",
                "humidity",
                "wind_speed",
                "wind_gust",
                "wind_dir",
        };
    }

    public String getFieldValue(String name) {
        return switch (name) {
            case "hour" -> String.valueOf(hour());
            case "temp" -> formatTemp(temp());
            case "feels_like" -> formatTemp(feelsLike());
            case "condition" -> condition();
            case "prec_strength" -> String.valueOf(precStrength());
            case "prec_type" -> precTypeString(precType());
            case "wind_speed" -> String.format("%.1f", windSpeed());
            case "wind_gust" -> String.format("%.1f", windGust());
            case "humidity" -> String.format("%.0f%%", humidity());
            case "wind_dir" -> windDir();
            default -> "";
        };
    }

    private static String formatTemp(double t) {
        return String.format("%+.0f", t);
    }

    private static String precTypeString(int t) {
        return switch (t) {
            case 0 -> "none";
            case 1 -> "rain";
            case 2 -> "snow";
            default -> "?";
        };
    }
}
