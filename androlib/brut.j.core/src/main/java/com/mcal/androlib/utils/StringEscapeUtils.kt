package com.mcal.androlib.utils

object StringEscapeUtils {
    @JvmStatic
    fun unescapeJava(str: String?): String? {
        if (str == null) {
            return null
        }

        val builder = StringBuilder()
        var hadSlash = false

        for (element in str) {
            if (hadSlash) {
                when (element) {
                    'n' -> builder.append('\n')
                    't' -> builder.append('\t')
                    'b' -> builder.append('\b')
                    'r' -> builder.append('\r')
                    'f' -> builder.append('\u000c')
                    '\'' -> builder.append('\'')
                    '\"' -> builder.append('\"')
                    '\\' -> builder.append('\\')
                    else -> builder.append('\\').append(element)
                }
                hadSlash = false
            } else {
                if (element == '\\') {
                    hadSlash = true
                } else {
                    builder.append(element)
                }
            }
        }

        if (hadSlash) {
            builder.append('\\')
        }

        return builder.toString()
    }
}
