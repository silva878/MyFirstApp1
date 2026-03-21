package ru.shaumyan.myfirstapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.shaumyan.myfirstapp.R
import ru.shaumyan.myfirstapp.adapter.OnPostInteractionListener
import ru.shaumyan.myfirstapp.adapter.PostsAdapter
import ru.shaumyan.myfirstapp.databinding.FragmentFeedBinding
import ru.shaumyan.myfirstapp.dto.Post
import ru.shaumyan.myfirstapp.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()

    private val interactionListener = object : OnPostInteractionListener {
        // ... существующие методы

        override fun onPostClick(post: Post) {
            // Переход к детальному фрагменту с передачей ID поста
            val bundle = Bundle().apply {
                putLong("postId", post.id)
            }
            findNavController().navigate(
                R.id.action_feedFragment_to_postDetailFragment,
                bundle
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter

        // Важно: используем viewLifecycleOwner для подписки
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            // Переход к созданию нового поста
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
