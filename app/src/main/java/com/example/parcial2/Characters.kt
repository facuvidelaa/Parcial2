package com.example.parcial2

import android.os.Parcelable

data class CharacterResponse(
    val results: List<MyResult>
)

data class MyResult(
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val species: String,
)

data class Location(
    val name: String,
)