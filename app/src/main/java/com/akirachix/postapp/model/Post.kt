package com.akirachix.postapp.model

import android.icu.text.CaseMap.Title

data class Post(
    var userId:Int,
    var id:Int,
    var title: String,
    var body:String
)
