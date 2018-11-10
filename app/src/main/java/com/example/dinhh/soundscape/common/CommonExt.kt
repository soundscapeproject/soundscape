package com.example.dinhh.soundscape.common

import android.util.Log

fun Any.logV(log: String) = Log.v(this::class.java.simpleName, log)

fun Any.logE(log: String) = Log.e(this::class.java.simpleName, log)

fun Any.logD(log: String) = Log.d(this::class.java.simpleName, log)

fun Any.logI(log: String) = Log.i(this::class.java.simpleName, log)

fun Any.logW(log: String) = Log.w(this::class.java.simpleName, log)