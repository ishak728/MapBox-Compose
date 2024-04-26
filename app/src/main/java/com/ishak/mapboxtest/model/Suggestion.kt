package com.ishak.mapboxtest.model

data class Suggestion(
    val address: String,
    val context: Context,
    val external_ids: ExternalIds,
    val feature_type: String,
    val full_address: String,
    val language: String,
    val maki: String,
    val mapbox_id: String,
    val metadata: Metadata,
    val name: String,
    val place_formatted: String,
    val poi_category: List<String>,
    val poi_category_ids: List<String>
)