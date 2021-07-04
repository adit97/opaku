package com.opaku.id.core.data.source.local.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) : ISessionManager {

    companion object {
        const val KEY_IS_LOGIN = "is_login"
        const val KEY_USER_ID = "user_id"
    }

    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences("app-pref", Context.MODE_PRIVATE)
    }

    private val editor: SharedPreferences.Editor by lazy {
        preference.edit()
    }

    override fun clear() {
        editor.clear().apply()
    }

    override var isLogin: Boolean
        get() = preference.getBoolean(KEY_IS_LOGIN, false)
        set(value) = editor.putBoolean(KEY_IS_LOGIN, value).apply()

    override var userId: Long
        get() = preference.getLong(KEY_USER_ID, 0L)
        set(value) = editor.putLong(KEY_USER_ID, value).apply()

}