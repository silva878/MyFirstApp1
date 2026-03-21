package ru.shaumyan.myfirstapp.adapter
import ru.shaumyan.myfirstapp.dto.Post
interface OnPostInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onAvatarClick(post: Post) {}

    fun onPostClick(post: Post) {}  // новый метод для клика на карточку
}

