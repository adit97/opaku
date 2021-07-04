package com.opaku.id.core.data.source.local.session

interface ISessionManager {
    var isLogin: Boolean
    var userId: Long
    fun clear()
}