package com.akirachix.postapp.ui

import PostsViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.postapp.databinding.ActivityCommentsBinding

class CommentsActivity : AppCompatActivity() {

    private var postId: Int = 0
    private lateinit var binding: ActivityCommentsBinding
    private val commentsAdapter by lazy { CommentsAdapter(emptyList()) }
    private lateinit var postsViewModel: PostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postsViewModel = ViewModelProvider(this).get(PostsViewModel::class.java)


        postId = intent.extras?.getInt("POST_ID") ?: return

        setupRecyclerView()
        observeViewModel()

        // Fetch post and comments using the ViewModel
        postsViewModel.fetchPostsById(postId)
        postsViewModel.fetchComments(postId)
    }

    private fun setupRecyclerView() {
        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.adapter = commentsAdapter
    }

    private fun observeViewModel() {
        // Observe the Post LiveData
        postsViewModel.postLiveData.observe(this, Observer { post ->
            post?.let {
                binding.tvPostTitle.text = it.title
                binding.tvPostBody.text = it.body
            } ?: showToast("Post not found")
        })

        // Observe the Comments LiveData
        postsViewModel.commentsLiveData.observe(this, Observer { comments ->
            if (comments.isNotEmpty()) {
                commentsAdapter.commentsList = comments
                commentsAdapter.notifyDataSetChanged()
            } else {
                showToast("No comments found")
            }
        })

        // Observe the error LiveData
        postsViewModel.errorLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                showToast(it)
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
