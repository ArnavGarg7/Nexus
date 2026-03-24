# NEXUS — Continuity & Discovery Navigator

An Android application for comic book and pop culture fans to navigate complex universes, discover reading paths, and test their knowledge.

## Architecture

**Stack:** Kotlin + Jetpack Compose + MVVM + Clean Architecture

```
app/
├── core/
│   ├── data/
│   │   ├── local/       # Room database, DAOs, entities
│   │   ├── remote/      # Retrofit API service, DTOs, mappers
│   │   └── repository/  # Repository implementations
│   ├── di/              # Hilt dependency injection modules
│   └── network/         # OkHttp, interceptors
├── domain/
│   ├── model/           # Pure domain models
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Use case classes
└── features/
    ├── onboarding/      # Onboarding quiz flow
    ├── home/            # Home dashboard
    ├── search/          # Character/event search
    ├── character/       # Character detail + reading path
    ├── pathfinder/      # Pathfinder Engine
    ├── events/          # Event Navigator
    ├── reading/         # Dynamic Reading Orders
    ├── mediabridge/     # Cross-media recommendations
    ├── lore/            # Lore & Trivia Explorer
    ├── quiz/            # Quiz Hub + Quiz Engine
    └── profile/         # User profile + badges
```

## Setup

1. Clone the repository
2. Copy `local.properties.template` to `local.properties`
3. Add your API keys:
   - `MARVEL_API_KEY` — from developer.marvel.com
   - `COMIC_VINE_API_KEY` — from comicvine.gamespot.com/api
   - `TMDB_API_KEY` — from themoviedb.org/settings/api
4. Open in Android Studio Hedgehog or later
5. Run on an emulator or device (API 26+)

## Font Resources

Place these font files in `app/src/main/res/font/`:
- `bangers_regular.ttf` — Google Fonts: Bangers
- `inter_regular.ttf` — Google Fonts: Inter Regular
- `inter_medium.ttf` — Google Fonts: Inter Medium
- `inter_semibold.ttf` — Google Fonts: Inter SemiBold

## Key Features (MVP)

- Pathfinder Engine — Personalised entry points into comic universes
- Character Journey Navigator — Curated reading paths per character
- Dynamic Reading Orders — Essential & Full Experience modes
- Media Bridge — Comics ↔ Film/TV/Games cross-recommendations
- Event Navigator — Major crossover events with pre/post context
- Fan Knowledge Quiz System — Gamified trivia with XP, badges, streaks
- Lore Explorer — Deep lore, Easter eggs, creator insights

## PRD

Full Product Requirements Document: `NEXUS_PRD.docx`
