package com.leqi.baselib.ext

import com.blankj.utilcode.util.ToastUtils


/**
 * Unicode中文解码
 */
fun String.decodeUnicode(): String {
    var aChar: Char
    val len = this.length
    val outBuffer = StringBuffer(len)
    var x = 0
    while (x < len) {
        aChar = this[x++]
        if (aChar == '\\') {
            aChar = this[x++]
            if (aChar == 'u') {
                // Read the xxxx
                var value = 0
                for (i in 0..3) {
                    aChar = this[x++]
                    value = when (aChar) {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> (value shl 4) + aChar.toInt() - '0'.toInt()
                        'a', 'b', 'c', 'd', 'e', 'f' -> (value shl 4) + 10 + aChar.toInt() - 'a'.toInt()
                        'A', 'B', 'C', 'D', 'E', 'F' -> (value shl 4) + 10 + aChar.toInt() - 'A'.toInt()
                        else -> throw IllegalArgumentException("Malformed   \\uxxxx   encoding.")
                    }
                }
                outBuffer.append(value.toChar())
            } else {
                when (aChar) {
                    't' -> aChar = '\t'
                    'r' -> aChar = '\r'
                    'n' -> aChar = '\n'
                }
                outBuffer.append(aChar)
            }
        } else outBuffer.append(aChar)
    }
    return outBuffer.toString()
}


/**
 * toast提示
 */
fun String.toast() {
    ToastUtils.showShort(this)
}
