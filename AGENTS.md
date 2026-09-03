# YandexWeatherCli — AGENTS.md

## Build & Run

```bash
mvn package -q                              # builds uber-jar + shell wrapper
mkdir -p ~/.local/bin
cp target/appassembler/bin/yweather ~/.local/bin/
cp target/yweather-1.0.0.jar ~/.local/bin/
export PATH="$HOME/.local/bin:$PATH"
yweather --lat <lat> --lon <lon> [options]
```

## CLI

| Argument | Description |
|----------|-------------|
| `--lat`, `--lon` | Coordinates (mutually exclusive with `--place`) |
| `--place <name>` | Named place from `places.json` (Russian or English) |
| `--days <N>` | Forecast days (default 1) |
| `--hours <N\|N-M>` | Single hour or range e.g. `3` or `12-22` |
| `--fields <list>` | Comma-separated fields (see defaults below) |

Default `--fields`: `hour,temp,feels_like,condition,prec_strength,prec_type,wind_speed,wind_gust,humidity`

Available fields: `hour`, `temp`, `feels_like`, `condition`, `prec_strength`, `prec_type`, `wind_speed`, `wind_gust`, `humidity`, `wind_dir`.

Output is pipe-delimited CSV with header row `date|field1|field2|...`.

## Config Files (resolved: next to JAR → CWD)

| File | Purpose |
|------|---------|
| `api-key.txt` | Yandex Weather API key (gitignored) |
| `places.json` | Named places with `names`, `lat`, `lon` |

## API

Yandex Weather API v2 — `https://api.weather.yandex.ru/v2/forecast` with `X-Yandex-Weather-Key` header. Query params: `lat`, `lon`, `lang=ru_RU`, `limit`, `hours=true`, `extra=true`.

## Project Layout

```
src/main/java/ru/daniil4jk/yweather/
  Main.java              — entrypoint
  cli/                   — CLI args parsing (CliArgParser, CliArgs, HourFilter)
  client/                — HTTP client + JSON response parser (WeatherClient, ForecastParser)
  config/                — file-based config loader (SomeConfig → ApiKeyConfig, PlacesConfig, PlaceLoader)
  format/                — output formatting (WeatherFormatter, ConditionMapper)
  model/                 — data records (Forecast, DayForecast, HourForecast, Place)
```

Java 17, Maven, jackson-databind 2.15.2. No tests. Russian-language messages.
