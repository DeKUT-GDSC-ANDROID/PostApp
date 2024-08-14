package com.akirachix.postapp.ui

import PostsViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.postapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: AdapterPost

    private lateinit var postsViewModel: PostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        postsViewModel = ViewModelProvider(this).get(PostsViewModel::class.java)



        // Observe the postsLiveData from the ViewModel
        postsViewModel.postsLiveData.observe(this, Observer { posts ->
            if (posts != null) {
                postAdapter = AdapterPost(posts, this)
                recyclerView.adapter = postAdapter
                Toast.makeText(baseContext, "Fetched ${posts.size} posts", Toast.LENGTH_LONG).show()
            }
        })

        // Observe the errorLiveData from the ViewModel to handle errors
        postsViewModel.errorLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()
            }
        })

        // Fetch posts using the ViewModel
        postsViewModel.fetchPosts()
    }
}
