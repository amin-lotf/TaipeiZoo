package com.example.taipeizooinfo.repository.util

import android.net.Uri
import java.net.URI


fun String.convertToHttps():String?{
    return try {
        val uri=URI(this)
        var link="https://${uri.host}${uri.path}"
        uri.query?.let {
            link += "?${uri.query}"
        }
        link

    }catch (t:Throwable){
        null
    }

}

