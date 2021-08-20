package com.mobile.submissionperintis.data.model

data class Artisan(
    val id: String,
    val name: String,
    val description: String,
    val rating: String,
    val services: List<Service>,
    val image: String
)