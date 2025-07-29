package com.example.digipostcard.model

data class PostCard(
    val id: String = "",
    val message: String = "",
    val hexColor: String = "#FFFFFF",
    val senderName: String = "",
    val senderAddress: String = "",
    val receiverName: String = "",
    val receiverAddress: String = ""
)