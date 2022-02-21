package com.example.saywhat.app

import android.app.Application
import com.example.saywhat.all.extensions.easyEmit
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.rx3.asFlow
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class SpeechToTextHelper @Inject constructor(private val application: Application) {
    private val model =
        Single.create<Model> { downstream ->
            StorageService.unpack(
                application,
                "model-en-us",
                "model",
                { downstream.onSuccess(it) },
                { downstream.onError(it) },
            )
        }
            .toObservable().asFlow()
    private val micSpeechService = model.map { SpeechService(Recognizer(it, 16000.0f), 16000.0f) }

    sealed class SpeechToTextResult {
        data class Partial(val x: String) : SpeechToTextResult()
        data class Chunk(val x: String) : SpeechToTextResult()
    }

    object End

    /**
     * [sampleRate] example: 16000f
     * [inputStream] example: application.assets.open("10001-90210-01803.wav")
     */
    fun speechToText(inputStream: InputStream, sampleRate: Float) =
        model
            .map {
                SpeechStreamService(
                    Recognizer(it, sampleRate),
                    inputStream.also { if (it.skip(44) != 44L) throw IOException("File too short") },
                    sampleRate
                )
            }
            .flatMapMerge {
                val results = MutableSharedFlow<Any>()
                it.start(object : RecognitionListener {
                    override fun onPartialResult(hypothesis: String) {
                        results.easyEmit(SpeechToTextResult.Partial(hypothesis))
                    }

                    override fun onResult(hypothesis: String) {
                        results.easyEmit(SpeechToTextResult.Chunk(hypothesis))
                    }

                    override fun onFinalResult(hypothesis: String) {
                        results.easyEmit(SpeechToTextResult.Chunk(hypothesis))
                        results.easyEmit(End)
                    }

                    override fun onError(exception: Exception) {
                        throw exception
                    }

                    override fun onTimeout() {
                        throw TimeoutException()
                    }
                })
                results
            }
            .takeWhile { it is SpeechToTextResult }.map { it as SpeechToTextResult }
}