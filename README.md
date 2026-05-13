# Weather Forecast App

An Android weather application that shows the current-day forecast, the 7-day forecast, and supports browsing forecasts for any saved city plus the device's current location.

## Features

- **Daily** — current weather + 24-hour hourly forecast for the active city, with UV index, humidity, wind, and visibility cards.
- **Weekly** — 7-day forecast with high/low and precipitation probability per day.
- **Locations** — manage saved cities, search by name to add new ones, long-press to remove. The device's GPS location is detected on first launch and pinned as the default.
- Connectivity-aware: shows a top alert when offline.

## Tech stack

- Kotlin, Coroutines, Flow
- Jetpack Compose (Material), Material Icons Extended
- Retrofit + kotlinx.serialization for networking
- Room for the on-device cache
- Hilt for dependency injection
- [Open-Meteo](https://open-meteo.com/) for forecast data (no API key required)

## Architecture

Clean Architecture with strict module boundaries:

```
app                       Application + nav graph
common/
  base-ui                 Theme, BaseViewModel, shared composables
  entity                  Domain models, response DTOs, UI models
  feature-api             FeatureApi contracts for nav registration
  utils                   Dispatchers, formatters, weather code mapper
data/
  api                     Retrofit services, repositories
  db                      Room entities, DAOs, cached weather repo
feature/
  daily/      daily-domain + daily-presentation
  weekly/     weekly-domain + weekly-presentation
  radar/      radar-domain + radar-presentation
  splash/     splash-domain + splash-presentation
```

Each feature is split into `*-domain` (use cases, framework-free) and `*-presentation` (ViewModel + Compose screens). Screens talk to the host app via the `FeatureApi` interface in `common/feature-api`, so features remain decoupled.

The presentation layer uses an MVI flavor: each screen defines an `Event` (input), `State` (immutable snapshot), and `Effect` (one-shot side effect) on a `Contract` interface. `BaseViewModel` wires the channels.

The cache layer (`CachedWeatherRepository`) stores a JSON-serialized `Weather` payload per location and merges hourly / forecast refreshes under a mutex so concurrent updates don't race.

## Running

Open in Android Studio (Iguana or newer recommended), sync Gradle, and run on a device or emulator with **API 24+**. Location permission is requested on first launch; if denied, the app falls back to a default location.

> Release builds: `isMinifyEnabled = true` is set in `app/build.gradle.kts`. ProGuard rules for Retrofit, kotlinx.serialization, Room, and Hilt are included in `app/proguard-rules.pro`.

## Testing

JVM unit tests live under each module's `src/test/java`. Run them with:

```
./gradlew test
```

Examples:
- `common/utils` — `WeatherCodeMapperTest`, `DateFormattersTest`
- `feature/radar/radar-domain` — `FetchCityCardsUseCaseImplTest`

Compose previews are provided for every `@Composable` in `common/base-ui` and on each screen, with inline sample data — open any of the screen files (e.g. `DailyScreen.kt`) and use the Preview pane.

## AI tools used

The product logic, architecture, and module boundaries are mine. I used AI assistance for a focused set of tasks listed below.

What I added with AI help:

- **Compose `@Preview` functions** — every `@Composable` in `common/base-ui` and on each feature screen has a `@Preview` with inline sample data, generated with AI assistance.
- **ProGuard rules** in `app/proguard-rules.pro` — the keep rules for Retrofit, kotlinx.serialization, Hilt, Room, and Compose were drafted with AI help so release builds with `isMinifyEnabled = true` don't strip reflection-touched classes.
- **Unit tests** — `WeatherCodeMapperTest`, `DateFormattersTest`, and `FetchCityCardsUseCaseImplTest` were written with AI assistance (test cases, fake repository stubs, and assertion style).
- **Variable / package / file name spelling cleanup across the whole app** — fixes like `dependecy → dependency`, `rader_presentation → radar_presentation`, `dayle → daily`, `handel* → handle*`, and the `Ui` suffix on UI-layer types (`HourSlot → HourSlotUi`, etc.) were applied with AI assistance to keep the rename consistent across imports, package declarations, and the radar module's `namespace`.

Every change was reviewed and tested before committing — nothing was merged blind.
