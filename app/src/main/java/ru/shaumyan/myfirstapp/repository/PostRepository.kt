package ru.shaumyan.myfirstapp.repository
import androidx.lifecycle.LiveData
import ru.shaumyan.myfirstapp.dto.Post


interface PostRepository {
    // Возвращает LiveData, на которую можно подписаться
   fun getAll(): LiveData<List<Post>>

    // Лайк/дизлайк
    fun likeById(id: Long)

    // Репост (увеличение счетчика)


    // Изменение просмотров (может пригодиться позже)
    fun increaseViews(id: Long)
    fun shareById(id: Long)

}