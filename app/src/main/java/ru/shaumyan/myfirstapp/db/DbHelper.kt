package ru.shaumyan.myfirstapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.shaumyan.myfirstapp.db.PostContract.Columns

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myfirstapp.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private const val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        val contentValues = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Астрология. Астрологические прогнозы")
            put(Columns.AUTHOR_ID, 2)
            put(
                Columns.CONTENT,
                "Гороскоп — главный инструмент астрологии, который отражает характеристику расположения планет в определённый момент времени по отношению к знакам зодиака. Зодиак делится на 12 равных секторов, каждый из которых назван в честь соответствующего астрономического созвездия: Овен, Телец, Близнецы, Рак, Лев, Дева, Весы, Скорпион, Стрелец, Козерог, Водолей и Рыбы."
            )
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 999)
            put(Columns.SHARES, 25)
            put(Columns.VIEWS, 5700)
            putNull(Columns.VIDEO)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues)

        // Второй пост с видео
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "АйТи")
            put(Columns.AUTHOR_ID, 3)
            put(
                Columns.CONTENT,
                "АйТи» — российская компания, основанная в 1990 году. Сегодня группа компаний АйТи, помимо системного интегратора — компании «АйТи."
            )
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Family beauty")
            put(Columns.AUTHOR_ID, 4)
            put(
                Columns.CONTENT,
                "Наша миссия: создавать красоту волос через этику, ответственность и признание Вашей индивидуальности.Салон красоты в Воронеже находится в центре города, рядом с Галереей Чижова"
            )
            put(Columns.PUBLISHED, "23 мая в 09:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 456)
            put(Columns.SHARES, 56)
            put(Columns.VIEWS, 3455)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Путешествия")
            put(Columns.AUTHOR_ID, 5)
            put(
                Columns.CONTENT,
                "В эти выходные мы отправились в маленький исторический городок, где на каждом шагу ощущается дух старины. Узкие мощёные улочки, уютные кафешки с местной кухней и приветливые жители сделали прогулку невероятно приятной. Особенно запомнились виды с холма, открывающиеся на вечерний закат. Такие моменты вдохновляют открывать новые горизонты и наполняют сердце теплом."
            )
            put(Columns.PUBLISHED, "20 мая в 20:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 533)
            put(Columns.SHARES, 33)
            put(Columns.VIEWS, 2345)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Еда и кулинария")
            put(Columns.AUTHOR_ID, 6)
            put(
                Columns.CONTENT,
                "Недавно попробовала приготовить крем-суп из брокколи, который одновременно лёгкий и очень вкусный. Использовала свежие овощи, немного сливок и приправила всё ароматными травами. Результат приятно удивил – суп получился нежным, с бархатистой текстурой и насыщенным вкусом. Готовится легко и быстро, что делает его идеальным вариантом для занятых будней или лёгкого ужина после насыщенного дня."
            )
            put(Columns.PUBLISHED, "20 мая в 23:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 123)
            put(Columns.SHARES, 57)
            put(Columns.VIEWS, 1457)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Здоровье и спорт")
            put(Columns.AUTHOR_ID, 7)
            put(
                Columns.CONTENT,
                "Пару дней назад начала ежедневно выделять время на утреннюю растяжку и простые дыхательные упражнения. Уже через несколько дней заметила, что уровень энергии повысился, исчезло чувство дискомфорта после долгого сидения, а настроение улучшилось. Это доказывает, что даже несколько минут в день, посвящённые заботе о теле, могут значительно повысить качество жизни и подготовить к любым повседневным задачам."
            )
            put(Columns.PUBLISHED, "24 мая в 19:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 123)
            put(Columns.SHARES, 57)
            put(Columns.VIEWS, 1457)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Технологии")
            put(Columns.AUTHOR_ID, 8)
            put(
                Columns.CONTENT,
                "Обновил свой смартфон до новой модели 2026 года, и теперь я просто в восторге от его возможностей. Камера делает потрясающе чёткие снимки даже при плохом освещении, а интерфейс интуитивно понятен и очень удобен. Особенно нравится функция быстрой зарядки — за полчаса батарея восполняется почти до полного заряда. Это инвестиция в комфорт и продуктивность, которую оценит каждый современный пользователь."
            )
            put(Columns.PUBLISHED, "26 апреля в 13:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 467)
            put(Columns.SHARES, 67)
            put(Columns.VIEWS, 1644)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Мода и стиль")
            put(Columns.AUTHOR_ID, 9)
            put(
                Columns.CONTENT,
                "Нашли идеальные джинсы, которые сидят как влитые и подходят к любой обуви"
            )
            put(Columns.PUBLISHED, "23 марта в 22:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 567)
            put(Columns.SHARES, 48)
            put(Columns.VIEWS, 1987)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Книги и саморазвитие")
            put(Columns.AUTHOR_ID, 10)
            put(
                Columns.CONTENT,
                "Недавно закончил читать книгу «Эссенциализм», и она стала настоящим откровением. Автор учит не просто эффективному управлению временем, а глубокому пониманию, насколько важно отбрасывать всё лишнее и фокусироваться только на самых значимых целях. Практические советы и вдохновляющие истории помогают перестроить мышление и перестать распыляться. Рекомендую всем, кто хочет сделать свою жизнь более осознанной и продуктивной."
            )
            put(Columns.PUBLISHED, "25 мая в 21:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 556)
            put(Columns.SHARES, 87)
            put(Columns.VIEWS, 1899)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Кино и сериалы")
            put(Columns.AUTHOR_ID, 11)
            put(
                Columns.CONTENT,
                "Посмотрел новый сезон одного из своих любимых сериалов и был приятно удивлён качеством сценария и актёрской игры. Сюжет приобрёл новую глубину, а неожиданные повороты держали в напряжении до последнего эпизода. Этот сезон отлично подходит для того, чтобы собрать друзей и устроить уютный вечер кино, обсуждая каждую деталь и строя свои теории о развитии событий."
            )
            put(Columns.PUBLISHED, "19 мая в 20:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 765)
            put(Columns.SHARES, 35)
            put(Columns.VIEWS, 2766)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
    }
}
