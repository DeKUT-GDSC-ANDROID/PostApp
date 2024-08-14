package com.akirachix.postapp.repository

import com.akirachix.postapp.api.ApiClient
import com.akirachix.postapp.api.PostsApiInterface
import com.akirachix.postapp.model.Comments
import com.akirachix.postapp.model.Post
import retrofit2.Response

class PostRepository {
    private val apiClient = ApiClient.buildApiInterface(PostsApiInterface::class.java)

    suspend fun fetchPosts(): Response<List<Post>> {
        // Assumes apiClient.fetchPosts() is a suspend function
        return apiClient.fetchPosts()
    }

    suspend fun fetchPostById(postId: Int): Response<Post> {
        // Assumes apiClient.fetchPostById(postId) is a suspend function
        return apiClient.fetchPostById(postId)
    }

    suspend fun fetchComments(postId: Int): Response<List<Comments>> {
        // Assumes apiClient.fetchComments(postId) is a suspend function
        return apiClient.fetchComments(postId)
    }
}
