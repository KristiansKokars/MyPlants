package com.kristianskokars.myplants.core.data

object DeepLinks {
    // Remember to edit the manifest for the deep link receiver link too!
    private const val MY_PLANTS_DEEP_LINK_URL = "https://www.myplants.com"

    const val plantPattern = "$MY_PLANTS_DEEP_LINK_URL/plant/{id}"
    fun item(id: String, type: Type) = "$MY_PLANTS_DEEP_LINK_URL/$type/$id"

    enum class Type {
        PLANT;

        override fun toString(): String = when (this) {
            PLANT -> "plant"
        }

        fun toPair(): Pair<String, String> = Type.KEY to toString()

        companion object {
            const val ID = "id"
            const val KEY = "type"
        }
    }

    enum class Extra {
        NAME;

        override fun toString(): String = when (this) {
            NAME -> "name"
        }
    }
}

fun String.toDeepLinkType() = when (this) {
    "plant" -> DeepLinks.Type.PLANT
    else -> null
}
