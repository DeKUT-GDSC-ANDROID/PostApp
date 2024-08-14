package com.akirachix.postapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.postapp.databinding.PostItemViewBinding
import com.akirachix.postapp.model.Post

class AdapterPost(private val posts: List<Post>, private val context: Context) :
    RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post, context)
    }
}

class PostViewHolder(private val binding: PostItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post, context: Context) {
        binding.tvTitle.text = post.title
        binding.tvId.text = post.id.toString()
        binding.tvUserId.text = post.userId.toString()
        binding.tvBody.text = post.body

        binding.clPost.setOnClickListener {
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra("POST_ID", post.id)
            context.startActivity(intent)
        }
    }
}
