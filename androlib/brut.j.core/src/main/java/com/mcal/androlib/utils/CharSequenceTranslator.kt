package com.mcal.androlib.utils

object CharSequenceTranslator {
    /**
     * Returns an upper case hexadecimal `String` for the given
     * character.
     *
     * @param codePoint The code point to convert.
     * @return An upper case hexadecimal `String`
     */
    @JvmStatic
    fun hex(codePoint: Int): String {
        return Integer.toHexString(codePoint).uppercase()
    }
}
