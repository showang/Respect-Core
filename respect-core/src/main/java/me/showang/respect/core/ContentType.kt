package me.showang.respect.core

interface ContentType {
    companion object {
        const val JSON = "application/json"
        const val FORM_URL_ENCODED = "application/x-www-form-urlencoded"
        const val NONE = ""
    }

}