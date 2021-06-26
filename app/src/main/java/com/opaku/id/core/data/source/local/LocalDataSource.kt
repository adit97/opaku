package com.opaku.id.core.data.source.local

import com.opaku.id.core.data.source.local.room.AppDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val appDao: AppDao) {

}