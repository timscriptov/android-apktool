package com.mcal.common.tasks

import java.util.regex.Matcher
import java.util.regex.Pattern

object LogHelper {
    private val DIAGNOSTIC_PATTERN: Pattern = Pattern.compile("(.*?):(\\d+): (.*?): (.+)")
    private val DIAGNOSTIC_PATTERN_NO_LINE: Pattern = Pattern.compile("(.*?): (.*?)" + ":" + " (.+)")

    fun formatLog(error: String): String {
        val lines = error.split("\n").toTypedArray()
        for (line in lines) {
            if (line.isEmpty()) {
                continue
            }
            val matcher: Matcher = DIAGNOSTIC_PATTERN.matcher(line)
            val m: Matcher = DIAGNOSTIC_PATTERN_NO_LINE.matcher(line)
            val path: String
            val lineNumber: String
//            val level: String
            val message: String
            if (matcher.find()) {
                path = matcher.group(1) as String
                lineNumber = matcher.group(2) as String
//                level = matcher.group(3) as String
                message = matcher.group(4) as String
            } else if (m.find()) {
                path = m.group(1) as String
                lineNumber = "-1"
//                level = m.group(2) as String
                message = m.group(3) as String
            } else {
//                val trim = line.trim()
//                level = if (trim.startsWith("error")) {
//                    "Error"
//                } else {
//                    "Info"
//                }
                path = ""
                lineNumber = "-1"
                message = line
            }
            return "Source: $path;Line: $lineNumber;Message: $message"
        }
        return "Unknown error"
    }
}