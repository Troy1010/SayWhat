package com.example.saywhat.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


internal class YouTubeIDParserTest {
    @ParameterizedTest
    @MethodSource("parseParams")
    fun parse(givenString: String, expectedString: String?, expectedThrowable: Throwable?) {
        // # Given
        val youTubeIDParser = YouTubeIDParser()
        // # When
        val result = runCatching { youTubeIDParser.parse(givenString) }.getOrElse { it }
        // # Then
        expectedString?.also { assertEquals(expectedString, result) }
        expectedThrowable?.also { assert(expectedThrowable.javaClass.isInstance(result)) }
    }

    companion object {
        @JvmStatic
        fun parseParams(): Stream<Arguments?>? {
            return Stream.of(
                arguments("https://www.youtube.com/watch?v=uEUMVwc4o5U&ab_channel=BobRoss", "uEUMVwc4o5U", null),
                arguments("S0Q4gqBUs7c", "S0Q4gqBUs7c", null),
                arguments("oh no!", null, Exception()),
            )
        }
    }
}