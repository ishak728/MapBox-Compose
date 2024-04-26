package com.ishak.mapboxtest.model

data class Context(
    val country: Country,
    val neighborhood: Neighborhood,
    val place: Place,
    val postcode: Postcode,
    val region: Region,
    val street: Street
)