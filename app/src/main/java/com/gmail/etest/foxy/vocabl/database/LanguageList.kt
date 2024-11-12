package com.gmail.etest.foxy.vocabl.database

data class LanguageSupport(val language: String, val iso: String, val hasDictionarySupport: Boolean)

fun getSupportedLanguageList(): List<LanguageSupport>{
    val supportedLanguagesList = ArrayList<LanguageSupport>()

    supportedLanguagesList.add(LanguageSupport("Custom", "custom", false))
    supportedLanguagesList.add(LanguageSupport("Afrikaans", "af", true))
    supportedLanguagesList.add(LanguageSupport("Albanian", "sq", false))
    supportedLanguagesList.add(LanguageSupport("Amharic", "am", false))
    supportedLanguagesList.add(LanguageSupport("Arabic", "ar", true))
    supportedLanguagesList.add(LanguageSupport("Armenian", "hy", false))
    supportedLanguagesList.add(LanguageSupport("Assamese", "as", false))
    supportedLanguagesList.add(LanguageSupport("Azerbaijani (Latin)", "az", false))
    supportedLanguagesList.add(LanguageSupport("Bangla", "bn", true))
    supportedLanguagesList.add(LanguageSupport("Bashkir", "ba", false))
    supportedLanguagesList.add(LanguageSupport("Basque", "eu", false))
    supportedLanguagesList.add(LanguageSupport("Bhojpuri", "bho", false))
    supportedLanguagesList.add(LanguageSupport("Bodo", "brx", false))
    supportedLanguagesList.add(LanguageSupport("Bosnian (Latin)", "bs", true))
    supportedLanguagesList.add(LanguageSupport("Bulgarian", "bg", true))
    supportedLanguagesList.add(LanguageSupport("Cantonese (Traditional)", "yue", false))
    supportedLanguagesList.add(LanguageSupport("Catalan", "ca", true))
    supportedLanguagesList.add(LanguageSupport("Chinese (Literary)", "lzh", false))
    supportedLanguagesList.add(LanguageSupport("Chinese Simplified", "zh-Hans", true))
    supportedLanguagesList.add(LanguageSupport("Chinese Traditional", "zh-Hant", false))
    supportedLanguagesList.add(LanguageSupport("chiShona", "sn", false))
    supportedLanguagesList.add(LanguageSupport("Croatian", "hr", true))
    supportedLanguagesList.add(LanguageSupport("Czech", "cs", true))
    supportedLanguagesList.add(LanguageSupport("Danish", "da", true))
    supportedLanguagesList.add(LanguageSupport("Dari", "prs", false))
    supportedLanguagesList.add(LanguageSupport("Divehi", "dv", false))
    supportedLanguagesList.add(LanguageSupport("Dogri", "doi", false))
    supportedLanguagesList.add(LanguageSupport("Dutch", "nl", true))
    supportedLanguagesList.add(LanguageSupport("English", "en", true))
    supportedLanguagesList.add(LanguageSupport("Estonian", "et", false))
    supportedLanguagesList.add(LanguageSupport("Faroese", "fo", false))
    supportedLanguagesList.add(LanguageSupport("Fijian", "fj", false))
    supportedLanguagesList.add(LanguageSupport("Filipino", "fil", false))
    supportedLanguagesList.add(LanguageSupport("Finnish", "fi", true))
    supportedLanguagesList.add(LanguageSupport("French", "fr", true))
    supportedLanguagesList.add(LanguageSupport("French (Canada)", "fr-ca", false))
    supportedLanguagesList.add(LanguageSupport("Galician", "gl", false))
    supportedLanguagesList.add(LanguageSupport("Georgian", "ka", false))
    supportedLanguagesList.add(LanguageSupport("German", "de", true))
    supportedLanguagesList.add(LanguageSupport("Greek", "el", true))
    supportedLanguagesList.add(LanguageSupport("Gujarati", "gu", false))
    supportedLanguagesList.add(LanguageSupport("Haitian Creole", "ht", true))
    supportedLanguagesList.add(LanguageSupport("Hausa", "ha", false))
    supportedLanguagesList.add(LanguageSupport("Hebrew", "he", true))
    supportedLanguagesList.add(LanguageSupport("Hindi", "hi", true))
    supportedLanguagesList.add(LanguageSupport("Hmong Daw (Latin)", "mww", true))
    supportedLanguagesList.add(LanguageSupport("Hungarian", "hu", true))
    supportedLanguagesList.add(LanguageSupport("Icelandic", "is", true))
    supportedLanguagesList.add(LanguageSupport("Igbo", "ig", false))
    supportedLanguagesList.add(LanguageSupport("Indonesian", "id", true))
    supportedLanguagesList.add(LanguageSupport("Inuinnaqtun", "ikt", false))
    supportedLanguagesList.add(LanguageSupport("Inuktitut", "iu", false))
    supportedLanguagesList.add(LanguageSupport("Inuktitut (Latin)", "iu-Latn", false))
    supportedLanguagesList.add(LanguageSupport("Irish", "ga", false))
    supportedLanguagesList.add(LanguageSupport("Italian", "it", true))
    supportedLanguagesList.add(LanguageSupport("Japanese", "ja", true))
    supportedLanguagesList.add(LanguageSupport("Kannada", "kn", false))
    supportedLanguagesList.add(LanguageSupport("Kashmiri", "ks", false))
    supportedLanguagesList.add(LanguageSupport("Kazakh", "kk", false))
    supportedLanguagesList.add(LanguageSupport("Khmer", "km", false))
    supportedLanguagesList.add(LanguageSupport("Kinyarwanda", "rw", false))
    supportedLanguagesList.add(LanguageSupport("Klingon", "tlh-Latn", true))
    supportedLanguagesList.add(LanguageSupport("Klingon (plqaD)", "tlh-Piqd", false))
    supportedLanguagesList.add(LanguageSupport("Konkani", "gom", false))
    supportedLanguagesList.add(LanguageSupport("Korean", "ko", true))
    supportedLanguagesList.add(LanguageSupport("Kurdish (Central)", "ku", false))
    supportedLanguagesList.add(LanguageSupport("Kurdish (Northern)", "kmr", false))
    supportedLanguagesList.add(LanguageSupport("Kyrgyz (Cyrillic)", "ky", false))
    supportedLanguagesList.add(LanguageSupport("Lao", "lo", false))
    supportedLanguagesList.add(LanguageSupport("Latvian", "lv", true))
    supportedLanguagesList.add(LanguageSupport("Lithuanian", "lt", true))
    supportedLanguagesList.add(LanguageSupport("Lingala", "ln", false))
    supportedLanguagesList.add(LanguageSupport("Lower Sorbian", "dsb", false))
    supportedLanguagesList.add(LanguageSupport("Luganda", "lug", false))
    supportedLanguagesList.add(LanguageSupport("Macedonian", "mk", false))
    supportedLanguagesList.add(LanguageSupport("Maithili", "mai", false))
    supportedLanguagesList.add(LanguageSupport("Malagasy", "mg", false))
    supportedLanguagesList.add(LanguageSupport("Malay (Latin)", "ms", true))
    supportedLanguagesList.add(LanguageSupport("Malayalam", "ml", false))
    supportedLanguagesList.add(LanguageSupport("Maltese", "mt", true))
    supportedLanguagesList.add(LanguageSupport("Maori", "mi", false))
    supportedLanguagesList.add(LanguageSupport("Marathi", "mr", false))
    supportedLanguagesList.add(LanguageSupport("Mongolian (Cyrillic)", "mn-Cyrl", false))
    supportedLanguagesList.add(LanguageSupport("Mongolian (Traditional)", "mn-Mong", false))
    supportedLanguagesList.add(LanguageSupport("Myanmar", "my", false))
    supportedLanguagesList.add(LanguageSupport("Nepali", "ne", false))
    supportedLanguagesList.add(LanguageSupport("Norwegian", "nb", true))
    supportedLanguagesList.add(LanguageSupport("Nyanja", "nya", false))
    supportedLanguagesList.add(LanguageSupport("Odia", "or", false))
    supportedLanguagesList.add(LanguageSupport("Pashto", "ps", false))
    supportedLanguagesList.add(LanguageSupport("Persian", "fa", true))
    supportedLanguagesList.add(LanguageSupport("Polish", "pl", true))
    supportedLanguagesList.add(LanguageSupport("Portuguese (Brazil)", "pt", true))
    supportedLanguagesList.add(LanguageSupport("Portuguese (Portugal)", "pt-pt", false))
    supportedLanguagesList.add(LanguageSupport("Punjabi", "pa", false))
    supportedLanguagesList.add(LanguageSupport("Queretaro Otomi", "otq", false))
    supportedLanguagesList.add(LanguageSupport("Romanian", "ro", true))
    supportedLanguagesList.add(LanguageSupport("Rundi", "run", false))
    supportedLanguagesList.add(LanguageSupport("Russian", "ru", true))
    supportedLanguagesList.add(LanguageSupport("Samoan (Latin)", "sm", false))
    supportedLanguagesList.add(LanguageSupport("Serbian (Cyrillic)", "sr-Cyrl", false))
    supportedLanguagesList.add(LanguageSupport("Serbian (Latin)", "sr-Latn", true))
    supportedLanguagesList.add(LanguageSupport("Sesotho", "st", false))
    supportedLanguagesList.add(LanguageSupport("Sesotho sa Leboa", "nso", false))
    supportedLanguagesList.add(LanguageSupport("Setswana", "tn", false))
    supportedLanguagesList.add(LanguageSupport("Sindhi", "sd", false))
    supportedLanguagesList.add(LanguageSupport("Sinhala", "si", false))
    supportedLanguagesList.add(LanguageSupport("Slovak", "sk", true))
    supportedLanguagesList.add(LanguageSupport("Slovenian", "sl", true))
    supportedLanguagesList.add(LanguageSupport("Somali (Arabic)", "so", false))
    supportedLanguagesList.add(LanguageSupport("Spanish", "es", true))
    supportedLanguagesList.add(LanguageSupport("Swahili (Latin)", "sw", true))
    supportedLanguagesList.add(LanguageSupport("Swedish", "sv", true))
    supportedLanguagesList.add(LanguageSupport("Tahitian", "ty", false))
    supportedLanguagesList.add(LanguageSupport("Tamil", "ta", true))
    supportedLanguagesList.add(LanguageSupport("Tatar (Latin)", "tt", false))
    supportedLanguagesList.add(LanguageSupport("Telugu", "te", false))
    supportedLanguagesList.add(LanguageSupport("Thai", "th", true))
    supportedLanguagesList.add(LanguageSupport("Tibetan", "bo", false))
    supportedLanguagesList.add(LanguageSupport("Tigrinya", "ti", false))
    supportedLanguagesList.add(LanguageSupport("Tongan", "to", false))
    supportedLanguagesList.add(LanguageSupport("Turkish", "tr", true))
    supportedLanguagesList.add(LanguageSupport("Turkmen (Latin)", "tk", false))
    supportedLanguagesList.add(LanguageSupport("Ukrainian", "uk", true))
    supportedLanguagesList.add(LanguageSupport("Upper Sorbian", "hsb", false))
    supportedLanguagesList.add(LanguageSupport("Urdu", "ur", true))
    supportedLanguagesList.add(LanguageSupport("Uyghur (Arabic)", "ug", false))
    supportedLanguagesList.add(LanguageSupport("Uzbek (Latin)", "uz", false))
    supportedLanguagesList.add(LanguageSupport("Vietnamese", "vi", true))
    supportedLanguagesList.add(LanguageSupport("Welsh", "cy", true))
    supportedLanguagesList.add(LanguageSupport("Xhosa", "xh", false))
    supportedLanguagesList.add(LanguageSupport("Yoruba", "yo", false))
    supportedLanguagesList.add(LanguageSupport("Yucatec Maya", "yua", false))
    supportedLanguagesList.add(LanguageSupport("Zulu", "zu", false))

    return supportedLanguagesList
}

fun getIsoFromLanguage(language: String): String?{
    return getSupportedLanguageList().find { it.language == language }?.iso
}

fun getDictionaryBooleanFromLanguage(language: String): Boolean{
    return getSupportedLanguageList().find { it.language == language }?.hasDictionarySupport ?: false
}

fun getLanguages(): List<String> {
    val languageList = ArrayList<String>()
    for(supportedLanguages in getSupportedLanguageList()){
        languageList.add(supportedLanguages.language)
    }
    return languageList
}
