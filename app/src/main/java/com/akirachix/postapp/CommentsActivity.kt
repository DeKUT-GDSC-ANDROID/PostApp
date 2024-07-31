package com.akirachix.postapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.postapp.databinding.ActivityCommentsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsActivity : AppCompatActivity() {

    private var postId: Int = 0
    private lateinit var binding: ActivityCommentsBinding
    private val commentsAdapter by lazy { CommentsAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.extras?.getInt("POST_ID") ?: return

        setupRecyclerView()
        fetchPost(postId)
        fetchCommentsByPostID(postId)
    }

    private fun setupRecyclerView() {
        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.adapter = commentsAdapter
    }

    private fun fetchPost(postId: Int) {
        val apiClient = ApiClient.buildApiInterface(PostsApiInterface::class.java)
        apiClient.fetchPostById(postId).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    response.body()?.let { post ->
                        binding.tvPostTitle.text = post.title
                        binding.tvPostBody.text = post.body
                    } ?: showToast("Post not found")
                } else {
                    showToast("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                showToast("Failure: ${t.message}")
            }
        })
    }

    private fun fetchCommentsByPostID(postId: Int) {
        val apiClient = ApiClient.buildApiInterface(PostsApiInterface::class.java)
        apiClient.fetchCommentsByPostId(postId).enqueue(object : Callback<List<Comments>> {
            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                if (response.isSuccessful) {
                    val comments = response.body() ?: emptyList()
                    if (comments.isNotEmpty()) {
                        commentsAdapter.commentsList = comments
                        commentsAdapter.notifyDataSetChanged()
                    } else {
                        showToast("No comments found")
                    }
                } else {
                    showToast("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                showToast("Failure: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}