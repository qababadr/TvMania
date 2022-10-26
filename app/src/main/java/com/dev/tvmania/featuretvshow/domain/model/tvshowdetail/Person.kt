package com.dev.tvmania.featuretvshow.domain.model.tvshowdetail

import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image

data class Person(
    val id: Long,
    val image: Image?,
    val name: String,
    val updated: Long,
    val url: String
)
