package ru.daniil4jk.yweather.cli;

import ru.daniil4jk.yweather.config.PlaceLoader;
import ru.daniil4jk.yweather.model.HourForecast;
import ru.daniil4jk.yweather.model.Place;

import java.util.Arrays;
import java.util.List;

public final class CliArgParser {

    private final PlaceLoader placeLoader;

    public CliArgParser(PlaceLoader placeLoader) {
        this.placeLoader = placeLoader;
    }

    public CliArgs parse(String[] args) {
        String lat = null, lon = null, place = null;
        int days = 1;
        int singleHour = -1;
        String rangeStr = null;

        String[] fields = HourForecast.supportedFields();
        String fieldsStr = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--lat" -> lat = args[++i];
                case "--lon" -> lon = args[++i];
                case "--place" -> place = args[++i];
                case "--days" -> days = Integer.parseInt(args[++i]);
                case "--hours" -> {
                    String h = args[++i];
                    if (h.contains("-")) {
                        rangeStr = h;
                    } else {
                        singleHour = Integer.parseInt(h);
                    }
                }
                case "--fields" -> fieldsStr = args[++i];
                default -> {
                    System.err.println("Unknown option: " + args[i]);
                    System.exit(1);
                }
            }
        }

        if (place != null) {
            if (lat != null || lon != null) {
                System.err.println("--place нельзя использовать вместе с --lat/--lon");
                System.exit(1);
            }
            try {
                Place p = placeLoader.findByName(place);
                lat = p.lat();
                lon = p.lon();
            } catch (Exception e) {
                System.err.println("Ошибка загрузки " + e.getMessage());
                System.exit(1);
            }
        } else if (lat == null || lon == null) {
            System.err.println("Укажите --place <название> или --lat <lat> --lon <lon>");
            System.err.println("Использование: yweather --place <название> [options]");
            System.err.println("              yweather --lat <lat> --lon <lon> [options]");
            System.err.println("Options:");
            System.err.println("  --days <N>        Количество дней прогноза (по умолч. 1)");
            System.err.println("  --hours <N|N-M>   Час или диапазон (3 или 12-22). По умолчанию все часы.");
            System.err.println("  --fields <list>   Поля для вывода");
            System.exit(1);
        }

        HourFilter filter;
        if (rangeStr != null) {
            var parts = rangeStr.split("-");
            filter = new HourFilter(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } else if (singleHour >= 0) {
            filter = new HourFilter(singleHour, singleHour);
        } else {
            filter = new HourFilter(0, 23);
        }

        if (fieldsStr != null) {
            fields = fieldsStr.split(",");
        }

        return new CliArgs(lat, lon, days, filter, fields);
    }
}
