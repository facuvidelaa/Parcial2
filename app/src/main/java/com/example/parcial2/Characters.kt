package com.example.parcial2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterResponse(
    val results: List<MyResult>
): Parcelable

@Parcelize
data class MyResult(
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val species: String,
): Parcelable

@Parcelize
data class Location(
    val name: String,
) : Parcelable