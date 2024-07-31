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
    private lateinit var binding: ActivityCommentsBinding
    private var postId = 0
    private lateinit var adapter: AdapterPost
    private val posts = mutableListOf<Post>() // List to hold posts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getIntExtra("POST_ID", 0)

        if (postId != 0) {
            fetchPost()
        } else {
            Toast.makeText(this, "Post ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Setup RecyclerView
        binding.recyclerView1.layoutManager = LinearLayoutManager(this)
        adapter = AdapterPost(posts, this)
        binding.recyclerView1.adapter = adapter
    }

    private fun fetchPost() {
        val apiClient = ApiClient.buildApiClient(PostsApiInterface::class.java)
        val request = apiClient.fetchPostById(postId)
        request.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    post?.let {
                        // Clear and add post to the list
                        posts.clear()
                        posts.add(it)
                        adapter.notifyDataSetChanged() // Notify adapter of data change
                    } ?: run {
                        Toast.makeText(this@CommentsActivity, "Post not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CommentsActivity, response.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@CommentsActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
