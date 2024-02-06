package com.kristianskokars.myplants.core.data.model

data class Plant(
    val id: String,
    val name: String,
    val description: String,
    val waterInMilliliters: Int,
    val pictureUrl: String?
) {
    companion object {
        fun getPreview() = Plant(
            id = "I",
            name = "Monstera",
            description = "Short Description",
            waterInMilliliters = 50,
            pictureUrl = null
        )
    }
}
