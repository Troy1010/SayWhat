package com.example.saywhat.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.saywhat.application
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
internal class SpeechToTextHelperTest {
    private val speechToTextHelper by lazy { SpeechToTextHelper(application) }

    @Test
    fun speechToText() {
        // # Given
        speechToTextHelper.speechToText(
            inputStream = application.assets.open("10001-90210-01803.wav"),
            sampleRate = 16000f,
        )
            .doOnNext { if (it is SpeechToTextHelper.SpeechToTextResult.Chunk) logz("result:$it") }
            // # When
            .test()
            .apply { await(30, TimeUnit.SECONDS) }
            // # Then
            .assertComplete()
    }
}