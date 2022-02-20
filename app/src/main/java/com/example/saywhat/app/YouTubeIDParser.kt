package com.example.saywhat.app

import io.reactivex.rxjava3.exceptions.CompositeException
import javax.inject.Inject


class YouTubeIDParser @Inject constructor() {
    fun parse(s: String): String {
        return runCatching { Regex("v=(.{11})").find(s)!!.groupValues[1] }.getOrElse { error1 ->
            runCatching { Regex("^(.{11})$").find(s)!!.groupValues[1] }.getOrElse { error2 ->
                throw YouTubeIDParserException(CompositeException(error1, error2))
            }
        }
    }
}