package com.gmail.etest.foxy.vocabl.api

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


data class BackTranslations(
    val normalizedText: String,
    val displayText: String,
    val numExamples: Int,
    val frequencyCount: Int
)


data class Translations(
    val normalizedTarget: String,
    val displayTarget: String,
    val posTag: String,
    val confidence: Double,
    val prefixWord: String,
    val backTranslations: List<BackTranslations>
)

data class ApiDictionaryResponse(
    val normalizedSource: String,
    val displaySource: String,
    val translations: List<Translations>
)

data class NormalTranslation(
    val text: String,
    val to: String
)

data class ApiTranslationResponse(
    val translations: List<NormalTranslation>
)

class TranslatorAPI {

    private val key = "d55c08e62cbd48f5a62e93beb79be736"
    private val location = "westeurope"

    private val client = OkHttpClient()

    fun translatedTextToDictionaryPost(text: String, isoFrom: String, isoTo: String, hasDictionarySupport: Boolean): Any? {

        try {
            var url = ""

            val mediaType = "application/json".toMediaTypeOrNull()
            val body = "[{\"Text\": \"$text\"}]".toRequestBody(mediaType)


            if(hasDictionarySupport){
                url = "https://api.cognitive.microsofttranslator.com/dictionary/lookup" +
                        "?api-version=3.0&from=$isoFrom&to=$isoTo"

                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", key)
                    .addHeader("Ocp-Apim-Subscription-Region", location)
                    .addHeader("Content-type", "application/json")
                    .build()

                val response = client.newCall(request).execute()

                val jsonData = response.body?.string()
                val gson = GsonBuilder().create()

                response.close()

                val typeToken = object : TypeToken<List<ApiDictionaryResponse>>() {}.type
                return gson.fromJson<List<ApiDictionaryResponse>>(jsonData, typeToken)[0]

            } else {

                url = "https://api.cognitive.microsofttranslator.com/translate" +
                        "?api-version=3.0&from=$isoFrom&to=$isoTo"

                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", key)
                    .addHeader("Ocp-Apim-Subscription-Region", location)
                    .addHeader("Content-type", "application/json")
                    .build()

                val response = client.newCall(request).execute()

                val jsonData = response.body?.string()
                val gson = GsonBuilder().create()

                response.close()

                val typeToken = object : TypeToken<List<ApiTranslationResponse>>() {}.type
                return gson.fromJson<List<ApiTranslationResponse>>(jsonData, typeToken)[0]
            }

        } catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }
}

/*
[
    {
        "normalizedSource": "fly",
        "displaySource": "fly",
        "translations": [
            {
                "normalizedTarget": "volar",
                "displayTarget": "volar",
                "posTag": "VERB",
                "confidence": 0.4081,
                "prefixWord": "",
                "backTranslations": [
                    {
                        "normalizedText": "fly",
                        "displayText": "fly",
                        "numExamples": 15,
                        "frequencyCount": 4637
                    },
                    {
                        "normalizedText": "flying",
                        "displayText": "flying",
                        "numExamples": 15,
                        "frequencyCount": 1365
                    },
                    {
                        "normalizedText": "blow",
                        "displayText": "blow",
                        "numExamples": 15,
                        "frequencyCount": 503
                    },
                    {
                        "normalizedText": "flight",
                        "displayText": "flight",
                        "numExamples": 15,
                        "frequencyCount": 135
                    }
                ]
            },
            {
                "normalizedTarget": "mosca",
                "displayTarget": "mosca",
                "posTag": "NOUN",
                "confidence": 0.2668,
                "prefixWord": "",
                "backTranslations": [
                    {
                        "normalizedText": "fly",
                        "displayText": "fly",
                        "numExamples": 15,
                        "frequencyCount": 1697
                    },
                    {
                        "normalizedText": "flyweight",
                        "displayText": "flyweight",
                        "numExamples": 0,
                        "frequencyCount": 48
                    },
                    {
                        "normalizedText": "flies",
                        "displayText": "flies",
                        "numExamples": 9,
                        "frequencyCount": 34
                    }
                ]
            },
            {
                "normalizedTarget": "operan",
                "displayTarget": "operan",
                "posTag": "VERB",
                "confidence": 0.1144,
                "prefixWord": "",
                "backTranslations": [
                    {
                        "normalizedText": "operate",
                        "displayText": "operate",
                        "numExamples": 15,
                        "frequencyCount": 1344
                    },
                    {
                        "normalizedText": "fly",
                        "displayText": "fly",
                        "numExamples": 1,
                        "frequencyCount": 422
                    }
                ]
            },
            {
                "normalizedTarget": "pilotar",
                "displayTarget": "pilotar",
                "posTag": "VERB",
                "confidence": 0.095,
                "prefixWord": "",
                "backTranslations": [
                    {
                        "normalizedText": "fly",
                        "displayText": "fly",
                        "numExamples": 15,
                        "frequencyCount": 104
                    },
                    {
                        "normalizedText": "pilot",
                        "displayText": "pilot",
                        "numExamples": 15,
                        "frequencyCount": 61
                    }
                ]
            },
            {
                "normalizedTarget": "moscas",
                "displayTarget": "moscas",
                "posTag": "VERB",
                "confidence": 0.0644,
                "prefixWord": "",
                "backTranslations": [
                    {
                        "normalizedText": "flies",
                        "displayText": "flies",
                        "numExamples": 15,
                        "frequencyCount": 1219
                    },
                    {
                        "normalizedText": "fly",
                        "displayText": "fly",
                        "numExamples": 15,
                        "frequencyCount": 143
                    }
                ]
            },
            {
                "normalizedTarget": "marcha",
                "displayTarget": "marcha",
                "posTag": "NOUN",
                "confidence": 0.0514,
                "prefixWord": "",
                "backTranslations": [
                    {
                        "normalizedText": "march",
                        "displayText": "March",
                        "numExamples": 15,
                        "frequencyCount": 5355
                    },
                    {
                        "normalizedText": "up",
                        "displayText": "up",
                        "numExamples": 15,
                        "frequencyCount": 1277
                    },
                    {
                        "normalizedText": "running",
                        "displayText": "running",
                        "numExamples": 15,
                        "frequencyCount": 752
                    },
                    {
                        "normalizedText": "going",
                        "displayText": "going",
                        "numExamples": 15,
                        "frequencyCount": 570
                    },
                    {
                        "normalizedText": "fly",
                        "displayText": "fly",
                        "numExamples": 15,
                        "frequencyCount": 253
                    }
                ]
            }
        ]
    }
]


*/