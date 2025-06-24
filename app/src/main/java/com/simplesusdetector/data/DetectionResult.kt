package com.simplesusdetector.data

data class DetectionResult(
    val title: String,
    val details: List<String> = emptyList(),
    val fix: String = ""
)