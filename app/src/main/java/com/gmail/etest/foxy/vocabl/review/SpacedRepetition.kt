package com.gmail.etest.foxy.vocabl.review

import kotlin.math.roundToInt

data class SpacedRepetition(var quality: Int, var repetitions: Int, var previousInterval: Int, var previousEaseFactor: Double)

fun spacedRepetitionAlgorithm(spacedRepetition: SpacedRepetition): SpacedRepetition {

    val interval: Int
    var easeFactor: Double

    when (spacedRepetition.quality) {
        5 -> { // quality = 5
            easeFactor =
                spacedRepetition.previousEaseFactor + 0.1 * 0.08

            interval = when (spacedRepetition.repetitions) {
                0 -> 3
                1 -> (2.3 * spacedRepetition.previousEaseFactor).roundToInt()
                else -> (spacedRepetition.previousInterval * easeFactor).roundToInt()
            }

            spacedRepetition.repetitions++
        }
        3 -> {// quality = 3
            easeFactor =
                spacedRepetition.previousEaseFactor + (0.1 - (5 - spacedRepetition.quality) * (0.08 + (5 - spacedRepetition.quality) * 0.02))

            interval = when (spacedRepetition.repetitions) {
                0 -> 2
                1 -> (1.8 * spacedRepetition.previousEaseFactor).roundToInt()
                else -> (spacedRepetition.previousInterval * easeFactor * 0.7).roundToInt()
            }

            spacedRepetition.repetitions++
        }
        1 -> {// quality = 1
            easeFactor =
                spacedRepetition.previousEaseFactor + (0.1 - (5 - spacedRepetition.quality) * (0.08 + (5 - spacedRepetition.quality) * 0.02))

            interval = when (spacedRepetition.repetitions) {
                0 -> 1
                1 -> spacedRepetition.previousInterval
                else -> (spacedRepetition.previousInterval * easeFactor * 0.3).roundToInt()
            }

            spacedRepetition.repetitions /= 2
        }
        else -> {// quality = 0
            interval = 1
            easeFactor = spacedRepetition.previousEaseFactor
        }
    }

    if (easeFactor < 1.3) {
        easeFactor = 1.3
    }

    spacedRepetition.previousInterval = interval
    spacedRepetition.previousEaseFactor = easeFactor

    return spacedRepetition
}
