package com.mobile.submissionperintis.helper

import android.content.Context
import com.chibatching.kotpref.KotprefModel

class LocalCache(context: Context) : KotprefModel(context) {
    var email by nullableStringPref()
    var phoneNumber by nullableStringPref()
    var fullName by nullableStringPref()
    var image by nullableStringPref()
}