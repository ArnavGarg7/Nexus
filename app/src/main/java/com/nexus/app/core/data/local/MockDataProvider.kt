package com.nexus.app.core.data.local

import com.nexus.app.domain.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockDataProvider @Inject constructor() {

    // ── Characters ──────────────────────────────────────────────────────
    val characters: List<Character> = listOf(
        Character(
            id = "char_batman", name = "Batman",
            aliases = listOf("Bruce Wayne", "The Dark Knight", "The Caped Crusader"),
            publisher = Publisher.DC,
            firstAppearance = "Detective Comics #27 (1939)",
            creators = listOf("Bob Kane", "Bill Finger"),
            synopsis = "After witnessing the murder of his parents as a child, Bruce Wayne swore to rid Gotham City of crime. Using his vast fortune and brilliant mind, he became Batman — a symbol of fear for criminals and hope for the innocent.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/639.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/639.jpg",
            tags = listOf("Dark", "Street Level", "Detective", "Identity", "Legacy"),
            keyStoryArcs = listOf("Year One", "The Long Halloween", "Court of Owls", "Hush", "Knightfall"),
            relatedCharacterIds = listOf("char_superman", "char_joker"),
            mediaAppearances = listOf("The Dark Knight (2008)", "Batman Begins (2005)", "The Batman (2022)")
        ),
        Character(
            id = "char_spiderman", name = "Spider-Man",
            aliases = listOf("Peter Parker", "Your Friendly Neighborhood Spider-Man"),
            publisher = Publisher.MARVEL,
            firstAppearance = "Amazing Fantasy #15 (1962)",
            creators = listOf("Stan Lee", "Steve Ditko"),
            synopsis = "Bitten by a radioactive spider, teenager Peter Parker gained incredible powers. After the death of his Uncle Ben, Peter vowed to use his abilities responsibly — because with great power comes great responsibility.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/133.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/133.jpg",
            tags = listOf("Hopeful", "Street Level", "Identity", "Redemption", "Family"),
            keyStoryArcs = listOf("Kraven's Last Hunt", "The Night Gwen Stacy Died", "Spider-Verse", "Back in Black"),
            relatedCharacterIds = listOf("char_ironman", "char_daredevil"),
            mediaAppearances = listOf("Spider-Man: No Way Home (2021)", "Spider-Man: Into the Spider-Verse (2018)")
        ),
        Character(
            id = "char_superman", name = "Superman",
            aliases = listOf("Clark Kent", "Kal-El", "The Man of Steel"),
            publisher = Publisher.DC,
            firstAppearance = "Action Comics #1 (1938)",
            creators = listOf("Jerry Siegel", "Joe Shuster"),
            synopsis = "The last survivor of the doomed planet Krypton, Kal-El was sent to Earth as a baby. Raised as Clark Kent in Smallville, Kansas, he uses his extraordinary powers to protect humanity as Superman.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/791.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/791.jpg",
            tags = listOf("Hopeful", "Cosmic", "Legacy", "Power", "Identity"),
            keyStoryArcs = listOf("All-Star Superman", "Kingdom Come", "For the Man Who Has Everything", "Red Son"),
            relatedCharacterIds = listOf("char_batman", "char_wonderwoman"),
            mediaAppearances = listOf("Man of Steel (2013)", "Superman & Lois (2021)")
        ),
        Character(
            id = "char_ironman", name = "Iron Man",
            aliases = listOf("Tony Stark", "The Armored Avenger"),
            publisher = Publisher.MARVEL,
            firstAppearance = "Tales of Suspense #39 (1963)",
            creators = listOf("Stan Lee", "Larry Lieber", "Don Heck", "Jack Kirby"),
            synopsis = "Genius billionaire Tony Stark was captured by terrorists and built a suit of powered armor to escape. He became Iron Man — using his technology to protect the world while battling his own demons.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/85.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/85.jpg",
            tags = listOf("Political", "Identity", "Redemption", "Power"),
            keyStoryArcs = listOf("Demon in a Bottle", "Extremis", "Armor Wars", "Civil War"),
            relatedCharacterIds = listOf("char_spiderman", "char_captainamerica"),
            mediaAppearances = listOf("Iron Man (2008)", "Avengers: Endgame (2019)")
        ),
        Character(
            id = "char_wonderwoman", name = "Wonder Woman",
            aliases = listOf("Diana Prince", "Princess Diana of Themyscira"),
            publisher = Publisher.DC,
            firstAppearance = "All Star Comics #8 (1941)",
            creators = listOf("William Moulton Marston"),
            synopsis = "Born of the Amazons on Themyscira, Diana was sculpted from clay and given life by the gods. She entered the world of men as Wonder Woman to bring peace, justice, and equality.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/807.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/807.jpg",
            tags = listOf("Hopeful", "Legacy", "Power", "War"),
            keyStoryArcs = listOf("The Hiketeia", "Gods and Mortals", "The Circle", "Year One"),
            relatedCharacterIds = listOf("char_batman", "char_superman"),
            mediaAppearances = listOf("Wonder Woman (2017)", "Wonder Woman 1984 (2020)")
        ),
        Character(
            id = "char_wolverine", name = "Wolverine",
            aliases = listOf("Logan", "James Howlett", "Weapon X"),
            publisher = Publisher.MARVEL,
            firstAppearance = "The Incredible Hulk #181 (1974)",
            creators = listOf("Roy Thomas", "Len Wein", "John Romita Sr."),
            synopsis = "Born with mutant powers including retractable bone claws and a healing factor, Logan's past was stolen from him by the Weapon X program that bonded indestructible adamantium to his skeleton.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/161.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/161.jpg",
            tags = listOf("Dark", "Street Level", "Identity", "Redemption"),
            keyStoryArcs = listOf("Old Man Logan", "Weapon X", "Enemy of the State", "Logan"),
            relatedCharacterIds = listOf("char_spiderman", "char_daredevil"),
            mediaAppearances = listOf("Logan (2017)", "X-Men: Days of Future Past (2014)")
        ),
        Character(
            id = "char_daredevil", name = "Daredevil",
            aliases = listOf("Matt Murdock", "The Man Without Fear"),
            publisher = Publisher.MARVEL,
            firstAppearance = "Daredevil #1 (1964)",
            creators = listOf("Stan Lee", "Bill Everett"),
            synopsis = "Blinded by radioactive material as a boy, Matt Murdock gained extraordinarily heightened remaining senses. By day he's a lawyer fighting for justice; by night he patrols Hell's Kitchen as Daredevil.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/52.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/52.jpg",
            tags = listOf("Dark", "Street Level", "Identity", "Redemption"),
            keyStoryArcs = listOf("Born Again", "The Man Without Fear", "Guardian Devil", "Out"),
            relatedCharacterIds = listOf("char_spiderman", "char_wolverine"),
            mediaAppearances = listOf("Daredevil (Netflix, 2015)", "Daredevil: Born Again (2025)")
        ),
        Character(
            id = "char_captainamerica", name = "Captain America",
            aliases = listOf("Steve Rogers", "The First Avenger", "The Sentinel of Liberty"),
            publisher = Publisher.MARVEL,
            firstAppearance = "Captain America Comics #1 (1941)",
            creators = listOf("Joe Simon", "Jack Kirby"),
            synopsis = "Steve Rogers was a frail young man who volunteered for a secret military experiment during World War II. The Super-Soldier Serum transformed him into Captain America — the living legend of liberty.",
            imageUrl = "https://www.superherodb.com/pictures2/portraits/10/100/274.jpg",
            coverUrl = "https://www.superherodb.com/pictures2/portraits/10/100/274.jpg",
            tags = listOf("Hopeful", "Political", "Legacy", "War", "Identity"),
            keyStoryArcs = listOf("The Winter Soldier", "Civil War", "The Death of Captain America"),
            relatedCharacterIds = listOf("char_ironman", "char_wolverine"),
            mediaAppearances = listOf("Captain America: The Winter Soldier (2014)", "Avengers: Endgame (2019)")
        )
    )

    // ── Events / Story Arcs ─────────────────────────────────────────────
    val events: List<StoryArc> = listOf(
        StoryArc(
            id = "event_civil_war", title = "Civil War",
            publisher = Publisher.MARVEL,
            issueIds = listOf("civil_war_1", "civil_war_2", "civil_war_3", "civil_war_4", "civil_war_5", "civil_war_6", "civil_war_7"),
            essentialIssueIds = listOf("civil_war_1", "civil_war_3", "civil_war_7"),
            writer = "Mark Millar",
            startYear = 2006, endYear = 2007,
            synopsis = "When a catastrophic incident involving young superheroes results in massive civilian casualties, the U.S. government passes the Superhuman Registration Act. Iron Man supports it; Captain America opposes it. The Marvel Universe tears itself apart.",
            coverImageUrl = "https://upload.wikimedia.org/wikipedia/en/7/72/Civil_War_7.jpg",
            tags = listOf("Political", "War", "Identity"),
            isEvent = true,
            preEventArcIds = listOf("new_avengers_illuminati"),
            postEventArcIds = listOf("initiative_arc")
        ),
        StoryArc(
            id = "event_crisis_infinite_earths", title = "Crisis on Infinite Earths",
            publisher = Publisher.DC,
            issueIds = listOf("coie_1", "coie_2", "coie_3", "coie_4", "coie_5", "coie_6", "coie_7", "coie_8", "coie_9", "coie_10", "coie_11", "coie_12"),
            essentialIssueIds = listOf("coie_1", "coie_7", "coie_8", "coie_12"),
            writer = "Marv Wolfman",
            startYear = 1985, endYear = 1986,
            synopsis = "The Anti-Monitor threatens to destroy all positive-matter universes. Heroes and villains from across the multiverse unite for the greatest battle in DC history. The DC Universe would never be the same.",
            coverImageUrl = "https://upload.wikimedia.org/wikipedia/en/a/a3/Crisis_on_Infinite_Earths_1.jpg",
            tags = listOf("Cosmic", "Legacy", "War"),
            isEvent = true,
            preEventArcIds = emptyList(),
            postEventArcIds = listOf("legends_arc")
        ),
        StoryArc(
            id = "event_secret_wars_2015", title = "Secret Wars (2015)",
            publisher = Publisher.MARVEL,
            issueIds = listOf("sw_1", "sw_2", "sw_3", "sw_4", "sw_5", "sw_6", "sw_7", "sw_8", "sw_9"),
            essentialIssueIds = listOf("sw_1", "sw_5", "sw_9"),
            writer = "Jonathan Hickman",
            startYear = 2015, endYear = 2015,
            synopsis = "The Marvel multiverse is collapsing. When Earth-616 and Earth-1610 collide, Doctor Doom salvages what he can and forges Battleworld — a patchwork planet where he rules as God Emperor. Reed Richards must find a way to rebuild reality.",
            coverImageUrl = "https://upload.wikimedia.org/wikipedia/en/e/e8/Secret_Wars_%282015%29_1.jpg",
            tags = listOf("Cosmic", "Power", "Legacy"),
            isEvent = true,
            preEventArcIds = listOf("hickman_avengers_arc"),
            postEventArcIds = listOf("all_new_all_different_arc")
        ),
        StoryArc(
            id = "event_blackest_night", title = "Blackest Night",
            publisher = Publisher.DC,
            issueIds = listOf("bn_1", "bn_2", "bn_3", "bn_4", "bn_5", "bn_6", "bn_7", "bn_8"),
            essentialIssueIds = listOf("bn_1", "bn_4", "bn_8"),
            writer = "Geoff Johns",
            startYear = 2009, endYear = 2010,
            synopsis = "The dead rise across the DC Universe as Black Lantern rings reanimate fallen heroes and villains. Hal Jordan and the Green Lantern Corps must unite all colour corps of the emotional spectrum to stop Nekron's army of the dead.",
            coverImageUrl = "https://upload.wikimedia.org/wikipedia/en/a/a6/Blackest_Night_1.jpg",
            tags = listOf("Dark", "Cosmic", "War"),
            isEvent = true,
            preEventArcIds = listOf("sinestro_corps_war"),
            postEventArcIds = listOf("brightest_day_arc")
        ),
        StoryArc(
            id = "arc_year_one", title = "Batman: Year One",
            publisher = Publisher.DC,
            issueIds = listOf("batman_404", "batman_405", "batman_406", "batman_407"),
            essentialIssueIds = listOf("batman_404", "batman_405", "batman_406", "batman_407"),
            writer = "Frank Miller",
            startYear = 1987, endYear = 1987,
            synopsis = "The definitive origin of both Batman and Jim Gordon. Bruce Wayne returns to Gotham and forges his identity as the Dark Knight while Jim Gordon fights corruption within the GCPD.",
            coverImageUrl = "https://upload.wikimedia.org/wikipedia/en/6/63/Batman_Year_One_cover.jpg",
            tags = listOf("Dark", "Street Level", "Identity"),
            isEvent = false,
            preEventArcIds = emptyList(),
            postEventArcIds = listOf("arc_long_halloween")
        ),
        StoryArc(
            id = "arc_long_halloween", title = "Batman: The Long Halloween",
            publisher = Publisher.DC,
            issueIds = listOf("tlh_1", "tlh_2", "tlh_3", "tlh_4", "tlh_5", "tlh_6", "tlh_7", "tlh_8", "tlh_9", "tlh_10", "tlh_11", "tlh_12", "tlh_13"),
            essentialIssueIds = listOf("tlh_1", "tlh_6", "tlh_13"),
            writer = "Jeph Loeb",
            startYear = 1996, endYear = 1997,
            synopsis = "A mysterious serial killer named Holiday is murdering people on holidays. Batman, Jim Gordon, and Harvey Dent form a pact to bring down the Falcone crime family — but the line between justice and vengeance blurs.",
            coverImageUrl = "https://upload.wikimedia.org/wikipedia/en/4/40/Batman_long_halloween.jpg",
            tags = listOf("Dark", "Street Level", "Identity", "Detective"),
            isEvent = false,
            preEventArcIds = listOf("arc_year_one"),
            postEventArcIds = emptyList()
        )
    )

    // ── Media Items ─────────────────────────────────────────────────────
    val mediaItems: List<MediaItem> = listOf(
        MediaItem(
            id = "media_dark_knight", title = "The Dark Knight",
            type = MediaType.FILM, studio = "Warner Bros.", releaseYear = 2008,
            synopsis = "Batman faces the Joker, a criminal mastermind who plunges Gotham into anarchy. Christopher Nolan's masterpiece redefined the superhero genre.",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg",
            relatedComicIds = listOf("arc_year_one", "arc_long_halloween"),
            characterIds = listOf("char_batman"),
            tags = listOf("Dark", "Street Level", "Identity")
        ),
        MediaItem(
            id = "media_endgame", title = "Avengers: Endgame",
            type = MediaType.FILM, studio = "Marvel Studios", releaseYear = 2019,
            synopsis = "After Thanos' devastating snap, the surviving Avengers assemble for one final stand to undo the destruction and restore the universe.",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            relatedComicIds = listOf("event_secret_wars_2015"),
            characterIds = listOf("char_ironman", "char_captainamerica", "char_spiderman"),
            tags = listOf("Cosmic", "War", "Legacy")
        ),
        MediaItem(
            id = "media_winter_soldier", title = "Captain America: The Winter Soldier",
            type = MediaType.FILM, studio = "Marvel Studios", releaseYear = 2014,
            synopsis = "Steve Rogers teams up with Black Widow and new ally Falcon to expose a vast conspiracy within S.H.I.E.L.D., leading to a confrontation with the mysterious Winter Soldier.",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMzA2NDkwODAwM15BMl5BanBnXkFtZTgwODk5MTgzMTE@._V1_.jpg",
            relatedComicIds = listOf("event_civil_war"),
            characterIds = listOf("char_captainamerica"),
            tags = listOf("Political", "Street Level", "Identity")
        ),
        MediaItem(
            id = "media_daredevil_netflix", title = "Daredevil",
            type = MediaType.TV_SERIES, studio = "Netflix / Marvel Television", releaseYear = 2015,
            synopsis = "Blinded as a boy but gifted with extraordinary senses, Matt Murdock fights crime as a lawyer by day and as the superhero Daredevil in Hell's Kitchen by night.",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BODcwOTg2MDE3NF5BMl5BanBnXkFtZTgwNTUyNTY1NjM@._V1_.jpg",
            relatedComicIds = listOf("born_again_arc"),
            characterIds = listOf("char_daredevil"),
            tags = listOf("Dark", "Street Level", "Identity", "Redemption")
        ),
        MediaItem(
            id = "media_spider_verse", title = "Spider-Man: Into the Spider-Verse",
            type = MediaType.ANIMATED, studio = "Sony Pictures Animation", releaseYear = 2018,
            synopsis = "Brooklyn teen Miles Morales becomes Spider-Man and must join forces with Spider-People from across dimensions to stop a threat to all realities.",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_.jpg",
            relatedComicIds = listOf("spider_verse_arc"),
            characterIds = listOf("char_spiderman"),
            tags = listOf("Hopeful", "Cosmic", "Identity", "Family")
        )
    )

    // ── Reading Paths ───────────────────────────────────────────────────
    val readingPaths: List<ReadingPath> = listOf(
        ReadingPath(
            id = "path_batman_beginner", title = "Batman: The Essential Journey",
            description = "The definitive entry point for Batman. Start with his origin and progress through his greatest stories.",
            pathType = PathType.BEGINNER,
            issues = listOf(
                ReadingPathItem(1, "Batman: Year One (#404-407)", "The definitive Batman origin story", true),
                ReadingPathItem(2, "The Long Halloween (#1-13)", "The best Batman detective mystery", true),
                ReadingPathItem(3, "Dark Victory (#1-13)", "Direct sequel to Long Halloween", false),
                ReadingPathItem(4, "Hush (#1-12)", "Introduction to Batman's rogues gallery", true),
                ReadingPathItem(5, "Under the Red Hood", "Jason Todd's return — pivotal Bat-family moment", true),
                ReadingPathItem(6, "The Court of Owls (#1-11)", "Scott Snyder's modern masterpiece", true)
            ),
            estimatedHours = 18, tags = listOf("Dark", "Street Level", "Detective")
        ),
        ReadingPath(
            id = "path_spiderman_beginner", title = "Spider-Man: Great Power, Great Responsibility",
            description = "Follow Peter Parker's journey from high-school hero to the iconic Spider-Man.",
            pathType = PathType.BEGINNER,
            issues = listOf(
                ReadingPathItem(1, "Amazing Fantasy #15", "The issue that started it all", true),
                ReadingPathItem(2, "The Night Gwen Stacy Died", "The story that changed comics forever", true),
                ReadingPathItem(3, "Kraven's Last Hunt", "The darkest Spider-Man story ever told", true),
                ReadingPathItem(4, "Ultimate Spider-Man #1-13", "The modern origin by Bendis", true),
                ReadingPathItem(5, "Spider-Man: Blue", "A beautiful love letter to Gwen Stacy", false),
                ReadingPathItem(6, "Spider-Verse", "Every Spider-Man in every universe!", true)
            ),
            estimatedHours = 14, tags = listOf("Hopeful", "Street Level", "Identity")
        ),
        ReadingPath(
            id = "path_marvel_cosmic", title = "Marvel Cosmic: Journey to the Stars",
            description = "Explore the cosmic side of Marvel from the Infinity Gauntlet to the Guardians.",
            pathType = PathType.ESSENTIAL,
            issues = listOf(
                ReadingPathItem(1, "Silver Surfer: Parable", "The herald of Galactus in a philosophical tale", true),
                ReadingPathItem(2, "Infinity Gauntlet #1-6", "Thanos wields ultimate power", true),
                ReadingPathItem(3, "Annihilation", "The cosmic Marvel renaissance", true),
                ReadingPathItem(4, "Guardians of the Galaxy vol. 2 (2008)", "The modern Guardians begin", true),
                ReadingPathItem(5, "Infinity", "Jonathan Hickman's cosmic opus", false),
                ReadingPathItem(6, "Secret Wars (2015)", "The multiverse collapses", true)
            ),
            estimatedHours = 20, tags = listOf("Cosmic", "War", "Power")
        ),
        ReadingPath(
            id = "path_dc_crisis", title = "DC Crisis Events: Essential Guide",
            description = "Navigate DC's most universe-altering events in chronological order.",
            pathType = PathType.ESSENTIAL,
            issues = listOf(
                ReadingPathItem(1, "Crisis on Infinite Earths #1-12", "The event that unified DC continuity", true),
                ReadingPathItem(2, "Zero Hour #4-0", "A fracture in time", false),
                ReadingPathItem(3, "Infinite Crisis #1-7", "The multiverse returns", true),
                ReadingPathItem(4, "Final Crisis #1-7", "Grant Morrison's mythic epic", true),
                ReadingPathItem(5, "Flashpoint #1-5", "Barry Allen breaks reality", true),
                ReadingPathItem(6, "Dark Nights: Metal #1-6", "The Dark Multiverse opens", false)
            ),
            estimatedHours = 22, tags = listOf("Cosmic", "Legacy", "War")
        )
    )

    // ── Lore Cards ──────────────────────────────────────────────────────
    val loreCards: List<LoreCard> = listOf(
        LoreCard("lore_1", "The Real Creator of Batman", "While Bob Kane is officially credited, Bill Finger wrote the first Batman story, designed the iconic costume (adding the cowl, cape, and gloves), and created key elements like Gotham City, the Batcave, and most of Batman's rogues gallery. Finger received posthumous credit in 2015.", LoreCategory.CREATOR_INSIGHT, "char_batman", "character", null),
        LoreCard("lore_2", "Mazzucchelli's Neorealism", "David Mazzucchelli's art in Batman: Year One deliberately echoed Italian Neorealist cinema — using muted palettes, gritty urban environments, and naturalistic human figures to ground Batman in reality rather than superheroic spectacle.", LoreCategory.REAL_WORLD_INFLUENCE, "char_batman", "character", null),
        LoreCard("lore_3", "Spider-Man Was Almost Rejected", "Stan Lee's editor Martin Goodman initially rejected Spider-Man, saying 'people hate spiders' and a teenage hero could only be a sidekick. Lee slipped the character into the final issue of Amazing Fantasy — it became the best-selling issue of the year.", LoreCategory.ORIGIN, "char_spiderman", "character", null),
        LoreCard("lore_4", "The Death of Gwen Stacy Changed Comics", "The Night Gwen Stacy Died (Amazing Spider-Man #121-122) is widely credited as the story that ended the Silver Age of Comics. Marvel killed a major love interest — previously unthinkable — ushering in a darker, more mature era.", LoreCategory.TRIVIA, "char_spiderman", "character", null),
        LoreCard("lore_5", "Superman's Jewish Origins", "Jerry Siegel and Joe Shuster, both Jewish, created Superman partly as an immigrant fantasy — Kal-El (Hebrew for 'Voice of God') is sent from a doomed world to a new land where he becomes a champion. The parallels to the Moses narrative are intentional.", LoreCategory.REAL_WORLD_INFLUENCE, "char_superman", "character", null),
        LoreCard("lore_6", "Tony Stark Was Politically Provocative", "Stan Lee deliberately created Tony Stark as a character readers 'shouldn't' like — a wealthy arms dealer — to prove that good writing could make any character sympathetic. It was Lee's challenge to himself during the Vietnam War era.", LoreCategory.CREATOR_INSIGHT, "char_ironman", "character", null),
        LoreCard("lore_7", "The Marston Experiment", "William Moulton Marston, creator of Wonder Woman, was also the inventor of the systolic blood-pressure test — a key component of the lie detector. This directly inspired Wonder Woman's Lasso of Truth.", LoreCategory.ORIGIN, "char_wonderwoman", "character", null),
        LoreCard("lore_8", "Wolverine's First Appearance Was as a Hulk Villain", "Wolverine first appeared not in an X-Men comic, but as a government agent sent to fight the Hulk in The Incredible Hulk #181. Len Wein originally envisioned him as a teenager whose claws were part of his gloves, not his body.", LoreCategory.TRIVIA, "char_wolverine", "character", null),
        LoreCard("lore_9", "Crisis Killed Barry Allen and Supergirl", "Crisis on Infinite Earths (1985) killed two A-list characters: Barry Allen (The Flash) and Kara Zor-El (Supergirl). Barry's death was so iconic that he stayed dead for 23 years — a near-record in comics.", LoreCategory.TRIVIA, "event_crisis_infinite_earths", "event", null),
        LoreCard("lore_10", "Civil War Was Inspired by the Patriot Act", "Mark Millar has openly stated that Civil War was a direct allegory for the post-9/11 political landscape, specifically the tension between national security and individual civil liberties embodied by the Patriot Act.", LoreCategory.REAL_WORLD_INFLUENCE, "event_civil_war", "event", null),
        LoreCard("lore_11", "Born Again Is Considered the Greatest Daredevil Story", "Frank Miller's Daredevil: Born Again is frequently cited by writers and critics as not only the best Daredevil story ever told, but one of the greatest superhero stories in any medium.", LoreCategory.CREATOR_INSIGHT, "char_daredevil", "character", null),
        LoreCard("lore_12", "Cap's Shield Is a National Symbol", "Captain America's shield has appeared in real-world political protests and social movements. It has become a genuine cultural symbol of standing up against authority — something Steve Rogers embodies across all media.", LoreCategory.EASTER_EGG, "char_captainamerica", "character", null)
    )

    // ── Quizzes ──────────────────────────────────────────────────────────
    val quizzes: List<Quiz> = listOf(
        Quiz(
            id = "quiz_batman_101", title = "How Well Do You Know Batman?",
            category = QuizCategory.CHARACTER, difficulty = QuizDifficulty.CASUAL_FAN,
            questions = listOf(
                QuizQuestion("q1", QuestionType.MULTIPLE_CHOICE, "What is Batman's real name?", null, listOf("Clark Kent", "Bruce Wayne", "Tony Stark", "Oliver Queen"), 1, "Bruce Wayne has been Batman since Detective Comics #27 in 1939.", null),
                QuizQuestion("q2", QuestionType.MULTIPLE_CHOICE, "In which city does Batman operate?", null, listOf("Metropolis", "Star City", "Gotham City", "Central City"), 2, "Gotham City was created by Bill Finger and is often depicted as a dark mirror of New York City.", null),
                QuizQuestion("q3", QuestionType.MULTIPLE_CHOICE, "Who murdered Batman's parents?", null, listOf("The Joker", "Joe Chill", "Ra's al Ghul", "Bane"), 1, "Joe Chill murdered Thomas and Martha Wayne in Crime Alley when Bruce was a child.", null),
                QuizQuestion("q4", QuestionType.MULTIPLE_CHOICE, "Which villain broke Batman's back?", null, listOf("Killer Croc", "Deathstroke", "Bane", "Clayface"), 2, "In the Knightfall storyline (1993), Bane systematically exhausted Batman and then broke his back.", null),
                QuizQuestion("q5", QuestionType.MULTIPLE_CHOICE, "What is the name of Batman's butler?", null, listOf("Jarvis", "Alfred Pennyworth", "Lucius Fox", "Commissioner Gordon"), 1, "Alfred Pennyworth has served the Wayne family for decades and is Bruce's surrogate father figure.", null)
            ),
            relatedEntityId = "char_batman", estimatedMinutes = 5, xpReward = 50
        ),
        Quiz(
            id = "quiz_marvel_events", title = "Marvel Crossover Events",
            category = QuizCategory.EVENT, difficulty = QuizDifficulty.HARDCORE_FAN,
            questions = listOf(
                QuizQuestion("q6", QuestionType.MULTIPLE_CHOICE, "Which event saw Captain America and Iron Man on opposing sides?", null, listOf("Secret Invasion", "Civil War", "Siege", "Fear Itself"), 1, "Civil War (2006) divided the Marvel Universe over the Superhuman Registration Act.", null),
                QuizQuestion("q7", QuestionType.MULTIPLE_CHOICE, "Who wrote Secret Wars (2015)?", null, listOf("Brian Michael Bendis", "Jason Aaron", "Jonathan Hickman", "Dan Slott"), 2, "Jonathan Hickman's multi-year Avengers/New Avengers saga culminated in Secret Wars.", null),
                QuizQuestion("q8", QuestionType.MULTIPLE_CHOICE, "In Infinity Gauntlet, what does Thanos do first after acquiring all stones?", null, listOf("Destroys the Avengers", "Snaps half the universe", "Fights Galactus", "Creates a shrine"), 1, "Thanos erased half of all living beings in the universe to impress Death.", null),
                QuizQuestion("q9", QuestionType.MULTIPLE_CHOICE, "What replaced Earth-616 and Earth-1610 in Secret Wars?", null, listOf("Counter-Earth", "The Negative Zone", "Battleworld", "The Microverse"), 2, "Doctor Doom forged Battleworld from the remnants of the dying multiverse.", null),
                QuizQuestion("q10", QuestionType.MULTIPLE_CHOICE, "Which cosmic event is considered the start of Marvel Cosmic's renaissance?", null, listOf("Infinity War", "Annihilation", "War of Kings", "Realm of Kings"), 1, "Annihilation (2006) reinvigorated Marvel's cosmic characters and led to the modern Guardians of the Galaxy.", null)
            ),
            relatedEntityId = "event_civil_war", estimatedMinutes = 7, xpReward = 100
        ),
        Quiz(
            id = "quiz_dc_crisis", title = "DC Crisis Events: The Deep Dive",
            category = QuizCategory.EVENT, difficulty = QuizDifficulty.LORE_MASTER,
            questions = listOf(
                QuizQuestion("q11", QuestionType.MULTIPLE_CHOICE, "Who is the main villain in Crisis on Infinite Earths?", null, listOf("Darkseid", "The Anti-Monitor", "Brainiac", "Parallax"), 1, "The Anti-Monitor sought to destroy all positive-matter universes in the multiverse.", null),
                QuizQuestion("q12", QuestionType.MULTIPLE_CHOICE, "Which Flash died in Crisis on Infinite Earths?", null, listOf("Jay Garrick", "Wally West", "Barry Allen", "Bart Allen"), 2, "Barry Allen sacrificed himself to destroy the Anti-Monitor's antimatter cannon. He stayed dead for 23 years.", null),
                QuizQuestion("q13", QuestionType.MULTIPLE_CHOICE, "What caused the Flashpoint timeline?", null, listOf("Darkseid's invasion", "Superman's exile", "Barry Allen saving his mother", "Brainiac's attack"), 2, "Barry Allen went back in time to prevent his mother's murder, creating the Flashpoint timeline.", null),
                QuizQuestion("q14", QuestionType.MULTIPLE_CHOICE, "Who wrote the Blackest Night event?", null, listOf("Grant Morrison", "Geoff Johns", "Scott Snyder", "Tom King"), 1, "Geoff Johns wrote Blackest Night as the culmination of his epic Green Lantern run.", null),
                QuizQuestion("q15", QuestionType.MULTIPLE_CHOICE, "What did the New 52 reboot?", null, listOf("Only Batman titles", "Only Superman titles", "The entire DC Universe", "Only the Justice League"), 2, "DC relaunched its entire line with 52 new #1 issues in September 2011.", null)
            ),
            relatedEntityId = "event_crisis_infinite_earths", estimatedMinutes = 8, xpReward = 150
        ),
        Quiz(
            id = "quiz_mcu_films", title = "MCU Phase 1-4 Trivia",
            category = QuizCategory.MOVIE_TV, difficulty = QuizDifficulty.CASUAL_FAN,
            questions = listOf(
                QuizQuestion("q16", QuestionType.MULTIPLE_CHOICE, "What is the first film in the MCU?", null, listOf("The Incredible Hulk", "Iron Man", "Captain America: The First Avenger", "Thor"), 1, "Iron Man (2008) launched the MCU and featured the first post-credits scene teasing the Avengers.", null),
                QuizQuestion("q17", QuestionType.MULTIPLE_CHOICE, "Which Infinity Stone is in Loki's scepter?", null, listOf("Space Stone", "Reality Stone", "Mind Stone", "Power Stone"), 2, "The Mind Stone powered Loki's scepter and later became the core of Vision.", null),
                QuizQuestion("q18", QuestionType.MULTIPLE_CHOICE, "Who directed Captain America: The Winter Soldier?", null, listOf("Joss Whedon", "The Russo Brothers", "Taika Waititi", "Jon Favreau"), 1, "The Russo Brothers directed The Winter Soldier, which reinvented Cap as a political thriller.", null),
                QuizQuestion("q19", QuestionType.MULTIPLE_CHOICE, "What planet is Thanos from?", null, listOf("Xandar", "Titan", "Knowhere", "Hala"), 1, "Thanos hails from Titan, a moon of Saturn in the Marvel Universe.", null),
                QuizQuestion("q20", QuestionType.MULTIPLE_CHOICE, "Who said 'I am Iron Man' first?", null, listOf("War Machine", "Tony Stark", "Pepper Potts", "Obadiah Stane"), 1, "Tony Stark broke superhero convention by publicly revealing his identity at the end of Iron Man (2008).", null)
            ),
            relatedEntityId = "media_endgame", estimatedMinutes = 5, xpReward = 50
        )
    )

    // ── Helper Lookups ──────────────────────────────────────────────────
    fun getCharacterById(id: String): Character? = characters.find { it.id == id }
    fun getEventById(id: String): StoryArc? = events.find { it.id == id }
    fun getMediaById(id: String): MediaItem? = mediaItems.find { it.id == id }
    fun getLoreForCharacter(characterId: String): List<LoreCard> = loreCards.filter { it.relatedEntityId == characterId }
    fun getLoreForEvent(eventId: String): List<LoreCard> = loreCards.filter { it.relatedEntityId == eventId }
    fun getSimilarCharacters(characterId: String): List<Character> {
        val character = getCharacterById(characterId) ?: return emptyList()
        return characters.filter { it.id != characterId && it.tags.any { tag -> tag in character.tags } }.take(4)
    }
    fun getReadingPathForCharacter(characterId: String): ReadingPath? {
        val name = getCharacterById(characterId)?.name?.lowercase() ?: return null
        return readingPaths.find { it.id.contains(name.replace(" ", "").replace("-", "")) }
    }
    fun getQuizzesForCharacter(characterId: String): List<Quiz> = quizzes.filter { it.relatedEntityId == characterId }
    fun getMediaForCharacter(characterId: String): List<MediaItem> = mediaItems.filter { characterId in it.characterIds }
    fun getEventsForPublisher(publisher: Publisher): List<StoryArc> = events.filter { it.publisher == publisher && it.isEvent }
    fun getRecommendedQuizzes(): List<Quiz> = quizzes.take(3)
    fun getFeaturedCharacters(): List<Character> = characters.take(6)
}
