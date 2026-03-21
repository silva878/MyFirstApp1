package ru.shaumyan.myfirstapp.repository
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.shaumyan.myfirstapp.dto.Post
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PostRepositoryFileImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val filename = "posts.json"

    // Тип для десериализации списка постов
    private val type = object : TypeToken<List<Post>>() {}.type

    // Счетчик для генерации ID
    private var nextId = 1L

    // Текущий пользователь
    private val currentUserId = 1L
    private val currentUserName = "Я"

    // Данные в памяти
    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        // При создании репозитория пытаемся загрузить данные из файла
        loadData()
    }

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            // Создание нового поста
            val newPost = post.copy(
                id = generateNextId(),
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            listOf(newPost) + posts
        } else {
            // Обновление существующего поста
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        saveData()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
        saveData()
    }

    // Загрузка данных из файла

    private fun loadData() {
        val file = getPostsFile()
        if (!file.exists()) {
            // Если файла нет, создаем начальные данные
            createInitialData()
            saveData()
            return
        }

        try {
            context.openFileInput(filename).bufferedReader().use { reader ->
                val loadedPosts: List<Post> = gson.fromJson(reader, type)
                if (loadedPosts.isNotEmpty()) {
                    posts = loadedPosts
                    // Вычисляем следующий ID на основе максимального существующего
                    nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    _data.value = posts
                } else {
                    createInitialData()
                    saveData()
                }
            }
        } catch (e: Exception) {
            // В случае ошибки создаем начальные данные
            e.printStackTrace()
            createInitialData()
            saveData()
        }
    }

    //Сохранение данных в файл

    private fun saveData() {
        try {
            context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use { writer ->
                gson.toJson(posts, writer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Получение файла для хранения постов

    private fun getPostsFile(): File = context.filesDir.resolve(filename)

    //Создание начальных данных при первом запуске
    private fun createInitialData() {
        posts = listOf(
            Post(
                id = 1,
                author = " Астрология. Астрологические прогнозы",
                authorId = 2,
                content = " Гороскоп — главный инструмент астрологии, который отражает характеристику расположения планет в определённый момент времени по отношению к знакам зодиака. Зодиак делится на 12 равных секторов, каждый из которых назван в честь соответствующего астрономического созвездия: Овен, Телец, Близнецы, Рак, Лев, Дева, Весы, Скорпион, Стрелец, Козерог, Водолей и Рыбы.",
                published = "10 апреля в 11:55",
                likedByMe = false,
                likes = 999,
                shares = 25,
                views = 5700,
                video = null
            ),
            Post(
                id = 2,
                author = "АйТи",
                authorId = 3,
                content = "«АйТи» — российская компания, основанная в 1990 году. Сегодня группа компаний АйТи, помимо системного интегратора — компании «АйТи.",
                published = "22 мая в 10:15",
                likedByMe = false,
                likes = 342,
                shares = 89,
                views = 2300,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"  // пример видео
            ),
            Post(
                id = 3,
                author = "Family beauty",
                authorId = 4,
                content = "Наша миссия: создавать красоту волос через этику, ответственность и признание Вашей индивидуальности.Салон красоты в Воронеже находится в центре города, рядом с Галереей Чижова",
                published = "23 мая в 09:42",
                likedByMe = false,
                likes = 1250,
                shares = 420,
                views = 8900
            ),
            Post(
                id = 4,
                author = "Путешествия",
                authorId = 5,
                content = "В эти выходные мы отправились в маленький исторический городок, где на каждом шагу ощущается дух старины. Узкие мощёные улочки, уютные кафешки с местной кухней и приветливые жители сделали прогулку невероятно приятной. Особенно запомнились виды с холма, открывающиеся на вечерний закат. Такие моменты вдохновляют открывать новые горизонты и наполняют сердце теплом.",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 5678,
                shares = 1234,
                views = 45000

            ),
            Post(
                id = 5,
                author = "Еда и кулинария",
                authorId = 6,
                content = "Недавно попробовала приготовить крем-суп из брокколи, который одновременно лёгкий и очень вкусный. Использовала свежие овощи, немного сливок и приправила всё ароматными травами. Результат приятно удивил – суп получился нежным, с бархатистой текстурой и насыщенным вкусом. Готовится легко и быстро, что делает его идеальным вариантом для занятых будней или лёгкого ужина после насыщенного дня.",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 3434,
                shares = 1344,
                views = 3455
            ),
            Post(
                id = 6,
                author = "Здоровье и спорт",
                authorId = 7,
                content = "Пару дней назад начала ежедневно выделять время на утреннюю растяжку и простые дыхательные упражнения. Уже через несколько дней заметила, что уровень энергии повысился, исчезло чувство дискомфорта после долгого сидения, а настроение улучшилось. Это доказывает, что даже несколько минут в день, посвящённые заботе о теле, могут значительно повысить качество жизни и подготовить к любым повседневным задачам.",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 3444,
                shares = 2300,
                views = 2344
            ),
            Post(
                id = 7,
                author = "Технологии",
                authorId = 8,
                content = "Обновил свой смартфон до новой модели 2026 года, и теперь я просто в восторге от его возможностей. Камера делает потрясающе чёткие снимки даже при плохом освещении, а интерфейс интуитивно понятен и очень удобен. Особенно нравится функция быстрой зарядки — за полчаса батарея восполняется почти до полного заряда. Это инвестиция в комфорт и продуктивность, которую оценит каждый современный пользователь.",
                published = "26 апреля в 13:00",
                likedByMe = false,
                likes = 2354,
                shares = 2344,
                views = 3277
            ),
            Post(
                id = 8,
                author = "Мода и стиль",
                authorId = 9,
                content = "Нашли идеальные джинсы, которые сидят как влитые и подходят к любой обуви",
                published = "23 марта в 22:00",
                likedByMe = false,
                likes = 45900,
                shares = 678,
                views = 42000
            ),
            Post(
                id = 9,
                author = "Книги и саморазвитие",
                authorId = 10,
                content = "Недавно закончил читать книгу «Эссенциализм», и она стала настоящим откровением. Автор учит не просто эффективному управлению временем, а глубокому пониманию, насколько важно отбрасывать всё лишнее и фокусироваться только на самых значимых целях. Практические советы и вдохновляющие истории помогают перестроить мышление и перестать распыляться. Рекомендую всем, кто хочет сделать свою жизнь более осознанной и продуктивной.",
                published = "25 мая в 21:00",
                likedByMe = false,
                likes = 2100,
                shares = 500,
                views = 21000
            ),
            Post(
                id = 10,
                author = "Кино и сериалы",
                authorId = 11,
                content = "Посмотрел новый сезон одного из своих любимых сериалов и был приятно удивлён качеством сценария и актёрской игры. Сюжет приобрёл новую глубину, а неожиданные повороты держали в напряжении до последнего эпизода. Этот сезон отлично подходит для того, чтобы собрать друзей и устроить уютный вечер кино, обсуждая каждую деталь и строя свои теории о развитии событий.",
                published = "19 мая в 20:30",
                likedByMe = false,
                likes = 3000,
                shares = 2477,
                views = 36999
            )
        )
        _data.value = posts
    }

    // Генерация следующего ID

    private fun generateNextId(): Long = nextId++

    // Форматирование даты

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}
