package com.example.saywhat

import androidx.test.platform.app.InstrumentationRegistry

val application by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App }
