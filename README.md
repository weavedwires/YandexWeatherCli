# YandexWeatherCli

CLI-утилита для прогноза погоды через Yandex Weather API (тариф «Оптимальный»).
Выводит машиночитаемый pipe-разделительный CSV.

Установленную команду можно вызывать одной строкой, указав только место:

```bash
yweather --place Екатеринбург
```

## Требования

- **Java 17+** (проверить: `java -version`)
- API-ключ Yandex Weather

## 1. Получи API-ключ

Зарегистрируйся на [Yandex Weather API](https://developer.tech.yandex.ru/services/).
В бесплатном тарифе 30 запросов в день.

## 2. Установка

### Из релиза

Скачай `yweather-<версия>.zip` со [страницы релизов](https://github.com/daniil4jk/YandexWeatherCli/releases)
и распакуй в `~/.local/bin`:

```bash
mkdir -p ~/.local/bin
unzip yweather-*.zip -d ~/.local/bin
```

### Из исходников

```bash
git clone https://github.com/daniil4jk/YandexWeatherCli
cd YandexWeatherCli
mvn clean package

mkdir -p ~/.local/bin
install -m 755 yweather ~/.local/bin/
cp target/yweather-1.1.0.jar ~/.local/bin/yweather.jar
```

В обоих случаях добавь `~/.local/bin` в `PATH` (если ещё не добавлен):

```bash
export PATH="$HOME/.local/bin:$PATH"
```

`yweather` — это wrapper, который запускает `yweather.jar`, лежащий с ним рядом.

## 3. Настройка

Файлы конфигурации ищутся **рядом с JAR**, а затем в **текущей директории**:

- `api-key.txt` — ключ Yandex Weather (обязателен). Переименуй `api-key.txt.example`
  в `api-key.txt` и вставь ключ.
- `places.json` — список мест, доступных по имени (опционально; поставляется с релизом).

## 4. Запуск

```bash
yweather --place Екатеринбург
yweather --place Москва --days 3
yweather --place Москва --hours 12-22
yweather --lat 56.83 --lon 60.60 --hours 0-12
```

## CLI-флаги

| Флаг | Описание |
|------|----------|
| `--place <name>` | Место из `places.json` (русское или английское имя) |
| `--lat`, `--lon` | Координаты (вместо `--place`) |
| `--days <N>` | Количество дней прогноза (по умолчанию 1) |
| `--hours <N\|N-M>` | Один час (`5`) или диапазон (`12-22`). По умолчанию все часы |
| `--fields <list>` | Какие поля выводить (через запятую) |

Флага `--compact` нет: вывод всегда машиночитаемый.

**По умолчанию** `--fields`: `hour,temp,feels_like,condition,prec_strength,prec_type,wind_speed,wind_gust,humidity`.

Формат: pipe-разделительный CSV с заголовком `date|field1|field2|...`:

```
date|hour|temp|feels_like|condition|...
2026-06-14|0|+12|+10|Ясно|...
2026-06-14|1|+11|+9|Ясно|...
```

## Доступные поля в --fields

| Поле | Тип | Описание | Пример |
|------|-----|----------|--------|
| `hour` | число | Час от 0 до 23 | `14` |
| `temp` | °C | Температура воздуха | `+18`, `-5` |
| `feels_like` | °C | Ощущаемая температура | `+16` |
| `condition` | строка | Погодное явление (маппится на русский) | `Ясно`, `Дождь`, `Гроза с градом` |
| `prec_strength` | число | Интенсивность осадков (0 = нет, >0 = мм/ч) | `0.0`, `1.5` |
| `prec_type` | enum | Тип осадков: `none` / `rain` / `snow` | `rain` |
| `wind_speed` | м/с | Скорость ветра | `3.2` |
| `wind_gust` | м/с | Порывы ветра | `7.1` |
| `humidity` | % | Влажность | `65%` |
| `wind_dir` | строка | Направление ветра (румбы) | `С`, `ЮЗ`, `СВ` |

### Примечания к полям

- **`prec_type`** — внутри хранится как int: `0` → без осадков, `1` → дождь, `2` → снег
- **`wind_dir`** — маппинг румбов Яндекс.Погоды: `n→С, ne→СВ, e→В, se→ЮВ, s→Ю, sw→ЮЗ, w→З, nw→СЗ`
- **`condition`** — все коды погодных явлений маппятся на русские названия (ясно, дождь, гроза и т.д.)
- **`temp`** — всегда с явным знаком (`%+.0f`)
- **`prec_strength`** — сырое значение из JSON; 0 = без осадков, >0 = интенсивность в мм/ч

## places.json

Список мест, которые можно запрашивать по имени через `--place`.
Алиасов имён рекомендуется указывать как можно больше — это повышает шанс,
что запрос сработает даже при ошибке в написании.

```json
[
  {
    "names": ["Москва", "Moscow"],
    "lat": 55.755864,
    "lon": 37.617698
  },
  {
    "names": ["Лондон", "London"],
    "lat": 51.507351,
    "lon": -0.127696
  }
]
```

## Лицензия

MIT.
