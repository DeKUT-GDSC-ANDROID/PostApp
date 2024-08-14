package com.akirachix.postapp.api

import com.akirachix.postapp.model.Comments
import com.akirachix.postapp.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApiInterface {
    @GET("/posts")
    suspend fun fetchPosts(): Response<List<Post>>

    @GET("/posts/{postId}")
    suspend  fun fetchPostById(@Path("postId") postId:Int): Response<Post>

    @GET("/posts/{postId}/comments")
    suspend fun fetchComments(@Path("postId") postId: Int) : Response<List<Comments>>
}