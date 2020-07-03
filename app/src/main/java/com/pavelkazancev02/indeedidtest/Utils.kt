package com.pavelkazancev02.indeedidtest

import android.app.Activity
import android.content.Context

object Utils {
    fun saveStringInSP(context: Context, key: String, value: String){
        val editor = context.getSharedPreferences("SP", Activity.MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringFromSP(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences("SP", Activity.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }
}