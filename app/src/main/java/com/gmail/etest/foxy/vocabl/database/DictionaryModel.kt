package com.gmail.etest.foxy.vocabl.database

import java.util.Date

data class DictionaryModel(
    val id: Int? = null,
    val name: String,
    val languageFrom: String,
    val languageTo: String,
    val translationSuggestions: Boolean,
    val count: Int = 0,
    val entryId: List<Int>?,
    val creationDate: Date = Date(),
    val updateDate: Date? = null
)

data class DictionaryEntryModel(
    val entryId: Int? = null,
    val entryName: String,
    val entryDescription: String,
    var repetitions: Int = 0,
    var previousInterval: Int = 0,
    var previousEaseFactor: Double = 2.5,
    val status: String = "New",
    val creationDate: Date = Date(),
    var reviewDate: Date = Date(),
    val updateDate: Date? = null
)