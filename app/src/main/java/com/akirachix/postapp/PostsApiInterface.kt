package com.akirachix.postapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApiInterface {
    @GET("/posts")
    fun fetchPosta(): Call<List<Post>>


    @GET("/posts/{postId}")
    fun fetchPostById(@Path("postId")postId: Int): Call<Post>

}