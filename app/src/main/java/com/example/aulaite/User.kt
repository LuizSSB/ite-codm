package com.example.aulaite

import java.io.Serializable

data class User(
    val id: Int,
    var username: String
) : Serializable
