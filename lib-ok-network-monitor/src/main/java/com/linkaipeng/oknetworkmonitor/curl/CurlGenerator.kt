package com.linkaipeng.oknetworkmonitor.curl

import okhttp3.Request
import okio.Buffer
import java.nio.charset.Charset

class CurlGenerator {

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        fun generateCurl(request: Request): String {
            var compressed = false

            var curlCmd = "curl"
            curlCmd += " -X " + request.method

            val headers = request.headers
            for (i in 0 until headers.size) {
                val name = headers.name(i)
                var value = headers.value(i)

                val start = 0
                val end = value.length - 1
                if (value[start] == '"' && value[end] == '"') {
                    value = "\\\"" + value.substring(1, end) + "\\\""
                }

                if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(value, ignoreCase = true)) {
                    compressed = true
                }
                curlCmd += " -H \"$name: $value\""
            }

            request.body?.let {
                val contentType = it.contentType()
                contentType?.let { contentType -> curlCmd += " -H Content-Type: $contentType" }
                val buffer = Buffer().apply { it.writeTo(this) }
                val charset = contentType?.charset(UTF8) ?: UTF8
                // try to keep to a single line and use a subshell to preserve any line breaks
                curlCmd += " --data $'" + buffer.readString(charset).replace("\n", "\n") + "'"
            }

            curlCmd += (if (compressed) " --compressed " else " ") + "\"${request.url}\""

            return curlCmd
        }
    }
}