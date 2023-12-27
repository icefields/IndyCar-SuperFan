package org.hungrytessy.indycarsuperfan.common

import android.util.Log

const val TAG_LOG = "lucie"

class Logger(private vararg val messages: Any?) {
    init {
        invoke()
    }

    operator fun invoke() {
        val sb = StringBuilder()
        messages.forEach {
            sb.append("$it")
            sb.append(" **** ")
        }
        Log.d(TAG_LOG, sb.toString())
    }
}

typealias L = Logger
