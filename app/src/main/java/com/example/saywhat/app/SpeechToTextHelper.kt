package com.example.saywhat.app

import android.app.Application
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
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
    private val micSpeechService = model.map { SpeechService(Recognizer(it, 16000.0f), 16000.0f) }

    sealed class SpeechToTextResult {
        data class Partial(val x: String) : SpeechToTextResult()
        data class Chunk(val x: String) : SpeechToTextResult()
    }

    /**
     * [sampleRate] example: 16000f
     * [inputStream] example: application.assets.open("10001-90210-01803.wav")
     */
    fun speechToText(inputStream: InputStream, sampleRate: Float): Observable<SpeechToTextResult> {
        return model.map {
            if (inputStream.skip(44) != 44L) throw IOException("File too short")
            SpeechStreamService(Recognizer(it, sampleRate), inputStream, sampleRate)
        }.flatMapObservable {
            val results = PublishSubject.create<SpeechToTextResult>()
            it.start(object : RecognitionListener {
                override fun onPartialResult(hypothesis: String) {
                    results.onNext(SpeechToTextResult.Partial(hypothesis))
                }

                override fun onResult(hypothesis: String) {
                    results.onNext(SpeechToTextResult.Chunk(hypothesis))
                }

                override fun onFinalResult(hypothesis: String) {
                    results.onNext(SpeechToTextResult.Chunk(hypothesis))
                    results.onComplete()
                }

                override fun onError(exception: Exception) {
                    results.onError(exception)
                }

                override fun onTimeout() {
                    results.onError(TimeoutException())
                }
            })
            results
        }
    }
}