package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockAnimeRepository : AnimeRepository {
    private val animeList = listOf(
        Anime(
            id = 1,
            title = "Стальной алхимик: Братство",
            rating = "9.1",
            genres = listOf("Экшен", "Приключения", "Драма", "Фэнтези", "Сёнен"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2009-2010, Bones",
            episodesInfo = "1 сезон, 64 серии",
            description = "Братья Эдвард и Альфонс Элрики нарушили главный закон алхимии - попытались воскресить мать. В результате Эд потерял руку и ногу, а Аль стал закован в доспехи. Теперь они ищут философский камень, чтобы вернуть свои тела.",
            ratings = mapOf(1 to 50, 2 to 30, 3 to 80, 4 to 120, 5 to 200, 6 to 300, 7 to 450, 8 to 600, 9 to 750, 10 to 900),
            recommendationIds = listOf(2, 3, 4)
        ),
        Anime(
            id = 2,
            title = "Ковбой Бибоп",
            rating = "8.9",
            genres = listOf("Экшен", "Фантастика", "Нуар", "Приключения", "Сёнен"),
            imageResId = R.drawable.cowboy_beebop,
            info = "1998-1999, Sunrise",
            episodesInfo = "1 сезон, 26 серий",
            description = "Команда охотников за головами путешествует по Солнечной системе на корабле 'Бибоп' в поисках преступников. Каждый из них скрывает тёмное прошлое, которое постепенно раскрывается.",
            ratings = mapOf(1 to 20, 2 to 15, 3 to 40, 4 to 60, 5 to 100, 6 to 200, 7 to 350, 8 to 500, 9 to 650, 10 to 800),
            recommendationIds = listOf(1, 5, 7)
        ),
        Anime(
            id = 3,
            title = "Твоё имя",
            rating = "8.8",
            genres = listOf("Романтика", "Драма", "Фэнтези", "Повседневность"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2016, CoMix Wave Films",
            episodesInfo = "Фильм",
            description = "Старшеклассники Мицуха и Таки обнаруживают, что временами меняются телами. Вскоре между ними возникает связь, но внезапно обмен прекращается. Таки решает найти девушку, не подозревая, что их разделяет не только расстояние, но и время.",
            ratings = mapOf(1 to 10, 2 to 5, 3 to 15, 4 to 30, 5 to 50, 6 to 100, 7 to 250, 8 to 400, 9 to 600, 10 to 850),
            recommendationIds = listOf(4, 6, 8)
        ),
        Anime(
            id = 4,
            title = "Атака титанов",
            rating = "9.0",
            genres = listOf("Экшен", "Драма", "Фэнтези", "Ужасы", "Сёнен"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2013-2023, Wit Studio, MAPPA",
            episodesInfo = "4 сезона, 89 серий",
            description = "Человечество живёт за тремя стенами, защищающими от титанов - гигантских человекоподобных существ. После прорыва стены Эрен Йегер клянётся уничтожить всех титанов.",
            ratings = mapOf(1 to 30, 2 to 20, 3 to 50, 4 to 80, 5 to 150, 6 to 250, 7 to 400, 8 to 550, 9 to 700, 10 to 950),
            recommendationIds = listOf(1, 5, 9)
        ),
        Anime(
            id = 5,
            title = "Девушка, покорившая время",
            rating = "8.3",
            genres = listOf("Романтика", "Драма", "Фантастика", "Школа"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2006, Madhouse",
            episodesInfo = "Фильм",
            description = "Старшеклассница Макото обретает способность перемещаться во времени. Она использует эту способность для решения повседневных проблем, пока не сталкивается с последствиями своих действий.",
            ratings = mapOf(1 to 15, 2 to 10, 3 to 25, 4 to 40, 5 to 70, 6 to 120, 7 to 200, 8 to 300, 9 to 450, 10 to 650),
            recommendationIds = listOf(3, 6, 10)
        ),
        Anime(
            id = 6,
            title = "Монстр",
            rating = "8.8",
            genres = listOf("Драма", "Психологическое", "Триллер", "Детектив", "Сёнен"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2004-2005, Madhouse",
            episodesInfo = "1 сезон, 74 серии",
            description = "Нейрохирург Кензо Тэмма спасает жизнь мальчика, не зная, что тот станет серийным убийцей. Осознав свою ошибку, доктор отправляется на поиски 'монстра', которого сам же и воскресил.",
            ratings = mapOf(1 to 25, 2 to 15, 3 to 35, 4 to 60, 5 to 100, 6 to 180, 7 to 300, 8 to 450, 9 to 600, 10 to 800),
            recommendationIds = listOf(2, 7, 11)
        ),
        Anime(
            id = 7,
            title = "Ходячий замок",
            rating = "8.7",
            genres = listOf("Фэнтези", "Приключения", "Романтика", "Драма"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2004, Studio Ghibli",
            episodesInfo = "Фильм",
            description = "Юная шляпница Софи проклята Ведьмой Пустоши и превращена в старуху. В поисках спасения она попадает в ходячий замок загадочного волшебника Хаула.",
            ratings = mapOf(1 to 10, 2 to 5, 3 to 20, 4 to 40, 5 to 80, 6 to 150, 7 to 300, 8 to 500, 9 to 700, 10 to 900),
            recommendationIds = listOf(3, 5, 8)
        ),
        Anime(
            id = 8,
            title = "Судьба/Ночь схватки",
            rating = "8.4",
            genres = listOf("Экшен", "Фэнтези", "Драма", "Сверхъестественное"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2006, Studio Deen",
            episodesInfo = "1 сезон, 24 серии",
            description = "Семь магов вызывают семерых легендарных героев для участия в Священной Граальской войне. Победитель получит возможность исполнить любое желание.",
            ratings = mapOf(1 to 40, 2 to 30, 3 to 60, 4 to 90, 5 to 150, 6 to 250, 7 to 400, 8 to 550, 9 to 650, 10 to 750),
            recommendationIds = listOf(4, 9, 12)
        ),
        Anime(
            id = 9,
            title = "Волчий дождь",
            rating = "8.1",
            genres = listOf("Фантастика", "Драма", "Приключения", "Психологическое"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2003, Bones",
            episodesInfo = "1 сезон, 26 серий",
            description = "В мире, где волки считаются вымершими, четверо оборотней ищут легендарный Рай - место, где они смогут обрести покой. Их преследуют люди, не подозревающие об истинной природе 'собак'.",
            ratings = mapOf(1 to 30, 2 to 20, 3 to 50, 4 to 70, 5 to 120, 6 to 200, 7 to 300, 8 to 400, 9 to 500, 10 to 600),
            recommendationIds = listOf(2, 6, 10)
        ),
        Anime(
            id = 10,
            title = "Паразит",
            rating = "8.5",
            genres = listOf("Ужасы", "Фантастика", "Драма", "Психологическое", "Сёнен"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2014-2015, Madhouse",
            episodesInfo = "1 сезон, 24 серии",
            description = "Паразиты захватывают человеческие тела, но один из них по ошибке занял только руку школьника Синъити. Теперь они вынуждены сосуществовать и бороться с другими паразитами.",
            ratings = mapOf(1 to 20, 2 to 15, 3 to 40, 4 to 60, 5 to 100, 6 to 180, 7 to 300, 8 to 450, 9 to 600, 10 to 750),
            recommendationIds = listOf(1, 4, 7)
        ),
        Anime(
            id = 11,
            title = "Код Гиас",
            rating = "8.7",
            genres = listOf("Фантастика", "Политика", "Меха", "Психологическое"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2006-2008, Sunrise",
            episodesInfo = "2 сезона, 50 серий",
            description = "Принц Британии Лэлуш получает способность приказывать людям, но должен смотреть им в глаза. Он использует эту силу, чтобы свергнуть Британскую империю и создать мир для своей сестры.",
            ratings = mapOf(1 to 25, 2 to 20, 3 to 50, 4 to 80, 5 to 150, 6 to 250, 7 to 400, 8 to 550, 9 to 650, 10 to 800),
            recommendationIds = listOf(3, 8, 12)
        ),
        Anime(
            id = 12,
            title = "Класс убийц",
            rating = "8.2",
            genres = listOf("Экшен", "Комедия", "Школа", "Сёнен"),
            imageResId = R.drawable.cowboy_beebop,
            info = "2015-2016, Lerche",
            episodesInfo = "2 сезона, 22 серии",
            description = "Класс 3-E готовится убить своего учителя - инопланетянина, уничтожившего Луну. За его голову обещано огромное вознаграждение, но Коро-сенсей оказался лучшим учителем, которого они когда-либо имели.",
            ratings = mapOf(1 to 30, 2 to 25, 3 to 60, 4 to 90, 5 to 150, 6 to 220, 7 to 350, 8 to 450, 9 to 550, 10 to 650),
            recommendationIds = listOf(5, 9, 11)
        )
    )

    override suspend fun getAnimeList(): List<Anime> {
        delay(1000) // Имитация загрузки
        if (Random.nextFloat() < 0.1f) { // 10% chance of error
            throw Exception("Ошибка сервера")
        }
        return animeList
    }

    override suspend fun getAnimeById(id: Int): Anime? {
        delay(1000)
        if (Random.nextBoolean()) throw Exception("Ошибка загрузки аниме")
        return animeList.find { it.id == id }?.copy(
            recommendations = animeList.filter { it.id in (animeList.find { a -> a.id == id }?.recommendationIds ?: emptyList()) }
        )
    }

    override suspend fun searchAnime(query: String): List<Anime> {
        delay(500)
        if (query.isEmpty()) return emptyList()
        return animeList.filter {
            it.title.contains(query, true) ||
            it.genres.any { genre -> genre.contains(query, true) }
        }
    }
}