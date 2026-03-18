package ru.shaumyan.myfirstapp.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.shaumyan.myfirstapp.dto.Post

class PostRepositoryInMemoryImpl:PostRepository  {

    // Теперь это список, а не один пост
    private var posts = listOf(
        Post(
            id = 1,
            author = " Астрология. Астрологические прогнозы",
            content = " Гороскоп — главный инструмент астрологии, который отражает характеристику расположения планет в определённый момент времени по отношению к знакам зодиака. Зодиак делится на 12 равных секторов, каждый из которых назван в честь соответствующего астрономического созвездия: Овен, Телец, Близнецы, Рак, Лев, Дева, Весы, Скорпион, Стрелец, Козерог, Водолей и Рыбы.",
            " 10 апреля в 11:55",
            likedByMe = false,
            likes = 999,
            shares = 25,
            views = 5700
        ),
        Post(
            id = 2,
            author = "Астрология. Астрологические прогнозы",
            content = "Первое деление неба на 12 частей появилось на территории современного Ирана — Вавилона — Шумерской цивилизацией.",
            published = "22 мая в 10:15",
            likedByMe = false,
            likes = 342,
            shares = 89,
            views = 2300
        ),
        Post(
            id = 3,
            author = "Астрология. Астрологические прогнозы",
            content = "Древние считали, что Солнце, вращаясь, за сутки посещало все знаки зодиака. Именно поэтому происходило изменение карты.",
            published = "23 мая в 09:42",
            likedByMe = false,
            likes = 1250,
            shares = 420,
            views = 8900
        ),
        Post(
            id = 4,
            author = "Астрология. Астрологические прогнозы",
            content = "Вычисление солнечного года и лунного месяца впервые оказалось под силу халдеям — народам Древней Греции. По расположению планет составляли гороскоп новорожденного. ",
            published = "20 мая в 20:00",
            likedByMe = false,
            likes = 5678,
            shares = 1234,
            views = 45000
        )
    )

    private val _data = MutableLiveData(posts)

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
    }
}


